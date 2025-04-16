package presentacion.GUIfactura;

import javax.swing.JOptionPane;

import eventos.Evento;
import misc.Messages;
import misc.PanelUtils;
import negocio.factura.TFactura;
import presentacion.IGUI;

@SuppressWarnings("serial")
public class VistaQPDeVenta extends VistaAdd_QuitarPdeVenta implements IGUI {
	
	public VistaQPDeVenta() {}
	
	public VistaQPDeVenta(TFactura tFac) {
		this.setTitle("QUITAR PASE DE VENTA");
		
		super.initComps();
		
		super.okAndCancelListener(tFac, Evento.QUITAR_PASE_DE_VENTA);
		
		this.setVisible(true);
		this.setLocationRelativeTo(null);
	}
	
	@Override
	public void actualizar(Evento evento, Object datos) {
		if (evento == Evento.RES_QUITAR_PASE_DE_VENTA_OK) {
			PanelUtils.panelMessage(this, Messages.EX_PASE_QUITADO_DE_VENTA);
			//... Cómo hacer repintar el carrito??? 
		}
		else if(evento == Evento.RES_QUITAR_PASE_DE_VENTA_KO) PanelUtils.panelMessage(this, Messages.X_PASE_QUITADO_DE_VENTA);
	}
}
