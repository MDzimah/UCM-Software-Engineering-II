package presentacion.GUITaquillero;

import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import misc.Messages;
import misc.Pair;
import presentacion.Evento;
import presentacion.ViewUtils;
import presentacion.VistaDefault;
import presentacion.controlador.Controlador;

public class VistaActualizarTaquillero_0 extends VistaDefault {

	private JLabel l_id;
	private JSpinner id;
	private JButton aceptar, cancelar;
	
	public VistaActualizarTaquillero_0() {
		initGUI();
		this.setVisible(true);
	}
	
	private void initGUI() {
		//iniciar componentes
		this.setTitle("Actualizar Taquillero");
		l_id = new JLabel("ID:");
		id = ViewUtils.integerSpinner(1, 1, Integer.MAX_VALUE, 1);
		aceptar = new JButton("Aceptar");
		cancelar = new JButton("Cancelar");
		
		//lista de parejas para initComps de VistaDefault
		ArrayList<Pair<JComponent, JComponent>> componentes = new ArrayList<>();
		componentes.add(new Pair<>(l_id, id));
		super.initComps(componentes, aceptar, cancelar);
		
		//oyentes
		aceptar.addActionListener((e) -> {
			try {
				int _id = (int) id.getValue();
				SwingUtilities.invokeLater(()-> {Controlador.getInstance().accion(Evento.ACTUALIZAR_TAQUILLERO_0, _id); });
				VistaActualizarTaquillero_0.this.dispose();
			} catch (NumberFormatException ex) {
				ViewUtils.createErrorDialogMessage("El ID debe ser un número entero.");
				VistaActualizarTaquillero_0.this.dispose();
			}
		});
				
		cancelar.addActionListener((e) -> {
			VistaActualizarTaquillero_0.this.dispose();
		});
	}

	@Override
	public void actualizar(Evento evento, Object datos) {
		if(evento == Evento.RES_KO) {
			ViewUtils.createErrorDialogMessage(Messages.EX_TAQUILLERO_ACTUALIZADO_ERROR + '\n' + Messages.ERROR.formatted(((Exception) datos).getMessage()));
		}
		
	}

}
