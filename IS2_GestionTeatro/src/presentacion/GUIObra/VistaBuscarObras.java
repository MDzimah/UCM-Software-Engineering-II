package presentacion.GUIObra;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

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
import presentacion.TablaDefault;
import presentacion.VistaDefault;
import presentacion.controlador.Controlador;

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
				SwingUtilities.invokeLater(()->{Controlador.getInstance().accion(Evento.BUSCAR_OBRA, paramBusqueda);});
				VistaBuscarObras.this.dispose();
			});
			
			cancelar.addActionListener(e ->{
				VistaBuscarObras.this.dispose();
			});
		}


		@Override
		public void actualizar(presentacion.Evento evento, Object datos) {
			if (evento == Evento.RES_OK) {

				ArrayList<String[]> colNames = new ArrayList<>();
				colNames.add(Messages.colNomsObra);

				ArrayList<TObra> obras = new ArrayList<>();
				for (TObra o : (List<TObra>) datos) {
					obras.add(o);
				}

				ArrayList<ArrayList<TObra>> data = new ArrayList<>();
				obras.sort((p1, p2) -> Integer.compare(p1.getIdObra(), p2.getIdObra()));
				data.add(obras);

				TablaDefault<TObra> tabla = new TablaDefault<>("OBRAS", colNames, data, false);
				tabla.setVisible(true);
			} else if (evento == Evento.RES_KO) {
				ViewUtils.createErrorDialogMessage(Messages.EX_OBRA_BUSCAR_ERROR + '\n' + Messages.ERROR.formatted(((Exception) datos).getMessage()));
			}
		}
}
