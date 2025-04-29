package presentacion.GUIObra;

import java.util.Collection;

import misc.Evento;
import presentacion.IGUI;
import presentacion.controlador.Controlador;
import presentacion.factoria.FactoriaAbstractaPresentacion;

public class VistaMostrarActivas implements IGUI{

	public VistaMostrarActivas() {
		Controlador.getInstance().accion(Evento.LISTAR_OBRAS, null);
	}
	
	@Override
	public void actualizar(Evento evento, Object datos) {
		if(evento==Evento.RES_OK) {
			String[] nomCols = {"ID","TITULO", "AUTOR", "GENERO", "SINOPSIS"};
			FactoriaAbstractaPresentacion.getInstance().createTabla("MOSTRAR OBRAS ACTIVAS", nomCols, (Collection<Object>)datos, true);	
		}
		else if(evento==Evento.RES_KO) {
			FactoriaAbstractaPresentacion.getInstance().createDialogMessage("No se han podido mostrar las obras.\n" + (String)datos);
		}
	}
}
