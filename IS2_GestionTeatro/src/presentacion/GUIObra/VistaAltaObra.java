package presentacion.GUIObra;

import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import misc.Messages;
import misc.Pair;
import negocio.obra.TObra;
import presentacion.Evento;
import presentacion.IGUI;
import presentacion.ViewUtils;
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
		titulo = new JTextField(20);
		autor = new JTextField(20);
		genero = new JTextField(20);
		sinopsis = new JTextField(20);
		
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

			SwingUtilities.invokeLater(()->{Controlador.getInstance().accion(Evento.ALTA_OBRA, obra);});
			VistaAltaObra.this.dispose();
		});
		
		cancelar.addActionListener(e ->{
			VistaAltaObra.this.dispose();
		});
	}
	


	@Override
	public void actualizar(presentacion.Evento evento, Object datos) {
		if(evento==Evento.RES_OK) {
			ViewUtils.createDialogMessage(Messages.EX_OBRA_ANYADIDA_CORRECT + '\n' + " Id: "+ (int)datos);

		}
		else if(evento==Evento.RES_KO) {
			ViewUtils.createErrorDialogMessage(Messages.EX_OBRA_ANYADIDA_ERROR + '\n' + Messages.ERROR.formatted(((Exception) datos).getMessage()));
		}
	}

}
