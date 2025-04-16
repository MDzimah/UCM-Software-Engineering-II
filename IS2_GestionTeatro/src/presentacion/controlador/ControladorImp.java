package presentacion.controlador;

import java.util.Collection;

import eventos.Evento;
import misc.Pair;
import negocio.factoria.FactoriaAbstractaNegocio;
import negocio.factura.SAFactura;
import negocio.factura.TFactura;
import negocio.factura.TLineaFactura;
import negocio.obra.SAObra;
import negocio.pase.SAPase;
import negocio.pase.TPase;
import presentacion.factoria.FactoriaAbstractaPresentacion;

public class ControladorImp extends Controlador {

	@SuppressWarnings("unchecked")
	@Override
	public void accion(Evento evento, Object datos) {
		switch(evento) {
		
		//Factura
		case ANYADIR_PASE_A_VENTA: {
			Pair<TFactura, TLineaFactura> facYNuevaLinea = (Pair<TFactura, TLineaFactura>)datos;
			TLineaFactura newTlf = facYNuevaLinea.getSecond();
			SAPase saP = FactoriaAbstractaNegocio.getInstance().crearSAPase();
			FactoriaAbstractaNegocio.getInstance().crearSAObra().readByName().getId();
			
			TPase tPase = saP.readByTitleAndDate();
			
			if (tPase != null) {
				newTlf.setPrecioVenta(tPase.getPrecio());
				newTlf.setFechaPase(tPase.getFecha());
				newTlf.setIdPase(tPase.getIdPase());
				TFactura tFac = facYNuevaLinea.getFirst();
				
				//SET ID FACTURA?????? No se hace lo de la fac nueva al cerrarVenta??? newTlf.setIdFactura(...)
				//Quizás JAIME (PARA QUE LO VEAS) cuando cierras venta tienes q hacer un método q recorra el carrito y de a todos los TLineaFactura los id de su factura
				newTlf.setIdLineaFactura(tFac.addToCarrito(newTlf));
				
				FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_ANYADIR_PASE_A_VENTA_OK, null);
			}
			else FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_ANYADIR_PASE_A_VENTA_KO, newTlf.getTituloObra() + ' ' + newTlf.getFechaPase());
			break;
		}
		case BUSCAR_FACTURA: {
			int idFac = (int)datos;
			
			SAFactura saFac = FactoriaAbstractaNegocio.getInstance().crearSAFactura();
			TFactura tFacBuscada = saFac.read(idFac);
			
			if(tFacBuscada != null)	FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_BUSCAR_FACTURA_OK, tFacBuscada);
			else FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_BUSCAR_FACTURA_KO, idFac);
			
			break;
		}
		case CERRAR_VENTA: {
			
			
			break;
		}
		case MOSTRAR_FACTURAS: {
			SAFactura saFac = FactoriaAbstractaNegocio.getInstance().crearSAFactura();
			Collection<TFactura> allFacturas = saFac.readAll();
			
			if (!allFacturas.isEmpty()) FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_MOSTRAR_FACTURAS_OK, allFacturas); 
			else  FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_MOSTRAR_FACTURAS_KO, null); 
			break;
		}
		case QUITAR_PASE_DE_VENTA: {
			Pair<TFactura, TLineaFactura> facYLineaParaQuitar = (Pair<TFactura, TLineaFactura>)datos;
			TLineaFactura tLf = facYLineaParaQuitar.getSecond();
			SAFactura saFac = FactoriaAbstractaNegocio.getInstance().crearSAFactura();
			
			//if (/*EXiste TLineaFactura en la Factura*/) {
				//...
				FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_ANYADIR_PASE_A_VENTA_OK, null);
			//}
			//else 
				FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_ANYADIR_PASE_A_VENTA_KO, tLf.getTituloObra() + ' ' + tLf.getFechaPase());
			
			
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
