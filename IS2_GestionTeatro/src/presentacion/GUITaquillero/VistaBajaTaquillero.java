package presentacion.GUITaquillero;

import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import misc.Pair;
import presentacion.Evento;
import presentacion.ViewUtils;
import presentacion.VistaDefault;
import presentacion.controlador.Controlador;

public class VistaBajaTaquillero extends VistaDefault {

	private JLabel l_id;
	private JSpinner id;
	private JButton aceptar, cancelar;
	
	public VistaBajaTaquillero() {
		initGUI();
		this.setVisible(true);
	}
	
	private void initGUI() {
		//iniciar componentes
		this.setTitle("Baja taquillero");
		l_id = new JLabel("Id: ");
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
				SwingUtilities.invokeLater(()-> {Controlador.getInstance().accion(Evento.BAJA_TAQUILLERO, _id); });
				VistaBajaTaquillero.this.dispose();
			} catch (NumberFormatException ex) {
				VistaBajaTaquillero.this.dispose();
			}
		});
				
		cancelar.addActionListener((e) -> {
			VistaBajaTaquillero.this.dispose();
		});
	}

	@Override
	public void actualizar(Evento evento, Object datos) {
		if(evento == Evento.RES_OK) {
			ViewUtils.createDialogMessage("Se ha eliminado correctamente el taquillero: " + (int)datos);
		} else if(evento == Evento.RES_KO) {
			ViewUtils.createErrorDialogMessage("No se ha podido eliminar el taquillero.\n" + "Error: " +((Exception) datos).getMessage());
		}
		
	}

}
