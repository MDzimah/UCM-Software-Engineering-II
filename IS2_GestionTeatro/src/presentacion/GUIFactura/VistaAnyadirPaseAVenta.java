package presentacion.GUIFactura;

import exceptions.BBDDReadException;
import misc.Messages;
import presentacion.Evento;
import presentacion.ViewUtils;

@SuppressWarnings("serial")
public class VistaAnyadirPaseAVenta extends ModificacionPaseEnVenta {
	
	public VistaAnyadirPaseAVenta() {
		this.setTitle("Añadir pase a venta");
		
		super.initComps();
		
		super.okAndCancelListener(Evento.ANYADIR_PASE_A_VENTA);
		
		this.setVisible(true);
		this.setLocationRelativeTo(null);
	}
	
	@Override
	public void actualizar(Evento evento, Object datos) {
		if (evento == Evento.RES_OK) ViewUtils.createDialogMessage(Messages.EX_PASE_ANYADIDO_A_VENTA.formatted((int)datos));
		else if(evento == Evento.RES_KO) { 
			String error;
			if (datos instanceof BBDDReadException) error = ((BBDDReadException)datos).getMessage();
			else error = Messages.ID_NO_ENCONTRADO.formatted((int)datos);
			ViewUtils.createErrorDialogMessage(Messages.X_ANYADIR_PASE_A_VENTA + ' ' + Messages.MOTIVO.formatted(error));
		}
	}
}
