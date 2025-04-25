package presentacion.GUIObra;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTextField;

import misc.Pair;
import presentacion.Evento;
import presentacion.IGUI;
import presentacion.VistaDefault;
import presentacion.controlador.Controlador;

public class VistaBuscarObra extends VistaDefault implements IGUI{
	//Atributos
		private JButton buscar, cancelar;
		private JTextField id, titulo, autor , genero, sinopsis;

		//Constructor
		public VistaBuscarObra() {
			initGUI();
			this.setVisible(true);
		}
		
		private void initGUI() {
			//Inicializamos los componentes
			this.setTitle("Buscar obra");
			buscar = new JButton("Buscar");
			cancelar = new JButton("Cancelar");
			JLabel id1 = new JLabel("Id"), titulo1 = new JLabel("Titulo"), autor1 = new JLabel("Autor"), genero1 = new JLabel("Género"), sinopsis1 = new JLabel("Sinopsis");
			id = new JTextField();
			titulo = new JTextField();
			autor = new JTextField();
			genero = new JTextField();
			sinopsis = new JTextField();
			
			ArrayList<Pair<JComponent, JComponent>> campos = new ArrayList<>();
			campos.add(new Pair<>(id1, id));
			campos.add(new Pair<>(titulo1, titulo));
			campos.add(new Pair<>(autor1, autor));
			campos.add(new Pair<>(genero1, genero));
			campos.add(new Pair<>(sinopsis1, sinopsis));

			super.initComps(campos, buscar, cancelar, false);
			
			//Declaramos los listeners
			buscar.addActionListener(e ->{
				String id2 = id.getText(), titulo2 = titulo.getText(), autor2= autor.getText(), genero2= genero.getText(), sinopsis2= sinopsis.getText();
				List<String> paramBusqueda= new LinkedList<String>();
				paramBusqueda.add(id2); paramBusqueda.add(titulo2); paramBusqueda.add(autor2); paramBusqueda.add(genero2); paramBusqueda.add(sinopsis2);
				Controlador.getInstance().accion(Evento.BUSCAR_OBRA, paramBusqueda);
				VistaBuscarObra.this.dispose();
			});
			
			cancelar.addActionListener(e ->{
				VistaBuscarObra.this.dispose();
			});
		}
		@Override
		public void actualizar(Evento evento, Object datos) {
			// TODO Auto-generated method stub
			
		}

}
