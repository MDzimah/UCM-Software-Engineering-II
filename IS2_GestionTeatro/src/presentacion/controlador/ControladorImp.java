package presentacion.controlador;

import java.util.ArrayList;
import java.util.Collection;

import eventos.Evento;
import exceptions.BBDDReadException;
import misc.PanelUtils;
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
				
				FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_ANYADIR_PASE_A_VENTA_OK, null);
			}
			else FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_ANYADIR_PASE_A_VENTA_KO, newTLf.getIdPase());
			break;
		}
		case BUSCAR_FACTURA: {
			int idFac = (int)datos;
			SAFactura saFac = FactoriaAbstractaNegocio.getInstance().crearSAFactura();
			try {
				TFactura tFacBuscada = saFac.read(idFac);
			
				if(tFacBuscada != null)	FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_BUSCAR_FACTURA_OK, tFacBuscada);
				else FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_BUSCAR_FACTURA_KO, idFac);
			}
			catch(BBDDReadException e) {
				PanelUtils.panelBBDDReadError(null, e.getMessage());
			}
			
			break;
		}
		case CERRAR_VENTA: {
			
			
			break;
		}
		case MOSTRAR_FACTURAS: {
			SAFactura saFac = FactoriaAbstractaNegocio.getInstance().crearSAFactura();
			try {
				Collection<TFactura> allFacturas = saFac.readAll();
				
				if (!allFacturas.isEmpty()) FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_MOSTRAR_FACTURAS_OK, allFacturas); 
				else FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_MOSTRAR_FACTURAS_KO, null); 
			}
			catch(BBDDReadException e) {
				PanelUtils.panelBBDDReadError(null, e.getMessage());
			}
			break;
		}
		case QUITAR_PASE_DE_VENTA: {
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
				
				if (!estaba) FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_QUITAR_PASE_DE_VENTA_OK, null);
			}
			FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_QUITAR_PASE_DE_VENTA_KO, tLfAQuitar.getIdPase());
			break;
		}
		
		
		//Cliente
		
		case ALTA_CLIENTE: 
		case BUSCAR_CLIENTE: 
		case ELIMINAR_CLIENTE:
		case MOSTRAR_CLIENTE: 
		case ACTUALIZAR_CLIENTE: 
		
		//Taquillero
		
		
		//Obra
		
		
		//CompTea
		
		
		//MiemCompTea
		
		default: 
		
		}
	}

}
