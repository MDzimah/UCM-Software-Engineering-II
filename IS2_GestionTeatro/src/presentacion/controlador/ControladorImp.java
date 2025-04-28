package presentacion.controlador;

import java.util.Collection;
import java.util.List;

import exceptions.BBDDReadException;
import misc.Evento;
import negocio.factoria.FactoriaAbstractaNegocio;
import negocio.factura.*;
import negocio.obra.*;
import negocio.pase.SAPase;
import negocio.pase.TPase;
import presentacion.GUIFactura.VistaVentaEnCurso;
import presentacion.factoria.FactoriaAbstractaPresentacion;

public class ControladorImp extends Controlador {
	@Override
	public void accion(Evento evento, Object datos) {
		switch(evento) {
		//Factura
		case ANYADIR_PASE_A_VENTA: {
			//Es un mensaje de error
			if (datos instanceof String) {
				FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_KO, (String)datos);
				break;
			}
			try {
				TLineaFactura newTLf = (TLineaFactura)datos;
				SAFactura saFac = FactoriaAbstractaNegocio.getInstance().crearSAFactura();
				int res = saFac.anyadirPaseAVenta(newTLf, VistaVentaEnCurso.getCarrito());
				
				if (res == 1) FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_OK, null);
				else if (res == 0) FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_KO, newTLf.getIdPase());
				else FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_KO, null);
			}
			catch(BBDDReadException e) {
				FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_KO, e);
			}
			break;
		}
		case QUITAR_PASE_DE_VENTA: {
			if (datos instanceof String) {
				FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_KO, (String)datos);
				break;
			}
			
			try {
				TLineaFactura tLfAQuitar = (TLineaFactura)datos;
				SAFactura saFac = FactoriaAbstractaNegocio.getInstance().crearSAFactura();
				int res = saFac.quitarPaseDeVenta(tLfAQuitar, VistaVentaEnCurso.getCarrito());
						
				if (res == 1) FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_OK, null);
				else if (res == 0) FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_KO, tLfAQuitar.getIdPase());
				else FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_KO, null);
			}
			catch(BBDDReadException e) {
				FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_KO, e);
			}
			break;
		}
		case BUSCAR_FACTURA: {
			if (datos instanceof String) {
				FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_KO, (String)datos);
				break;
			}
			try {
				int idFac = (int)datos;
				SAFactura saFac = FactoriaAbstractaNegocio.getInstance().crearSAFactura();
				TFactura tFacBuscada = saFac.buscarFactura(idFac);
			
				if(tFacBuscada != null)	FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_OK, tFacBuscada);
				else FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_KO, idFac);
			}
			catch(BBDDReadException e) {
				 FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_KO, e);
			}
			
			break;
		}
		case CERRAR_VENTA: {
			
			//Creación de la factura, tratamiento de las 4 excepciones
			
			break;
		}
		case MOSTRAR_FACTURAS: {
			try {
				SAFactura saFac = FactoriaAbstractaNegocio.getInstance().crearSAFactura();
				Collection<TFactura> allFacturas = saFac.facturasActivas();
				
				if (!allFacturas.isEmpty()) FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_OK, allFacturas); 
				else FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_KO, null); 
			}
			catch(BBDDReadException e) {
				 FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_KO, e);
			}
			break;
		}
		
		
		
		
		//Cliente
		
		case ALTA_CLIENTE: 
		case BUSCAR_CLIENTE: 
		case ELIMINAR_CLIENTE:
		case MOSTRAR_CLIENTE: 
		case ACTUALIZAR_CLIENTE: 
		
		//Taquillero
			
			
		//Pase
		
		case CREAR_PASE: {
			try {
				SAPase saPase = FactoriaAbstractaNegocio.getInstance().crearSAPase();
				saPase.create((TPase) datos);
				FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_OK, null);
			} catch(Exception e) {
				FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_KO, e.getMessage());
			}
		}
		case ELIMINAR_PASE: {
			try {
				SAPase saPase = FactoriaAbstractaNegocio.getInstance().crearSAPase();
				int idBuscado = ((TPase) datos).getIdPase();
				saPase.delete(idBuscado);
				FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_OK, null);
			} catch(Exception e) {
				FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_KO, e.getMessage());
			}
		}
		case CONSULTAR_PASE: 
		case BUSCAR_PASE: {
			try {
				SAPase saPase = FactoriaAbstractaNegocio.getInstance().crearSAPase();
				int idBuscado = ((TPase) datos).getIdPase();
				TPase tPaseBuscado = saPase.read(idBuscado);
				FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_OK, tPaseBuscado); //le paso el transfer para que lo muestre
			} catch(Exception e) {
				FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_KO, e.getMessage());
			}
		}
		case LISTAR_PASES:{
			try {
				SAPase saPase = FactoriaAbstractaNegocio.getInstance().crearSAPase();
				int idBuscado = ((TPase) datos).getIdPase();
				TPase tPaseBuscado = saPase.read(idBuscado);
				FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_OK, null); //le paso el transfer para que lo muestre
			} catch(Exception e) {
				FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_KO, e.getMessage());
			}
		}
		
		//Obra
		case CREAR_OBRA:{
			try{
				SAObra saObra = FactoriaAbstractaNegocio.getInstance().crearSAObra();
				int val = saObra.create((TObra)datos);
				FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_OK, val);
			}
			catch(Exception e) {
				FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_KO, "Error: " +e.getMessage());
			}
			break;
		}
		case ELIMINAR_OBRA:{
			try {
				SAObra saObra = FactoriaAbstractaNegocio.getInstance().crearSAObra();
				saObra.delete((int)datos);
				FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_OK, (int)datos);
			}
			catch(Exception e) {
				FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_KO, "Error: " +e.getMessage());
			}
			break;
		}
		case MODIFICAR_OBRA:
		{
			try {
				SAObra saObra = FactoriaAbstractaNegocio.getInstance().crearSAObra();
				saObra.update((TObra)datos);
				FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_OK, (TObra)datos);
			}
			catch(Exception e) {
				FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_KO, "Error: " +e.getMessage());
			}
			break;
		}
		case CONSULTAR_OBRA:
		{
			try {
				SAObra saObra = FactoriaAbstractaNegocio.getInstance().crearSAObra();
				TObra obra = saObra.read((int)datos);
				FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_OK, obra);
			}
			catch(Exception e) {
				FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_KO, "Error: " +e.getMessage());
			}
			break;
		}
		case BUSCAR_OBRA:
		{
			try {
				SAObra saObra = FactoriaAbstractaNegocio.getInstance().crearSAObra();
				List<TObra> obras = (List<TObra>) saObra.search((List<String>)datos);
				FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_OK, obras);
			}
			catch(Exception e) {
				FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_KO, "Error: " +e.getMessage());
			}
			break;
		}
		case LISTAR_OBRAS:
		{
			try {
				SAObra saObra = FactoriaAbstractaNegocio.getInstance().crearSAObra();
				List<TObra> obras = (List<TObra>) saObra.readActive();
				FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_OK, obras);
			}
			catch(Exception e) {
				FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_KO, "Error: " +e.getMessage());
			}
			break;
		}
		
		//CompTea
		
		
		//MiemCompTea
		case ACTUALIZAR_MIEMBRO_COMPANIA:
		case BUSCAR_MIEMBRO_COMPANIA:
		case CONTRATAR_MIEMBRO_COMPANIA:
		case DESPEDIR_MIEMBRO_COMPANIA:
		case LISTAR_MIEMBRO_COMPANIA:
		
		//Todas las ventanas que abren a otras (las que no sean de CU)
		default: 
			switch((Evento) datos) {
				case MENU_FACTURA: FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.MENU_FACTURA, null);
				
				default:
			}
		}
	}

}
