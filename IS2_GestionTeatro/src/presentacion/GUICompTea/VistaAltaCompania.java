package presentacion.GUICompTea;

import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTextField;

import misc.Evento;
import misc.Pair;
import presentacion.IGUI;
import presentacion.controlador.Controlador;
import presentacion.VistaDefault;
import negocio.compTea.TCompTea;

public class VistaAltaCompania extends VistaDefault implements IGUI{
	
	
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
		JTextField coste = new JTextField();
		
		
		ArrayList<Pair<JComponent, JComponent>> campos = new ArrayList<>();
		campos.add(new Pair<>(nombrelabel, nombre));
		campos.add(new Pair<>(direccionlabel, direccion));
		campos.add(new Pair<>(costeContratacionLabel, coste));
		anyadir.addActionListener(e ->{
			String nombreString = nombre.getText(); 
			String direccionString = direccion.getText();
			String costeString= coste.getText();
			TCompTea tCompTea= new TCompTea(-1,nombreString,direccionString,true,Float.parseFloat(costeString));
			Controlador.getInstance().accion(Evento.ALTA_COMPANIA, tCompTea);
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