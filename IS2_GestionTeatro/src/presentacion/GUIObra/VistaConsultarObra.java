package presentacion.GUIObra;

import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTextField;

import misc.Pair;
import presentacion.Evento;
import presentacion.IGUI;
import presentacion.VistaDefault;
import presentacion.controlador.Controlador;

public class VistaConsultarObra extends VistaDefault implements IGUI{
	private JButton consultar, cancelar;
	
	private JTextField id;

	//Constructor
	public VistaConsultarObra() {
		initGUI();
		this.setVisible(true);
	}
	
	private void initGUI() {
		//Inicializamos los componentes
		this.setTitle("Consultar obra");
		consultar = new JButton("Consultar");
		cancelar = new JButton("Cancelar");
		JLabel id1 = new JLabel("Id");
		id = new JTextField();
		
		ArrayList<Pair<JComponent, JComponent>> campos = new ArrayList<>();
		campos.add(new Pair<>(id1, id));

		super.initComps(campos, consultar, cancelar, false);
		
		//Declaramos los listeners
		consultar.addActionListener(e ->{
			Integer id2 = Integer.valueOf(id.getText());
			Controlador.getInstance().accion(Evento.CONSULTAR_OBRA, id2);
			VistaConsultarObra.this.dispose();
		});
		
		cancelar.addActionListener(e ->{
			VistaConsultarObra.this.dispose();
		});
	}

	@Override
	public void actualizar(Evento evento, Object datos) {
		// TODO Auto-generated method stub
		
	}
}
