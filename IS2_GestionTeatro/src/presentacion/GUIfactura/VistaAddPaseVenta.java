package presentacion.GUIfactura;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

import negocio.pase.TPase;
import presentacion.*;
import presentacion.controlador.Controlador;

@SuppressWarnings("serial")
public class VistaAddPaseVenta extends JFrame implements IGUI {
	private JTextField tNombre;
	private JSpinner sFecha;
	private JTextField tCtdad;
	private JButton ok;
	private JButton cancel;
	
	public VistaAddPaseVenta() {
		super("AÑADIR PASE A VENTA");
		JPanel mainPanel = new JPanel(new BorderLayout());
		mainPanel.setSize(Constants.getScaledScreenDimension(2, 2));
		this.tNombre = new JTextField(20);
        //SpinnerDateModel para año, mes, día y hora
		SpinnerDateModel model = new SpinnerDateModel(Calendar.getInstance().getTime(), null, null, Calendar.HOUR_OF_DAY);
        this.sFecha = new JSpinner(model);
		this.tCtdad = new JTextField(20);
		this.ok = new JButton("Aceptar");
		this.cancel = new JButton("Cancelar");
		
		JPanel infoPanel = new JPanel();
		infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
		infoPanel.add(PanelUtils.createLabelFieldPair("Nombre:", tNombre));
		infoPanel.add(PanelUtils.createLabelFieldPair("Fecha (DD/MM/AAAA) Hora (HH:MM):", sFecha));
		infoPanel.add(PanelUtils.createLabelFieldPair("Cantidad:", tCtdad));
		mainPanel.add(infoPanel, BorderLayout.CENTER);
		
		JPanel responsePanel = PanelUtils.createResponsePair(ok, cancel);
		mainPanel.add(responsePanel, BorderLayout.SOUTH);
		
		ok.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				try {
					//Nombre
					String nom = tNombre.getText();
					
					//Fecha
					Date fechaSeleccionada = (Date) sFecha.getValue();
					Calendar cal = Calendar.getInstance();
					cal.setTime(fechaSeleccionada);
					int a = cal.get(Calendar.YEAR);
					int m = cal.get(Calendar.MONTH);
					int d = cal.get(Calendar.DAY_OF_MONTH);
					int h = cal.get(Calendar.HOUR_OF_DAY);
					
					//Cantidad
					int ctdad = Integer.valueOf(tCtdad.getText());
					if (ctdad <= 0) throw new ArithmeticException();
					
					TPase tPase = new TPase(nom,a,m,d,h,ctdad);
					
					Controlador.getInstance().accion(Evento.ANYADIR_PASE_A_VENTA.getind(), tPase);
				}
				catch(ArithmeticException ex) {
					PanelUtils.panelCamposIncorrectos(VistaAddPaseVenta.this);
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
		if (e == Evento.RES_ALTA_CLIENTE_OK) JOptionPane.showMessageDialog(this, Messages.EX_PASE_ANYADIDO_A_VENTA);
		else if(e == Evento.RES_ALTA_CLIENTE_KO) JOptionPane.showMessageDialog(this, Messages.X_PASE_ANYADIDO_A_VENTA);
	}
}
