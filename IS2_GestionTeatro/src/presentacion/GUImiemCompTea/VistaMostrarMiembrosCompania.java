package presentacion.GUIMiemCompTea;

import java.util.Collection;

import misc.JSwingUtils;
import misc.Messages;
import negocio.miemCompTea.TMiemCompTea;
import presentacion.Evento;
import presentacion.VistaDefault;
import presentacion.controlador.Controlador;
import presentacion.factoria.FactoriaAbstractaPresentacion;

public class VistaMostrarMiembrosCompania extends VistaDefault{
	
	public VistaMostrarMiembrosCompania() {
		Controlador.getInstance().accion(Evento.MOSTRAR_MIEMBROS_COMPANIA, null);
	}

	@Override
	public void actualizar(Evento evento, Object datos) {
		if (evento == Evento.RES_OK) {
			JSwingUtils.createTabla("MIEMBROS DE LAS COMPAÑÍAS TEATRALES", Messages.colNomsMiemCompTea, (Collection<TMiemCompTea>)datos, true, false);
		}
		else if (evento == Evento.RES_KO) {
			String error;
			if (datos instanceof String) error = (String) datos;
			else error = Messages.NO_HAY_DATOS;
			JSwingUtils.createErrorDialogMessage(Messages.X_MIEMBROS_LISTADOS + ' ' + Messages.MOTIVO.formatted(error));
		}
		
	}
	
}
