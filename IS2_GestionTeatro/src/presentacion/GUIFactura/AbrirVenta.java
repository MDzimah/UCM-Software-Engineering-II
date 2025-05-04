package presentacion.GUIFactura;

import java.util.ArrayList;
import java.util.Collection;

import negocio.factura.TLineaFactura;

public class AbrirVenta {
	private static Collection<TLineaFactura> carrito;
	
	public static void resetCarrito() {
		carrito =  new ArrayList<TLineaFactura>();
	}
	
	public static Collection<TLineaFactura> getCarrito() { return carrito; } 
	
	//YA HECHO EN MAINWINDOW. TIENES Q METER EL CARRITO AHÍ DE ALGUNA FORMA....
	/*
	private JScrollPane vistaCarrito;
	
	private JButton aceptar;
	private JButton cancelar;
	
	public VistaVentaEnCurso() {
		super("Nueva venta");
		JSwingUtils.setAppIcon(this);
		this.setLayout(new BoxLayout(vistaCarrito, BoxLayout.Y_AXIS));
		
		anyadirPase = new JButton("Añadir pase");
		anyadirPase.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				FactoriaAbstractaPresentacion.getInstance().createVista(Evento.ANYADIR_PASE_A_VENTA);
			}
		});
		this.add(anyadirPase);
		
		quitarPase = new JButton("Quitar pase");
		quitarPase.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				FactoriaAbstractaPresentacion.getInstance().createVista(Evento.QUITAR_PASE_DE_VENTA);
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
				FactoriaAbstractaPresentacion.getInstance().createVista(Evento.CERRAR_VENTA);
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
		if (evento == Evento.RES_OK || evento == Evento.RES_QUITAR_PASE_DE_VENTA_OK) {
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
		LocalDateTime date = tLineaFactura.getFechaPase();
		String fecha = date.getHour() + ":" + date.getMinute() + " - " + date.getDayOfMonth() + "/" + date.getMonthValue() + "/" + date.getYear();
		label.setText(tLineaFactura.getCantidad() + " X " + tLineaFactura.getTituloObra() + " | " + fecha);
		return label;
	}
	*/
}
