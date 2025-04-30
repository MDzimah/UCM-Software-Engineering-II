package presentacion.GUICompTea;

import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTextField;

import integracion.factoria.FactoriaAbstractaIntegracion;
import misc.Evento;
import misc.Pair;
import presentacion.IGUI;
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
		JButton anyadir= new JButton("Añadir");
		JButton cancelar = new JButton("Cancelar");
		JLabel pregunta= new JLabel("Nombre de la compania para borrar");
		
		JTextField nombre = new JTextField();
		
		
		ArrayList<Pair<JComponent, JComponent>> campos = new ArrayList<>();
		campos.add(new Pair<>(pregunta, nombre));
		anyadir.addActionListener(e ->{
			String nombreString = nombre.getText(); 
			FactoriaAbstractaIntegracion.getInstance().crearDAOCompTea();
			
			Controlador.getInstance().accion(Evento.ELIMINAR_COMPANIA_TEATRAL, nombreString);
			this.dispose();	//Igual cambio algo de aqui porque el problema es que como esta ahora se ejecuta el controller antes de cerrar la ventana
		});
		cancelar.addActionListener(e ->{
			this.dispose();
		});
		

		initComps(campos, anyadir, cancelar, false);//no se si tiene que ser fullscreen...
	
	}
	
	
	@Override
	public void actualizar(Evento evento, Object datos) {
		// TODO Auto-generated method stub
		
	}

}