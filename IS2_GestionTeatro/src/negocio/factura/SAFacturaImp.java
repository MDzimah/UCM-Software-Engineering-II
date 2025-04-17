package negocio.factura;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import exceptions.BBDDFacReadException;
import exceptions.BBDDFacWriteException;
import exceptions.BBDDReadException;
import exceptions.BBDDWriteException;
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
		
		Collection<TLineaFactura> carritoFinal = new ArrayList<>();
		
		DAOCliente daoCliente = FactoriaAbstractaIntegracion.getInstance().crearDAOCliente();
		
		TCliente leidoCliente = daoCliente.read(tFactura.getIdCliente());
		DAOTaquillero daoTaquillero = FactoriaAbstractaIntegracion.getInstance().crearDAOTaquillero();
		TTaquillero leidoTaquillero = daoTaquillero.read(tFactura.getIdTaquillero());
		
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
				TPase tPase = daoPase.read(tLinea.getIdPase());
				if (tPase != null) {
					
					/*
					 * 
					 * 
					 * 
					 * 
					 * 
					 * ESTO LO HACEMOS 2 VECES, LO DE COMPRAR (YO AL AÑADIR/QUITAR AL CARRITO Y AQUÍ OTRA)
					 * 
					 * 
					 * 
					 * 
					 * 
					 * 
					 * 
					 * */
					
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
				DAOFactura daoFac = FactoriaAbstractaIntegracion.getInstance().crearDAOFactura();
				id = daoFac.create(tFactura);
			}
		}
		
		return id;
	}

	@Override
	public TFactura read(int id) throws BBDDReadException {
		DAOFactura daoFac = FactoriaAbstractaIntegracion.getInstance().crearDAOFactura();
		return daoFac.read(id);
	}

	@Override
	public int update(TFactura tFac) throws BBDDReadException, BBDDWriteException {
		DAOFactura daoFac = FactoriaAbstractaIntegracion.getInstance().crearDAOFactura();
		return daoFac.update(tFac);
	}

	@Override
	public int delete(int id) throws BBDDReadException, BBDDWriteException {
		DAOFactura daoFac = FactoriaAbstractaIntegracion.getInstance().crearDAOFactura();
		return daoFac.delete(id);
	}

	@Override
	public Collection<TFactura> readAll() throws BBDDReadException {
		DAOFactura daoFac = FactoriaAbstractaIntegracion.getInstance().crearDAOFactura();
		return daoFac.readAll();
	}

}
