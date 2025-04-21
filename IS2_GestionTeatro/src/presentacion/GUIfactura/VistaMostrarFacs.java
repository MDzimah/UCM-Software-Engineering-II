package presentacion.GUIfactura;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import misc.Constants;
import misc.Evento;
import misc.Messages;
import misc.SwingUtils;
import presentacion.IGUI;
import presentacion.controlador.Controlador;
import presentacion.factoria.FactoriaAbstractaPresentacion;


@SuppressWarnings("serial")
public class VistaMostrarFacs extends JFrame implements IGUI {
	private JButton mostrar;
	private JButton cancel;
	
	public VistaMostrarFacs() {
		super("MOSTRAR FACTURAS");
		SwingUtils.setAppIcon(this);
		this.mostrar = new JButton("Mostrar");
		this.cancel = new JButton("Cancelar");
		JPanel responsePanel = SwingUtils.createResponsePair(this.mostrar, this.cancel);
		responsePanel.setSize(Constants.getScaledScreenDimension(2, 2));
		
		mostrar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Controlador.getInstance().accion(Evento.MOSTRAR_FACTURAS, null);
				dispose();
			}
		});
		
		cancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) { dispose(); }
		});
		
		this.setVisible(true);
		this.setLocationRelativeTo(null);
		
	}
	
	@Override
	public void actualizar(Evento evento, Object datos) {
		if (evento == Evento.RES_MOSTRAR_FACTURAS_OK) {
			@SuppressWarnings("unchecked")
			Collection<Object> castedData = (Collection<Object>)datos;
			String[] nomCols = {"ID","ID CLIENTE", "ID TAQUILLERO", "FECHA", "IMPORTE"};
			FactoriaAbstractaPresentacion.getInstance().createTabla("MOSTRAR FACTURAS", nomCols, castedData);
		}
		else if(evento == Evento.RES_MOSTRAR_FACTURAS_KO) {
			 FactoriaAbstractaPresentacion.getInstance().createDialogMessage(Messages.X_MOSTRAR_FACTURAS);
		}
	}

}
