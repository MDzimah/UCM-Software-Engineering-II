package negocio.factura;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import exceptions.UnknownClienteException;
import exceptions.UnknownTaquilleroException;
import integracion.cliente.DAOCliente;
import integracion.factoria.FactoriaAbstractaIntegracion;
import integracion.factura.DAOFactura;
import integracion.pase.DAOPase;
import integracion.taquillero.DAOTaquillero;
import negocio.cliente.TCliente;
import negocio.factoria.FactoriaAbstractaNegocio;
import negocio.obra.TObra;
import negocio.pase.SAPase;
import negocio.pase.TPase;
import negocio.taquillero.TTaquillero;

public class SAFacturaImp implements SAFactura {
	
	@Override
	public int create(TFactura tFactura) {
		int id = -1;
		
		List<TLineaFactura> carritoFinal = new ArrayList<>();
		
		DAOCliente daoCliente = FactoriaAbstractaIntegracion.getInstance().crearDAOCliente();
		TCliente leidoCliente = daoCliente.readByDNI(tFactura.getCliente());
		DAOTaquillero daoTaquillero = FactoriaAbstractaIntegracion.getInstance().crearDAOTaquillero();
		TTaquillero leidoTaquillero = daoTaquillero.readById(tFactura.getTaquillero());
		
		// Comprobamos excepciones
		if (leidoCliente == null) {
			throw new UnknownClienteException();
		}
		if (leidoTaquillero == null) {
			throw new UnknownTaquilleroException();
		}
		
		DAOPase daoPase = FactoriaAbstractaIntegracion.getInstance().crearDAOPase();
		
		// Cliente y Taquillero existen
		if (leidoCliente.getActivo()) {
			for (TLineaFactura tLinea : tFactura.getCarrito()) {
				TPase tPase = daoPase.read(tLinea.getPase());
				if (tPase != null) {
					SAPase saPase = FactoriaAbstractaNegocio.getInstance().crearSAPase();
					int cantidadVendida = saPase.comprar(tPase, tLinea);
					saPase.update(tPase);
					tLinea.setCantidad(tLinea.getCantidad() - cantidadVendida);
					if (cantidadVendida > 0) {
						carritoFinal.add(tLinea);
					}
				}
			}
			if (carritoFinal.size() > 0) {
				tFactura.setCarrito(carritoFinal);
				DAOFactura daoFactura = FactoriaAbstractaIntegracion.getInstance().crearDAOFactura();
				id = daoFactura.create(tFactura);
			}
		}
		
		
	}

	@Override
	public TFactura read(int id) {
		DAOFactura daoFactura = FactoriaAbstractaIntegracion.getInstance().crearDAOFactura();
		return daoFactura.read(id);
	}

	@Override
	public int update(TFactura tFac) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(int id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Collection<TFactura> readAll() {
		DAOFactura daoFactura = FactoriaAbstractaIntegracion.getInstance().crearDAOFactura();
		return daoFactura.readAll();
	}

}
