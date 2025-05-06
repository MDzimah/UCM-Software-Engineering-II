package presentacion.GUIFactura;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

import javax.swing.*;

import exceptions.BBDDReadException;
import misc.*;
import negocio.factura.TFactura;
import presentacion.Evento;
import presentacion.IGUI;
import presentacion.ViewUtils;
import presentacion.TablaDefault;
import presentacion.VistaDefault;
import presentacion.controlador.Controlador;


@SuppressWarnings("serial")
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
		evento = Evento.RES_OK;
		if (evento == Evento.RES_OK) {
			String[][String[]] colNames = new String[1][1]; 
			colNames[1][1] = Messages.colNomsFactura;
			this.createTable("FACTURAS", Messages.colNomsFactura, (ArrayList<TFactura>)datos, false);
		}
		else if(evento == Evento.RES_KO) {
			String error;
			if (datos instanceof BBDDReadException) error = ((BBDDReadException)datos).getMessage();
			else error = Messages.NO_HAY_DATOS;
			ViewUtils.createErrorDialogMessage(Messages.X_MOSTRAR_FACTURAS + ' ' + Messages.MOTIVO.formatted(error));
		}
		mostrado = false;
	}
}
