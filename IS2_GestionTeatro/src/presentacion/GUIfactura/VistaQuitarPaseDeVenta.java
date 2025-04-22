package presentacion.GUIfactura;

import misc.Evento;
import misc.Messages;
import presentacion.factoria.FactoriaAbstractaPresentacion;

@SuppressWarnings("serial")
public class VistaQuitarPaseDeVenta extends ModificacionPaseEnVenta {
	
	public VistaQuitarPaseDeVenta() {
		this.setTitle("QUITAR PASE DE VENTA");
		
		super.initComps();
		
		super.okAndCancelListener(Evento.QUITAR_PASE_DE_VENTA);
		
		this.setVisible(true);
		this.setLocationRelativeTo(null);
	}
	
	@Override
	public void actualizar(Evento evento, Object datos) {
		if (evento == Evento.RES_OK) {
			FactoriaAbstractaPresentacion.getInstance().createDialogMessage(Messages.EX_PASE_QUITADO_DE_VENTA);
			//... Cómo hacer repintar el carrito??? 
			this.dispose();
		}
		else if(evento == Evento.RES_KO) {
			FactoriaAbstractaPresentacion.getInstance().createErrorDialogMessage(Messages.X_QUITAR_PASE_DE_VENTA + ' ' + Messages.MOTIVO.formatted((String)datos));
		}
	}
}
