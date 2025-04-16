package integracion.factura;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

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
	
		//Creamos factura nueva que entrará en la BDFactura
		JSONObject nuevaFactura = new JSONObject();
		
		nuevaFactura.put(Messages.KEY_act, tFactura.getActivo());
		nuevaFactura.put(Messages.KEY_idCli, tFactura.getIdCliente());
		nuevaFactura.put(Messages.KEY_idTaq, tFactura.getIdTaquillero());
		nuevaFactura.put(Messages.KEY_fecha, tFactura.getFecha().toString());
		
		DAOLineaFactura daoLineaFactura = FactoriaAbstractaIntegracion.getInstance().crearDAOLineaFactura();
		JSONArray carrito = new JSONArray();
		for (TLineaFactura tLineaFactura : tFactura.getCarrito()) {
			int idLineaFactura = daoLineaFactura.create(tLineaFactura);
			carrito.put(idLineaFactura);
		}
		nuevaFactura.put(Messages.KEY_carrito, carrito);
		nuevaFactura.put(Messages.KEY_importe, tFactura.getImporte());
		
		
		//Aumentamos el último índice e insertamos el nuevo TFactura
		int newId = bdFactura.getInt(Messages.KEY_lastId) + 1;
		bdFactura.put(Messages.KEY_lastId, newId);
		facturas.put(Integer.toString(newId), nuevaFactura);
		bdFactura.put(Messages.KEY_facs, facturas);
		
		OpsBBDD.write(bdFactura, Messages.BDFac);
		
		return newId;
	}

	@Override
	public int delete(int id) throws BBDDReadException, BBDDWriteException {
		JSONObject bdFactura = OpsBBDD.read(Messages.BDFac);
		JSONObject facs = bdFactura.getJSONObject(Messages.KEY_facs);
        
        JSONObject fac = facs.getJSONObject(Integer.toString(id));
        
        if (fac != null) {
        	fac.put(Messages.KEY_act, false);
        	return id;
        }
        return -1; //No se ha encontrado la factura pasada por parámetro
	}

	@Override
	public TFactura read(int id) throws BBDDReadException {
		JSONObject facturas = OpsBBDD.read(Messages.BDFac).getJSONObject(Messages.KEY_facs);
		
		JSONObject fac = facturas.getJSONObject(Integer.toString(id));
		
		return fac == null ? null : read(fac);
	}

	@Override
	public Collection<TFactura> readAll() throws BBDDReadException {
		JSONObject bdFactura = OpsBBDD.read(Messages.BDFac);
		
		Collection<TFactura> facturasValidas = new ArrayList<>();
		
		JSONObject facturas = new JSONObject(bdFactura.getJSONArray(Messages.KEY_facs));
		for (int i = 0; i < facturas.length(); i++) {
			JSONObject factura = facturas.getJSONObject(Integer.toString(i));
			if (factura.getBoolean(Messages.KEY_act)) { //Cojo solo las facturas activas
				facturasValidas.add(read(factura));
			}
		}
	
		return facturasValidas;
	}

	@Override
	public int update(TFactura tFactura) throws BBDDReadException, BBDDWriteException {
		JSONObject bdFactura = OpsBBDD.read(Messages.BDFac);
		JSONObject facs = bdFactura.getJSONObject(Messages.KEY_facs);
		
		for (int i = 0; i < facs.length(); ++i) {
			
        	JSONObject jObj = facs.getJSONObject(Integer.toString(i));
        	
            if (jObj.getInt(Messages.KEY_idFac) == tFactura.getIdFactura()) {
            	facs.put(Integer.toString(i),tFactura);
            	OpsBBDD.write(bdFactura, Messages.BDFac);
                return i;
            }
        }
        return -1; //No se ha encontrado la factura pasada por parámetro
	}
	
	
	private TFactura read(JSONObject jsonFac) {
		Collection<TLineaFactura> carrito = new ArrayList<>();
		DAOLineaFactura daoLineaFactura = FactoriaAbstractaIntegracion.getInstance().crearDAOLineaFactura();
		
		JSONArray lineas = jsonFac.getJSONArray(Messages.KEY_carrito);
		for (int i = 0; i < lineas.length(); i++) {
			int idLineaFactura = lineas.getInt(i);
			TLineaFactura tLineaFactura = daoLineaFactura.read(idLineaFactura);
			carrito.add(tLineaFactura);
		}
		
		return new TFactura(
				jsonFac.getInt(Messages.KEY_idFac), 
				jsonFac.getInt(Messages.KEY_idCli), 
				jsonFac.getInt(Messages.KEY_idTaq),
				jsonFac.getBoolean(Messages.KEY_act),
				LocalDateTime.parse(jsonFac.getString(Messages.KEY_fecha)),
				carrito,
				jsonFac.getInt(Messages.KEY_importe));
	}
}
