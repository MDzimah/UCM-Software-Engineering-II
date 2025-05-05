package presentacion.GUIFactura;

import negocio.factura.TLineaFactura;

@SuppressWarnings("serial")
public class VistaAnyadirPaseAVenta extends ModificacionPaseEnVenta {
	
	public VistaAnyadirPaseAVenta() {
		this.setTitle("Añadir pase a venta");
		
		super.initComps();
		
		super.okAndCancelListener();
		
		this.setVisible(true);
		this.setLocationRelativeTo(null);
	}

	@Override
	void accion(TLineaFactura tLineaFactura) {
		boolean estaba = false;
		for(TLineaFactura tLf : AbrirVenta.getCarrito()) {
			if (tLf.getIdPase() == tLineaFactura.getIdPase()) {
				tLf.setCantidad(tLf.getCantidad() + tLineaFactura.getCantidad());
				estaba = true;
				break;
			}
		}
		if (!estaba) AbrirVenta.getCarrito().add(tLineaFactura);
		
		AbrirVenta.updateCarritoCountButton();
	}
}
