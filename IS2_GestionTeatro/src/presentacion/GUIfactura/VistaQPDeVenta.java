package presentacion.GUIfactura;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;

import negocio.pase.TPase;
import presentacion.Constants;
import presentacion.Evento;
import presentacion.IGUI;
import presentacion.Messages;
import presentacion.PanelUtils;
import presentacion.controlador.Controlador;

@SuppressWarnings("serial")
public class VistaQPDeVenta extends JFrame implements IGUI {
	private JLabel lNombre;
	private JTextField tNombre;
	private JLabel lFecha;
	private JSpinner sFecha;
	private JLabel lCtdad;
	private JTextField tCtdad;
	private JButton ok;
	private JButton cancel;
	
	public VistaQPDeVenta() {
		super("QUITAR PASE DE VENTA");
		JPanel mainPanel = new JPanel(new BorderLayout());
		mainPanel.setSize(Constants.getScaledScreenDimension(2, 2));
		this.lNombre = new JLabel("Nombre:");
		this.tNombre = new JTextField(20);
		this.lFecha = new JLabel("Fecha (DD/MM/AAAA) Hora (HH:MM):");
		SpinnerDateModel model = new SpinnerDateModel(Calendar.getInstance().getTime(), null, null, Calendar.HOUR_OF_DAY);
        this.sFecha = new JSpinner(model);
		this.lCtdad = new JLabel("Cantidad:");
		this.tCtdad = new JTextField(20);
		this.ok = new JButton("Aceptar");
		this.cancel = new JButton("Cancelar");
		
		JPanel infoPanel = new JPanel();
		infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
		infoPanel.add(PanelUtils.createLabelFieldPair(this.lNombre, this.tNombre));
		infoPanel.add(PanelUtils.createLabelFieldPair(this.lFecha, this.sFecha));
		infoPanel.add(PanelUtils.createLabelFieldPair(this.lCtdad, this.tCtdad));
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
					
					Controlador.getInstance().accion(Evento.QUITAR_PASE_DE_VENTA.getind(), tPase);
					dispose();
				}
				catch(ArithmeticException ex) {
					PanelUtils.panelCamposIncorrectos(VistaQPDeVenta.this);
					setVisible(true);
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
		if (e == Evento.RES_QUITAR_PASE_DE_VENTA_OK) {
			JOptionPane.showMessageDialog(this, Messages.EX_PASE_QUITADO_DE_VENTA);
			//...
		}
		else if(e == Evento.RES_QUITAR_PASE_DE_VENTA_KO) JOptionPane.showMessageDialog(this, Messages.X_PASE_QUITADO_DE_VENTA);
	}
}
