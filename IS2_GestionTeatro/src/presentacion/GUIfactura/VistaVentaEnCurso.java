package presentacion.GUIfactura;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
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
	
	private Collection<TLineaFactura> carrito;
	
	private JScrollPane vistaCarrito;
	
	private JButton aceptar;
	private JButton cancelar;
	
	private TFactura tFactura;
	
	public VistaVentaEnCurso() {
		super("Nueva venta");
		this.setLayout(new BoxLayout(vistaCarrito, BoxLayout.Y_AXIS));
		
		//Aquí no se pasa al controlador un TFactura, tengo apuntado q hay q pasar un TDatosVenta (un transfer q no se ha creado todavía)
		/*
		TDatosVenta tiene:
		- idCliente
		- idVenta
		- Colleción de TLineaFactura
		*/
		this.tFactura = new TFactura();
		
		anyadirPase = new JButton("Añadir pase");
		anyadirPase.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new VistaAddPaseVenta();
			}
		});
		this.add(anyadirPase);
		
		
		this.add(new JLabel("Mi carrito"));
		
		
		vistaCarrito = new JScrollPane();
		vistaCarrito.setLayout(new BoxLayout(vistaCarrito, BoxLayout.Y_AXIS));
		this.add(vistaCarrito);
		
		
		aceptar = new JButton("Aceptar");
		aceptar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new VistaCerrarVenta(tFactura);
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
	public void actualizar(int evento, Object datos) {
		Evento e = Evento.intAEvento(evento);
		if (e == Evento.RES_ANYADIR_PASE_A_VENTA_OK) {
			cargarVistaCarrito();
		}
	}
	
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
		LocalDateTime date = tLineaFactura.getFecha();
		String fecha = date.getHour() + ":" + date.getMinute() + " - " + date.getDayOfMonth() + "/" + date.getMonthValue() + "/" + date.getYear();
		label.setText(tLineaFactura.getCantidad() + " X " + tLineaFactura.getTituloObra() + " | " + fecha);
		return label;
	}

}
