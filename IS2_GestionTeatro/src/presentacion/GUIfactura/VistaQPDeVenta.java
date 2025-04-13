package presentacion.GUIfactura;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import presentacion.Evento;
import presentacion.IGUI;
import presentacion.Messages;

@SuppressWarnings("serial")
public class VistaQPDeVenta extends VistaAdd_QuitarPdeVenta implements IGUI {
	private JLabel lTituloObra;
	private JTextField tTituloObra;
	private JLabel lFecha;
	private JSpinner sFecha;
	private JLabel lCtdad;
	private JTextField tCtdad;
	private JButton ok;
	private JButton cancel;
	
	public VistaQPDeVenta() {
		this.setTitle("QUITAR PASE DE VENTA");
		
		super.initComps(this.lTituloObra, this.tTituloObra, 
				this.lFecha, this.sFecha, 
				this.lCtdad, this.tCtdad, 
				this.ok, this.cancel);
		
		super.okAndCancelListener(this.tTituloObra, 
				this.sFecha, 
				this.tCtdad, 
				this.ok, 
				this.cancel, 
				Evento.QUITAR_PASE_DE_VENTA);
		
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
