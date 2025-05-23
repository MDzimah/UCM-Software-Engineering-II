package presentacion.GUIPase;

import java.util.ArrayList;
import java.util.Collection;

import misc.Messages;
import negocio.factoria.FactoriaAbstractaNegocio;
import negocio.pase.TPase;
import presentacion.Evento;
import presentacion.IGUI;
import presentacion.ViewUtils;
import presentacion.TablaDefault;
import presentacion.controlador.Controlador;

public class VistaActualizarPase_1 implements IGUI {
	
	public void cargarPase(TPase datos) {
		ArrayList<String[]> colNames = new ArrayList<>();
        colNames.add(Messages.colNomsPase);
        
		ArrayList<TPase> pases = new ArrayList<>();
		pases.add((TPase) datos);
		
		ArrayList<ArrayList<TPase>> data = new ArrayList<>();
		data.add(pases);
		
		TablaDefault<TPase> tabla = new TablaDefault<TPase>("PASES", colNames, data, true);
		tabla.setVisible(true);
		tabla.getOkButton().addActionListener(e -> {
			TPase tPaseNuevo = (TPase) (tabla.getEdiciones()).get(0);
			Controlador.getInstance().accion(Evento.ACTUALIZAR_PASE_1, tPaseNuevo);
			tabla.dispose();
		});
	}
	
	@Override
	public void actualizar(Evento evento, Object datos) {
		if(evento==Evento.RES_OK) {
			ViewUtils.createDialogMessage(Messages.EX_PASE_ACTUALIZADO);
		}
		else if (evento==Evento.RES_KO) {
			ViewUtils.createErrorDialogMessage(Messages.X_PASE_ACTUALIZADO + ' ' + Messages.MOTIVO.formatted(((Exception) datos).getMessage()));
		}
	}

}
