package presentacion.GUIfactura;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Collection;

import javax.swing.*;

import eventos.Evento;
import misc.Constants;
import misc.Messages;
import misc.PanelUtils;
import negocio.factura.TFactura;
import presentacion.IGUI;
import presentacion.controlador.Controlador;
import presentacion.superClases.TablaDefault;

@SuppressWarnings("serial")
public class VistaBuscarFac extends JFrame implements IGUI {
	private JLabel lIdFac;
	private JTextField tIdFac;
	private JButton buscar;
	private JButton cancel;
	
	public VistaBuscarFac(boolean primeraVez) {
		super("BUSCAR FACTURA");
			if(primeraVez) {
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
						Controlador.getInstance().accion(Evento.BUSCAR_FACTURA, tFac);
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
	}
	
	@Override
	public void actualizar(Evento evento, Object datos) {
		if (evento == Evento.RES_BUSCAR_FACTURA_OK) {
			Collection<Object> fac = new ArrayList<Object>();
			fac.add((TFactura)datos);
			String[] nomCols = {"ID","ID CLIENTE", "ID TAQUILLERO", "FECHA", "PASES COMPRADOS", "IMPORTE"};
			TablaDefault tb = new TablaDefault(nomCols, fac, "BUSCAR FACTURA");
			tb.setVisible(true);
		}
		else if(evento == Evento.RES_BUSCAR_FACTURA_KO) {
			PanelUtils.panelMessage(this, Messages.X_BUSCAR_FACTURA.formatted((String) datos));
		}
	}

}
