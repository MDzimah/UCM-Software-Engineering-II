package presentacion.GUITaquillero;

import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import misc.Pair;
import presentacion.Evento;
import presentacion.VistaDefault;
import presentacion.controlador.Controlador;

public class VistaDespedirTaquillero extends VistaDefault {

	private JLabel l_id;
	private JTextField id;
	private JButton aceptar, cancelar;
	
	public VistaDespedirTaquillero() {
		initGUI();
		this.setVisible(true);
	}
	
	private void initGUI() {
		//iniciar componentes
		this.setTitle("Baja taquillero");
		l_id = new JLabel("Id: ");
		id = new JTextField();
		aceptar = new JButton("Aceptar");
		cancelar = new JButton("Cancelar");
		
		//lista de parejas para initComps de VistaDefault
		ArrayList<Pair<JComponent, JComponent>> componentes = new ArrayList<>();
		componentes.add(new Pair<>(l_id, id));
		
		super.initComps(componentes, aceptar, cancelar);
		
		//oyentes
		aceptar.addActionListener((e) -> {
			if(id.getText() != "") {
				try {
					int _id = Integer.parseInt(id.getText());
					SwingUtilities.invokeLater(()-> {Controlador.getInstance().accion(Evento.BAJA_TAQUILLERO, _id); });
					VistaDespedirTaquillero.this.dispose();
				} catch (NumberFormatException ex) {
					VistaDespedirTaquillero.this.dispose();
				}
			}
		});
				
		cancelar.addActionListener((e) -> {
			VistaDespedirTaquillero.this.dispose();
		});
	}

	@Override
	public void actualizar(Evento evento, Object datos) {
		// TODO Auto-generated method stub
		
	}

}
