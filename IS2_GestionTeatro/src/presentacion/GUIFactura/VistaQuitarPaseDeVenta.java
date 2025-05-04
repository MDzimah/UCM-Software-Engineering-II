package presentacion.GUIFactura;

import exceptions.BBDDReadException;
import misc.Messages;
import presentacion.Evento;
import presentacion.ViewUtils;

@SuppressWarnings("serial")
public class VistaQuitarPaseDeVenta extends ModificacionPaseEnVenta {
	
	public VistaQuitarPaseDeVenta() {
		this.setTitle("Quitar pase de venta");
		
		super.initComps();
		
		super.okAndCancelListener(Evento.QUITAR_PASE_DE_VENTA);
		
		this.setVisible(true);
		this.setLocationRelativeTo(null);
	}
	
	@Override
	public void actualizar(Evento evento, Object datos) {
		if (evento == Evento.RES_OK) ViewUtils.createDialogMessage(Messages.EX_PASE_QUITADO_DE_VENTA.formatted((int)datos));
		else if(evento == Evento.RES_KO) {
			String error;
			if (datos instanceof BBDDReadException) error = ((BBDDReadException)datos).getMessage();
			else error = Messages.NO_EN_CARRITO; 
			ViewUtils.createErrorDialogMessage(Messages.X_QUITAR_PASE_DE_VENTA + ' ' + Messages.MOTIVO.formatted(error));
		}
	}
}
