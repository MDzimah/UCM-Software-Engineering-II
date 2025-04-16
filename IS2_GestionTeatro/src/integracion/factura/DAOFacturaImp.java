package integracion.factura;

import java.nio.*;
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
	public int create(TFactura tFactura) throws BBDDWriteException {
		JSONObject bdFactura;
		try {
			bdFactura = OpsBBDD.read(Messages.BDFac);
		
			JSONArray facturas = new JSONArray(bdFactura.get(Messages.KEY_facs));
			
			int newId = bdFactura.getInt(Messages.KEY_lastId) + 1;
			bdFactura.put(Messages.KEY_lastId, newId);
			
			JSONObject nuevaFactura = new JSONObject();
			
			nuevaFactura.put(Messages.KEY_idFac, newId);
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
			
			
			facturas.put(nuevaFactura);
			bdFactura.put(Messages.KEY_facs, facturas);
			
			OpsBBDD.write(bdFactura, Messages.BDFac);
			
			return newId;
		} 
		catch (BBDDReadException e) {
			PanelUtils.panelBBDDReadError(null, Messages.BDFac, e.getMessage());
		}
		catch (BBDDWriteException e) {
			PanelUtils.panelBBDDWriteError(null, Messages.BDFac, e.getMessage());
		}
		return -1;
	}

	@Override
	public int delete(int id) {
		return 0;
	}

	@Override
	public TFactura read(int id) throws BBDDReadException {
		JSONObject bdFactura = OpsBBDD.read(Messages.BDFac);

		JSONArray facturas = new JSONArray(bdFactura.getJSONArray(Messages.BDFac));
		
		for (int i = 0; i < facturas.length(); i++) {
			JSONObject factura = facturas.getJSONObject(i);
			if (factura.getInt(Messages.KEY_idFac) == id) {
				return read(factura);
			}
		}
		return null;
	}

	@Override
	public Collection<TFactura> readAll() throws BBDDReadException {
		JSONObject bdFactura = OpsBBDD.read(Messages.BDFac);
		
		Collection<TFactura> facturasValidas = new ArrayList<>();
		
		JSONArray facturas = new JSONArray(bdFactura.getJSONArray(Messages.KEY_facs));
		for (int i = 0; i < facturas.length(); i++) {
			JSONObject factura = facturas.getJSONObject(i);
			if (factura.getBoolean(Messages.KEY_act)) {
				facturasValidas.add(read(factura));
			}
		}
	
		return facturasValidas;
	}

	@Override
	public int update(TFactura tFactura) throws BBDDWriteException {
		int res = -1;
		String content = new String(Files.readAllBytes(Paths.get(path)), StandardCharsets.UTF_8);
	 
        // Parse JSON
        JSONObject root = new JSONObject(content);
        JSONArray users = root.getJSONArray("users");

        // Find and update user
        for (int i = 0; i < users.length(); i++) {
            JSONObject user = users.getJSONObject(i);
            if (user.getInt("id") == 2) {
                user.put("name", "Robert");
                break;
            }
        }

        // Write back to file
        Files.write(Paths.get(path), root.toString(4).getBytes(StandardCharsets.UTF_8));
        System.out.println("Updated JSON successfully.");
		return res;
		
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
