package presentacion.GUIObra;

import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTextField;

import misc.*;
import negocio.obra.TObra;
import presentacion.Evento;
import presentacion.IGUI;
import presentacion.VistaDefault;
import presentacion.controlador.Controlador;
import presentacion.factoria.FactoriaAbstractaPresentacion;

public class VistaActualizarObra_1 extends VistaDefault implements IGUI{
	//Atributos
	private JButton actualizar, cancelar;
	private JTextField titulo, autor , genero, sinopsis;

	//Constructor
		public VistaActualizarObra_1() {
			initGUI();
		}
		public void setDatos(TObra obra) {
			JLabel id1 = new JLabel(String.valueOf(obra.getIdObra()));

			titulo.setText(obra.getTitulo());
			autor.setText(obra.getAutor());
			genero.setText(obra.getGenero());
			sinopsis.setText(obra.getSinopsis());
			this.setVisible(true);
		}
		
		private void initGUI() {
			//Inicializamos los componentes
			this.setTitle("Actualizar obra");
			actualizar = new JButton("Actualizar");
			cancelar = new JButton("Cancelar");
			JLabel id0 = new JLabel("Id"), id1 = new JLabel(), titulo1 = new JLabel("Titulo"), autor1 = new JLabel("Autor"), genero1 = new JLabel("Género"), sinopsis1 = new JLabel("Sinopsis");
			titulo = new JTextField();
			autor = new JTextField();
			genero = new JTextField();
			sinopsis = new JTextField();

			ArrayList<Pair<JComponent, JComponent>> campos = new ArrayList<>();
			campos.add(new Pair<>(id0, id1));
			campos.add(new Pair<>(titulo1, titulo));
			campos.add(new Pair<>(autor1, autor));
			campos.add(new Pair<>(genero1, genero));			
			campos.add(new Pair<>(sinopsis1, sinopsis));

			super.initComps(campos, actualizar, cancelar);
			
			//Declaramos los listeners
			actualizar.addActionListener(e ->{
				String titulo2 = titulo.getText(), autor2= autor.getText(), genero2= genero.getText(), sinopsis2= sinopsis.getText();
				TObra obra1 = new TObra(titulo2, autor2, genero2, sinopsis2);

				Controlador.getInstance().accion(Evento.ACTUALIZAR_OBRA_1, obra1);
				VistaActualizarObra_1.this.dispose();
			});
			
			cancelar.addActionListener(e ->{
				VistaActualizarObra_1.this.dispose();
			});
		}



	@Override
	public void actualizar(Evento evento, Object datos) {
		if(evento==Evento.RES_OK) {
			JSwingUtils.createErrorDialogMessage("Se ha actualizado correctamente la obra " + (int)datos);

		}
		else if(evento==Evento.RES_KO) {
			JSwingUtils.createErrorDialogMessage("No se ha podido actualizar la obra.\n" + (String)datos);
		}
	}
}

