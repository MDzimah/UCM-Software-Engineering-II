package presentacion.GUIFactura;

import misc.Evento;
import misc.Messages;
import presentacion.factoria.FactoriaAbstractaPresentacion;

@SuppressWarnings("serial")
public class VistaAddPaseVenta extends ModificacionPaseEnVenta {
	
	public VistaAddPaseVenta() {
			this.setTitle("AÑADIR PASE A VENTA");
			
			super.initComps();
			
			super.okAndCancelListener(Evento.ANYADIR_PASE_A_VENTA);
			
			this.setVisible(true);
			this.setLocationRelativeTo(null);
	}
	
	@Override
	public void actualizar(Evento evento, Object datos) {
		if (evento == Evento.RES_OK) {
			FactoriaAbstractaPresentacion.getInstance().createDialogMessage(Messages.EX_PASE_ANYADIDO_A_VENTA);
		}
		else if(evento == Evento.RES_KO) { 
			FactoriaAbstractaPresentacion.getInstance().createErrorDialogMessage(Messages.X_ANYADIR_PASE_A_VENTA + ' ' + Messages.MOTIVO.formatted((String)datos));
		}
	}
}
