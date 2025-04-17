package integracion.factura;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;

import exceptions.*;
import integracion.factoria.FactoriaAbstractaIntegracion;
import misc.*;
import negocio.factura.TFactura;
import negocio.factura.TLineaFactura;

public class DAOFacturaImp implements DAOFactura {

	@Override
	public int create(TFactura tFactura) throws BBDDReadException, BBDDWriteException {
		JSONObject bdFactura;
		bdFactura = OpsBBDD.read(Messages.BDFac);
	
		JSONObject facturas = new JSONObject(bdFactura.get(Messages.KEY_facs));
		
		//Aumentamos el último índice de la bd
		int newId = bdFactura.getInt(Messages.KEY_lastId) + 1;
		bdFactura.put(Messages.KEY_lastId, newId);
		
		//Creamos la nueva factura
		JSONObject nuevaFactura = new JSONObject();
		nuevaFactura.put(Messages.KEY_act, tFactura.getActivo());
		nuevaFactura.put(Messages.KEY_idCli, tFactura.getIdCliente());
		nuevaFactura.put(Messages.KEY_idTaq, tFactura.getIdTaquillero());
		nuevaFactura.put(Messages.KEY_fecha, tFactura.getFecha().toString());
		
		DAOLineaFactura daoLineaFactura = FactoriaAbstractaIntegracion.getInstance().crearDAOLineaFactura();
		JSONArray carrito = new JSONArray();
		
		for (TLineaFactura tLineaFactura : tFactura.getCarrito()) {
			//Damos el id de la factura a sus líneas
			tLineaFactura.setIdFactura(newId);
			int idLineaFactura = daoLineaFactura.create(tLineaFactura);
			carrito.put(idLineaFactura);
		}
		nuevaFactura.put(Messages.KEY_carrito, carrito);
		nuevaFactura.put(Messages.KEY_importe, tFactura.getImporte());
		
		//La insertamos en la bd de facturas, su clave es su id
		facturas.put(Integer.toString(newId), nuevaFactura);
		OpsBBDD.write(bdFactura, Messages.BDFac);
		
		return newId;
	}

	@Override
	public int delete(int id) throws BBDDReadException, BBDDWriteException {
		JSONObject bdFactura = OpsBBDD.read(Messages.BDFac);
		JSONObject facs = bdFactura.getJSONObject(Messages.KEY_facs);
        
		if (facs.has(Integer.toString(id))) {
	        JSONObject fac = facs.getJSONObject(Integer.toString(id));
	        fac.put(Messages.KEY_act, false);
	        OpsBBDD.write(bdFactura, Messages.BDFac);
	        return id;
		}
        return -1; //No se ha encontrado la factura con dicho id
	}

	@Override
	public TFactura read(int id) throws BBDDReadException {
		JSONObject facs = OpsBBDD.read(Messages.BDFac).getJSONObject(Messages.KEY_facs);
		
		TFactura tFacRead = null;
		if (facs.has(Integer.toString(id))) {
			JSONObject fac = facs.getJSONObject(Integer.toString(id));
			tFacRead = readAux(fac);
			tFacRead.setIdFactura(id);
		}
		
		return tFacRead;
	}

	@Override
	public Collection<TFactura> readAll() throws BBDDReadException {
		JSONObject bdFactura = OpsBBDD.read(Messages.BDFac);
		
		Collection<TFactura> facturasValidas = new ArrayList<>();
		
		JSONObject facturas = new JSONObject(bdFactura.getJSONArray(Messages.KEY_facs));
		
		Set<String> allIdsFacs = facturas.keySet();
		for (String idFactura : allIdsFacs) {
			JSONObject factura = facturas.getJSONObject(idFactura);
			
			//Cojo solo las facturas activas
			if (factura.getBoolean(Messages.KEY_act)) {
				TFactura tFacVal = this.readAux(factura);
				tFacVal.setIdFactura(Integer.valueOf(idFactura)); //No va a dar excepción porque a la BD llegan solo id con números
				facturasValidas.add(tFacVal);
			}
		}
	
		return facturasValidas;
	}

	@Override
	public int update(TFactura tFactura) throws BBDDReadException, BBDDWriteException {
		JSONObject bdFactura = OpsBBDD.read(Messages.BDFac);
		JSONObject facs = bdFactura.getJSONObject(Messages.KEY_facs);
		
		Set<String> allIdsFacs = facs.keySet();
		for (String idFactura : allIdsFacs) {
            if (Integer.valueOf(idFactura) == tFactura.getIdFactura()) {
            	JSONObject fac = facs.getJSONObject(idFactura);
            	fac.put(Messages.KEY_act, tFactura.getActivo());
            	fac.put(Messages.KEY_idCli, tFactura.getIdCliente());
            	fac.put(Messages.KEY_idTaq, tFactura.getIdTaquillero());
            	fac.put(Messages.KEY_fecha, tFactura.getFecha().toString());
        	
        		JSONArray carrito = new JSONArray();
        		
        		DAOLineaFactura daoLineaFactura = FactoriaAbstractaIntegracion.getInstance().crearDAOLineaFactura();
        		for (TLineaFactura tLineaFactura : tFactura.getCarrito()) {
        			carrito.put(tLineaFactura.getIdLineaFactura());
        			daoLineaFactura.update(tLineaFactura);
        		}
        		fac.put(Messages.KEY_carrito, carrito);
        		fac.put(Messages.KEY_importe, tFactura.getImporte());
            	
            	OpsBBDD.write(bdFactura, Messages.BDFac);
                return Integer.valueOf(idFactura);
            }
        }
        return -1; //No se ha encontrado la factura pasada por parámetro
	}
	
	
	private TFactura readAux(JSONObject jsonFac) throws BBDDReadException {
		Collection<TLineaFactura> carrito = new ArrayList<TLineaFactura>();
		DAOLineaFactura daoLineaFactura = FactoriaAbstractaIntegracion.getInstance().crearDAOLineaFactura();
		
		JSONArray lineas = jsonFac.getJSONArray(Messages.KEY_carrito);
		for (int i = 0; i < lineas.length(); i++) {
			int idLineaFactura = lineas.getInt(i);
			TLineaFactura tLineaFactura = daoLineaFactura.read(idLineaFactura);
			carrito.add(tLineaFactura);
		}
		
		return new TFactura(
				jsonFac.getInt(Messages.KEY_idCli), 
				jsonFac.getInt(Messages.KEY_idTaq),
				jsonFac.getBoolean(Messages.KEY_act),
				LocalDateTime.parse(jsonFac.getString(Messages.KEY_fecha)),
				carrito,
				jsonFac.getInt(Messages.KEY_importe));
	}
}
