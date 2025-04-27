package presentacion.GUIFactura;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;

import javax.swing.*;

import exceptions.BBDDReadException;
import misc.Constants;
import misc.Evento;
import misc.Messages;
import misc.JSwingUtils;
import negocio.factura.TFactura;
import presentacion.IGUI;
import presentacion.controlador.Controlador;
import presentacion.factoria.FactoriaAbstractaPresentacion;

@SuppressWarnings("serial")
public class VistaBuscarFac extends JFrame implements IGUI {
	private JLabel lIdFac;
	private JTextField tIdFac;
	private JButton buscar;
	private JButton cancel;
	
	public VistaBuscarFac() {
		super("BUSCAR FACTURA");
		JSwingUtils.setAppIcon(this);
		JPanel mainPanel = new JPanel(new BorderLayout());
		mainPanel.setSize(Constants.getScaledScreenDimension(2, 2));
		this.lIdFac = new JLabel("Id factura:");
		this.tIdFac = new JTextField(20);
		this.buscar = new JButton("Aceptar");
		this.cancel = new JButton("Cancelar");
		
		JPanel infoPanel = new JPanel();
		infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
		infoPanel.add(JSwingUtils.createComponentPair(this.lIdFac, this.tIdFac));
		mainPanel.add(infoPanel, BorderLayout.CENTER);
		
		JPanel responsePanel = JSwingUtils.createResponsePair(buscar, cancel);
		mainPanel.add(responsePanel, BorderLayout.SOUTH);
		
		buscar.addActionListener(e->{
			try {
				Controlador.getInstance().accion(Evento.BUSCAR_FACTURA, Integer.valueOf(tIdFac.getText()));
				dispose();
			}
			catch(ArithmeticException ex) {
				Controlador.getInstance().accion(Evento.BUSCAR_FACTURA, Messages.ERROR_CAMPOS_INCORRECTOS);
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
			
			FactoriaAbstractaPresentacion.getInstance().createTabla("BUSCAR FACTURA", nomCols, fac, true);
		}
		else if(evento == Evento.RES_KO) {
			String error;
			if (datos instanceof String) error = (String)datos;
			else if (datos instanceof BBDDReadException) error = ((BBDDReadException)datos).getMessage();
			else error = Messages.ID_NO_ENCONTRADO.formatted(String.valueOf(((int)datos)));
			FactoriaAbstractaPresentacion.getInstance().createErrorDialogMessage(Messages.X_BUSCAR_FACTURA + ' ' + Messages.MOTIVO.formatted(error));
		}
	}
}
