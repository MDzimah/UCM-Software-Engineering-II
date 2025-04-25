package presentacion.GUIObra;

import java.util.ArrayList;
import java.util.Collection;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTextField;

import misc.Evento;
import misc.Pair;
import presentacion.IGUI;
import presentacion.controlador.Controlador;
import presentacion.factoria.FactoriaAbstractaPresentacion;
import presentacion.VistaDefault;

public class VistaBorrarObra extends VistaDefault implements IGUI{
	//Atributos
	private JButton eliminar, cancelar;
	
	private JTextField id;

	//Constructor
	public VistaBorrarObra() {
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

		super.initComps(campos, eliminar, cancelar, false);
		
		//Declaramos los listeners
		eliminar.addActionListener(e ->{
			Integer id2 = Integer.valueOf(id.getText());
			Controlador.getInstance().accion(Evento.ELIMINAR_OBRA, id2);
			VistaBorrarObra.this.dispose();	//Igual cambio algo de aqui porque el problema es que como esta ahora se ejecuta el controller antes de cerrar la ventana
		});
		
		cancelar.addActionListener(e ->{
			VistaBorrarObra.this.dispose();
		});
	}


	@Override
	public void actualizar(misc.Evento evento, Object datos) {
		if(evento==Evento.RES_OK) {
			FactoriaAbstractaPresentacion.getInstance().createDialogMessage("Se ha eliminado correctamente la obra " + (int)datos);			
		}
		else if(evento==Evento.RES_KO) {
			FactoriaAbstractaPresentacion.getInstance().createErrorDialogMessage("No se ha podido eliminar la obra.\n" +(String)datos);
		}
	}
}
