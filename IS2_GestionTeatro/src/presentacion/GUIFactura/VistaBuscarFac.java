package presentacion.GUIFactura;

import java.awt.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;

import javax.swing.*;

import exceptions.BBDDReadException;
import misc.Constants;
import misc.Evento;
import misc.Messages;
import misc.Pair;
import misc.JSwingUtils;
import negocio.factura.TFactura;
import presentacion.VistaDefault;
import presentacion.controlador.Controlador;

@SuppressWarnings("serial")
public class VistaBuscarFac extends VistaDefault {
	private JLabel lIdFac;
	private JSpinner sIdFac;
	private JButton buscar;
	private JButton cancel;
	
	public VistaBuscarFac() {
		this.setTitle("BUSCAR FACTURA");
		JSwingUtils.setAppIcon(this);
		JPanel mainPanel = new JPanel(new BorderLayout());
		mainPanel.setSize(Constants.getScaledScreenDimension(2, 2));
		this.lIdFac = new JLabel("Id factura:");
		SpinnerNumberModel spinnerModel = new SpinnerNumberModel(0, 0, Long.MAX_VALUE, 1);
		this.sIdFac = new JSpinner(spinnerModel);
		this.buscar = new JButton("Buscar");
		this.cancel = new JButton("Cancelar");
		
		ArrayList<Pair<JComponent, JComponent>> labeledComponents = new ArrayList<>();
		labeledComponents.add(new Pair<>(lIdFac, sIdFac));
		super.initComps(labeledComponents, cancel, buscar);
		
		buscar.addActionListener(e->{
			try {
				sIdFac.commitEdit();
				int idFac = (int)sIdFac.getValue();
				Controlador.getInstance().accion(Evento.BUSCAR_FACTURA, idFac);
				dispose();
			}
			catch(ParseException ex) {
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
			Collection<Object> fac = new ArrayList<Object>();
			fac.add((TFactura)datos);
			String[] nomCols = {"ID","ID CLIENTE", "ID TAQUILLERO", "FECHA", "IMPORTE", "SUBTOTAL"};
			
			JSwingUtils.createTabla("BUSCAR FACTURA", nomCols, fac, true);
		}
		else if(evento == Evento.RES_KO) {
			String error;
			if (datos instanceof String) error = (String)datos;
			else if (datos instanceof BBDDReadException) error = ((BBDDReadException)datos).getMessage();
			else error = Messages.ID_NO_ENCONTRADO.formatted(String.valueOf(((int)datos)));
			JSwingUtils.createErrorDialogMessage(Messages.X_BUSCAR_FACTURA + ' ' + Messages.MOTIVO.formatted(error));
		}
	}
}
