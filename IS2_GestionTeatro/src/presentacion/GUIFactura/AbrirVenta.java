package presentacion.GUIFactura;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;

import negocio.factura.TLineaFactura;

public class AbrirVenta {
	private static List<TLineaFactura> carrito;
	private static JButton bAbVenta;
	
	public static void enableButton() {
		bAbVenta.setEnabled(true);
	}
	
	public static void setButton(JButton abrirVenta) {
		bAbVenta = abrirVenta;
	}
	
	public static void resetCarrito() {
		carrito =  new ArrayList<TLineaFactura>();
	}
	
	public static List<TLineaFactura> getCarrito() { return carrito; } 
}
