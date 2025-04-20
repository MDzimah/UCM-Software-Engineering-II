package presentacion.factoria;

import misc.Evento;
import presentacion.*;
import presentacion.GUIfactura.*;

/* Hay que hacer alguna forma para que la FactoriaPresentación se encargue solamente de crear
vistas y que sea el controlador solamente el que se encargue de pedir que se creen. Si no, estamos
mezcando FactoriaPresentación con el Controlador y eso hace que la arquitectura sea más sucia. Lo de 
create "NonIGUIVistas" tiene que desaparecer completamente y solamente ha de haber createVista con IGUI
*/
//SOLUCIÓN: TODAS LAS VENTANAS TIENEN Q EXTENDER IGUI SALVO LAS Q SEAN DE ERRORES Y TAL, Q SERÁN MÉTODOS DE FACTORIAABSTRACTAPRESENTACIÓN.
public class FactoriaPresentacion extends FactoriaAbstractaPresentacion {

	@Override
	public IGUI createVista(Evento e) {
		switch(e) {
		case MAINWINDOW: return new MainWindow();
		//Factura
		case ANYADIR_PASE_A_VENTA: return new VistaAddPaseVenta();
		case BUSCAR_FACTURA: return new VistaBuscarFac();
		case CERRAR_VENTA: return new VistaCerrarVenta();
		case MOSTRAR_FACTURAS: return new VistaMostrarFacs();
		case QUITAR_PASE_DE_VENTA: return new VistaQPDeVenta();
		
		
		//Cliente
		
		case ALTA_CLIENTE: return null;
		case BUSCAR_CLIENTE: return null;
		case ELIMINAR_CLIENTE: return null;
		case MOSTRAR_CLIENTE: return null;
		case ACTUALIZAR_CLIENTE:  return null;
		
		//Taquillero
		
		
		//Obra
		
		
		//CompTea
		
		
		//MiemCompTea
		case ACTUALIZAR_MIEMBRO_COMPANIA:
		case BUSCAR_MIEMBRO_COMPANIA:
		case CONTRATAR_MIEMBRO_COMPANIA:
		case DESPEDIR_MIEMBRO_COMPANIA:
		case LISTAR_MIEMBRO_COMPANIA:
		
		default: return null;
		
		}
	}
}	
