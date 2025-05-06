package presentacion.GUICompTea;

import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import integracion.factoria.FactoriaAbstractaIntegracion;
import misc.Pair;
import presentacion.Evento;
import presentacion.IGUI;
import presentacion.ViewUtils;
import presentacion.controlador.Controlador;
import presentacion.VistaDefault;
import negocio.compTea.TCompTea;

public class VistaBajaCompTea extends VistaDefault implements IGUI{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	public VistaBajaCompTea() {
		initGUI();
		this.setVisible(true);
	}
	
	private void initGUI() {
		
		this.setTitle("Baja Compañia Teatral");//no se si dan problemas la verdad
		JButton anyadir= new JButton("Aceptar");
		JButton cancelar = new JButton("Cancelar");
		JLabel pregunta= new JLabel("Id de la compania para borrar");
		
		JSpinner IdField = ViewUtils.integerSpinner(0, 0, Integer.MAX_VALUE, 1);
		
		
		ArrayList<Pair<JComponent, JComponent>> campos = new ArrayList<>();
		campos.add(new Pair<>(pregunta, IdField));
		anyadir.addActionListener(e ->{
			try {
				IdField.commitEdit();
				int id = (int)IdField.getValue();
				SwingUtilities.invokeLater(()->{Controlador.getInstance().accion(Evento.BAJA_COMPANIA_TEATRAL, id);});
				dispose();
			}
			catch(Exception ex) {
				ViewUtils.createInvalidFieldsPanel();
				IdField.updateUI();
			}
			this.dispose();	//Igual cambio algo de aqui porque el problema es que como esta ahora se ejecuta el controller antes de cerrar la ventana
		});
		cancelar.addActionListener(e ->{
			this.dispose();
		});
		

		initComps(campos, anyadir, cancelar);//no se si tiene que ser fullscreen...
	
	}
	
	
	@Override
	public void actualizar(Evento evento, Object datos) {
		// TODO Auto-generated method stub
		
	}

}