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
		
		JSONObject nuevaLinea = new JSONObject();
		
		nuevaLinea.put(Messages.KEY_idFac, tLineaFactura.getIdFactura());
		nuevaLinea.put(Messages.KEY_idPase, tLineaFactura.getIdPase());
		nuevaLinea.put(Messages.KEY_ctdad, tLineaFactura.getCantidad());
		nuevaLinea.put(Messages.KEY_LF_precio, tLineaFactura.getPrecioVenta());
		
		int newId = bdLinFac.getInt(Messages.KEY_lastId) + 1;
		bdLinFac.put(Messages.KEY_lastId, newId);
		lineasFactura.put(Integer.toString(newId), nuevaLinea);
		
		OpsBBDD.write(lineasFactura, Messages.BDLinFac);
		
		return newId;
	}
	
	@Override
	public int delete(int id) throws BBDDReadException, BBDDWriteException {
		JSONObject bdLinFac = OpsBBDD.read(Messages.BDLinFac);
		JSONObject lineasFactura = bdLinFac.getJSONObject(Messages.KEY_facs);
        
        JSONObject linea = lineasFactura.getJSONObject(Integer.toString(id));
        
        if (linea != null) {
        	linea.put(Messages.KEY_act, false);
        	return id;
        }
        return -1; //No se ha encontrado la linea de factura con dicho id
	}

	@Override
	public TLineaFactura read(int id) throws BBDDReadException {
		JSONObject bdLinFac = OpsBBDD.read(Messages.BDLinFac);
		JSONObject lineasFactura = bdLinFac.getJSONObject(Messages.KEY_facs);
		
		JSONObject linea = lineasFactura.getJSONObject(Integer.toString(id));
		
		return linea == null ? null : new TLineaFactura(id, 
				linea.getInt(Messages.KEY_idFac), 
				linea.getInt(Messages.KEY_idPase), 
				linea.getInt(Messages.KEY_ctdad),
				linea.getFloat(Messages.KEY_LF_precio));
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
