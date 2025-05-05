package presentacion.GUIFactura;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;

import negocio.factura.TLineaFactura;

public class AbrirVenta {
	private static List<TLineaFactura> carrito =  new ArrayList<TLineaFactura>();;
	private static JButton bCarr;
	private static JButton bAbVenta;

	
	public static void enableButton() {
		bAbVenta.setEnabled(true);
	}
	
	public static void setAbrirVentaButton(JButton button) {
		bAbVenta = button;
	}
	
	public static void setCarritoButton(JButton button) {
		bCarr = button;
	}
	
	public static void updateCarritoButton() {
		bCarr.setText(String.valueOf(carrito.size()));
	}
	
	public static void resetCarrito() {
		carrito.clear();
	}
	
	public static List<TLineaFactura> getCarrito() { return carrito; } 
}
