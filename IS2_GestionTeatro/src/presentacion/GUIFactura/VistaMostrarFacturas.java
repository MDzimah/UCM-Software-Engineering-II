package presentacion.GUIFactura;

import java.util.ArrayList;

import exceptions.BBDDReadException;
import misc.*;
import negocio.factura.TFactura;
import presentacion.Evento;
import presentacion.IGUI;
import presentacion.ViewUtils;
import presentacion.TablaDefault;
import presentacion.controlador.Controlador;


public class VistaMostrarFacturas implements IGUI {
	private static boolean mostrado = false;
	public VistaMostrarFacturas() {
		if (mostrado == false) {
			mostrado = true;
			Controlador.getInstance().accion(Evento.MOSTRAR_FACTURAS, null);
		}
	}
	
	@Override
	public void actualizar(Evento evento, Object datos) {
		if (evento == Evento.RES_OK) {
			ArrayList<String[]> colNames = new ArrayList<>();
			colNames.add(Messages.colNomsFactura);
			ArrayList<ArrayList<TFactura>> data = new ArrayList<>();
			data.add((ArrayList<TFactura>)datos);
			
			/*
			 ArrayList<TFactura> facturas = new ArrayList<>();
	            facturas.add(new TFactura(101, 201, true, LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS), 120.5f, 100.0f));
	            facturas.add(new TFactura(102, 202, true, LocalDateTime.now().minusDays(1).truncatedTo(ChronoUnit.SECONDS), 95.0f, 80.0f));
			ArrayList<ArrayList<TFactura>> data = new ArrayList<>();
			data.add(facturas);
			*/
			new TablaDefault<TFactura>("Facturas", colNames, data, false);
		}
		else if(evento == Evento.RES_KO) {
			String error;
			if (datos instanceof BBDDReadException) error = ((BBDDReadException)datos).getMessage();
			else error = Messages.NO_HAY_DATOS;
			ViewUtils.createErrorDialogMessage(Messages.X_MOSTRAR_FACTURAS + ' ' + Messages.MOTIVO.formatted(error));
		}
		mostrado = false;
	}
	
	/*
	public static void main(String[]a) {
		VistaMostrarFacturas v = new VistaMostrarFacturas();
	}
	*/
}
