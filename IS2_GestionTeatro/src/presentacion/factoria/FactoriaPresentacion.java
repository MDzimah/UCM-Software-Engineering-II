package presentacion.factoria;

import presentacion.*;
import presentacion.GUIfactura.*;
import presentacion.GUIcliente.*;

public class FactoriaPresentacion extends FactoriaAbstractaPresentacion {

	@Override
	public IGUI createVista(int idEvento) {
		switch(Evento.intAEvento(idEvento)) {
		//Factura
		case ANYADIR_PASE_A_VENTA: return new VistaAddPaseVenta();
		case BUSCAR_FACTURA: return new VistaBuscarFac();
		case CERRAR_VENTA: return new VistaCerrarVenta();
		case MOSTRAR_FACTURAS: return new VistaMostrarFacs();
		case QUITAR_PASE_DE_VENTA: return new VistaQPDeVenta();
		
		
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
