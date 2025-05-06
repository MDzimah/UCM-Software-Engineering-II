	package negocio.cliente;

import java.util.Collection;

import exceptions.BBDDReadException;
import exceptions.BBDDWriteException;
import exceptions.UnknownClienteException;
import integracion.cliente.DAOCliente;
import integracion.factoria.FactoriaAbstractaIntegracion;
import integracion.factura.DAOFactura;
import negocio.factoria.FactoriaAbstractaNegocio;
import negocio.factura.SAFactura;
import negocio.factura.TFactura;

public class SAClienteImp implements SACliente {

	@Override
	public int create(TCliente cl) throws BBDDReadException, BBDDWriteException {
		DAOCliente dao = FactoriaAbstractaIntegracion.getInstance().crearDAOCliente();
		//no se si excepcion
		if (dao.read(cl.getIdCliente()) != null) return -1;
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
		int nid = dao.delete(id);
		if (nid != -1) {
			//eliminamos todas las facturas asociadas al cliente eliminado
			SAFactura sa = FactoriaAbstractaNegocio.getInstance().crearSAFactura();
			Collection<TFactura> c = sa.allFacturasPorCliente(nid);
			for (TFactura fac : c) {
				if (fac.getIdCliente() == nid) sa.delete(fac.getIdFactura());
			}
		}
		return nid;
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
			if (cl.getTipo() == "Normal") {
				TClienteNormal cln = (TClienteNormal) cl;
				if (cln.getPuntosAcumulados() != 0) return (float) ((float)importe*(0.5 + 1/cln.getPuntosAcumulados()));
				else return importe;
			}
			if (cl.getTipo() == "VIP") {
				TClienteVIP clv = (TClienteVIP) cl;
				return (float) (importe*0.25);
			}
			throw new Exception();
		}
		catch (Exception e) {
			throw new UnknownClienteException();
		}
		
	}

}
