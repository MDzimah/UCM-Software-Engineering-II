package integracion.factura;

import java.util.Collection;

import org.json.JSONArray;
import org.json.JSONObject;

import negocio.factura.TLineaFactura;

public class DAOLineaFacturaImp implements DAOLineaFactura {

	@Override
	public int create(TLineaFactura tLineaFactura) {
		JSONObject bdFactura = new JSONObject();
		
		JSONArray lineasFactura = bdFactura.getJSONArray("lineas");
		
		int newId = bdFactura.getInt("lastId") + 1;
		bdFactura.put("lastId", newId);
		
		JSONObject nuevaLinea = new JSONObject();
		
		nuevaLinea.put("idLineaFactura", newId);
		nuevaLinea.put("idFactura", tLineaFactura.getIdFactura());
		nuevaLinea.put("idPase", tLineaFactura.getIdPase());
		
		nuevaLinea.put("tituloObra", tLineaFactura.getTituloObra());
		nuevaLinea.put("fecha", tLineaFactura.getFechaPase().toString());
		nuevaLinea.put("cantidad", tLineaFactura.getCantidad());
		nuevaLinea.put("precioVenta", tLineaFactura.getPrecioVenta());
		
		lineasFactura.put(nuevaLinea);
		bdFactura.put("lineas", lineasFactura);
		
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
	public Collection<TLineaFactura> readAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int update(TLineaFactura tLineaFac) {
		// TODO Auto-generated method stub
		return 0;
	}
}
