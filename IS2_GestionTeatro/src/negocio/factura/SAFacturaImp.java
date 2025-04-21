package negocio.factura;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

import exceptions.BBDDReadException;
import exceptions.BBDDWriteException;
import exceptions.UnknownClienteException;
import exceptions.UnknownTaquilleroException;
import integracion.cliente.DAOCliente;
import integracion.factoria.FactoriaAbstractaIntegracion;
import integracion.factura.DAOFactura;
import integracion.factura.DAOLineaFactura;
import integracion.pase.DAOPase;
import integracion.taquillero.DAOTaquillero;
import misc.Messages;
import negocio.cliente.SACliente;
import negocio.cliente.TCliente;
import negocio.factoria.FactoriaAbstractaNegocio;
import negocio.pase.SAPase;
import negocio.pase.TPase;
import negocio.taquillero.TTaquillero;

public class SAFacturaImp implements SAFactura {
	
	@Override
	public int create(TDatosVenta tDv) throws UnknownClienteException, UnknownTaquilleroException, BBDDReadException, BBDDWriteException  {
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
		if (leidoCliente.getActivo()) {
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
						carritoFinal,
						subTotal,
						importeFinal);
				DAOFactura daoFac = FactoriaAbstractaIntegracion.getInstance().crearDAOFactura();
				idFacNueva = daoFac.create(tFacFinal); 
				
				
				DAOLineaFactura daoLineaFactura = FactoriaAbstractaIntegracion.getInstance().crearDAOLineaFactura();
				for (TLineaFactura tLineaFactura : tFactura.getCarrito()) {
					//Damos el id de la factura a sus líneas
					tLineaFactura.setIdFactura(newId);
					int idLineaFactura = daoLineaFactura.create(tLineaFactura);
					carrito.put(idLineaFactura);
					
				}
				//Internamente usa DAOLineaFactura y da el id de la factura a las líneas
				//TAQUILLERO TIENE Q ACTUALIZARSE
			}
		}
		
		return idFacNueva;
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
