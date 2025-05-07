package presentacion.GUIPase;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.Collection;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;

import misc.Messages;
import misc.Pair;
import negocio.factura.TFactura;
import negocio.pase.TPase;
import presentacion.Evento;
import presentacion.IGUI;
import presentacion.ViewUtils;
import presentacion.TablaDefault;
import presentacion.VistaDefault;
import presentacion.controlador.Controlador;
import presentacion.factoria.FactoriaAbstractaPresentacion;

public class VistaBuscarPase extends VistaDefault {
	private JSpinner id;
	private JButton buscar;
	private JButton cancelar;
	
	public VistaBuscarPase() {
		this.setTitle("BUSCAR PASE");
		this.buscar = new JButton("Aceptar");
		this.cancelar = new JButton("Cancelar");
		JLabel id1 = new JLabel("Id");
		id = ViewUtils.integerSpinner(0, 0, Integer.MAX_VALUE, 1);
		
		ArrayList<Pair<JComponent, JComponent>> pairComponents = new ArrayList<>();
		pairComponents.add(new Pair<>(id1, id));
		
		super.initComps(pairComponents, buscar, cancelar);
		
		this.setVisible(true);
		
		buscar.addActionListener(e->{
			Controlador.getInstance().accion(Evento.BUSCAR_PASE, Integer.valueOf((int)id.getValue()));
			dispose();
		});
		
		cancelar.addActionListener(e->{this.setVisible(false); dispose();});
	}
	
	@Override
	public void actualizar(Evento evento, Object datos) {
		if (evento == Evento.RES_OK) {

			ArrayList<String[]> colNames = new ArrayList<>();
			colNames.add(Messages.colNomsPase);

			ArrayList<TPase> pases = new ArrayList<>();
			pases.add((TPase) datos);

			ArrayList<ArrayList<TPase>> data = new ArrayList<>();
			data.add(pases);

			// Create and display the table with the pase data
			TablaDefault<TPase> tabla = new TablaDefault<>("Pase", colNames, data, false);
			tabla.setVisible(true);
		} else if (evento == Evento.RES_KO) {
			String error;
			if (datos instanceof Exception) error = ((Exception) datos).getMessage();
			else error = Messages.EXC_UNKNOWN_PASE;
			ViewUtils.createErrorDialogMessage(Messages.X_PASE_BUSCADO + ' ' + Messages.MOTIVO.formatted(error));
		}
	}
}
