package presentacion.GUIfactura;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import negocio.factura.TFactura;
import presentacion.Constants;
import presentacion.Evento;
import presentacion.IGUI;
import presentacion.Messages;
import presentacion.PanelUtils;
import presentacion.controlador.Controlador;

@SuppressWarnings("serial")
public class VistaBuscarFac extends JFrame implements IGUI {
	private JLabel lIdFac;
	private JTextField tIdFac;
	private JButton buscar;
	private JButton cancel;
	
	public VistaBuscarFac() {
		super("BUSCAR FACTURA");
		JPanel mainPanel = new JPanel(new BorderLayout());
		mainPanel.setSize(Constants.getScaledScreenDimension(2, 2));
		this.lIdFac = new JLabel("Id factura:");
		this.tIdFac = new JTextField(20);
		this.buscar = new JButton("Aceptar");
		this.cancel = new JButton("Cancelar");
		
		JPanel infoPanel = new JPanel();
		infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
		infoPanel.add(PanelUtils.createLabelComponentPair(this.lIdFac, this.tIdFac));
		mainPanel.add(infoPanel, BorderLayout.CENTER);
		
		JPanel responsePanel = PanelUtils.createResponsePair(buscar, cancel);
		mainPanel.add(responsePanel, BorderLayout.SOUTH);
		
		buscar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					TFactura tFac = new TFactura(Integer.valueOf(tIdFac.getText()));
					Controlador.getInstance().accion(Evento.BUSCAR_FACTURA.getind(), tFac);
				}
				catch(ArithmeticException ex) {
					PanelUtils.panelCamposIncorrectos(VistaBuscarFac.this);
				}
			}
			
		});
		
		cancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) { dispose(); }
		});
		
		this.setVisible(true);
		this.setLocationRelativeTo(null);
	}
	@Override
	public void actualizar(int evento, Object datos) {
		Evento e = Evento.intAEvento(evento);
		if (e == Evento.RES_BUSCAR_FACTURA_OK) {
			//Muestra la factura buscada supongo
		}
		else if(e == Evento.RES_BUSCAR_FACTURA_KO) JOptionPane.showMessageDialog(this, Messages.X_BUSCAR_FACTURA);
	}

}
