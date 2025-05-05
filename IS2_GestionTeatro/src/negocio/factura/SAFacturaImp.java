package negocio.factura;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

import exceptions.*;
import integracion.cliente.DAOCliente;
import integracion.factoria.FactoriaAbstractaIntegracion;
import integracion.factura.DAOFactura;
import integracion.factura.DAOLineaFactura;
import integracion.pase.DAOPase;
import integracion.taquillero.DAOTaquillero;
import negocio.cliente.SACliente;
import negocio.cliente.TCliente;
import negocio.factoria.FactoriaAbstractaNegocio;
import negocio.pase.SAPase;
import negocio.pase.TPase;
import negocio.taquillero.TTaquillero;

public class SAFacturaImp implements SAFactura {
	public int crearFactura(TDatosVenta tDv) throws UnknownClienteException, UnknownTaquilleroException, BBDDReadException, BBDDWriteException  {
		int idFacNueva = -1;
		
		Collection<TLineaFactura> carritoFinal = new ArrayList<TLineaFactura>();
		
		DAOCliente daoCliente = FactoriaAbstractaIntegracion.getInstance().crearDAOCliente();
		
		TCliente leidoCliente = daoCliente.read(tDv.getIdCliente());
		DAOTaquillero daoTaquillero = FactoriaAbstractaIntegracion.getInstance().crearDAOTaquillero();
		TTaquillero leidoTaquillero = daoTaquillero.read(tDv.getIdTaquillero());
		
		//Comprobamos excepciones
		if (leidoCliente == null) {
			throw new UnknownClienteException();
		}
		if (leidoTaquillero == null) {
			throw new UnknownTaquilleroException();
		}
		
		DAOPase daoPase = FactoriaAbstractaIntegracion.getInstance().crearDAOPase();
		
		//Cliente y Taquillero existen, preparamos la nueva factura
		if (!leidoCliente.getActivo()) {
			return -1;
		}
		float importeFinal = 0;
		for (TLineaFactura tLinea : tDv.getCarrito()) {
			TPase tPase = daoPase.read(tLinea.getIdPase());
			if (tPase != null) {					
				SAPase saPase = FactoriaAbstractaNegocio.getInstance().crearSAPase();
				int cantidadVendida = saPase.comprar(tPase.getIdPase(), tLinea.getCantidad());
				if (cantidadVendida > 0) {
					tLinea.setCantidad(cantidadVendida);
					tLinea.setPrecioVenta(tPase.getPrecio()*cantidadVendida);
					importeFinal += tLinea.getPrecioVenta();
					carritoFinal.add(tLinea);
				}
			}
		}
		if (carritoFinal.size() > 0) {
			SACliente saCl = FactoriaAbstractaNegocio.getInstance().crearSACliente();
			float subTotal = saCl.aplicarDescuento(tDv.getIdCliente(), importeFinal); 
			TFactura tFacFinal = new TFactura(tDv.getIdCliente(), 
					tDv.getIdTaquillero(), 
					true, 
					LocalDateTime.now(),
					subTotal,
					importeFinal);
			DAOFactura daoFac = FactoriaAbstractaIntegracion.getInstance().crearDAOFactura();
			idFacNueva = daoFac.create(tFacFinal); 
			
			
			DAOLineaFactura daoLineaFactura = FactoriaAbstractaIntegracion.getInstance().crearDAOLineaFactura();
			for (TLineaFactura tLineaFactura : carritoFinal) {
				//Damos el id de la factura a sus líneas
				tLineaFactura.setIdFactura(idFacNueva);
				int idLineaFactura = daoLineaFactura.create(tLineaFactura);		
			}
			//TAQUILLERO TIENE Q ACTUALIZARSE
		}
		
		return idFacNueva;
	}
	
	@Override
	public TFactura read(int idFactura) throws BBDDReadException {
		DAOFactura daoFac = FactoriaAbstractaIntegracion.getInstance().crearDAOFactura();
		return daoFac.read(idFactura);
	}

	@Override
	public Collection<TFactura> allFacturas() throws BBDDReadException {
		DAOFactura daoFac = FactoriaAbstractaIntegracion.getInstance().crearDAOFactura();
		return daoFac.readAll();
	}

	@Override
	public Collection<TFactura> allFacturasPorCliente(int idCliente) throws BBDDReadException {
		DAOFactura daoFac = FactoriaAbstractaIntegracion.getInstance().crearDAOFactura();
		ArrayList<TFactura> allFacs = (ArrayList<TFactura>)daoFac.readAll();

		ArrayList<TFactura> facsPorCli = new ArrayList<TFactura>();
		
		for (TFactura tFac : allFacs) {
			if (tFac.getIdCliente() == idCliente) facsPorCli.add(tFac);
		}
		
		return facsPorCli;
	}
}
