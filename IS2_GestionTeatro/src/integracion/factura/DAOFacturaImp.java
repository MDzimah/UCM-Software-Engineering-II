package integracion.factura;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

import org.json.JSONArray;
import org.json.JSONObject;

import integracion.factoria.FactoriaAbstractaIntegracion;
import negocio.factura.TFactura;
import negocio.factura.TLineaFactura;

public class DAOFacturaImp implements DAOFactura {

	@Override
	public int create(TFactura tFactura) {
		JSONObject bdFactura = new JSONObject(); // cambiar: crear metodo que devuelva un JSON de la BD
		if (bdFactura == null) {
			// HACER ALGO
		}
		JSONArray facturas = new JSONArray(bdFactura.get("facturas"));
		
		int newId = bdFactura.getInt("lastId") + 1;
		bdFactura.put("lastId", newId);
		
		JSONObject nuevaFactura = new JSONObject();
		
		nuevaFactura.put("idFactura", newId);
		nuevaFactura.put("activo", tFactura.getActivo());
		
		nuevaFactura.put("idCliente", tFactura.getIdCliente());
		nuevaFactura.put("idTaquillero", tFactura.getIdTaquillero());
		nuevaFactura.put("fecha", tFactura.getFecha().toString());
		
		DAOLineaFactura daoLineaFactura = FactoriaAbstractaIntegracion.getInstance().crearDAOLineaFactura();
		JSONArray carrito = new JSONArray();
		for (TLineaFactura tLineaFactura : tFactura.getCarrito()) {
			int idLineaFactura = daoLineaFactura.create(tLineaFactura);
			carrito.put(idLineaFactura);
		}
		nuevaFactura.put("carrito", carrito);
		
		nuevaFactura.put("importe", tFactura.getImporte());
		
		
		facturas.put(nuevaFactura);
		bdFactura.put("facturas", facturas);
		DataBase.write(bdFactura); // implementar metodo write
		
		return newId;
	}

	@Override
	public int delete(int id) {
		return 0;
	}

	@Override
	public TFactura read(int id) {
		JSONObject bdFactura = DataBase.read("BDFactura.json");

		JSONArray facturas = new JSONArray(bdFactura.getJSONArray("facturas"));
		
		for (int i = 0; i < facturas.length(); i++) {
			JSONObject factura = facturas.getJSONObject(i);
			if (factura.getInt("idFactura") == id) {
				return read(factura);
			}
		}
	}

	@Override
	public Collection<TFactura> readAll() {
		JSONObject bdFactura = DataBase.read("BDFactura.json");
		
		Collection<TFactura> facturasValidas = new ArrayList<>();
		
		JSONArray facturas = new JSONArray(bdFactura.getJSONArray("facturas"));
		for (int i = 0; i < facturas.length(); i++) {
			JSONObject factura = facturas.getJSONObject(i);
			if (factura.getBoolean("activo")) {
				facturasValidas.add(read(factura));
			}
		}
		
		return facturasValidas;
	}

	@Override
	public int update(TFactura tFactura) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	private TFactura read(JSONObject jsonFactura) {
		TFactura tFactura = new TFactura();
		
		tFactura.setIdFactura(jsonFactura.getInt("idFactura"));
		tFactura.setActivo(jsonFactura.getBoolean("activo"));
		
		tFactura.setIdCliente(jsonFactura.getInt("idCliente"));
		tFactura.setIdTaquillero(jsonFactura.getInt("idTaquillero"));
		tFactura.setFecha(LocalDateTime.parse(jsonFactura.getString("fecha")));
		
		Collection<TLineaFactura> carrito = new ArrayList<>();
		DAOLineaFactura daoLineaFactura = FactoriaAbstractaIntegracion.getInstance().crearDAOLineaFactura();
		JSONArray lineas = jsonFactura.getJSONArray("carrito");
		for (int i = 0; i < lineas.length(); i++) {
			int idLineaFactura = lineas.getInt(i);
			TLineaFactura tLineaFactura = daoLineaFactura.read(idLineaFactura);
			carrito.add(tLineaFactura);
		}
		tFactura.setCarrito(carrito);
		
		tFactura.setImporte(jsonFactura.getInt("importe"));
		
		return tFactura;
	}
	
}
