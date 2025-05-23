package presentacion.GUIPase;

import java.util.ArrayList;
import java.util.Collection;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.JTextField;

import misc.Messages;
import misc.Pair;
import negocio.pase.TPase;
import presentacion.Evento;
import presentacion.ViewUtils;
import presentacion.TablaDefault;
import presentacion.VistaDefault;
import presentacion.controlador.Controlador;

public class VistaMostrarPasesPorObra extends VistaDefault {
	private JSpinner idObra;
	private JButton buscar;
	private JButton cancelar;
	
	public VistaMostrarPasesPorObra() {
		this.setTitle("MOSTRAR PASES POR OBRA");
		this.buscar = new JButton("Aceptar");
		this.cancelar = new JButton("Cancelar");
		JLabel id1 = new JLabel("Id obra:");
		idObra = ViewUtils.integerSpinner(0, 0, Integer.MAX_VALUE, 1);
		
		ArrayList<Pair<JComponent, JComponent>> pairComponents = new ArrayList<>();
		pairComponents.add(new Pair<>(id1, idObra));
		
		super.initComps(pairComponents, buscar, cancelar);
		
		this.setVisible(true);
		
		buscar.addActionListener(e->{
			Controlador.getInstance().accion(Evento.MOSTRAR_PASES_POR_OBRA, Integer.valueOf((int)idObra.getValue()));
			dispose();
		});
		
		cancelar.addActionListener(e->{dispose();});
		
		this.setVisible(true);
		this.setLocationRelativeTo(null);
	}
	
	@Override
	public void actualizar(Evento evento, Object datos) {
	    if (evento == Evento.RES_OK) {
	        ArrayList<String[]> colNames = new ArrayList<>();
	        colNames.add(Messages.colNomsPase);

	        ArrayList<TPase> pases = new ArrayList<>();
	        pases.addAll((ArrayList<TPase>) datos);

	        ArrayList<ArrayList<TPase>> data = new ArrayList<>();
	        data.add(pases);

	        TablaDefault<TPase> tabla = new TablaDefault<>("Pases", colNames, data, false);
	        tabla.setVisible(true);

	    } else if (evento == Evento.RES_KO) {
	        ViewUtils.createErrorDialogMessage(Messages.X_MOSTRAR_PASES + ' ' + Messages.MOTIVO.formatted(((Exception) datos).getMessage()));
	    }
	}
}