package presentacion.GUIfactura;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import eventos.Evento;
import misc.Messages;
import misc.Pair;
import negocio.factura.TFactura;
import negocio.factura.TLineaFactura;
import presentacion.IGUI;
import presentacion.controlador.Controlador;
import presentacion.superClases.VistaDefault;

@SuppressWarnings("serial")
public class VistaCerrarVenta extends VistaDefault implements IGUI {

	private JLabel labelCliente;
	private JTextField tfCliente;
	private JLabel labelTaquillero;
	private JTextField tfTaquillero;

	private JButton aceptar;
	private JButton cancelar;

	private Collection<TLineaFactura> carrito;

	public VistaCerrarVenta() {}

	public VistaCerrarVenta(TFactura tFactura) {
		this.setTitle("CERRAR VENTA");
		this.carrito = tFactura.getCarrito();
		this.labelCliente = new JLabel("DNI del cliente:");
		this.tfCliente = new JTextField(50);

		this.labelTaquillero = new JLabel("DNI del taquillero:");
		this.tfTaquillero = new JTextField(50);

		this.aceptar = new JButton("Aceptar");
		this.cancelar = new JButton("Cancelar");

		ArrayList<Pair<JLabel, JComponent>> labeledComponents = new ArrayList<>();
		labeledComponents.add(new Pair<>(labelCliente, tfCliente));
		labeledComponents.add(new Pair<>(labelTaquillero, tfTaquillero));

		super.initComps(labeledComponents, aceptar, cancelar);
		
		aceptar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String cliente = tfCliente.getText();
				String taquillero = tfTaquillero.getText();
				TFactura factura = new TFactura(cliente, taquillero, carrito);
				Controlador.getInstance().accion(Evento.CERRAR_VENTA, factura);
			}
		});

		cancelar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				VistaCerrarVenta.this.dispose();
			}
		});
	}

	@Override
	public void actualizar(Evento evento, Object datos) {
		if (evento == Evento.RES_CERRAR_VENTA_OK) {
			JOptionPane.showMessageDialog(this, Messages.EX_VENTA_CERRADA);
		} else if (evento == Evento.RES_CERRAR_VENTA_KO) {
			JOptionPane.showMessageDialog(this, Messages.X_VENTA_CERRADA);
		}
	}
}
