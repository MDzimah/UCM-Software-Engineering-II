package presentacion.GUIfactura;

import misc.Evento;
import misc.Messages;
import presentacion.factoria.FactoriaAbstractaPresentacion;

@SuppressWarnings("serial")
public class VistaQPDeVenta extends VistaAdd_QuitarPdeVenta {
	
	public VistaQPDeVenta() {
		this.setTitle("QUITAR PASE DE VENTA");
		
		super.initComps();
		
		super.okAndCancelListener(Evento.QUITAR_PASE_DE_VENTA);
		
		this.setVisible(true);
		this.setLocationRelativeTo(null);

	}
	
	@Override
	public void actualizar(Evento evento, Object datos) {
		if (evento == Evento.RES_QUITAR_PASE_DE_VENTA_OK) {
			FactoriaAbstractaPresentacion.getInstance().messageDialog(Messages.EX_PASE_QUITADO_DE_VENTA);
			//... Cómo hacer repintar el carrito??? 
			this.dispose();
		}
		else if(evento == Evento.RES_QUITAR_PASE_DE_VENTA_KO) {
			FactoriaAbstractaPresentacion.getInstance().messageDialog(Messages.X_PASE_QUITADO_DE_VENTA);
		}
	}
}
