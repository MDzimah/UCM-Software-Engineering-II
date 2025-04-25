package presentacion.controlador;

import java.util.ArrayList;
import java.util.Collection;

import exceptions.BBDDReadException;
import misc.Evento;
import misc.Messages;
import negocio.factoria.FactoriaAbstractaNegocio;
import negocio.factura.SAFactura;
import negocio.factura.TFactura;
import negocio.factura.TLineaFactura;
import negocio.pase.SAPase;
import negocio.pase.TPase;
import presentacion.GUIfactura.VistaVentaEnCurso;
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
				SAPase saP = FactoriaAbstractaNegocio.getInstance().crearSAPase();
				
				TPase tPase = saP.read(newTLf.getIdPase());
				
				
				if (tPase != null) {
					Collection<TLineaFactura> carr = VistaVentaEnCurso.getCarrito();
					
					boolean estaba = false;
					
					//Recorremos el carrito para ver si ya hay una instancia de la línea factura
					//Si sí, entonces sumamos a la cantidad que ya había
					for(TLineaFactura tLf : carr) {
						if (tLf.getIdPase() == newTLf.getIdPase()) {
							tLf.setCantidad(tLf.getCantidad() + newTLf.getCantidad());
							estaba = true;
							break;
						}
					}
					
					//No hay instancia previa de newTLf en el carrito
					if (!estaba) carr.add(newTLf);	
					
					FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_OK, null);
				}
				else FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_KO, newTLf.getIdPase());
			}
			catch(BBDDReadException e) {
				FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_KO, e.getMessage());
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
				TFactura tFacBuscada = saFac.read(idFac);
			
				if(tFacBuscada != null)	FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_OK, tFacBuscada);
				else FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_KO, Messages.ID_NO_ENCONTRADO.formatted(idFac));
			}
			catch(BBDDReadException e) {
				 FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_KO, e.getMessage());
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
				Collection<TFactura> allFacturas = saFac.readAll();
				
				if (!allFacturas.isEmpty()) FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_OK, allFacturas); 
				else FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_KO, Messages.NO_HAY_DATOS); 
			}
			catch(BBDDReadException e) {
				 FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_KO, e.getMessage());
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
						
					if (!estaba) {
						FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_OK, null);
						break;
					}
				}
				FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_KO, tLfAQuitar.getIdPase());
			}
			catch(BBDDReadException e) {
				FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_KO, e.getMessage());
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
		
		case CREAR_PASE: 
		case ELIMINAR_PASE: 
		case CONSULTAR_PASE: 
		case BUSCAR_PASE: 
		case LISTAR_PASES: 
		
		//Obra
		
		
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
