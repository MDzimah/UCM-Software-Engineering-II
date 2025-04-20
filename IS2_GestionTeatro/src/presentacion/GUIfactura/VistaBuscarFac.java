package presentacion.GUIfactura;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Collection;

import javax.swing.*;

import misc.Constants;
import misc.Evento;
import misc.Messages;
import misc.SwingUtils;
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
		SwingUtils.setAppIcon(this);
		JPanel mainPanel = new JPanel(new BorderLayout());
		mainPanel.setSize(Constants.getScaledScreenDimension(2, 2));
		this.lIdFac = new JLabel("Id factura:");
		this.tIdFac = new JTextField(20);
		this.buscar = new JButton("Aceptar");
		this.cancel = new JButton("Cancelar");
		
		JPanel infoPanel = new JPanel();
		infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
		infoPanel.add(SwingUtils.createComponentPair(this.lIdFac, this.tIdFac));
		mainPanel.add(infoPanel, BorderLayout.CENTER);
		
		JPanel responsePanel = SwingUtils.createResponsePair(buscar, cancel);
		mainPanel.add(responsePanel, BorderLayout.SOUTH);
		
		buscar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					TFactura tFac = new TFactura(Integer.valueOf(tIdFac.getText()));
					Controlador.getInstance().accion(Evento.BUSCAR_FACTURA, tFac);
					dispose();
				}
				catch(ArithmeticException ex) {
					FactoriaAbstractaPresentacion.getInstance().camposIncorrectos();
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
	public void actualizar(Evento evento, Object datos) {
		if (evento == Evento.RES_BUSCAR_FACTURA_OK) {
			/*
			Collection<Object> fac = new ArrayList<Object>();
			fac.add((TFactura)datos);
			String[] nomCols = {"ID","ID CLIENTE", "ID TAQUILLERO", "FECHA", "PASES COMPRADOS", "IMPORTE"};
			
			Object[] datosParaTabla = new Object[3];
			datosParaTabla[0] = nomCols;
			datosParaTabla[1] = fac;
			datosParaTabla[2] = "BUSCAR FACTURA";
			
			FactoriaAbstractaPresentacion.getInstance().createNonIGUIVistas(Evento.TABLA_DEFAULT, datosParaTabla);
			*/
			//QUIZÁS HAGA ALGO GENÉRICO PERSONALIZADO PARA TODO EL MUNDO
		}
		else if(evento == Evento.RES_BUSCAR_FACTURA_KO) {
			FactoriaAbstractaPresentacion.getInstance().messageDialog(Messages.X_BUSCAR_FACTURA.formatted((String) datos));
		}
	}

}
