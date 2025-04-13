package presentacion.GUIfactura;
import java.awt.event.*;
import java.time.*;
import java.util.*;

import javax.swing.*;

import eventos.Evento;
import misc.Pair;
import misc.PanelUtils;
import negocio.factura.TLineaFactura;
import presentacion.controlador.Controlador;
import presentacion.superClases.VistaDefault;

@SuppressWarnings("serial")
public abstract class VistaAdd_QuitarPdeVenta extends VistaDefault {
	private JLabel lTituloObra;
	private JTextField tTituloObra;
	private JLabel lFecha;
	private JSpinner sFecha;
	private JLabel lCtdad;
	private JTextField tCtdad;
	private JButton ok;
	private JButton cancel;
	
	void initComps() {
		lTituloObra = new JLabel("Nombre:");
		tTituloObra = new JTextField(20);
		lFecha = new JLabel("Fecha (DD/MM/AAAA) Hora (HH:MM):");
		SpinnerDateModel model = new SpinnerDateModel(Calendar.getInstance().getTime(), null, null, Calendar.HOUR_OF_DAY);
        sFecha = new JSpinner(model);
		lCtdad = new JLabel("Cantidad:");
		tCtdad = new JTextField(20);
		ok = new JButton("Aceptar");
		cancel = new JButton("Cancelar");
		
		ArrayList<Pair<JLabel, JComponent>> labeledComponents = new ArrayList<>();
		labeledComponents.add(new Pair<>(lTituloObra, tTituloObra));
		labeledComponents.add(new Pair<>(lFecha, sFecha));
		labeledComponents.add(new Pair<>(lCtdad, tCtdad));
		super.initComps(labeledComponents, ok, cancel);
	}
	
	void okAndCancelListener(Evento evento) 
	{
		ok.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				try {
					//Titulo obra
					String titObra = tTituloObra.getText();
					
					//Fecha
					Date fechaSeleccionada = (Date) sFecha.getValue();
					LocalDateTime fechaPase = fechaSeleccionada.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
					
					//Cantidad
					int ctdad = Integer.valueOf(tCtdad.getText());
					if (ctdad <= 0) throw new ArithmeticException();
					
					TLineaFactura tLf= new TLineaFactura(titObra, fechaPase, ctdad);
					
					Controlador.getInstance().accion(evento.getind(), tLf);
				}
				catch(ArithmeticException ex) {
					PanelUtils.panelCamposIncorrectos(VistaAdd_QuitarPdeVenta.this);
				}
			}
			
		});
		
		cancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) { dispose(); }
		});
	}
	
}
