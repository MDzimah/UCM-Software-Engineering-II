package presentacion.GUITaquillero;

import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import misc.Messages;
import misc.Pair;
import negocio.taquillero.TTaquillero;
import presentacion.Evento;
import presentacion.TablaDefault;
import presentacion.ViewUtils;
import presentacion.VistaDefault;
import presentacion.controlador.Controlador;

public class VistaBuscarDNITaquillero extends VistaDefault {

	private JLabel l_dni;
	private JTextField dni;
	private JButton aceptar, cancelar;
	
	public VistaBuscarDNITaquillero() {
		initGUI();
		this.setVisible(true);
	}
	
	private void initGUI() {
		//iniciar componentes
		this.setTitle("Buscar Taquillero");
		l_dni = new JLabel("DNI:");
		dni = new JTextField();
		aceptar = new JButton("Aceptar");
		cancelar = new JButton("Cancelar");
		
		//lista de parejas para initComps de VistaDefault
		ArrayList<Pair<JComponent, JComponent>> componentes = new ArrayList<>();
		componentes.add(new Pair<>(l_dni, dni));
		super.initComps(componentes, aceptar, cancelar);
		
		//oyentes
		aceptar.addActionListener((e) -> {
			String _dni = dni.getText();
			SwingUtilities.invokeLater(()-> {Controlador.getInstance().accion(Evento.BUSCAR_DNI_TAQUILLERO, _dni); });
			VistaBuscarDNITaquillero.this.dispose();
		});
		
		cancelar.addActionListener((e) -> {
			VistaBuscarDNITaquillero.this.dispose();
		});
	}

	@Override
	public void actualizar(Evento evento, Object datos) {
	    if (evento == Evento.RES_OK) {
	    	TTaquillero tTaq = (TTaquillero) datos;
	    	
	        ArrayList<String[]> colNames = new ArrayList<>();
	        colNames.add(Messages.colNomsTaquillero);

	        ArrayList<TTaquillero> taquilleros = new ArrayList<>();
	        taquilleros.add(tTaq);

	        ArrayList<ArrayList<TTaquillero>> data = new ArrayList<>();
	        data.add(taquilleros);

	        TablaDefault<TTaquillero> tabla = new TablaDefault<>("Taquillero", colNames, data, false);
	        tabla.setVisible(true);

	    } else if (evento == Evento.RES_KO) {
	    	ViewUtils.createErrorDialogMessage(Messages.EX_TAQUILLERO_BUSCAR_ERROR + "\n" + Messages.ERROR.formatted(((Exception) datos).getMessage()));
	    }
	}
}