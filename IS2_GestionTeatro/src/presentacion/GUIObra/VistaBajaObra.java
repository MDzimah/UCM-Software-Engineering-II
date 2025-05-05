package presentacion.GUIObra;

import java.util.ArrayList;
import java.util.Collection;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import misc.Messages;
import misc.Pair;
import presentacion.Evento;
import presentacion.IGUI;
import presentacion.ViewUtils;
import presentacion.controlador.Controlador;
import presentacion.factoria.FactoriaAbstractaPresentacion;
import presentacion.VistaDefault;

public class VistaBajaObra extends VistaDefault implements IGUI{
	//Atributos
	private JButton eliminar, cancelar;
	
	private JSpinner id;

	//Constructor
	public VistaBajaObra() {
		initGUI();
		this.setVisible(true);
	}
	
	private void initGUI() {
		//Inicializamos los componentes
		this.setTitle("Borrar obra");
		eliminar = new JButton("Eliminar");
		cancelar = new JButton("Cancelar");
		JLabel id1 = new JLabel("Id");
		id = ViewUtils.integerSpinner(0, 0, Integer.MAX_VALUE, 1);
		
		ArrayList<Pair<JComponent, JComponent>> campos = new ArrayList<>();
		campos.add(new Pair<>(id1, id));

		super.initComps(campos, eliminar, cancelar);
		
		//Declaramos los listeners
		eliminar.addActionListener(e ->{
			try {
				id.commitEdit();
				int idObra = (int)id.getValue();
				SwingUtilities.invokeLater(()->{Controlador.getInstance().accion(Evento.BAJA_OBRA, idObra);});
				VistaBajaObra.this.dispose();
			}
			catch(Exception ex) {
				ViewUtils.createInvalidFieldsPanel();
				id.updateUI();
			}
		});
		
		cancelar.addActionListener(e ->{
			VistaBajaObra.this.dispose();
		});
	}


	@Override
	public void actualizar(presentacion.Evento evento, Object datos) {
		if(evento==Evento.RES_OK) {
			ViewUtils.createDialogMessage(Messages.EX_OBRA_BORRADA_CORRECT + '\n' + "Id: "+(int)datos);

		}
		else if(evento==Evento.RES_KO) {
			ViewUtils.createErrorDialogMessage(Messages.EX_OBRA_BORRADA_ERROR + '\n' + Messages.ERROR.formatted(((Exception) datos).getMessage()));

		}
	}
}
