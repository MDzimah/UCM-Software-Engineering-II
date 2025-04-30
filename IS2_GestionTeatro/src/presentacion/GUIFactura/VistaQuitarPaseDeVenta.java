package presentacion.GUIFactura;

import exceptions.BBDDReadException;
import misc.Evento;
import misc.JSwingUtils;
import misc.Messages;

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
		if (evento == Evento.RES_OK) JSwingUtils.createDialogMessage(Messages.EX_PASE_QUITADO_DE_VENTA.formatted((int)datos));
		else if(evento == Evento.RES_KO) {
			String error;
			if (datos instanceof BBDDReadException) error = ((BBDDReadException)datos).getMessage();
			else error = Messages.NO_EN_CARRITO; 
			
			JSwingUtils.createErrorDialogMessage(Messages.X_QUITAR_PASE_DE_VENTA + ' ' + Messages.MOTIVO.formatted(error));
		}
	}
}
