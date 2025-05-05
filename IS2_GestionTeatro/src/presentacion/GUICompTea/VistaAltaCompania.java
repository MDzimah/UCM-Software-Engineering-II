package presentacion.GUICompTea;

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
import presentacion.IGUI;
import presentacion.ViewUtils;
import presentacion.controlador.Controlador;
import presentacion.VistaDefault;
import negocio.compTea.TCompTea;

public class VistaAltaCompania extends VistaDefault implements IGUI{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	public VistaAltaCompania() {
		initGUI();
		this.setVisible(true);
	}
	
	private void initGUI() {
		
		this.setTitle("Añadir Compañia Teatral");//no se si dan problemas la verdad
		JButton anyadir= new JButton("Añadir");
		JButton cancelar = new JButton("Cancelar");
		JLabel nombrelabel= new JLabel("nombre");
		JLabel direccionlabel=new JLabel("direccion");
		JLabel costeContratacionLabel= new JLabel("coste contratacion");
		
		JTextField nombre = new JTextField();
		JTextField direccion = new JTextField();	
		JSpinner cost = ViewUtils.integerSpinner(0, 0, Integer.MAX_VALUE, 1);
		ArrayList<Pair<JComponent, JComponent>> campos = new ArrayList<>();
		campos.add(new Pair<>(nombrelabel, nombre));
		campos.add(new Pair<>(direccionlabel, direccion));
		campos.add(new Pair<>(costeContratacionLabel, cost));
		anyadir.addActionListener(e ->{
			String nombreString = nombre.getText(); 
			String direccionString = direccion.getText();
		
			try {
				cost.commitEdit();
				int costint = (int)cost.getValue();
				TCompTea tCompTea= new TCompTea(-1,nombreString,direccionString,true,costint);
				SwingUtilities.invokeLater(()->{Controlador.getInstance().accion(Evento.ALTA_COMPANIA_TEATRAL, tCompTea);});
			}
			catch(Exception ex) {
				ViewUtils.createInvalidFieldsPanel();
				cost.updateUI();
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
		if(evento==Evento.RES_OK) {
			ViewUtils.createDialogMessage(Messages.COMPANIA_CREADA + '\n' + " Id: "+ (int)datos);

		}
		else if(evento==Evento.RES_KO) {
			ViewUtils.createErrorDialogMessage(Messages.X_CREAR_COMPANIA+ '\n' + Messages.ERROR.formatted(((Exception) datos).getMessage()));
		}
		
	}

}