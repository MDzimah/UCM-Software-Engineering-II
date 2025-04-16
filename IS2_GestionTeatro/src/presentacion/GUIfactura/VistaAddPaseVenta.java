package presentacion.GUIfactura;

import javax.swing.*;

import eventos.Evento;
import misc.Messages;
import misc.PanelUtils;
import negocio.factura.TFactura;
import presentacion.*;

@SuppressWarnings("serial")
public class VistaAddPaseVenta extends VistaAdd_QuitarPdeVenta implements IGUI {
	
	public VistaAddPaseVenta() {}
	
	public VistaAddPaseVenta(TFactura tFac) {
		this.setTitle("AÑADIR PASE A VENTA");
		
		super.initComps();
		
		super.okAndCancelListener(tFac, Evento.ANYADIR_PASE_A_VENTA);
		
		this.setVisible(true);
		this.setLocationRelativeTo(null);
	}
	
	@Override
	public void actualizar(Evento evento, Object datos) {
		if (evento == Evento.RES_ANYADIR_PASE_A_VENTA_OK) {
			PanelUtils.panelMessage(this, Messages.EX_PASE_ANYADIDO_A_VENTA);
			//... Cómo hacer repintar el carrito??? 
		}
		else if(evento == Evento.RES_ANYADIR_PASE_A_VENTA_KO) 
			PanelUtils.panelMessage(this, Messages.X_PASE_ANYADIDO_A_VENTA.formatted((String)datos));
	}
}
