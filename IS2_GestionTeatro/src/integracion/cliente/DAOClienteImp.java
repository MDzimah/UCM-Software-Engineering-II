package integracion.cliente;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;

import exceptions.BBDDReadException;
import exceptions.BBDDWriteException;
import misc.Messages;
import misc.OpsBBDD;
import negocio.cliente.TCliente;
import negocio.cliente.TClienteNormal;
import negocio.cliente.TClienteVIP;
import negocio.cliente.VIPEnum;
import negocio.factura.TFactura;

public class DAOClienteImp implements DAOCliente {
	
	
	private int createNormal(TCliente tCliente,JSONObject bdc, int newId) throws BBDDReadException, BBDDWriteException {
		JSONObject clientes = (JSONObject) bdc.get(Messages.KEY_cliNorms);
		
		clientes.put(Integer.toString(newId), newClienteNormal((TClienteNormal) tCliente));
		OpsBBDD.write(bdc, Messages.BDCli);
		
		return newId;
	}
	
	private int createVIP(TCliente tCliente, JSONObject bdc, int newId) throws BBDDReadException, BBDDWriteException { 
		JSONObject clientes = (JSONObject) bdc.get(Messages.KEY_cliVIPs);
		
		clientes.put(Integer.toString(newId), newClienteVIP((TClienteVIP) tCliente));
		OpsBBDD.write(bdc, Messages.BDCli);
		
		return newId;
	}

	@Override
	public int create(TCliente tCliente) throws BBDDReadException, BBDDWriteException{
		JSONObject bdc = new JSONObject();
		
		if (OpsBBDD.isEmpty(Messages.BDCli)) {
			bdc.put(Messages.KEY_lastId,0);
			bdc.put(Messages.KEY_cliVIPs, new JSONObject());
			bdc.put(Messages.KEY_cliNorms, new JSONObject());
		}
		else bdc = OpsBBDD.read(Messages.BDCli);

		int newId = bdc.getInt(Messages.KEY_lastId) +1;
		bdc.put(Messages.KEY_lastId, newId);
		
		//cliente normal
		if (tCliente.getTipo() == "Normal") return createNormal(tCliente,bdc,newId);
		//cliente VIP
		else if (tCliente.getTipo() == "VIP") return createVIP(tCliente,bdc,newId);
		else return -1;
	}

	

	@Override
	public int delete(int id) throws BBDDReadException, BBDDWriteException {
		if (OpsBBDD.isEmpty(Messages.BDCli)) return -1;
		JSONObject bdc = OpsBBDD.read(Messages.BDCli);
		JSONObject clientes = (JSONObject) bdc.get(Messages.KEY_cliNorms);
		if (clientes.get(Integer.toString(id)) != null) {
			JSONObject cliente = (JSONObject) clientes.get(Integer.toString(id));
			cliente.put(Messages.KEY_act, false);
			OpsBBDD.write(bdc, Messages.BDCli);
			return id;
		}
		JSONObject clientesv = (JSONObject) bdc.get(Messages.KEY_cliVIPs);
		if (clientesv.get(Integer.toString(id)) != null) {
			JSONObject clientev = (JSONObject) clientesv.get(Integer.toString(id));
			clientev.put(Messages.KEY_act, false);
			OpsBBDD.write(bdc, Messages.BDCli);
			return id;
		}
		else return -1;
		
		
	}

	@Override
	public TCliente read(int id) throws BBDDReadException, BBDDWriteException {
		if (OpsBBDD.isEmpty(Messages.BDCli)) return null;
		JSONObject bdc = OpsBBDD.read(Messages.BDCli);
		JSONObject clientes = (JSONObject) bdc.get(Messages.KEY_cliNorms);
		//cliente normal
		if (clientes.get(Integer.toString(id)) != null) {
			JSONObject cliente = (JSONObject) clientes.get(Integer.toString(id));
			TClienteNormal tClienteN = new TClienteNormal();
			tClienteN.setActivo(cliente.getBoolean(Messages.KEY_act));
			tClienteN.setApellido(cliente.getString(Messages.KEY_apellido));
			tClienteN.setCuentaBancaria(cliente.getString(Messages.KEY_cuentaBancaria));
			tClienteN.setIdCliente(cliente.getInt(Messages.KEY_idCli));
			tClienteN.setNombre(cliente.getString(Messages.KEY_nombre));
			tClienteN.setDNI(cliente.getString(Messages.KEY_DNI));
			tClienteN.setTipo("Normal");
			tClienteN.setPuntosAcumulados(cliente.getInt(Messages.KEY_ptosAcum));
			return tClienteN;
		}
		//cliente VIP
		JSONObject clientesv = (JSONObject) bdc.get(Messages.KEY_cliVIPs);
		if (clientesv.get(Integer.toString(id)) != null) {
			JSONObject clientev = (JSONObject) clientesv.get(Integer.toString(id));
			TClienteVIP tClienteV = new TClienteVIP();
			tClienteV.setActivo(clientev.getBoolean(Messages.KEY_act));
			tClienteV.setApellido(clientev.getString(Messages.KEY_apellido));
			tClienteV.setCuentaBancaria(clientev.getString(Messages.KEY_cuentaBancaria));
			tClienteV.setIdCliente(clientev.getInt(Messages.KEY_idCli));
			tClienteV.setNombre(clientev.getString(Messages.KEY_nombre));
			tClienteV.setDNI(clientev.getString(Messages.KEY_DNI));
			tClienteV.setTipo("VIP");
			tClienteV.setNivelVIP((VIPEnum) clientev.get(Messages.KEY_nivelVIP));
			tClienteV.setCosteMensual(clientev.getFloat(Messages.KEY_costeMensual));
			return tClienteV;
		}
		else return null;
	}

