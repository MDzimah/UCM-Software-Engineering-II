package presentacion.GUIfactura;

import misc.Messages;
import presentacion.Evento;
import presentacion.factoria.FactoriaAbstractaPresentacion;

@SuppressWarnings("serial")
public class VistaAddPaseVenta extends VistaAdd_QuitarPdeVenta {
	
	public VistaAddPaseVenta() {
			this.setTitle("AÑADIR PASE A VENTA");
			
			super.initComps();
			
			super.okAndCancelListener(Evento.ANYADIR_PASE_A_VENTA);
			
			this.setVisible(true);
			this.setLocationRelativeTo(null);
	}
	
	@Override
	public void actualizar(Evento evento, Object datos) {
		if (evento == Evento.RES_ANYADIR_PASE_A_VENTA_OK) {
			FactoriaAbstractaPresentacion.getInstance().createNonIGUIVistas(Evento.MESSAGE_DIALOG, Messages.EX_PASE_ANYADIDO_A_VENTA);
			this.dispose();
		}
		else if(evento == Evento.RES_ANYADIR_PASE_A_VENTA_KO) { 
			FactoriaAbstractaPresentacion.getInstance().createNonIGUIVistas(Evento.MESSAGE_DIALOG, Messages.X_PASE_ANYADIDO_A_VENTA.formatted((String)datos));
		}
	}
}
