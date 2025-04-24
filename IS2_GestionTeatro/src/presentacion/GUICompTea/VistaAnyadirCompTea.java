package presentacion.GUICompTea;

import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTextField;

import misc.Evento;
import misc.Pair;
import negocio.obra.TObra;
import presentacion.IGUI;
import presentacion.controlador.Controlador;
import presentacion.VistaDefault;

public class VistaAnyadirCompTea extends VistaDefault implements IGUI{
	//Atributos
	private JButton aceptar, cancelar;
	private JTextField nombre,direccion,costecontratacion;

	//Constructor
	public VistaAnyadirCompTea() {
		initGUI();
		this.setVisible(true);
	}
	
	private void initGUI() {
		//Inicializamos los componentes
		this.setTitle("Añadir Compañia Teatral");//no se si dan problemas la verdad
		agregar = new JButton("Agregar");
		cancelar = new JButton("Cancelar");
		JLabel titulo1 = new JLabel("Titulo"), autor1 = new JLabel("Autor"), genero1 = new JLabel("Género"), sinopsis1 = new JLabel("Sinopsis");
		titulo = new JTextField();
		autor = new JTextField();
		genero = new JTextField();
		sinopsis = new JTextField();
		
		ArrayList<Pair<JLabel, JComponent>> campos = new ArrayList<>();
		campos.add(new Pair<>(titulo1, titulo));
		campos.add(new Pair<>(autor1, autor));
		campos.add(new Pair<>(genero1, genero));
		campos.add(new Pair<>(sinopsis1, sinopsis));

		super.initComps(campos, agregar, cancelar);
		
		//Declaramos los listeners
		agregar.addActionListener(e ->{
			String titulo2 = titulo.getText(), autor2= autor.getText(), genero2= genero.getText(), sinopsis2= sinopsis.getText();
			TObra obra = new TObra(titulo2, autor2, genero2, sinopsis2);
			Controlador.getInstance().accion(Evento.CREAR_OBRA, obra);
			VistaAgregarObra.this.dispose();	//Igual cambio algo de aqui porque el problema es que como esta ahora se ejecuta el controller antes de cerrar la ventana
		});
		
		cancelar.addActionListener(e ->{
			VistaAgregarObra.this.dispose();
		});
	}
	
	
	@Override
	public void actualizar(Evento evento, Object datos) {
		// TODO Auto-generated method stub
		
	}

}