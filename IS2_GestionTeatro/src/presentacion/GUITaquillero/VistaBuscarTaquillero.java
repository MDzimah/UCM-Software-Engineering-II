package presentacion.GUITaquillero;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import misc.Genero;
import misc.Messages;
import misc.Pair;
import negocio.taquillero.TTaquillero;
import presentacion.Evento;
import presentacion.TablaDefault;
import presentacion.ViewUtils;
import presentacion.VistaDefault;
import presentacion.controlador.Controlador;

public class VistaBuscarTaquillero extends VistaDefault {

	private JLabel l_id;
	private JTextField id;
	private JButton aceptar, cancelar;
	
	public VistaBuscarTaquillero() {
		initGUI();
		this.setVisible(true);
	}
	
	private void initGUI() {
		//iniciar componentes
		this.setTitle("Buscar Taquillero");
		l_id = new JLabel("Id:");
		id = new JTextField();
		aceptar = new JButton("Aceptar");
		cancelar = new JButton("Cancelar");
		
		//lista de parejas para initComps de VistaDefault
		ArrayList<Pair<JComponent, JComponent>> componentes = new ArrayList<>();
		componentes.add(new Pair<>(l_id, id));
		super.initComps(componentes, aceptar, cancelar);
		
		//oyentes
		aceptar.addActionListener((e) -> {
			if(id.getText() != "") {
				try {
					int _id = Integer.parseInt(id.getText());
					SwingUtilities.invokeLater(()-> {Controlador.getInstance().accion(Evento.BUSCAR_TAQUILLERO, _id); });
					VistaBuscarTaquillero.this.dispose();
				} catch (NumberFormatException ex) {
					ViewUtils.createErrorDialogMessage("El ID debe ser un número entero.");
					VistaBuscarTaquillero.this.dispose();
				}
			}
		});
		
		cancelar.addActionListener((e) -> {
			VistaBuscarTaquillero.this.dispose();
		});
	}

	@Override
	public void actualizar(Evento evento, Object datos) {
	    if (evento == Evento.RES_OK) {

	        ArrayList<String[]> colNames = new ArrayList<>();
	        colNames.add(Messages.colNomsTaquillero);

	        ArrayList<TTaquillero> taquilleros = new ArrayList<>();
	        taquilleros.addAll((List<TTaquillero>) datos);

	        ArrayList<ArrayList<TTaquillero>> data = new ArrayList<>();
	        data.add(taquilleros);

	        TablaDefault<TTaquillero> tabla = new TablaDefault<>("Taquillero", colNames, data, false);
	        tabla.setVisible(true);

	    } else if (evento == Evento.RES_KO) {
	    	//Errores van en messages
	        ViewUtils.createErrorDialogMessage("No se han encontrado taquilleros.\n" + "Error: " + ((Exception) datos).getMessage());
	    }
	}
}
