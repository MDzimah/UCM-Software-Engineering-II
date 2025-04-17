package presentacion.GUIfactura;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import eventos.Evento;
import negocio.factura.TFactura;
import negocio.factura.TLineaFactura;
import presentacion.IGUI;

//VENATANA QUE MUESTRA EL CARRITO + 3 BOTONES

//- anyadir pase al carrito
//- 'aceptar' para cerrar la venta
//- 'cancelar'

@SuppressWarnings("serial")
public class VistaVentaEnCurso extends JFrame implements IGUI {
	
	private JButton anyadirPase;
	private JButton quitarPase;
	
	private static Collection<TLineaFactura> carrito = new ArrayList<TLineaFactura>();
	
	private JScrollPane vistaCarrito;
	
	private JButton aceptar;
	private JButton cancelar;
	
	public VistaVentaEnCurso() {
		super("NUEVA VENTA");
		this.setLayout(new BoxLayout(vistaCarrito, BoxLayout.Y_AXIS));
		
		anyadirPase = new JButton("Añadir pase");
		anyadirPase.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new VistaAddPaseVenta(true);
			}
		});
		this.add(anyadirPase);
		
		quitarPase = new JButton("Quitar pase");
		quitarPase.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new VistaQPDeVenta(true);
			}
		});
		this.add(quitarPase);
		
		this.add(new JLabel("Mi carrito"));
		
		
		vistaCarrito = new JScrollPane();
		vistaCarrito.setLayout(new BoxLayout(vistaCarrito, BoxLayout.Y_AXIS));
		this.add(vistaCarrito);
		
		
		aceptar = new JButton("Aceptar");
		aceptar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new VistaCerrarVenta();
			}
		});
		
		cancelar = new JButton("Cancelar");
		cancelar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				VistaVentaEnCurso.this.dispose();
			}
		});
		
		JPanel botones = new JPanel();
		botones.setLayout(new FlowLayout());
		botones.add(aceptar);
		botones.add(cancelar);
		this.add(botones);
		
		this.setVisible(true);
		this.setLocationRelativeTo(null);
	}

	@Override
	public void actualizar(Evento evento, Object datos) {
		if (evento == Evento.RES_ANYADIR_PASE_A_VENTA_OK) {
			cargarVistaCarrito();
		}
	}
	
	public static Collection<TLineaFactura> getCarrito() { return carrito; } 
	
	private void cargarVistaCarrito() {
		vistaCarrito.removeAll();
		for (TLineaFactura tLineaFactura : carrito) {
			vistaCarrito.add(crearVistaPase(tLineaFactura));
		}
		vistaCarrito.revalidate();
		vistaCarrito.repaint();
	}
	
	private JLabel crearVistaPase(TLineaFactura tLineaFactura) {
		JLabel label = new JLabel();
		LocalDateTime date = tLineaFactura.getFechaPase();
		String fecha = date.getHour() + ":" + date.getMinute() + " - " + date.getDayOfMonth() + "/" + date.getMonthValue() + "/" + date.getYear();
		label.setText(tLineaFactura.getCantidad() + " X " + tLineaFactura.getTituloObra() + " | " + fecha);
		return label;
	}

}
