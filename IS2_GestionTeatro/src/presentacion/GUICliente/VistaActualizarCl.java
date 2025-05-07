package presentacion.GUICliente;

import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import misc.Pair;
import presentacion.Evento;
import presentacion.IGUI;
import presentacion.ViewUtils;
import presentacion.VistaDefault;
import presentacion.GUITaquillero.VistaActualizarTaquillero_0;
import presentacion.controlador.Controlador;

@SuppressWarnings("serial")
public class VistaActualizarCl extends VistaDefault implements IGUI {

	private JLabel idl;
	private JSpinner id;
	private JButton aceptar, cancelar;
	
	public VistaActualizarCl() {
		initGUI();
		this.setVisible(true);
	}
	
	private void initGUI() {
		
		//inicializar componentes
		this.setTitle("Actualizar Cliente");
		idl = new JLabel("ID");
		id = ViewUtils.integerSpinner(0, 0,Integer.MAX_VALUE, 1);
		aceptar = new JButton("Aceptar");
		cancelar = new JButton("Cancelar");
		
		ArrayList<Pair<JComponent, JComponent>> componentes = new ArrayList<>();
		componentes.add(new Pair<>(idl, id));
		super.initComps(componentes, aceptar, cancelar);
		
		//oyentes
		aceptar.addActionListener((e) -> {
				int _id = (int) id.getValue();
				SwingUtilities.invokeLater(()-> {Controlador.getInstance().accion(Evento.ACTUALIZAR_CLIENTE, _id); });
				
				VistaActualizarCl.this.dispose();
		});
				
		cancelar.addActionListener((e) -> {
			VistaActualizarCl.this.dispose();
		});
	}

	@Override
	public void actualizar(Evento evento, Object datos) {
		if(evento == Evento.RES_KO) {
			ViewUtils.createErrorDialogMessage("No se ha podido actualizar el cliente.\n" + "Error: " +((Exception) datos).getMessage());
		}
		
	}

}
