package presentacion.GUIFactura;

import java.util.ArrayList;
import java.util.Collection;

import javax.swing.JButton;

import negocio.factura.TLineaFactura;

public class AbrirVenta {
	private static Collection<TLineaFactura> carrito =  new ArrayList<TLineaFactura>();
	private static JButton bCarr;
	private static JButton bAbVenta;

	//Botón abrir venta
	public static void setAbrirVentaButton(JButton button) { bAbVenta = button; }
	public static void enableButton() { bAbVenta.setEnabled(true); }
	
	//Botón carrito y el carrito en sí
	public static Collection<TLineaFactura> getCarrito() { return carrito; } 
	public static void resetCarrito() {	carrito.clear(); }
	public static void setCarritoButton(JButton button) { bCarr = button; }
	public static void updateCarritoCountButton() { 
		bCarr.setText(String.valueOf(carrito.size())); 
	}
}
