package presentacion.GUIfactura;
import java.awt.event.*;
import java.time.*;
import java.util.*;

import javax.swing.*;

import negocio.factura.TLineaFactura;
import presentacion.*;
import presentacion.controlador.Controlador;

@SuppressWarnings("serial")
public abstract class VistaAdd_QuitarPdeVenta extends VistaDefault {
	void initComps(JLabel lTituloObra, JTextField tTituloObra, 
				   JLabel lFecha, JSpinner sFecha,
				   JLabel lCtdad, JTextField tCtdad,
				   JButton ok, JButton cancel) 
	{
		lTituloObra = new JLabel("Nombre:");
		tTituloObra = new JTextField(20);
		lFecha = new JLabel("Fecha (DD/MM/AAAA) Hora (HH:MM):");
		SpinnerDateModel model = new SpinnerDateModel(Calendar.getInstance().getTime(), null, null, Calendar.HOUR_OF_DAY);
        sFecha = new JSpinner(model);
		lCtdad = new JLabel("Cantidad:");
		tCtdad = new JTextField(20);
		ok = new JButton("Aceptar");
		cancel = new JButton("Cancelar");
		
		JPanel infoPanel = super.initComps(lFecha, sFecha, lCtdad, tCtdad, ok, cancel);
		infoPanel.add(PanelUtils.createLabelComponentPair(lCtdad, tCtdad));
	}
	void okAndCancelListener(JTextField tTituloObra, JSpinner sFecha, JTextField tCtdad,
			   			     JButton ok, JButton cancel, Evento evento) 
	{
		ok.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				try {
					//Nombre
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
