package negocio.factura;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

import exceptions.*;
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
import negocio.taquillero.SATaquillero;
import negocio.taquillero.TTaquillero;

public class SAFacturaImp implements SAFactura {
	public int crearFactura(TDatosVenta tDv) throws UnknownClienteException, UnknownTaquilleroException, BBDDReadException, BBDDWriteException  {
		int idFacNueva = -1;
		
		Collection<TLineaFactura> carritoFinal = new ArrayList<TLineaFactura>();
		
		//El SA FActura NO puede acceder al DAO, tiene q acceder a ellos a traves del SA de otros subs
		SACliente saCl = FactoriaAbstractaNegocio.getInstance().crearSACliente();
		SATaquillero saTaq = FactoriaAbstractaNegocio.getInstance().crearSATaquillero();
		
		TCliente leidoCliente = saCl.read(tDv.getIdCliente());
		TTaquillero leidoTaquillero = saTaq.read(tDv.getIdTaquillero());
		
		//Comprobamos excepciones
		if (leidoCliente == null) {
			throw new UnknownClienteException();
		}
		if (leidoTaquillero == null) {
			throw new UnknownTaquilleroException();
		}
		
		DAOPase daoPase = FactoriaAbstractaIntegracion.getInstance().crearDAOPase();

		//Calculamos el importe final de la factura (si el carrito tiene lineas de factura)
		if (carritoFinal.size() > 0) {
			float importeFinal = 0;
			for (TLineaFactura tLinea : tDv.getCarrito()) {
				SAPase saPase = FactoriaAbstractaNegocio.getInstance().crearSAPase();
				TPase tPase = saPase.read(tLinea.getIdPase()); //HE HECHO CAMBIOS AQUÍ
				if (tPase != null) {					
					int cantidadVendida = saPase.comprar(tPase.getIdPase(), tLinea.getCantidad());
					if (cantidadVendida > 0) {
						tLinea.setCantidad(cantidadVendida);
						tLinea.setPrecioVenta(tPase.getPrecio()*cantidadVendida);
						importeFinal += tLinea.getPrecioVenta();
						carritoFinal.add(tLinea);
					}
				}
			}
		
			//Aplicamos descuento
			float subTotal = saCl.aplicarDescuento(tDv.getIdCliente(), importeFinal); 
			
			//Creamos la factura final
			TFactura tFacFinal = new TFactura(tDv.getIdCliente(), 
					tDv.getIdTaquillero(), 
					true, 
					LocalDateTime.now(),
					subTotal,
					importeFinal);
			DAOFactura daoFac = FactoriaAbstractaIntegracion.getInstance().crearDAOFactura();
			//La guardamos en la BD
			idFacNueva = daoFac.create(tFacFinal); 
			
			//Damos el id de la factura a sus líneas y las guardamos en la BD
			DAOLineaFactura daoLineaFactura = FactoriaAbstractaIntegracion.getInstance().crearDAOLineaFactura();
			for (TLineaFactura tLineaFactura : carritoFinal) {
				tLineaFactura.setIdFactura(idFacNueva);	
				daoLineaFactura.create(tLineaFactura);
			}
			
			//Actualizamos al taquillero encargado de la factura
			saTaq.aumentarVenta(tFacFinal.getIdTaquillero());
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
		ArrayList<TFactura> allFacs = (ArrayList<TFactura>)this.allFacturas();
		ArrayList<TFactura> facsPorCli = new ArrayList<TFactura>();
		
		for (TFactura tFac : allFacs) {
			if (tFac.getIdCliente() == idCliente) facsPorCli.add(tFac);
		}
		
		return facsPorCli;
	}
}
