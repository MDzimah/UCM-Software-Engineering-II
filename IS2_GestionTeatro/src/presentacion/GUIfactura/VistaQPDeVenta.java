package presentacion.GUIfactura;

import javax.swing.JOptionPane;

import eventos.Evento;
import misc.Messages;
import presentacion.IGUI;

@SuppressWarnings("serial")
public class VistaQPDeVenta extends VistaAdd_QuitarPdeVenta implements IGUI {
	public VistaQPDeVenta() {
		this.setTitle("QUITAR PASE DE VENTA");
		
		super.initComps();
		
		super.okAndCancelListener(Evento.QUITAR_PASE_DE_VENTA);
		
		this.setVisible(true);
		this.setLocationRelativeTo(null);
	}
	
	@Override
	public void actualizar(int evento, Object datos) {
		Evento e = Evento.intAEvento(evento);
		if (e == Evento.RES_QUITAR_PASE_DE_VENTA_OK) {
			JOptionPane.showMessageDialog(this, Messages.EX_PASE_QUITADO_DE_VENTA);
			//...
		}
		else if(e == Evento.RES_QUITAR_PASE_DE_VENTA_KO) JOptionPane.showMessageDialog(this, Messages.X_PASE_QUITADO_DE_VENTA);
	}
}
