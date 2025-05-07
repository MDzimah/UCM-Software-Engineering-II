package presentacion.GUIMiemCompTea;

import java.util.ArrayList;

import exceptions.BBDDReadException;
import misc.Messages;
import negocio.miemCompTea.TMiemCompTea;
import presentacion.Evento;
import presentacion.IGUI;
import presentacion.TablaDefault;
import presentacion.ViewUtils;
import presentacion.controlador.Controlador;

public class VistaMostrarMiembrosCompania implements IGUI {
	private static boolean mostrado = false;

	public VistaMostrarMiembrosCompania() {
		if (!mostrado) {
			mostrado = true;
			Controlador.getInstance().accion(Evento.MOSTRAR_MIEMBROS_COMPANIA, null);
		}
	}

	@Override
	public void actualizar(Evento evento, Object datos) {
		if (evento == Evento.RES_OK) {
			ArrayList<String[]> colNames = new ArrayList<>();
			colNames.add(Messages.colNomsMiemCompTea);

			ArrayList<ArrayList<TMiemCompTea>> data = new ArrayList<>();
			data.add((ArrayList<TMiemCompTea>) datos);

			new TablaDefault<>("Miembros de la compañía teatral", colNames, data, false);
		} else if (evento == Evento.RES_KO) {
			String error;
			if(datos instanceof Exception) error = ((Exception) datos).getMessage();
			else error = Messages.NO_HAY_DATOS;
			ViewUtils.createErrorDialogMessage(Messages.X_MIEMBROS_LISTADOS + ' ' + Messages.MOTIVO.formatted(error));
		}
		mostrado = false;
	}
}
