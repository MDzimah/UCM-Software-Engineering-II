package presentacion.GUIfactura;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import presentacion.Constants;
import presentacion.Evento;
import presentacion.IGUI;
import presentacion.Messages;
import presentacion.controlador.Controlador;


//TABLA CON TODOS LOS DATOS EN UNA TABLA DE CADA FACTURA (id, fecha, importe, etc.)

@SuppressWarnings("serial")
public class VistaMostrarFacs extends JFrame implements IGUI {
	private JButton mostrar;
	private JButton cancel;
	
	public VistaMostrarFacs() {
		super("MOSTRAR FACTURAS");
		JPanel responsePanel = new JPanel(new BorderLayout());
		responsePanel.setSize(Constants.getScaledScreenDimension(2, 2));
		this.mostrar = new JButton("Mostrar");
		this.cancel = new JButton("Cancelar");
		
		mostrar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Controlador.getInstance().accion(Evento.MOSTRAR_FACTURAS.getind(), ...);
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
			//Generar tabla
		}
		else if(e == Evento.RES_MOSTRAR_FACTURAS_KO) JOptionPane.showMessageDialog(this, Messages.X_MOSTRAR_FACTURAS);
	}

}
