package presentacion.GUITaquillero;

import java.util.ArrayList;

import exceptions.BBDDReadException;
import misc.Messages;
import negocio.taquillero.TTaquillero;
import presentacion.Evento;
import presentacion.IGUI;
import presentacion.TablaDefault;
import presentacion.ViewUtils;
import presentacion.controlador.Controlador;

public class VistaMostrarTaquilleros implements IGUI {
	private static boolean mostrado = false;

	public VistaMostrarTaquilleros() {
		if (!mostrado) {
			mostrado = true;
			Controlador.getInstance().accion(Evento.MOSTRAR_TAQUILLEROS, null);
		}
	}

	@Override
	public void actualizar(Evento evento, Object datos) {
		if (evento == Evento.RES_OK) {
			ArrayList<String[]> colNames = new ArrayList<>();
			colNames.add(Messages.colNomsTaquillero);

			ArrayList<ArrayList<TTaquillero>> data = new ArrayList<>();
			data.add((ArrayList<TTaquillero>) datos);

			new TablaDefault<>("Taquilleros", colNames, data, false);
		} else if (evento == Evento.RES_KO) {
			String error = (datos instanceof BBDDReadException) ? ((BBDDReadException) datos).getMessage() : Messages.NO_HAY_DATOS;
			ViewUtils.createErrorDialogMessage("No se han podido mostrar los taquilleros.\n" + Messages.MOTIVO.formatted(error));
			//El mensjae va en Messages
		}
		mostrado = false;
	}
}
