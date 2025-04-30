package presentacion.GUIObra;

import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTextField;

import misc.Evento;
import misc.JSwingUtils;
import misc.Pair;
import negocio.obra.TObra;
import presentacion.IGUI;
import presentacion.controlador.Controlador;
import presentacion.factoria.FactoriaAbstractaPresentacion;
import presentacion.VistaDefault;

public class VistaAltaObra extends VistaDefault implements IGUI{
	//Atributos
	private JButton agregar, cancelar;
	private JTextField titulo, autor , genero, sinopsis;

	//Constructor
	public VistaAltaObra() {
		initGUI();
		this.setVisible(true);
	}
	
	private void initGUI() {
		//Inicializamos los componentes
		this.setTitle("Agregar obra");
		agregar = new JButton("Agregar");
		cancelar = new JButton("Cancelar");
		JLabel titulo1 = new JLabel("Titulo"), autor1 = new JLabel("Autor"), genero1 = new JLabel("Género"), sinopsis1 = new JLabel("Sinopsis");
		titulo = new JTextField();
		autor = new JTextField();
		genero = new JTextField();
		sinopsis = new JTextField();
		
		ArrayList<Pair<JComponent, JComponent>> campos = new ArrayList<>();
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
			VistaAltaObra.this.dispose();	//Igual cambio algo de aqui porque el problema es que como esta ahora se ejecuta el controller antes de cerrar la ventana
		});
		
		cancelar.addActionListener(e ->{
			VistaAltaObra.this.dispose();
		});
	}
	


	@Override
	public void actualizar(misc.Evento evento, Object datos) {
		if(evento==Evento.RES_OK) {
			JSwingUtils.createErrorDialogMessage("Se ha añadido correctamente la obra " + (int)datos);

		}
		else if(evento==Evento.RES_KO) {
			JSwingUtils.createErrorDialogMessage("No se ha podido añadir la obra.\n" +(String)datos);

		}
	}

}
