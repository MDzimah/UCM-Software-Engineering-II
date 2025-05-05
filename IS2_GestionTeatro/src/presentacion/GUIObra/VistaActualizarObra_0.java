package presentacion.GUIObra;

import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import misc.Messages;
import misc.Pair;
import negocio.obra.TObra;
import presentacion.Evento;
import presentacion.IGUI;
import presentacion.ViewUtils;
import presentacion.VistaDefault;
import presentacion.controlador.Controlador;

public class VistaActualizarObra_0 extends VistaDefault implements IGUI{
	
	private JSpinner id;
	private JButton aceptar, cancelar;
	
	public VistaActualizarObra_0() {
		initGUI();
		
		this.setVisible(true);
	}
	
	private void initGUI() {
		this.setTitle("Actualizar obra");
		aceptar = new JButton("Aceptar");
		cancelar = new JButton("Cancelar");
		id = ViewUtils.integerSpinner(0, 0, Integer.MAX_VALUE, 1);
		JLabel id1 = new JLabel("Introduce el id");

		ArrayList<Pair<JComponent, JComponent>> campos = new ArrayList<>();
		campos.add(new Pair<>(id1, id));
		super.initComps(campos, aceptar, cancelar);
		
		//Declaramos los listeners
		aceptar.addActionListener(e ->{
			try {
				id.commitEdit();
				int idObra = (int)id.getValue();
				SwingUtilities.invokeLater(()->{Controlador.getInstance().accion(Evento.ACTUALIZAR_OBRA_0, idObra);});
				VistaActualizarObra_0.this.dispose();
			}
			catch(Exception ex) {
				ViewUtils.createInvalidFieldsPanel();
				id.updateUI();
			}
		});
		
		cancelar.addActionListener(e ->{
			VistaActualizarObra_0.this.dispose();
		});
	}
	@Override
	public void actualizar(Evento evento, Object datos) {
		if(evento==Evento.RES_KO) {
			ViewUtils.createErrorDialogMessage(Messages.EX_OBRA_ACTUALIZADA_ERROR + '\n' + Messages.ERROR.formatted(((Exception) datos).getMessage()));
		}
	}
}
