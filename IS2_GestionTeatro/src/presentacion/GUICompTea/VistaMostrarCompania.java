package presentacion.GUICompTea;

import java.util.ArrayList;

import exceptions.BBDDReadException;
import misc.Messages;
import negocio.compTea.TCompTea;
import presentacion.Evento;
import presentacion.IGUI;
import presentacion.TablaDefault;
import presentacion.ViewUtils;
import presentacion.controlador.Controlador;

public class VistaMostrarCompania implements IGUI {
	private static boolean mostrado = false;

	public VistaMostrarCompania() {
		if (!mostrado) {
			mostrado = true;
			Controlador.getInstance().accion(Evento.MOSTRAR_COMPANIA_TEATRAL, null);
		}
	}

	@Override
	public void actualizar(Evento evento, Object datos) {
		if (evento == Evento.RES_OK) {
			String[] nomCols = Messages.colNomsCompTea;
			ArrayList<String[]>listaColumnas= new ArrayList<String[]>();
			listaColumnas.add(nomCols);
			ArrayList<ArrayList<TCompTea>> data = new ArrayList<>();
			data.add((ArrayList<TCompTea>) datos);

			new TablaDefault("Compañías teatrales", listaColumnas, data, false);
		} else if (evento == Evento.RES_KO) {
			String error = (datos instanceof BBDDReadException) ? ((BBDDReadException) datos).getMessage() : Messages.NO_HAY_DATOS;
			ViewUtils.createErrorDialogMessage(Messages.X_MOSTRAR_COMPANIAS + ' ' + Messages.MOTIVO.formatted(error));
		}
		mostrado = false;
	}
}
