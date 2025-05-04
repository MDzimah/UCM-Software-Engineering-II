package presentacion.GUIMiemCompTea;

import java.util.ArrayList;
import java.util.Collection;

import javax.swing.JButton;

import misc.JSwingUtils;
import misc.Messages;
import negocio.miemCompTea.TMiemCompTea;
import presentacion.Evento;
import presentacion.TablaDefault;
import presentacion.VistaDefault;
import presentacion.controlador.Controlador;
import presentacion.factoria.FactoriaAbstractaPresentacion;

public class VistaMostrarMiembrosCompania extends VistaDefault{
	
	public VistaMostrarMiembrosCompania() {
		super();
		
		JButton mostrar = new JButton("Mostrar");
		JButton cancel = new JButton("Cancelar");
		
		super.initComps(null, mostrar, cancel);
		
		mostrar.addActionListener(e->{
			Controlador.getInstance().accion(Evento.MOSTRAR_MIEMBROS_COMPANIA, null);
			dispose();
		});
		
		cancel.addActionListener(e->dispose());
		
		this.setTitle("Mostrar miembros compania");
		this.setVisible(true);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void actualizar(Evento evento, Object datos) {
		if (evento == Evento.RES_OK) {
			new TablaDefault<TMiemCompTea>("MIEMBROS DE LA COMPANIA", Messages.colNomsMiemCompTea, (ArrayList<TMiemCompTea>)datos, false, false).setVisible(true);
		}
		else if (evento == Evento.RES_KO) {
			String error;
			if (datos instanceof String) error = (String) datos;
			else error = Messages.NO_HAY_DATOS;
			JSwingUtils.createErrorDialogMessage(Messages.X_MIEMBROS_LISTADOS + ' ' + Messages.MOTIVO.formatted(error));
		}
		
	}
	
}