	@Override
	public Collection<TCliente> readAll() throws BBDDReadException, BBDDWriteException {
		if (OpsBBDD.isEmpty(Messages.BDCli)) return null;
		JSONObject bdc = OpsBBDD.read(Messages.BDCli);
		JSONObject clientes = (JSONObject) bdc.get(Messages.KEY_cliNorms);
		JSONObject clientesv = (JSONObject) bdc.get(Messages.KEY_cliVIPs);
		List<TCliente> clist = new ArrayList<>();
		//obtenemos todas las claves (idCliente de todos los clientes)
		Set<String> scln = clientes.keySet();
		Set<String> sclv = clientesv.keySet();
		//añadimos todos los clientes
		for (String s: scln) {//lista de clientes normales
			clist.addLast((TCliente) clientes.get(s));
		}
		for (String s: sclv) {//lista de clientes vip
			clist.addLast((TCliente) clientesv.get(s));
		}
		return clist;
	}

	@Override
	public int update(TCliente tCliente) throws BBDDReadException, BBDDWriteException {
		if (OpsBBDD.isEmpty(Messages.BDCli)) return -1;
		JSONObject bdc = OpsBBDD.read(Messages.BDCli);
		JSONObject clientes = (JSONObject) bdc.get(Messages.KEY_cliNorms);
		if (clientes.get(Integer.toString(tCliente.getIdCliente())) != null) {
			clientes.put(Integer.toString(tCliente.getIdCliente()), newClienteNormal((TClienteNormal) tCliente));
			//bdc.put(Messages.KEY_cliNorms, clientes);
			OpsBBDD.write(bdc, Messages.BDCli);
			return tCliente.getIdCliente();
		}
		JSONObject clientesv = (JSONObject) bdc.get(Messages.KEY_cliVIPs);
		if (clientesv.get(Integer.toString(tCliente.getIdCliente())) != null) {
			clientes.put(Integer.toString(tCliente.getIdCliente()), newClienteVIP((TClienteVIP) tCliente));
			//bdc.put(Messages.KEY_cliVIPs, clientesv);
			OpsBBDD.write(bdc, Messages.BDCli);
			return tCliente.getIdCliente();
		}
		else return -1;
	}
	
	private JSONObject newClienteNormal(TClienteNormal tCliente) {
		JSONObject nCliente = new JSONObject();
		nCliente.put(Messages.KEY_idCli,tCliente.getIdCliente());
		nCliente.put(Messages.KEY_nombre, tCliente.getNombre());
		nCliente.put(Messages.KEY_apellido, tCliente.getApellido());
		nCliente.put(Messages.KEY_cuentaBancaria, tCliente.getCuentaBancaria());
		nCliente.put(Messages.KEY_act, tCliente.getActivo());
		nCliente.put(Messages.KEY_DNI, tCliente.getDNI());
		nCliente.put(Messages.KEY_ptosAcum, tCliente.getPuntosAcumulados());
		return nCliente;
	}
	
	private JSONObject newClienteVIP(TClienteVIP tCliente) {
		JSONObject nCliente = new JSONObject();
		nCliente.put(Messages.KEY_idCli,tCliente.getIdCliente());
		nCliente.put(Messages.KEY_nombre, tCliente.getNombre());
		nCliente.put(Messages.KEY_apellido, tCliente.getApellido());
		nCliente.put(Messages.KEY_cuentaBancaria, tCliente.getCuentaBancaria());
		nCliente.put(Messages.KEY_act, tCliente.getActivo());
		nCliente.put(Messages.KEY_costeMensual, tCliente.getCosteMensual());
		nCliente.put(Messages.KEY_DNI, tCliente.getDNI());
		nCliente.put(Messages.KEY_nivelVIP, tCliente.getNivelVIP());
		return nCliente;
	}

}
