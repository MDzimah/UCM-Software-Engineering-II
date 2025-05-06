package presentacion.GUIObra;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import exceptions.BBDDReadException;
import misc.Messages;
import negocio.obra.TObra;
import presentacion.Evento;
import presentacion.IGUI;
import presentacion.TablaDefault;
import presentacion.ViewUtils;
import presentacion.controlador.Controlador;

public class VistaMostrarObras implements IGUI {
	private static boolean mostrado = false;

	public VistaMostrarObras() {
		if (!mostrado) {
			mostrado = true;
			Controlador.getInstance().accion(Evento.MOSTRAR_OBRAS, null);
		}
	}

	@Override
	public void actualizar(Evento evento, Object datos) {
		if (evento == Evento.RES_OK) {
			ArrayList<String[]> colNames = new ArrayList<>();
			colNames.add(Messages.colNomsObra);

			ArrayList<ArrayList<TObra>> data = new ArrayList<>();
			
			ArrayList<TObra> info= new ArrayList<TObra>();
			for(int i =0 ; i<((List<TObra>) datos).size();++i)
				info.add(((List<TObra>) datos).get(i));
			
			info.sort((p1, p2) -> Integer.compare(p1.getIdObra(), p2.getIdObra()));
			data.add(info);

			new TablaDefault<>("OBRAS", colNames, data, false);
		} else if (evento == Evento.RES_KO) {
			String error = (datos instanceof BBDDReadException) ? ((BBDDReadException) datos).getMessage() : Messages.NO_HAY_DATOS;
			ViewUtils.createErrorDialogMessage(Messages.EX_OBRA_MOSTRAR_ERROR + '\n' + Messages.ERROR.formatted(error));
		}
		mostrado = false;
	}
}
