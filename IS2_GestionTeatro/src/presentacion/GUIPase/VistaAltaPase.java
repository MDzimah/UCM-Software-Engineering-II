package presentacion.GUIPase;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;

import org.jdatepicker.impl.*;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import misc.Messages;
import misc.Pair;
import negocio.pase.TPase;
import presentacion.Evento;
import presentacion.IGUI;
import presentacion.ViewUtils;
import presentacion.VistaDefault;
import presentacion.controlador.Controlador;
import presentacion.factoria.FactoriaAbstractaPresentacion;

public class VistaAltaPase extends VistaDefault {
	
	private JButton ok;
	private JButton cancelar;
	private JLabel idCompTeaLabel;
	private JSpinner idCompTeaText;
	private JLabel idObraLabel;
	private JSpinner idObraText;
	private JLabel fecha;
	private JDatePickerImpl datePicker;
	private JLabel cantidadStockLabel;
	private JTextField cantidadStockText;
	private JLabel precioLabel;
	private JTextField precioText;
	
	public VistaAltaPase() {
		this.setTitle("CREAR PASE");
		ok = new JButton("Aceptar");
		cancelar = new JButton("cancelar:");
		
		fecha = new JLabel("Fecha:");
		Properties p = new Properties();
        p.put("text.today", "Hoy");
        p.put("text.month", "Mes");
        p.put("text.year", "Año");
		UtilDateModel model = new UtilDateModel();
        JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
        datePicker = new JDatePickerImpl(datePanel, new DateComponentFormatter());
        
		idCompTeaLabel = new JLabel("id de la companya teatral:");
		idCompTeaText = ViewUtils.integerSpinner(0, 0, Integer.MAX_VALUE, 1);
		idObraLabel = new JLabel("id de la obra:");
		idObraText = ViewUtils.integerSpinner(0, 0, Integer.MAX_VALUE, 1);
		cantidadStockLabel = new JLabel("stock:");
		cantidadStockText = new JTextField(20);
		precioLabel = new JLabel("precio de compra:");
		precioText = new JTextField(20);
		ArrayList<Pair<JComponent, JComponent>> componentesEtiquetados = new ArrayList<>();
		componentesEtiquetados.add(new Pair<>(idCompTeaLabel,idCompTeaText));
		componentesEtiquetados.add(new Pair<>(idObraLabel,idObraText));
		componentesEtiquetados.add(new Pair<>(fecha,datePicker));
		componentesEtiquetados.add(new Pair<>(cantidadStockLabel,cantidadStockText));
		componentesEtiquetados.add(new Pair<>(precioLabel,precioText));
		super.initComps(componentesEtiquetados, ok, cancelar);
		this.setVisible(true);
		ok.addActionListener(e -> {
			int idCompTea, idObra, stock, precio;
			LocalDateTime localDateTime;
			try {
				idCompTea = Integer.valueOf((int)idCompTeaText.getValue());
				idObra = Integer.valueOf((int)idObraText.getValue());
				stock = Integer.valueOf(cantidadStockText.getText());
				precio = Integer.valueOf(precioText.getText());
				//LocalDateTime selectedDate = (LocalDateTime) datePicker.getModel().getValue();
				Date selectedDate = (Date) datePicker.getModel().getValue();
				Instant instant = selectedDate.toInstant();
				ZoneId zoneId = ZoneId.systemDefault(); 
				localDateTime = instant.atZone(zoneId).toLocalDateTime();
				if (stock <= 0 || precio < 0) ViewUtils.createErrorDialogMessage(Messages.EXC_CAMPOS_INCORRECTOS);
				else {
					TPase tPase = new TPase(-1, idCompTea, idObra, true, localDateTime, stock, precio);
					Controlador.getInstance().accion(Evento.ALTA_PASE, tPase);
				}
			} catch (Exception ex) {
				ViewUtils.createErrorDialogMessage(Messages.EXC_CAMPOS_INCORRECTOS);
			}
			this.dispose();
		});
		cancelar.addActionListener(e ->{
			this.dispose();
		});
		
		this.setVisible(true);
		this.setLocationRelativeTo(null);
	}
	
	@Override
	public void actualizar(Evento evento, Object datos) {
		if (evento == Evento.RES_OK) {
			ViewUtils.createDialogMessage(Messages.EX_PASE_CREADO + "\n" + "id: " + (int)datos);
		}
		else if(evento == Evento.RES_KO) {
			ViewUtils.createErrorDialogMessage(Messages.X_PASE_CREADO + ' ' + Messages.MOTIVO.formatted(((Exception) datos).getMessage()));
		}
	}

}
