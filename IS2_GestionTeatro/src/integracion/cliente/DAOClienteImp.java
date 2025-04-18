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
	
	
	private int createNormal(TCliente tCliente) throws BBDDReadException, BBDDWriteException {
		Integer id = Integer.parseInt(tCliente.getDNI().substring(0, tCliente.getDNI().length()-1));//se crea un id a partir del dni
		JSONObject bdcn = OpsBBDD.read(Messages.BDCliNorm);
		JSONObject clientes = (JSONObject) bdcn.get(Messages.KEY_CliNorms);
		if (!clientes.has(id.toString())) {
			TClienteNormal tClienteN = (TClienteNormal) tCliente;
			JSONObject nCliente = new JSONObject();
			nCliente.put(Messages.KEY_idCli,id);
			nCliente.put(Messages.KEY_nombre, tCliente.getNombre());
			nCliente.put(Messages.KEY_apellido, tCliente.getApellido());
			nCliente.put(Messages.KEY_cuentaBancaria, tCliente.getCuentaBancaria());
			nCliente.put(Messages.KEY_act, tCliente.getActivo());
			nCliente.put(Messages.KEY_DNI, tCliente.getDNI());
			nCliente.put(Messages.KEY_puntosAcum, tClienteN.getPuntosAcumulados());
			List<Integer> facturas = new ArrayList<Integer>();
			for (TFactura factura : tCliente.getFacturas()) {
				facturas.addLast(factura.getIdFactura());
			}
			nCliente.put(Messages.KEY_facturasCliente, facturas);
			clientes.put(id.toString(), nCliente);
			bdcn.put(Messages.KEY_CliNorms, clientes);
			OpsBBDD.write(bdcn, Messages.BDCliNorm);
			return id;
		}
		else return -1;
	}
	
	private int createVIP(TCliente tCliente) throws BBDDReadException, BBDDWriteException { 
		Integer id = Integer.parseInt(tCliente.getDNI().substring(0, tCliente.getDNI().length()-1));//se crea un id a partir del dni
		JSONObject bdcv = OpsBBDD.read(Messages.BDCliVIP);
		JSONObject clientes = (JSONObject) bdcv.get(Messages.KEY_CliVIPs);
		if (!clientes.has(id.toString())) {
			TClienteVIP tClienteV = (TClienteVIP) tCliente;
			JSONObject nCliente = new JSONObject();
			nCliente.put(Messages.KEY_idCli,id);
			nCliente.put(Messages.KEY_nombre, tCliente.getNombre());
			nCliente.put(Messages.KEY_apellido, tCliente.getApellido());
			nCliente.put(Messages.KEY_cuentaBancaria, tCliente.getCuentaBancaria());
			nCliente.put(Messages.KEY_act, tCliente.getActivo());
			nCliente.put(Messages.KEY_costeMensual, tClienteV.getCosteMensual());
			nCliente.put(Messages.KEY_DNI, tClienteV.getDNI());
			nCliente.put(Messages.KEY_nivelVIP, tClienteV.getNivelVIP());
			List<Integer> facturas = new ArrayList<Integer>();
			for (TFactura factura : tCliente.getFacturas()) {
				facturas.addLast(factura.getIdFactura());
			}
			nCliente.put(Messages.KEY_facturasCliente, facturas);
			clientes.put(id.toString(), nCliente);
			bdcv.put(Messages.KEY_CliVIPs, clientes);
			OpsBBDD.write(bdcv, Messages.BDCliVIP);
			return id;
		}
		else return -1;
	}

	@Override
	public int create(TCliente tCliente) throws BBDDReadException, BBDDWriteException{
		//cliente normal
		if (tCliente.getTipo() == "Normal") return createNormal(tCliente);
		else if (tCliente.getTipo() == "VIP") return createVIP(tCliente);
		else return -1;
	}

	

	@Override
	public int delete(int id) throws BBDDReadException, BBDDWriteException {
		JSONObject bdcn = OpsBBDD.read(Messages.BDCliNorm);
		JSONObject clientes = (JSONObject) bdcn.get(Messages.KEY_CliNorms);
		if (clientes.get(Integer.toString(id)) != null) {
			JSONObject cliente = (JSONObject) clientes.get(Integer.toString(id));
			cliente.put(Messages.KEY_act, false);
			OpsBBDD.write(bdcn, Messages.BDCliNorm);
			return id;
		}
		JSONObject bdcv = OpsBBDD.read(Messages.BDCliVIP);
		JSONObject clientesv = (JSONObject) bdcn.get(Messages.KEY_CliVIPs);
		if (clientesv.get(Integer.toString(id)) != null) {
			JSONObject clientev = (JSONObject) clientesv.get(Integer.toString(id));
			clientev.put(Messages.KEY_act, false);
			OpsBBDD.write(bdcv, Messages.BDCliVIP);
			return id;
		}
		else return -1;
		
		
	}

	@Override
	public TCliente read(int id) throws BBDDReadException, BBDDWriteException {
		JSONObject bdcn = OpsBBDD.read(Messages.BDCliNorm);
		JSONObject clientes = (JSONObject) bdcn.get(Messages.KEY_CliNorms);
		if (clientes.get(Integer.toString(id)) != null) {
			JSONObject cliente = (JSONObject) clientes.get(Integer.toString(id));
			TClienteNormal tClienteN = new TClienteNormal();
			tClienteN.setActivo(cliente.getBoolean(Messages.KEY_act));
			tClienteN.setApellido(cliente.getString(Messages.KEY_apellido));
			tClienteN.setCuentaBancaria(cliente.getString(Messages.KEY_cuentaBancaria));
			tClienteN.setFacturas((Collection<TFactura>) cliente.get(Messages.KEY_facturasCliente));
			tClienteN.setIdCliente(cliente.getInt(Messages.KEY_idCli));
			tClienteN.setNombre(cliente.getString(Messages.KEY_nombre));
			tClienteN.setDNI(cliente.getString(Messages.KEY_DNI));
			tClienteN.setTipo("Normal");
			tClienteN.setPuntosAcumulados(cliente.getInt(Messages.KEY_puntosAcum));
			return tClienteN;
		}
		JSONObject bdcv = OpsBBDD.read(Messages.BDCliVIP);
		JSONObject clientesv = (JSONObject) bdcv.get(Messages.KEY_CliVIPs);
		if (clientesv.get(Integer.toString(id)) != null) {
			JSONObject clientev = (JSONObject) clientesv.get(Integer.toString(id));
			TClienteVIP tClienteV = new TClienteVIP();
			tClienteV.setActivo(clientev.getBoolean(Messages.KEY_act));
			tClienteV.setApellido(clientev.getString(Messages.KEY_apellido));
			tClienteV.setCuentaBancaria(clientev.getString(Messages.KEY_cuentaBancaria));
			tClienteV.setFacturas((Collection<TFactura>) clientev.get(Messages.KEY_facturasCliente));
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
		JSONObject bdcn = OpsBBDD.read(Messages.BDCliNorm);
		JSONObject clientes = (JSONObject) bdcn.get(Messages.KEY_CliNorms);
		JSONObject bdcv = OpsBBDD.read(Messages.BDCliVIP);
		JSONObject clientesv = (JSONObject) bdcv.get(Messages.KEY_CliVIPs);
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

		JSONObject bdcn = OpsBBDD.read(Messages.BDCliNorm);
		JSONObject clientes = (JSONObject) bdcn.get(Messages.KEY_CliNorms);
		if (clientes.get(Integer.toString(tCliente.getIdCliente())) != null) {
			TClienteNormal tClienteN = (TClienteNormal) tCliente;
			JSONObject nCliente = new JSONObject();
			nCliente.put(Messages.KEY_idCli,tCliente.getIdCliente());
			nCliente.put(Messages.KEY_nombre, tCliente.getNombre());
			nCliente.put(Messages.KEY_apellido, tCliente.getApellido());
			nCliente.put(Messages.KEY_cuentaBancaria, tCliente.getCuentaBancaria());
			nCliente.put(Messages.KEY_act, tCliente.getActivo());
			nCliente.put(Messages.KEY_DNI, tCliente.getDNI());
			nCliente.put(Messages.KEY_puntosAcum, tClienteN.getPuntosAcumulados());
			List<Integer> facturas = new ArrayList<Integer>();
			for (TFactura factura : tCliente.getFacturas()) {
				facturas.addLast(factura.getIdFactura());
			}
			nCliente.put(Messages.KEY_facturasCliente, facturas);
			clientes.put(Integer.toString(tCliente.getIdCliente()), nCliente);
			bdcn.put(Messages.KEY_CliNorms, clientes);
			OpsBBDD.write(bdcn, Messages.BDCliNorm);
			return tCliente.getIdCliente();
		}
		JSONObject bdcv = OpsBBDD.read(Messages.BDCliVIP);
		JSONObject clientesv = (JSONObject) bdcv.get(Messages.KEY_CliVIPs);
		if (clientesv.get(Integer.toString(tCliente.getIdCliente())) != null) {
			TClienteVIP tClienteV = (TClienteVIP) tCliente;
			JSONObject nCliente = new JSONObject();
			nCliente.put(Messages.KEY_idCli,tCliente.getIdCliente());
			nCliente.put(Messages.KEY_nombre, tCliente.getNombre());
			nCliente.put(Messages.KEY_apellido, tCliente.getApellido());
			nCliente.put(Messages.KEY_cuentaBancaria, tCliente.getCuentaBancaria());
			nCliente.put(Messages.KEY_act, tCliente.getActivo());
			nCliente.put(Messages.KEY_costeMensual, tClienteV.getCosteMensual());
			nCliente.put(Messages.KEY_nivelVIP, tClienteV.getNivelVIP());
			nCliente.put(Messages.KEY_DNI, tCliente.getDNI());
			List<Integer> facturas = new ArrayList<Integer>();
			for (TFactura factura : tCliente.getFacturas()) {
				facturas.addLast(factura.getIdFactura());
			}
			nCliente.put(Messages.KEY_facturasCliente, facturas);
			clientes.put(Integer.toString(tCliente.getIdCliente()), nCliente);
			bdcv.put(Messages.KEY_CliVIPs, clientesv);
			OpsBBDD.write(bdcv, Messages.BDCliVIP);
			return tCliente.getIdCliente();
		}
		else return -1;
	}

	@Override
	public TCliente readByDNI(String dni) throws BBDDReadException, BBDDWriteException {
		return this.read(Integer.parseInt(dni.substring(0, dni.length()-1)));
	}

}
