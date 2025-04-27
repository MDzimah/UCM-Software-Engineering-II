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
import misc.Evento;
import misc.Messages;
import negocio.cliente.SACliente;
import negocio.cliente.TCliente;
import negocio.factoria.FactoriaAbstractaNegocio;
import negocio.pase.SAPase;
import negocio.pase.TPase;
import negocio.taquillero.TTaquillero;
import presentacion.GUIFactura.VistaVentaEnCurso;
import presentacion.factoria.FactoriaAbstractaPresentacion;

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
				//TAQUILLERO TIENE Q ACTUALIZARSE
			}
		}
		
		return idFacNueva;
	}

	@Override
	public int anyadirPaseAVenta(TLineaFactura newTLf, Collection<TLineaFactura> carrito) throws BBDDReadException {
		SAPase saP = FactoriaAbstractaNegocio.getInstance().crearSAPase();
		
		TPase tPase = saP.read(newTLf.getIdPase());
		
		boolean estaba = false;
		
		if (tPase != null) {
			Collection<TLineaFactura> carr = VistaVentaEnCurso.getCarrito();
			
			//Recorremos el carrito para ver si ya hay una instancia de la línea factura
			//Si sí, entonces sumamos a la cantidad que ya había (asegurando que queda stock)
			for(TLineaFactura tLf : carr) {
				if (tLf.getIdPase() == newTLf.getIdPase()) {
					if (tPase.getStock() - tLf.getCantidad() - newTLf.getCantidad() >= 0) tLf.setCantidad(tLf.getCantidad() + newTLf.getCantidad());
					else return -1;
					estaba = true;
					break;
				}
			}
			
			//No hay instancia previa de newTLf en el carrito
			if (!estaba) {
				if (tPase.getStock() - newTLf.getCantidad() >= 0) carrito.add(newTLf);	
				else return -1;
			}
			
			return 1;
		}
		else return 0;
	}
	
	@Override
	public int quitarPaseDeVenta(TLineaFactura tLfAQuitar, Collection<TLineaFactura> carrito) throws BBDDReadException {
		SAPase saP = FactoriaAbstractaNegocio.getInstance().crearSAPase();
		
		TPase tPase = saP.read(tLfAQuitar.getIdPase());
		
		if (tPase != null) {
			ArrayList<TLineaFactura> carr = (ArrayList<TLineaFactura>) VistaVentaEnCurso.getCarrito();
			
			boolean estaba = false;
			for(int i = 0; i < carr.size(); ++i) {
				TLineaFactura tLf = carr.get(i);
				if (tLf.getIdPase() == tLfAQuitar.getIdPase()) {
					tLf.setCantidad(tLf.getCantidad() - tLfAQuitar.getCantidad());
					if (carr.get(i).getCantidad() <= 0) carr.remove(i);
					estaba = true;
					break;
				}
			}
				
			if (estaba) return 1;
			else return -1;
		}
		else return 0;
	}
	
	@Override
	public TFactura buscarFactura(int id) throws BBDDReadException {
		DAOFactura daoFac = FactoriaAbstractaIntegracion.getInstance().crearDAOFactura();
		return daoFac.read(id);
	}

	@Override
	public Collection<TFactura> facturasActivas() throws BBDDReadException {
		DAOFactura daoFac = FactoriaAbstractaIntegracion.getInstance().crearDAOFactura();
		return daoFac.readAll();
	}

	@Override
	public Collection<TFactura> facturasPorCliente(int idCliente) throws BBDDReadException {
		DAOFactura daoFac = FactoriaAbstractaIntegracion.getInstance().crearDAOFactura();
		ArrayList<TFactura> allFacs = (ArrayList<TFactura>)daoFac.readAll();

		ArrayList<TFactura> facsPorCli = new ArrayList<TFactura>();
		
		for (TFactura tFac : allFacs) {
			if (tFac.getIdCliente() == idCliente) facsPorCli.add(tFac);
		}
		
		return facsPorCli;
	}
}
