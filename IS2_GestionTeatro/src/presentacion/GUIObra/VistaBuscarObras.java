package presentacion.GUIObra;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTextField;

import misc.JSwingUtils;
import misc.Pair;
import presentacion.Evento;
import presentacion.IGUI;
import presentacion.VistaDefault;
import presentacion.controlador.Controlador;
import presentacion.factoria.FactoriaAbstractaPresentacion;

public class VistaBuscarObras extends VistaDefault implements IGUI{
	//Atributos
		private JButton buscar, cancelar;
		private JTextField titulo, autor , genero;

		//Constructor
		public VistaBuscarObras() {
			initGUI();
			this.setVisible(true);
		}
		
		private void initGUI() {
			//Inicializamos los componentes
			this.setTitle("Buscar obra");
			buscar = new JButton("Buscar");
			cancelar = new JButton("Cancelar");
			JLabel titulo1 = new JLabel("Titulo"), autor1 = new JLabel("Autor"), genero1 = new JLabel("Género");
			titulo = new JTextField();
			autor = new JTextField();
			genero = new JTextField();
			
			ArrayList<Pair<JComponent, JComponent>> campos = new ArrayList<>();
			campos.add(new Pair<>(titulo1, titulo));
			campos.add(new Pair<>(autor1, autor));
			campos.add(new Pair<>(genero1, genero));

			super.initComps(campos, buscar, cancelar);
			
			//Declaramos los listeners
			buscar.addActionListener(e ->{
				String titulo2 = titulo.getText(), autor2= autor.getText(), genero2= genero.getText();
				List<String> paramBusqueda= new LinkedList<String>();
				paramBusqueda.add(titulo2); paramBusqueda.add(autor2); paramBusqueda.add(genero2);
				Controlador.getInstance().accion(Evento.BUSCAR_OBRA, paramBusqueda);
				VistaBuscarObras.this.dispose();
			});
			
			cancelar.addActionListener(e ->{
				VistaBuscarObras.this.dispose();
			});
		}


		@Override
		public void actualizar(presentacion.Evento evento, Object datos) {
			if(evento==Evento.RES_OK) {
				String[] nomCols = {"ID","TITULO", "AUTOR", "GENERO", "SINOPSIS"};
				JSwingUtils.createTabla("OBRAS", nomCols, (Collection<Object>)datos, true, false);

			}
			else if(evento==Evento.RES_KO) {
				JSwingUtils.createErrorDialogMessage("No se han encontrado obras.\n" +(String)datos);
			}
		}

}
