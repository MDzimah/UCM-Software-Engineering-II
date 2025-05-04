package presentacion.GUIFactura;

import java.awt.*;
import java.util.ArrayList;

import javax.swing.*;

import exceptions.BBDDReadException;
import misc.Constants;
import misc.Messages;
import misc.Pair;
import misc.JSwingUtils;
import negocio.factura.TFactura;
import presentacion.Evento;
import presentacion.TablaDefault;
import presentacion.VistaDefault;
import presentacion.controlador.Controlador;

@SuppressWarnings("serial")
public class VistaBuscarFac extends VistaDefault {
	private JLabel lIdFac;
	private JSpinner sIdFac;
	private JButton buscar;
	private JButton cancel;
	
	public VistaBuscarFac() {
		this.setTitle("Buscar factura");
		JSwingUtils.setAppIcon(this);
		JPanel mainPanel = new JPanel(new BorderLayout());
		mainPanel.setSize(Constants.getScaledScreenDimension(2, 2));
		this.lIdFac = new JLabel("Id factura:");
		SpinnerNumberModel spinnerModel = new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 1);
		this.sIdFac = new JSpinner(spinnerModel);
		this.buscar = new JButton("Buscar");
		this.cancel = new JButton("Cancelar");
		
		ArrayList<Pair<JComponent, JComponent>> labeledComponents = new ArrayList<>();
		labeledComponents.add(new Pair<>(lIdFac, sIdFac));
		super.initComps(labeledComponents, buscar, cancel);
		
		buscar.addActionListener(e->{
			try {
				sIdFac.commitEdit();
				Integer idFac = (Integer)sIdFac.getValue();
				SwingUtilities.invokeLater(()->{Controlador.getInstance().accion(Evento.BUSCAR_FACTURA, idFac);});
				dispose();
			}
			catch(Exception ex) {
				sIdFac.updateUI();
			}
		});
		
		cancel.addActionListener(e->{dispose();});
		
		this.setVisible(true);
		this.setLocationRelativeTo(null);
	}
	
	@Override
	public void actualizar(Evento evento, Object datos) {
		if (evento == Evento.RES_OK) {
			ArrayList<TFactura> fac = new ArrayList<TFactura>();
			fac.add((TFactura)datos);
			new TablaDefault<TFactura>("FACTURA", Messages.colNomsFactura, fac, false).setVisible(true);
		}
		else if(evento == Evento.RES_KO) {
			String error;
			if (datos instanceof BBDDReadException) error = ((BBDDReadException)datos).getMessage();
			else error = Messages.ID_NO_ENCONTRADO.formatted(String.valueOf(((int)datos)));
			JSwingUtils.createErrorDialogMessage(Messages.X_BUSCAR_FACTURA + ' ' + Messages.MOTIVO.formatted(error));
		}
	}
}
