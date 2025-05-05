package presentacion.GUIFactura;

import negocio.factura.TLineaFactura;

@SuppressWarnings("serial")
public class VistaQuitarPaseDeVenta extends ModificacionPaseEnVenta {
	
	public VistaQuitarPaseDeVenta() {
		this.setTitle("Quitar pase de venta");
		
		super.initComps();
		
		super.okAndCancelListener();
		
		this.setVisible(true);
		this.setLocationRelativeTo(null);
	}
	
	@Override
	void accion(TLineaFactura tLineaFactura) {
		for (TLineaFactura tLf : AbrirVenta.getCarrito()) {
			if (tLf.getIdPase() == tLineaFactura.getIdPase()) {
				tLf.setCantidad(tLf.getCantidad() - tLineaFactura.getCantidad());
				if (tLf.getCantidad() <= 0) AbrirVenta.getCarrito().remove(tLf);
				break;
			}
		}
		
		AbrirVenta.updateCarritoButton();
	}
}
