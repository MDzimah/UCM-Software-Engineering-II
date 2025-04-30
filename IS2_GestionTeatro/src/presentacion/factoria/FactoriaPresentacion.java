package presentacion.factoria;

import presentacion.*;
import presentacion.GUIFactura.*;
import presentacion.GUIObra.VistaActualizarObra;
import presentacion.GUIObra.VistaAltaObra;
import presentacion.GUIObra.VistaBajaObra;
import presentacion.GUIObra.VistaBuscarObra;
import presentacion.GUIObra.VistaConsultarObra;
import presentacion.GUIObra.VistaMostrarObras;
import presentacion.GUIPase.VistaActualizarPase;
import presentacion.GUIPase.VistaBuscarPase;
import presentacion.GUIPase.VistaConsultarPase;
import presentacion.GUIPase.VistaCrearPase;
import presentacion.GUIPase.VistaEliminarPase;
import presentacion.GUIPase.VistaListarPases;

public class FactoriaPresentacion extends FactoriaAbstractaPresentacion {

	@Override
	public IGUI createVista(Evento e) {
		switch(e) {
		case MAINWINDOW: return new MainWindow();
		//Factura
		case ANYADIR_PASE_A_VENTA: return new VistaAnyadirPaseAVenta();
		case BUSCAR_FACTURA: return new VistaBuscarFac();
		case CERRAR_VENTA: return new VistaCerrarVenta();
		case MOSTRAR_FACTURAS: return new VistaMostrarFacs();
		case QUITAR_PASE_DE_VENTA: return new VistaQuitarPaseDeVenta();
		
		
		//Cliente
		
		case ALTA_CLIENTE: return null;
		case BUSCAR_CLIENTE: return null;
		case ELIMINAR_CLIENTE: return null;
		case MOSTRAR_CLIENTE: return null;
		case ACTUALIZAR_CLIENTE:  return null;
		
		//Taquillero
		
		//Pase
		
		case CREAR_PASE: return new VistaCrearPase();
		case ELIMINAR_PASE: return new VistaEliminarPase();
		case BUSCAR_PASE: return new VistaBuscarPase();
		case LISTAR_PASES: return new VistaListarPases();
		case ACTUALIZAR_PASE: return new VistaActualizarPase();

		//Obra
		case CREAR_OBRA: return new VistaAltaObra();
		case ELIMINAR_OBRA: return new VistaBajaObra();
		case ACTUALIZAR_OBRA: return new VistaActualizarObra();
		case CONSULTAR_OBRA: return new VistaBuscarObra();
		case BUSCAR_OBRA: return new VistaConsultarObra();
		case LISTAR_OBRAS: return new VistaMostrarObras();
		
		//CompTea
		
		
		//MiemCompTea
		case ACTUALIZAR_MIEMBRO_COMPANIA:
		case BUSCAR_MIEMBRO_COMPANIA:
		case CONTRATAR_MIEMBRO_COMPANIA:
		case DESPEDIR_MIEMBRO_COMPANIA:
		case LISTAR_MIEMBROS_COMPANIA:
		
		default: return null;
		
		}
	}
}	
