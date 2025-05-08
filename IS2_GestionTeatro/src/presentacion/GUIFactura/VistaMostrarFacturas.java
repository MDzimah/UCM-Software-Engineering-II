package presentacion.GUIFactura;

//import java.time.LocalDateTime;
//import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import exceptions.BBDDReadException;
import misc.*;
import negocio.factura.TFactura;
import presentacion.Evento;
import presentacion.IGUI;
import presentacion.TablaDefault;
import presentacion.ViewUtils;
//import presentacion.TablaDefault;
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
			new TablaDefault<TFactura>("Facturas", colNames, data, false);
		}
		else if(evento == Evento.RES_KO) {
			String error = ((Exception)datos).getMessage();
			/*
			if (datos instanceof BBDDReadException) error = ((BBDDReadException)datos).getMessage();
			else error = Messages.NO_HAY_DATOS;
			*/
			ViewUtils.createErrorDialogMessage(Messages.X_MOSTRAR_FACTURAS + ' ' + Messages.MOTIVO.formatted(error));
		}
		mostrado = false;
	}
}
