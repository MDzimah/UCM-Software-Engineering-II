package presentacion.GUIfactura;

import javax.swing.*;

import eventos.Evento;
import misc.Messages;
import presentacion.*;

@SuppressWarnings("serial")
public class VistaAddPaseVenta extends VistaAdd_QuitarPdeVenta implements IGUI {
	private JLabel lTituloObra;
	private JTextField tTituloObra;
	private JLabel lFecha;
	private JSpinner sFecha;
	private JLabel lCtdad;
	private JTextField tCtdad;
	private JButton ok;
	private JButton cancel;
	
	public VistaAddPaseVenta() {
		this.setTitle("AÑADIR PASE A VENTA");
		
		super.initComps(this.lTituloObra, this.tTituloObra, 
				this.lFecha, this.sFecha, 
				this.lCtdad, this.tCtdad, 
				this.ok, this.cancel);
		
		super.okAndCancelListener(this.tTituloObra, 
				this.sFecha, 
				this.tCtdad, 
				this.ok, 
				this.cancel, 
				Evento.ANYADIR_PASE_A_VENTA);
		
		this.setVisible(true);
		this.setLocationRelativeTo(null);
	}
	
	@Override
	public void actualizar(int evento, Object datos) {
		Evento e = Evento.intAEvento(evento);
		if (e == Evento.RES_ANYADIR_PASE_A_VENTA_OK) {
			JOptionPane.showMessageDialog(this, Messages.EX_PASE_ANYADIDO_A_VENTA);
			//... Cómo hacer repintar el carrito???
		}
		else if(e == Evento.RES_ANYADIR_PASE_A_VENTA_KO) JOptionPane.showMessageDialog(this, Messages.X_PASE_ANYADIDO_A_VENTA);
	}
}
