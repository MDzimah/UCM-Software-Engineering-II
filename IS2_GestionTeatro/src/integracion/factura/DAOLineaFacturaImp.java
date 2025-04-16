package integracion.factura;

import org.json.JSONArray;
import org.json.JSONObject;

import misc.Messages;
import misc.OpsBBDD;
import negocio.factura.TLineaFactura;

public class DAOLineaFacturaImp implements DAOLineaFactura {

	@Override
	public int create(TLineaFactura tLineaFactura) {
		JSONObject bdLinFac = OpsBBDD.read(Messages.BDLFac);
		
		JSONArray lineasFactura = bdLinFac.getJSONArray("lineas");
		
		int newId = bdFactura.getInt("lastId") + 1;
		bdFactura.put("lastId", newId);
		
		JSONObject nuevaLinea = new JSONObject();
		
		nuevaLinea.put(Messages.KEY_idLinFac, newId);
		nuevaLinea.put(Messages.KEY_idFac, tLineaFactura.getIdFactura());
		nuevaLinea.put(Messages.KEY_idPase, tLineaFactura.getIdPase());
		nuevaLinea.put(Messages.KEY_fecha, tLineaFactura.getFechaPase().toString());
		nuevaLinea.put(Messages.KEY_ctdad, tLineaFactura.getCantidad());
		nuevaLinea.put(Messages.KEY_LF_precio, tLineaFactura.getPrecioVenta());
		
		lineasFactura.put(nuevaLinea);
		return newId;
	}
	
	@Override
	public int delete(int id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public TLineaFactura read(int id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public int update(TLineaFactura tLineaFac) {
		// TODO Auto-generated method stub
		return 0;
	}
}
