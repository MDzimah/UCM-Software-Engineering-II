package presentacion.controlador;

import presentacion.Evento;

public class ControladorImp extends Controlador {

	@Override
	public void accion(int evento, Object datos) {
		switch(Evento.intAEvento(evento)) {
		//Factura
		case ANYADIR_PASE_A_VENTA: 
		case BUSCAR_FACTURA: 
		case CERRAR_VENTA: 
		case MOSTRAR_FACTURAS: 
		case QUITAR_PASE_DE_VENTA: 
		
		
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
