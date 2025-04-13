package presentacion.GUIfactura;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import eventos.Evento;
import misc.Constants;
import misc.Messages;
import misc.PanelUtils;
import negocio.factura.TFactura;
import presentacion.IGUI;
import presentacion.controlador.Controlador;
import presentacion.superClases.TablaDefault;


//TABLA CON TODOS LOS DATOS EN UNA TABLA DE CADA FACTURA (id, fecha, importe, etc.)

@SuppressWarnings("serial")
public class VistaMostrarFacs extends JFrame implements IGUI {
	private JButton mostrar;
	private JButton cancel;
	
	public VistaMostrarFacs() {
		super("MOSTRAR FACTURAS");
		this.mostrar = new JButton("Mostrar");
		this.cancel = new JButton("Cancelar");
		JPanel responsePanel = PanelUtils.createResponsePair(this.mostrar, this.cancel);
		responsePanel.setSize(Constants.getScaledScreenDimension(2, 2));
		
		mostrar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Controlador.getInstance().accion(Evento.MOSTRAR_FACTURAS.getind(), null);
				dispose();
			}
		});
		
		cancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) { dispose(); }
		});
	}
	
	@Override
	public void actualizar(int evento, Object datos) {
		Evento e = Evento.intAEvento(evento);
		if (e == Evento.RES_MOSTRAR_FACTURAS_OK) {
			List<Object[]> data = (List<Object[]>)datos;
			String[] nomCols = {"ID", "TAQUILLERO", "PASES"};
			TablaDefault tb = new TablaDefault(nomCols, data, "FACTURAS");
		}
		else if(e == Evento.RES_MOSTRAR_FACTURAS_KO) JOptionPane.showMessageDialog(this, Messages.X_MOSTRAR_FACTURAS);
	}

}
