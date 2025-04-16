package integracion.factura;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

import org.json.JSONArray;
import org.json.JSONObject;

import exceptions.BBDDReadException;
import exceptions.BBDDWriteException;
import integracion.factoria.FactoriaAbstractaIntegracion;
import misc.OpsBBDD;
import misc.PanelUtils;
import negocio.factura.TFactura;
import negocio.factura.TLineaFactura;

public class DAOFacturaImp implements DAOFactura {

	@Override
	public int create(TFactura tFactura) {
		JSONObject bdFactura;
		try {
			bdFactura = OpsBBDD.read("BDFactura.json");
		
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
			
			OpsBBDD.write(bdFactura, "BDFactura.json");
			
			return newId;
		} 
		catch (BBDDReadException e) {
			PanelUtils.panelBBDDReadError(null, "BDFactura.json", e.getMessage());
			return -1;
		}
		catch (BBDDWriteException e) {
			PanelUtils.panelBBDDWriteError(null, "BDFactura.json", e.getMessage());
			return -1;
		}
	}

	@Override
	public int delete(int id) {
		return 0;
	}

	@Override
	public TFactura read(int id) {
		try {
			JSONObject bdFactura = OpsBBDD.read("BDFactura.json");
	
			JSONArray facturas = new JSONArray(bdFactura.getJSONArray("facturas"));
			
			for (int i = 0; i < facturas.length(); i++) {
				JSONObject factura = facturas.getJSONObject(i);
				if (factura.getInt("idFactura") == id) {
					return read(factura);
				}
			}
			
		} 
		catch (BBDDReadException e) {
			PanelUtils.panelBBDDReadError(null, "BDFactura.json", e.getMessage());
		}
		return null;
	}

	@Override
	public Collection<TFactura> readAll() {
		try {
			JSONObject bdFactura = OpsBBDD.read("BDFactura.json");
			
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
		catch (BBDDReadException e) {
			PanelUtils.panelBBDDReadError(null, "BDFactura.json", e.getMessage());
			return null;
		}
	}

	@Override
	public int update(TFactura tFactura) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	
	private TFactura read(JSONObject jsonFac) {
		Collection<TLineaFactura> carrito = new ArrayList<>();
		DAOLineaFactura daoLineaFactura = FactoriaAbstractaIntegracion.getInstance().crearDAOLineaFactura();
		
		JSONArray lineas = jsonFac.getJSONArray("carrito");
		for (int i = 0; i < lineas.length(); i++) {
			int idLineaFactura = lineas.getInt(i);
			TLineaFactura tLineaFactura = daoLineaFactura.read(idLineaFactura);
			carrito.add(tLineaFactura);
		}
		
		return new TFactura(
				jsonFac.getInt("idFactura"), 
				jsonFac.getInt("idCliente"), 
				jsonFac.getInt("idTaquillero"),
				jsonFac.getBoolean("activo"),
				LocalDateTime.parse(jsonFac.getString("fecha")),
				carrito,
				jsonFac.getInt("importe"));
	}
}
