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
import negocio.obra.TObra;
import presentacion.Evento;
import presentacion.IGUI;
import presentacion.TablaDefault;
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
			ArrayList<TObra> obra= new ArrayList<TObra>();
			obra.add((TObra) datos);
			
            TablaDefault<TObra> tabla = new TablaDefault<>("OBRA", Messages.colNomsObra, obra, false, false);
            tabla.setVisible(true);
		}
		else if(evento==Evento.RES_KO) {
			JSwingUtils.createErrorDialogMessage("No se han podido acceder a la obra.\n" + "Error: " +((Exception) datos).getMessage());
		}
	}
}
