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
import presentacion.Evento;
import presentacion.GUIFactura.AbrirVenta;
import presentacion.factoria.FactoriaAbstractaPresentacion;

public class SAFacturaImp implements SAFactura {

	@Override
	public boolean anyadirPaseAVenta(TLineaFactura newTLf, Collection<TLineaFactura> carrito) throws BBDDReadException {
		//Recorremos el carrito para ver si ya hay una instancia de la línea factura
		
		SAPase saP = FactoriaAbstractaNegocio.getInstance().crearSAPase(); 		
 		TPase tPase = saP.read(newTLf.getIdPase());
 		
 		if (tPase != null) {
 			boolean estaba = false;
			for(TLineaFactura tLf : carrito) {
				if (tLf.getIdPase() == newTLf.getIdPase()) {
					tLf.setCantidad(tLf.getCantidad() + newTLf.getCantidad());
					estaba = true;
					break;
				}
			}
			if (!estaba) carrito.add(newTLf);
			return true;
		}
 		else return false;
	}
	
	@Override
	public boolean quitarPaseDeVenta(TLineaFactura tLfAQuitar, Collection<TLineaFactura> carrito) throws BBDDReadException {
		ArrayList<TLineaFactura> carr = (ArrayList<TLineaFactura>) AbrirVenta.getCarrito();
			
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
		return estaba;
	}
	
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
