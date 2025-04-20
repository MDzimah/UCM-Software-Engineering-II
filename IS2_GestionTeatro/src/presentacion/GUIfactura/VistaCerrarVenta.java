package presentacion.GUIfactura;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTextField;

import misc.Messages;
import misc.Pair;
import misc.SwingUtils;
import negocio.factura.TDatosVenta;
import negocio.factura.TFactura;
import negocio.factura.TLineaFactura;
import presentacion.Evento;
import presentacion.VistaDefault;
import presentacion.controlador.Controlador;
import presentacion.factoria.FactoriaAbstractaPresentacion;

@SuppressWarnings("serial")
public class VistaCerrarVenta extends VistaDefault {

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
		SwingUtils.setAppIcon(this);
		this.carrito = tFactura.getCarrito();
		this.labelCliente = new JLabel("DNI del cliente:");
		this.tfCliente = new JTextField(20);

		this.labelTaquillero = new JLabel("DNI del taquillero:");
		this.tfTaquillero = new JTextField(20);

		this.aceptar = new JButton("Aceptar");
		this.cancelar = new JButton("Cancelar");

		ArrayList<Pair<JComponent, JComponent>> labeledComponents = new ArrayList<>();
		labeledComponents.add(new Pair<>(labelCliente, tfCliente));
		labeledComponents.add(new Pair<>(labelTaquillero, tfTaquillero));

		super.initComps(labeledComponents, aceptar, cancelar, false);
		
		aceptar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					int cliente = Integer.valueOf(tfCliente.getText());
					int taquillero = Integer.valueOf(tfTaquillero.getText());
					TDatosVenta tDv = new TDatosVenta(cliente, taquillero, carrito);
					Controlador.getInstance().accion(Evento.CERRAR_VENTA, tDv);
				}
				catch(ArithmeticException ex) {
					FactoriaAbstractaPresentacion.getInstance().createNonIGUIVistas(Evento.X_CAMPOS_INCORRECTOS, null);
				}
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
			FactoriaAbstractaPresentacion.getInstance().createNonIGUIVistas(Evento.MESSAGE_DIALOG, Messages.EX_VENTA_CERRADA);
		} else if (evento == Evento.RES_CERRAR_VENTA_KO) {
			FactoriaAbstractaPresentacion.getInstance().createNonIGUIVistas(Evento.MESSAGE_DIALOG, Messages.X_VENTA_CERRADA);
		}
	}
}
