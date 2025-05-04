package presentacion.factoria;

import presentacion.*;
import presentacion.GUIMiemCompTea.*;
import presentacion.GUIFactura.*;
import presentacion.GUICompTea.*;
import presentacion.GUIObra.*;
import presentacion.GUIPase.*;
import presentacion.GUITaquillero.*;

public class FactoriaPresentacion extends FactoriaAbstractaPresentacion {

	@Override
	public IGUI createVista(Evento e) {
		switch(e) {
		//Factura
		case ANYADIR_PASE_A_VENTA: return new VistaAnyadirPaseAVenta();
		case BUSCAR_FACTURA: return new VistaBuscarFac();
		case CERRAR_VENTA: return new VistaCerrarVenta();
		case MOSTRAR_FACTURAS: return new VistaMostrarFacs();
		case QUITAR_PASE_DE_VENTA: return new VistaQuitarPaseDeVenta();
		
		//Cliente
		case ALTA_CLIENTE: return null;
		case BUSCAR_CLIENTE: return null;
		case BAJA_CLIENTE: return null;
		case MOSTRAR_CLIENTES: return null;
		case ACTUALIZAR_CLIENTE:  return null;
		
		//Taquillero
		case ALTA_TAQUILLERO: return new VistaContratarTaquillero();
		case BAJA_TAQUILLERO: return new VistaDespedirTaquillero();
		case BUSCAR_TAQUILLERO: return new VistaBuscarTaquillero();
		case MOSTRAR_TAQUILLEROS: return new VistaMostrarTaquilleros();
		case ACTUALIZAR_TAQUILLERO: return new VistaActualizarTaquillero_0();
		
		//Pase
		case ALTA_PASE: return new VistaAltaPase();
		case BAJA_PASE: return new VistaBajaPase();
		case BUSCAR_PASE: return new VistaBuscarPase();
		case MOSTRAR_PASES: return new VistaMostrarPases();
		case ACTUALIZAR_PASE_CARGA: return new VistaActualizarPaseCarga();
		case ACTUALIZAR_PASE_DESCARGA: return new VistaActualizarPaseDescarga();

		//Obra
		case ALTA_OBRA: return new VistaAltaObra();
		case BAJA_OBRA: return new VistaBajaObra();
		case ACTUALIZAR_OBRA_0: return new VistaActualizarObra_0();
		case ACTUALIZAR_OBRA_1: return new VistaActualizarObra_1();
		case CONSULTAR_OBRA: return new VistaConsultarObra();
		case BUSCAR_OBRA: return new VistaBuscarObras();
		case MOSTRAR_OBRAS: return new VistaMostrarObras();
		
		//CompTea
		case ALTA_COMPANIA_TEATRAL:  return new VistaAltaCompania();
		case ACTUALIZAR0_COMPANIA_TEATRAL: return new VistaActualizarCompania(); // actualizar0 O actualizar1
		case BUSCAR_COMPANIA_TEATRAL: return new VistaBuscarCompania();
		case BAJA_COMPANIA_TEATRAL: return new VistaBajaCompTea();
		case MOSTRAR_COMPANIA_TEATRAL: return new VistaMostrarCompania();
		
		//MiemCompTea
		case ACTUALIZAR_MIEMBRO_COMPANIA_0: return new VistaActualizarMiembroCompania_0();
		case ACTUALIZAR_MIEMBRO_COMPANIA_1: return new VistaActualizarMiembroCompania_1();
		case BUSCAR_MIEMBRO_COMPANIA: return new VistaBuscarMiembroCompania();
		case CONTRATAR_MIEMBRO_COMPANIA: return new VistaContratarMiembroCompania();
		case DESPEDIR_MIEMBRO_COMPANIA: return new VistaDespedirMiembroCompania();
		case MOSTRAR_MIEMBROS_COMPANIA: return new VistaMostrarMiembrosCompania();
		
		default: return null;
		}
	}
	
}	
