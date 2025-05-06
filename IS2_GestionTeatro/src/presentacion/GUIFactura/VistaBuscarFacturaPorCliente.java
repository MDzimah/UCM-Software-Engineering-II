package presentacion.GUIFactura;

import java.awt.BorderLayout;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SwingUtilities;

import exceptions.BBDDReadException;
import misc.Messages;
import misc.Pair;
import negocio.factura.TFactura;
import presentacion.Evento;
import presentacion.TablaDefault;
import presentacion.ViewUtils;
import presentacion.VistaDefault;
import presentacion.controlador.Controlador;

public class VistaBuscarFacturaPorCliente extends VistaDefault {
	private JLabel lIdCliente;
	private JSpinner sIdCliente;
	private JButton buscar;
	private JButton cancel;
	
	public VistaBuscarFacturaPorCliente() {
		this.setTitle("Buscar factura por cliente");
		ViewUtils.setAppIcon(this);
		JPanel mainPanel = new JPanel(new BorderLayout());
		mainPanel.setSize(ViewUtils.getScaledScreenDimension(2, 2));
		this.lIdCliente = new JLabel("Id cliente:");
		this.sIdCliente = ViewUtils.integerSpinner(0, 0, Integer.MAX_VALUE, 1);
		this.buscar = new JButton("Buscar");
		this.cancel = new JButton("Cancelar");
		
		ArrayList<Pair<JComponent, JComponent>> labeledComponents = new ArrayList<>();
		labeledComponents.add(new Pair<>(lIdCliente, sIdCliente));
		super.initComps(labeledComponents, buscar, cancel);
		
		buscar.addActionListener(e->{
			try {
				sIdCliente.commitEdit();
				int idFac = (int)sIdCliente.getValue();
				SwingUtilities.invokeLater(()->{Controlador.getInstance().accion(Evento.BUSCAR_FACTURA_POR_CLIENTE, idFac);});
				dispose();
			}
			catch(Exception ex) {
				ViewUtils.createInvalidFieldsPanel();
				sIdCliente.updateUI();
			}
		});
		
		cancel.addActionListener(e->{dispose();});
		
		this.setVisible(true);
		this.setLocationRelativeTo(null);
	}
	
	@Override
	public void actualizar(Evento evento, Object datos) {
		if (evento == Evento.RES_OK) {
			ArrayList<String[]> colNames = new ArrayList<>();
			colNames.add(Messages.colNomsFactura);

			ArrayList<TFactura> singleFactura = new ArrayList<>();
			singleFactura.add((TFactura) datos);
			ArrayList<ArrayList<TFactura>> data = new ArrayList<>();
			data.add(singleFactura);

			new TablaDefault<>("Factura por cliente", colNames, data, false).setVisible(true);
		}
		else if (evento == Evento.RES_KO) {
			String error;
			if (datos instanceof BBDDReadException) error = ((BBDDReadException)datos).getMessage();
			else error = Messages.ID_NO_ENCONTRADO.formatted(String.valueOf((int) datos));
			ViewUtils.createErrorDialogMessage(Messages.X_BUSCAR_FACTURA + ' ' + Messages.MOTIVO.formatted(error));
		}
	}

}
