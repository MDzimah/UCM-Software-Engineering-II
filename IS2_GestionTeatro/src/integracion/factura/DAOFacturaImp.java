package integracion.factura;

import java.time.LocalDateTime;
import java.util.*;

import org.json.JSONObject;

import exceptions.*;
import misc.*;
import negocio.factura.*;

public class DAOFacturaImp implements DAOFactura {

	@Override
	public int create(TFactura tFactura) throws BBDDReadException, BBDDWriteException {
		JSONObject bdFac = new JSONObject();
		
		//Inicializo la BD si no está ya inicializada
		if (OpsBBDD.isEmpty(Messages.BDFac)) {
			bdFac.put(Messages.KEY_lastId, 0);
			bdFac.put(Messages.KEY_facs, new JSONObject());
		}
		else bdFac = OpsBBDD.read(Messages.BDFac);
		
		JSONObject facs = bdFac.getJSONObject(Messages.KEY_facs);
		
		//Aumentamos el último índice de la bd
		int newId = bdFac.getInt(Messages.KEY_lastId) + 1;
		bdFac.put(Messages.KEY_lastId, newId);
		
		//Creamos la nueva factura
		JSONObject nuevaFactura = new JSONObject();
		nuevaFactura.put(Messages.KEY_act, tFactura.getActivo());
		nuevaFactura.put(Messages.KEY_idCli, tFactura.getIdCliente());
		nuevaFactura.put(Messages.KEY_idTaq, tFactura.getIdTaquillero());
		nuevaFactura.put(Messages.KEY_fecha, tFactura.getFecha().toString());
		nuevaFactura.put(Messages.KEY_importe, tFactura.getImporte());
		nuevaFactura.put(Messages.KEY_subtotal, tFactura.getSubtotal());
		
		//La insertamos en la bd de facturas, su clave es su id
		facs.put(Integer.toString(newId), nuevaFactura);
		OpsBBDD.write(bdFac, Messages.BDFac);
		
		return newId;
	}

	@Override
	public int delete(int id) throws BBDDReadException, BBDDWriteException {
		if (!OpsBBDD.isEmpty(Messages.BDFac)) {
			JSONObject bdFac = OpsBBDD.read(Messages.BDFac);
			JSONObject facs = bdFac.getJSONObject(Messages.KEY_facs);
	        
			String _id = Integer.toString(id);
			if (facs.has(_id) && facs.getJSONObject(_id).getBoolean(Messages.KEY_act)) {
		        JSONObject fac = facs.getJSONObject(_id);
		        fac.put(Messages.KEY_act, false);
		        OpsBBDD.write(bdFac, Messages.BDFac);
		        return id;
			}
		}
        return -1; //No se ha encontrado la factura con dicho id
	}

	@Override
	public TFactura read(int id) throws BBDDReadException {
		if (!OpsBBDD.isEmpty(Messages.BDFac)) {
			JSONObject facs = OpsBBDD.read(Messages.BDFac).getJSONObject(Messages.KEY_facs);
			
			TFactura tFacRead = null;
			String _id = Integer.toString(id);
			
			//Aseguramos que el id está en las facturas y que 
			if (facs.has(_id) && facs.getJSONObject(_id).getBoolean(Messages.KEY_act)) {
				JSONObject fac = facs.getJSONObject(Integer.toString(id));
				tFacRead = readAux(fac);
				tFacRead.setIdFactura(id);
			}
			
			return tFacRead;
		}
		else return null;
	}

	@Override
	public Collection<TFactura> readAll() throws BBDDReadException {
		if (!OpsBBDD.isEmpty(Messages.BDFac)) {
			JSONObject bdFactura = OpsBBDD.read(Messages.BDFac);
			
			Collection<TFactura> facsValidas = new ArrayList<>();
			
			JSONObject facs = bdFactura.getJSONObject(Messages.KEY_facs);
			
			Set<String> allIdsFacs = facs.keySet();
			for (String idFactura : allIdsFacs) {
				JSONObject fac = facs.getJSONObject(idFactura);
				
				//Cojo solo las facturas activas
				if (fac.getBoolean(Messages.KEY_act)) {
					TFactura tFacVal = this.readAux(fac);
					tFacVal.setIdFactura(Integer.valueOf(idFactura)); //No va a dar excepción porque a la BD llegan solo id con números
					facsValidas.add(tFacVal);
				}
			}
			
			((ArrayList<TFactura>) facsValidas).sort((a, b) -> Integer.compare(a.getIdFactura(), b.getIdFactura()));
			return facsValidas;
		}
		else return null;
	}

	@Override
	public int update(TFactura tFactura) throws BBDDReadException, BBDDWriteException {
		if (!OpsBBDD.isEmpty(Messages.BDFac)) {
			JSONObject bdFactura = OpsBBDD.read(Messages.BDFac);
			JSONObject facs = bdFactura.getJSONObject(Messages.KEY_facs);
			
			Set<String> allIdsFacs = facs.keySet();
			for (String idFactura : allIdsFacs) {
				JSONObject fac = facs.getJSONObject(idFactura);
				
				//Solo se actualiza la factura si coinciden los ids y está activa
	            if (Integer.valueOf(idFactura) == tFactura.getIdFactura() && fac.getBoolean(Messages.KEY_act)) {
	            	fac.put(Messages.KEY_act, tFactura.getActivo());
	            	fac.put(Messages.KEY_idCli, tFactura.getIdCliente());
	            	fac.put(Messages.KEY_idTaq, tFactura.getIdTaquillero());
	            	fac.put(Messages.KEY_fecha, tFactura.getFecha().toString());
	        		fac.put(Messages.KEY_subtotal, tFactura.getSubtotal());
	        		fac.put(Messages.KEY_importe, tFactura.getImporte());
	            	
	            	OpsBBDD.write(bdFactura, Messages.BDFac);
	                return Integer.valueOf(idFactura);
	            }
	        }
		}
        return -1; //No se ha encontrado la factura pasada por parámetro
	}
	
	
	private TFactura readAux(JSONObject fac) {
		return new TFactura(
				fac.getInt(Messages.KEY_idCli), 
				fac.getInt(Messages.KEY_idTaq),
				fac.getBoolean(Messages.KEY_act),
				LocalDateTime.parse(fac.getString(Messages.KEY_fecha)),
				fac.getFloat(Messages.KEY_importe),
				fac.getFloat(Messages.KEY_subtotal));
	}
}
