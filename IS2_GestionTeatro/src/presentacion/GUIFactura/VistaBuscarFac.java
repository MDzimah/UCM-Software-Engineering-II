package presentacion.GUIFactura;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;

import javax.swing.*;

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
				TFactura tFac = new TFactura(Integer.valueOf(tIdFac.getText()));
				Controlador.getInstance().accion(Evento.BUSCAR_FACTURA, tFac);
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
			
			FactoriaAbstractaPresentacion.getInstance().createTabla("BUSCA_ FACTURA", nomCols, fac, false);
		}
		else if(evento == Evento.RES_KO) {
			FactoriaAbstractaPresentacion.getInstance().createErrorDialogMessage(Messages.X_BUSCAR_FACTURA + ' ' + Messages.MOTIVO.formatted((String)datos));
		}
	}

}
