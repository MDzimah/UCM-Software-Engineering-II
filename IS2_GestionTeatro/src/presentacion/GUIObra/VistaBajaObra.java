package presentacion.GUIObra;

import java.util.ArrayList;
import java.util.Collection;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import misc.JSwingUtils;
import misc.Pair;
import presentacion.Evento;
import presentacion.IGUI;
import presentacion.controlador.Controlador;
import presentacion.factoria.FactoriaAbstractaPresentacion;
import presentacion.VistaDefault;

public class VistaBajaObra extends VistaDefault implements IGUI{
	//Atributos
	private JButton eliminar, cancelar;
	
	private JTextField id;

	//Constructor
	public VistaBajaObra() {
		initGUI();
		this.setVisible(true);
	}
	
	private void initGUI() {
		//Inicializamos los componentes
		this.setTitle("Borrar obra");
		eliminar = new JButton("Eliminar");
		cancelar = new JButton("Cancelar");
		JLabel id1 = new JLabel("Id");
		id = new JTextField();
		
		ArrayList<Pair<JComponent, JComponent>> campos = new ArrayList<>();
		campos.add(new Pair<>(id1, id));

		super.initComps(campos, eliminar, cancelar);
		
		//Declaramos los listeners
		eliminar.addActionListener(e ->{
			if(!id.getText().equals("")) {
				try {
					Integer id2 = Integer.valueOf(id.getText());
					SwingUtilities.invokeLater(()->{Controlador.getInstance().accion(Evento.ELIMINAR_OBRA, id2);});
					VistaBajaObra.this.dispose();
				}
				catch(NumberFormatException ex) {
			        JSwingUtils.createErrorDialogMessage("El ID debe ser un número entero.");
				}
			}
		});
		
		cancelar.addActionListener(e ->{
			VistaBajaObra.this.dispose();
		});
	}


	@Override
	public void actualizar(presentacion.Evento evento, Object datos) {
		if(evento==Evento.RES_OK) {
			JSwingUtils.createErrorDialogMessage("Se ha eliminado correctamente la obra " + (int)datos);

		}
		else if(evento==Evento.RES_KO) {
			JSwingUtils.createErrorDialogMessage("No se ha podido eliminar la obra.\n" +(String)datos);

		}
	}
}
