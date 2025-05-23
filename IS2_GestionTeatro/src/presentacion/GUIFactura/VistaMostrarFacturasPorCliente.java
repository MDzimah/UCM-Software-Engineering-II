package presentacion.GUIFactura;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;

import exceptions.BBDDReadException;
import misc.Messages;
import misc.Pair;
import negocio.factura.TFactura;
import presentacion.Evento;
import presentacion.TablaDefault;
import presentacion.ViewUtils;
import presentacion.VistaDefault;
import presentacion.controlador.Controlador;

@SuppressWarnings("serial")
public class VistaMostrarFacturasPorCliente extends VistaDefault {
	private JLabel lIdCliente;
	private JSpinner sIdCliente;
	private JButton mostrar;
	private JButton cancel;
	
	public VistaMostrarFacturasPorCliente() {
		this.setTitle("Mostrar facturas por cliente");
		ViewUtils.setAppIcon(this);
		JPanel mainPanel = new JPanel(new BorderLayout());
		mainPanel.setSize(ViewUtils.getScaledScreenDimension(2, 2));
		this.lIdCliente = new JLabel("Id cliente:");
		this.sIdCliente = ViewUtils.integerSpinner(0, 0, Integer.MAX_VALUE, 1);
		this.mostrar = new JButton("Mostrar");
		this.cancel = new JButton("Cancelar");
		
		ArrayList<Pair<JComponent, JComponent>> labeledComponents = new ArrayList<>();
		labeledComponents.add(new Pair<>(lIdCliente, sIdCliente));
		super.initComps(labeledComponents, mostrar, cancel);
		
		mostrar.addActionListener(e->{
			try {
				sIdCliente.commitEdit();
				int idFac = (int)sIdCliente.getValue();
				SwingUtilities.invokeLater(()->{Controlador.getInstance().accion(Evento.MOSTRAR_FACTURAS_POR_CLIENTE, idFac);});
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

			ArrayList<TFactura> singleFactura = (ArrayList<TFactura>) datos;
			ArrayList<ArrayList<TFactura>> data = new ArrayList<>();
			data.add(singleFactura);

			new TablaDefault<>("Facturas por cliente", colNames, data, false);
		}
		else if (evento == Evento.RES_KO) {
			String error = ((Exception) datos).getMessage();
			ViewUtils.createErrorDialogMessage(Messages.X_MOSTRAR_FACTURAS + ' ' + Messages.MOTIVO.formatted(error));
		}
	}

}
