package presentacion.GUIObra;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import misc.*;
import presentacion.Evento;
import presentacion.IGUI;
import presentacion.VistaDefault;
import presentacion.controlador.Controlador;
import presentacion.factoria.FactoriaAbstractaPresentacion;

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


		super.initComps(campos, consultar, cancelar);
		
		//Declaramos los listeners
		consultar.addActionListener(e ->{
			if(!id.getText().equals("")) {
				try {
				Integer id2 = Integer.valueOf(id.getText());			
				SwingUtilities.invokeLater(()->{Controlador.getInstance().accion(Evento.CONSULTAR_OBRA, id2);});
				VistaConsultarObra.this.dispose();
				}
				catch(NumberFormatException ex) {
			        JSwingUtils.createErrorDialogMessage("El ID debe ser un número entero.");
				}
			}
		});
		
		cancelar.addActionListener(e ->{
			VistaConsultarObra.this.dispose();
		});
	}
	
	@Override
	public void actualizar(presentacion.Evento evento, Object datos) {
		if(evento==Evento.RES_OK) {
			String[] nomCols = {"ID","TITULO", "AUTOR", "GENERO", "SINOPSIS"};
			Collection<Object> obra= new LinkedList<Object>();
			obra.add(datos);
			JSwingUtils.createTabla("OBRA", nomCols, obra, false, false);
		}
		else if(evento==Evento.RES_KO) {
			JSwingUtils.createErrorDialogMessage("No se han podido acceder a la obra.\n" + (String)datos);
		}
	}
}
