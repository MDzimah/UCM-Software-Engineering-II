package presentacion.GUIFactura;
import java.util.*;

import javax.swing.*;

import misc.*;
import negocio.factura.*;
import presentacion.Evento;
import presentacion.ViewUtils;
import presentacion.VistaDefault;
import presentacion.controlador.Controlador;

@SuppressWarnings("serial")
public abstract class ModificacionPaseEnVenta extends VistaDefault {
	private JLabel lIdPase;
	private JSpinner sIdPase;
	private JLabel lCtdad;
	private JSpinner sCtdad;
	private JButton ok;
	private JButton cancel;
	
	void initComps() {
		ViewUtils.setAppIcon(this);
		lIdPase = new JLabel("Id pase:");
		SpinnerNumberModel spinnerModel = new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 1);
		sIdPase = new JSpinner(spinnerModel);
		/*
		lFecha = new JLabel("Fecha (DD/MM/AAAA) Hora (HH:MM):");
		SpinnerDateModel model = new SpinnerDateModel(Calendar.getInstance().getTime(), null, null, Calendar.HOUR_OF_DAY);
        sFecha = new JSpinner(model);
        */
		
		lCtdad = new JLabel("Cantidad:");
		spinnerModel = new SpinnerNumberModel(1, 1, 100, 1);
	    sCtdad = new JSpinner(spinnerModel);
		ok = new JButton("Aceptar");
		cancel = new JButton("Cancelar");
		
		ArrayList<Pair<JComponent, JComponent>> labeledComponents = new ArrayList<>();
		labeledComponents.add(new Pair<>(lIdPase, sIdPase));
		labeledComponents.add(new Pair<>(lCtdad, sCtdad));
		super.initComps(labeledComponents, ok, cancel);
	}
	
	void okAndCancelListener(Evento evento) {
		ok.addActionListener(e->{
			try {
				//Id pase
				sIdPase.commitEdit();
				int idPase = (Integer)sIdPase.getValue(); 
				
				//Fecha
				/*
				Date fechaSeleccionada = (Date) sFecha.getValue();
				LocalDateTime fechaPase = fechaSeleccionada.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
				*/
				
				//Cantidad
				sCtdad.commitEdit();
				int ctdad = (Integer)sCtdad.getValue(); 
			
				TLineaFactura tLf = new TLineaFactura(idPase, ctdad);
				SwingUtilities.invokeLater(()->{Controlador.getInstance().accion(evento, tLf);});
				dispose();
			}
			catch(Exception ex) {
				sCtdad.updateUI();
				sIdPase.updateUI();
			}
		});
		
		cancel.addActionListener(e->{dispose();});
	}
}
