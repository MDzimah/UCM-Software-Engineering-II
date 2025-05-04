package presentacion.GUIObra;

import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import misc.JSwingUtils;
import misc.Messages;
import misc.Pair;
import negocio.obra.TObra;
import presentacion.Evento;
import presentacion.IGUI;
import presentacion.VistaDefault;
import presentacion.controlador.Controlador;

public class VistaActualizarObra_0 extends VistaDefault implements IGUI{
	
	private JTextField id;
	private JButton aceptar, cancelar;
	
	public VistaActualizarObra_0() {
		initGUI();
		
		this.setVisible(true);
	}
	
	private void initGUI() {
		this.setTitle("Actualizar obra");
		aceptar = new JButton("Aceptar");
		cancelar = new JButton("Cancelar");
		id = new JTextField();
		JLabel id1 = new JLabel("Introduce el id");

		ArrayList<Pair<JComponent, JComponent>> campos = new ArrayList<>();
		campos.add(new Pair<>(id1, id));
		super.initComps(campos, aceptar, cancelar);
		
		//Declaramos los listeners
		aceptar.addActionListener(e ->{
			if(!id.getText().equals("")) {
				try{
					Integer id2 = Integer.valueOf(id.getText());
					SwingUtilities.invokeLater(()->{Controlador.getInstance().accion(Evento.ACTUALIZAR_OBRA_0, id2);});
					VistaActualizarObra_0.this.dispose();
			}
				catch(NumberFormatException ex) {
			        JSwingUtils.createErrorDialogMessage("El ID debe ser un número entero.");
				}
			}
		});
		
		cancelar.addActionListener(e ->{
			VistaActualizarObra_0.this.dispose();
		});
	}
	@Override
	public void actualizar(Evento evento, Object datos) {
		if(evento==Evento.RES_KO) {
			JSwingUtils.createErrorDialogMessage(Messages.X_PASE_ACTUALIZADO + ' ' + Messages.MOTIVO.formatted((String)datos));
		}
		
	}

}
