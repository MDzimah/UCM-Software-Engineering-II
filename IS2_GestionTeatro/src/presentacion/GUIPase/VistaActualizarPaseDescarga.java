package presentacion.GUIPase;

import java.util.ArrayList;
import java.util.Collection;

import misc.JSwingUtils;
import misc.Messages;
import negocio.factoria.FactoriaAbstractaNegocio;
import negocio.pase.TPase;
import presentacion.Evento;
import presentacion.IGUI;
import presentacion.TablaDefault;
import presentacion.controlador.Controlador;

public class VistaActualizarPaseDescarga implements IGUI {
	
	public void cargarPase(TPase datos) {
		ArrayList<TPase> p = new ArrayList<TPase>();
		p.add(datos);
		TablaDefault<TPase> tabla = new TablaDefault<TPase>("PASES", Messages.colNomsPase, p, true);
		tabla.setVisible(true);
		tabla.getOkButton().addActionListener(e -> {
			TPase tPaseNuevo = (TPase) tabla.getEdicion();
			Controlador.getInstance().accion(Evento.ACTUALIZAR_PASE_DESCARGA, tPaseNuevo);
		});
	}
	
	@Override
	public void actualizar(Evento evento, Object datos) {
		if(evento==Evento.RES_OK) {
			JSwingUtils.createDialogMessage(Messages.EX_PASE_ACTUALIZADO);
		}
		else if (evento==Evento.RES_OK) {
			JSwingUtils.createErrorDialogMessage(Messages.X_PASE_ACTUALIZADO + ' ' + Messages.MOTIVO.formatted((String)datos));
		}
	}

}
