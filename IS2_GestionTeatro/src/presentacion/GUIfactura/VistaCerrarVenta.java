package presentacion.GUIfactura;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import eventos.Evento;
import misc.Messages;
import negocio.factura.TFactura;
import negocio.factura.TLineaFactura;
import presentacion.IGUI;
import presentacion.controlador.Controlador;

@SuppressWarnings("serial")
public class VistaCerrarVenta extends JFrame implements IGUI {
	
	private JLabel labelCliente;
	private JTextField tfCliente;
	private JLabel labelTaquillero;
	private JTextField tfTaquillero;
	
	private JButton aceptar;
	private JButton cancelar;
	
	private JPanel panel;
	
	private Collection<TLineaFactura> carrito;
	
	public VistaCerrarVenta(TFactura tFactura) {
		super("CERRAR VENTA");
		
		this.carrito = tFactura.getCarrito();
		
		panel = new JPanel();
		panel.setSize(new Dimension(300, 100));
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		labelCliente = new JLabel("DNI del cliente:");
		tfCliente = new JTextField(50);
		panel.add(labelCliente);
		panel.add(tfCliente);
		
		//Creo q hacer esto no hace falta. Swing te lo gestiona automáticamente
		//Por eso creé en PanelUtils un método q te crea un panel Label-component
		//para uso general, por si te interesa utilizarlo o modificarlo así todos
		//tenemos el mismo panel "default"
		panel.add(Box.createVerticalStrut(10));
		
		labelTaquillero = new JLabel("DNI del taquillero:");
		tfTaquillero = new JTextField(50);
		panel.add(labelTaquillero);
		panel.add(tfTaquillero);
		
		
		JPanel botones = new JPanel();
		botones.setLayout(new FlowLayout());
		aceptar = new JButton("Aceptar");
		aceptar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String cliente = tfCliente.getText();
				String taquillero = tfTaquillero.getText();
				TFactura factura = new TFactura(cliente, taquillero, carrito);
				Controlador.getInstance().accion(Evento.CERRAR_VENTA, factura);
			}
		});
		botones.add(aceptar);
		
		cancelar = new JButton("Cancelar");
		cancelar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				VistaCerrarVenta.this.dispose();
			}
		});
		botones.add(cancelar);
		panel.add(botones);
		
		this.add(panel);
	}
	
	@Override
	public void actualizar(Evento evento, Object datos) {
		if (evento == Evento.RES_CERRAR_VENTA_OK) {
			JOptionPane.showMessageDialog(this, Messages.EX_VENTA_CERRADA);
		}
		else if (evento == Evento.RES_CERRAR_VENTA_KO) {
			JOptionPane.showMessageDialog(this, Messages.X_VENTA_CERRADA);
		}
	}


}
