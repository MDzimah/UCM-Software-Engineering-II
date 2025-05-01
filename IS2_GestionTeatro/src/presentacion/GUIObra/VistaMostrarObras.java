package presentacion.GUIObra;

import java.util.Collection;

import misc.JSwingUtils;
import presentacion.Evento;
import presentacion.IGUI;
import presentacion.controlador.Controlador;
import presentacion.factoria.FactoriaAbstractaPresentacion;

public class VistaMostrarObras implements IGUI{

	public VistaMostrarObras() {
		Controlador.getInstance().accion(Evento.MOSTRAR_OBRAS, null);
	}
	
	@Override
	public void actualizar(Evento evento, Object datos) {
		if(evento==Evento.RES_OK) {
			String[] nomCols = {"ID","TITULO", "AUTOR", "GENERO", "SINOPSIS"};
			JSwingUtils.createTabla("OBRAS", nomCols, (Collection<Object>)datos, true, false);
		}
		else if(evento==Evento.RES_KO) {
			JSwingUtils.createErrorDialogMessage("No se han podido mostrar las obras.\n" + (String)datos);
		}
	}
}
