package presentacion.GUIPase;

import java.util.ArrayList;

import exceptions.BBDDReadException;
import misc.Messages;
import negocio.pase.TPase;
import presentacion.Evento;
import presentacion.IGUI;
import presentacion.TablaDefault;
import presentacion.ViewUtils;
import presentacion.controlador.Controlador;

public class VistaMostrarPases implements IGUI {
	private static boolean mostrado = false;

	public VistaMostrarPases() {
		if (!mostrado) {
			mostrado = true;
			Controlador.getInstance().accion(Evento.MOSTRAR_PASES, null);
		}
	}

	@Override
	public void actualizar(Evento evento, Object datos) {
		if (evento == Evento.RES_OK) {
			ArrayList<String[]> colNames = new ArrayList<>();
			colNames.add(Messages.colNomsPase);

			ArrayList<ArrayList<TPase>> data = new ArrayList<>();
			data.add((ArrayList<TPase>) datos);

			new TablaDefault<>("Pases", colNames, data, false);
		} else if (evento == Evento.RES_KO) {
			ViewUtils.createErrorDialogMessage(Messages.X_MOSTRAR_PASES + ' ' + Messages.MOTIVO.formatted(((Exception) datos).getMessage()));
		}
		mostrado = false;
	}
}
