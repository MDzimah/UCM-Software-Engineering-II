package presentacion.GUIfactura;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;

import eventos.Evento;
import misc.*;
import negocio.factura.*;
import presentacion.controlador.Controlador;
import presentacion.superClases.VistaDefault;

@SuppressWarnings("serial")
public abstract class VistaAdd_QuitarPdeVenta extends VistaDefault {
	private JLabel lIdPase;
	private JTextField tIdPase;
	private JLabel lCtdad;
	private JTextField tCtdad;
	private JButton ok;
	private JButton cancel;
	
	void initComps() {
		lIdPase = new JLabel("Id pase:");
		tIdPase = new JTextField(20);
		/*
		lFecha = new JLabel("Fecha (DD/MM/AAAA) Hora (HH:MM):");
		SpinnerDateModel model = new SpinnerDateModel(Calendar.getInstance().getTime(), null, null, Calendar.HOUR_OF_DAY);
        sFecha = new JSpinner(model);
        */
		lCtdad = new JLabel("Cantidad:");
		tCtdad = new JTextField(20);
		ok = new JButton("Aceptar");
		cancel = new JButton("Cancelar");
		
		ArrayList<Pair<JLabel, JComponent>> labeledComponents = new ArrayList<>();
		labeledComponents.add(new Pair<>(lIdPase, tIdPase));
		labeledComponents.add(new Pair<>(lCtdad, tCtdad));
		super.initComps(labeledComponents, ok, cancel);
	}
	
	void okAndCancelListener(Evento evento) {
		ok.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				try {
					//Id pase
					int idPase = Integer.valueOf(tIdPase.getText());
					
					//Fecha
					/*
					Date fechaSeleccionada = (Date) sFecha.getValue();
					LocalDateTime fechaPase = fechaSeleccionada.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
					*/
					
					//Cantidad
					int ctdad = Integer.valueOf(tCtdad.getText());
					if (ctdad <= 0) throw new ArithmeticException();
					
					TLineaFactura tLf = new TLineaFactura(-1, -1, idPase, ctdad, -1);
					Controlador.getInstance().accion(evento, tLf);
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
