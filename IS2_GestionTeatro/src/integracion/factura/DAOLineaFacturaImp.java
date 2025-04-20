package integracion.factura;

import org.json.JSONObject;

import exceptions.BBDDReadException;
import exceptions.BBDDWriteException;
import misc.Messages;
import misc.OpsBBDD;
import negocio.factura.TLineaFactura;

public class DAOLineaFacturaImp implements DAOLineaFactura {

	@Override
	public int create(TLineaFactura tLineaFactura) throws BBDDReadException, BBDDWriteException {
		JSONObject bdLinFac = OpsBBDD.read(Messages.BDLinFac);
		JSONObject lineasFactura = bdLinFac.getJSONObject(Messages.KEY_LineasFac);
		
		//Aumentamos el último índice de la bd
		int newId = bdLinFac.getInt(Messages.KEY_lastId) + 1;
		bdLinFac.put(Messages.KEY_lastId, newId);
		
		//Creamos nueva linea
		JSONObject nuevaLinea = new JSONObject();
		nuevaLinea.put(Messages.KEY_idFac, tLineaFactura.getIdFactura());
		nuevaLinea.put(Messages.KEY_idPase, tLineaFactura.getIdPase());
		nuevaLinea.put(Messages.KEY_ctdad, tLineaFactura.getCantidad());
		nuevaLinea.put(Messages.KEY_LF_precio, tLineaFactura.getPrecioVenta());
		
		//La insertamos en la bd de facturas, su clave es su id
		lineasFactura.put(Integer.toString(newId), nuevaLinea);
		OpsBBDD.write(bdLinFac, Messages.BDLinFac);
		
		return newId;
	}
	
	@Override
	public int delete(int id) throws BBDDReadException, BBDDWriteException {
		JSONObject bdLinFac = OpsBBDD.read(Messages.BDLinFac);
		JSONObject lineasFactura = bdLinFac.getJSONObject(Messages.KEY_facs);
        
		if (lineasFactura.has(Integer.toString(id))) {
        JSONObject linea = lineasFactura.getJSONObject(Integer.toString(id));
    	linea.put(Messages.KEY_act, false);
    	return id;
        
		}
        return -1; //No se ha encontrado la linea de factura con dicho id
	}

	@Override
	public TLineaFactura read(int id) throws BBDDReadException {
		JSONObject bdLinFac = OpsBBDD.read(Messages.BDLinFac);
		JSONObject lineasFactura = bdLinFac.getJSONObject(Messages.KEY_facs);
		
		TLineaFactura tLfRead = null;
		if (lineasFactura.has(Integer.toString(id))) {
			JSONObject linea = lineasFactura.getJSONObject(Integer.toString(id));
			
			tLfRead = new TLineaFactura(id, 
					linea.getInt(Messages.KEY_idFac), 
					linea.getInt(Messages.KEY_idPase), 
					linea.getInt(Messages.KEY_ctdad),
					linea.getFloat(Messages.KEY_LF_precio));
		}
		return tLfRead;
	}
	
	@Override
	public int update(TLineaFactura tLineaFac) throws BBDDReadException, BBDDWriteException {
		JSONObject bdLinFac = OpsBBDD.read(Messages.BDLinFac);
		JSONObject lineasFactura = bdLinFac.getJSONObject(Messages.KEY_facs);
		
		for (int i = 0; i < lineasFactura.length(); ++i) {
			
        	JSONObject jObj = lineasFactura.getJSONObject(Integer.toString(i));
        	
            if (jObj.getInt(Messages.KEY_idFac) == tLineaFac.getIdFactura()) {
            	lineasFactura.put(Integer.toString(i),tLineaFac);
            	OpsBBDD.write(bdLinFac, Messages.BDFac);
                return i;
            }
        }
        return -1; //No se ha encontrado la factura pasada por parámetro
	}
}
