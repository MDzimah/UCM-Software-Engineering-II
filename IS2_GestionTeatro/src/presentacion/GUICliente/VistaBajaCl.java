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
import presentacion.GUITaquillero.VistaDespedirTaquillero;
import presentacion.controlador.Controlador;

@SuppressWarnings("serial")
public class VistaBajaCl extends VistaDefault implements IGUI{

	private JLabel idl;
	private JSpinner id;
	private JButton aceptar, cancelar;
	
	public VistaBajaCl() {
		initGUI();
		this.setVisible(true);
	}
	
	private void initGUI() {
		//iniciar componentes
		this.setTitle("Baja Cliente");
		idl = new JLabel("Id");
		id = ViewUtils.integerSpinner(0, 0, Integer.MAX_VALUE,1);
		aceptar = new JButton("Aceptar");
		cancelar = new JButton("Cancelar");
		
		//lista de parejas para initComps de VistaDefault
		ArrayList<Pair<JComponent, JComponent>> componentes = new ArrayList<>();
		componentes.add(new Pair<>(idl, id));
		
		super.initComps(componentes, aceptar, cancelar);
		
		//oyentes
		aceptar.addActionListener((e) -> {
				int idCl = (int) id.getValue();
				SwingUtilities.invokeLater(()-> {Controlador.getInstance().accion(Evento.BAJA_CLIENTE, idCl); });
				VistaBajaCl.this.dispose();
		});
				
		cancelar.addActionListener((e) -> {
			VistaBajaCl.this.dispose();
		});
	}

	@Override
	public void actualizar(Evento evento, Object datos) {
		if(evento == Evento.RES_OK) {
			ViewUtils.createDialogMessage("Se ha eliminado correctamente el cliente: " + (int)datos);
		} else if(evento == Evento.RES_KO) {
			ViewUtils.createErrorDialogMessage("No se ha podido eliminar el cliente.\n" + "Error: " +((Exception) datos).getMessage());
		}
		
	}

}
