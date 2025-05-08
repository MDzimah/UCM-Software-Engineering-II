package negocio.cliente;

import java.util.Collection;

import exceptions.BBDDReadException;
import exceptions.BBDDWriteException;
import exceptions.UnknownClienteException;
import integracion.cliente.DAOCliente;
import integracion.factoria.FactoriaAbstractaIntegracion;
import negocio.factoria.FactoriaAbstractaNegocio;
import negocio.factura.SAFactura;
import negocio.factura.TFactura;

public class SAClienteImp implements SACliente {

	@Override
	public int create(TCliente cl) throws BBDDReadException, BBDDWriteException {
		DAOCliente dao = FactoriaAbstractaIntegracion.getInstance().crearDAOCliente();
		Collection<TCliente> cls = dao.readAll();
		//Comprueba que el cliente no este ya registrado comparando los DNIs, que lo identifican univocamente
		for (TCliente c : cls) {
			if (c.getDNI().equals(cl.getDNI())) return -1;
		}
		return dao.create(cl);
	}

	@Override
	public TCliente read(int id) throws BBDDReadException, BBDDWriteException {
		DAOCliente dao = FactoriaAbstractaIntegracion.getInstance().crearDAOCliente();
		return dao.read(id);
	}

	@Override
	public int update(TCliente cl) throws BBDDReadException, BBDDWriteException {
		DAOCliente dao = FactoriaAbstractaIntegracion.getInstance().crearDAOCliente();
		return dao.update(cl);
	}

	@Override
	public int delete(int id) throws BBDDReadException, BBDDWriteException {
		DAOCliente dao = FactoriaAbstractaIntegracion.getInstance().crearDAOCliente();
		return dao.delete(id);
	}

	@Override
	public Collection<TCliente> readAll() throws BBDDReadException, BBDDWriteException {
		DAOCliente dao = FactoriaAbstractaIntegracion.getInstance().crearDAOCliente();
		return dao.readAll();
		
	}

	@Override
	public float aplicarDescuento(int idCliente, float importe) throws UnknownClienteException {
		DAOCliente dao = FactoriaAbstractaIntegracion.getInstance().crearDAOCliente();
		try {
			TCliente cl = dao.read(idCliente);
			if (cl == null) throw new Exception();
			if (cl.getTipo().equals("Normal")) {
				TClienteNormal cln = (TClienteNormal) cl;
				if (cln.getPuntosAcumulados() != 0) return (float) ((float)importe*(0.5 + 1/(cln.getPuntosAcumulados()+1)));
				else return importe;
			}
			if (cl.getTipo().equals("VIP")) {
				TClienteVIP clv = (TClienteVIP) cl;
				VIPEnum[] niveles = VIPEnum.values();
				for (int i=0;i<niveles.length;++i) {
					if (clv.getNivelVIP() == niveles[i]) return (float) (importe*(0.5-Math.atan((double) i)/4));
				}
				return importe;
			}
			throw new Exception();
		}
		catch (Exception e) {
			throw new UnknownClienteException();
		}
		
	}

}
