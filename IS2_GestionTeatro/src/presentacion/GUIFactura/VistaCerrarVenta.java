package presentacion.GUIFactura;

import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;

import misc.Messages;
import misc.Pair;
import negocio.factura.TDatosVenta;
import presentacion.*;
import presentacion.controlador.Controlador;

@SuppressWarnings("serial")
public class VistaCerrarVenta extends VistaDefault {
	private JLabel labelCliente;
	private JSpinner sCliente;
	private JLabel labelTaquillero;
	private JSpinner sTaquillero;

	private JButton aceptar;
	private JButton cancelar;

	public VistaCerrarVenta() {
		this.setTitle("Cerrar venta");
		ViewUtils.setAppIcon(this);
		
		SpinnerNumberModel spinnerModel = new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 1);

		this.labelCliente = new JLabel("Id del cliente:");
		this.sCliente = new JSpinner(spinnerModel);

		this.labelTaquillero = new JLabel("Id del taquillero:");
		this.sTaquillero = new JSpinner(spinnerModel);

		this.aceptar = new JButton("Aceptar");
		this.cancelar = new JButton("Cancelar");

		ArrayList<Pair<JComponent, JComponent>> labeledComponents = new ArrayList<>();
		labeledComponents.add(new Pair<>(labelCliente, sCliente));
		labeledComponents.add(new Pair<>(labelTaquillero, sTaquillero));

		super.initComps(labeledComponents, aceptar, cancelar);
		
		aceptar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					int cliente = (Integer) sCliente.getValue();
					int taquillero = (Integer) sTaquillero.getValue();
					TDatosVenta tDv = new TDatosVenta(cliente, taquillero, AbrirVenta.getCarrito());
					Controlador.getInstance().accion(Evento.CERRAR_VENTA, tDv);
				}
				catch(ArithmeticException ex) {
					ViewUtils.createInvalidFieldsPanel();
					sCliente.updateUI();
					sTaquillero.updateUI();
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
		if (evento == Evento.RES_OK) {
			ViewUtils.createDialogMessage(Messages.EX_VENTA_CERRADA.formatted((int)datos));
		} else if (evento == Evento.RES_KO) {
			String error;
			if (datos instanceof Exception) error = ((Exception)datos).getMessage();
			else error = Messages.CARRITO_VACIO;
			ViewUtils.createErrorDialogMessage(Messages.X_VENTA_CERRADA + ' ' + Messages.MOTIVO.formatted(error));
		}
		
		AbrirVenta.enableButton();
		AbrirVenta.resetCarrito();
	}
}
