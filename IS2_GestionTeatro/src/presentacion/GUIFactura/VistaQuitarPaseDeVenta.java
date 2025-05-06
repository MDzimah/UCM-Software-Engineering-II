package presentacion.GUIFactura;

import misc.Messages;
import negocio.factura.TLineaFactura;
import presentacion.ViewUtils;

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
	boolean accion(TLineaFactura tLineaFactura) {
		boolean estaba = false;
		for (TLineaFactura tLf : AbrirVenta.getCarrito()) {
			if (tLf.getIdPase() == tLineaFactura.getIdPase()) {
				tLf.setCantidad(tLf.getCantidad() - tLineaFactura.getCantidad());
				if (tLf.getCantidad() <= 0) AbrirVenta.getCarrito().remove(tLf);
				estaba = true;
				break;
			}
		}
		if (!estaba) ViewUtils.createErrorDialogMessage(Messages.NO_EN_CARRITO);
		AbrirVenta.updateCarritoCountButton();
		return estaba;
	}
}
