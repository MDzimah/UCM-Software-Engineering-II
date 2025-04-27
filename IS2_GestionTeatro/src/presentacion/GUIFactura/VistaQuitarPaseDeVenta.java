package presentacion.GUIFactura;

import exceptions.BBDDReadException;
import misc.Evento;
import misc.Messages;
import negocio.factura.TLineaFactura;
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
		if (evento == Evento.RES_OK) FactoriaAbstractaPresentacion.getInstance().createDialogMessage(Messages.EX_PASE_QUITADO_DE_VENTA);
		else if(evento == Evento.RES_KO) {
			String error;
			if (datos instanceof TLineaFactura) {
				if (datos != null) error = Messages.ID_NO_ENCONTRADO.formatted(((TLineaFactura)datos).getIdPase());
				else error = Messages.NO_EN_CARRITO; 
			}
			else if (datos instanceof BBDDReadException) error = ((BBDDReadException)datos).getMessage();
			else error = (String)datos;
			if (datos instanceof TLineaFactura)
			FactoriaAbstractaPresentacion.getInstance().createErrorDialogMessage(Messages.X_QUITAR_PASE_DE_VENTA + ' ' + Messages.MOTIVO.formatted(error));
		}
	}
}
