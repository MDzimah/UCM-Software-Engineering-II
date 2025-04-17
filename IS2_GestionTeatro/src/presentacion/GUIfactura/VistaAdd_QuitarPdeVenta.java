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
	private JSpinner sCtdad;
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
		SpinnerNumberModel spinnerModel = new SpinnerNumberModel(1, 1, 100, 1);
	    sCtdad = new JSpinner(spinnerModel);
		ok = new JButton("Aceptar");
		cancel = new JButton("Cancelar");
		
		ArrayList<Pair<JComponent, JComponent>> labeledComponents = new ArrayList<>();
		labeledComponents.add(new Pair<>(lIdPase, tIdPase));
		labeledComponents.add(new Pair<>(lCtdad, sCtdad));
		super.initComps(labeledComponents, ok, cancel, false);
	}
	
	void okAndCancelListener(Evento evento) {
		ok.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					//Id pase
					int idPase = Integer.valueOf(tIdPase.getText());
					
					//Fecha
					/*
					Date fechaSeleccionada = (Date) sFecha.getValue();
					LocalDateTime fechaPase = fechaSeleccionada.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
					*/
					
					//Cantidad
					
					sCtdad.commitEdit();
					int ctdad = (Integer)sCtdad.getValue(); 
				
					TLineaFactura tLf = new TLineaFactura(idPase, ctdad);
					Controlador.getInstance().accion(evento, tLf);
				}
				catch (Exception ex) {
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
