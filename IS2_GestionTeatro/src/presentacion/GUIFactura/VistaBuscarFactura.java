package presentacion.GUIFactura;

import java.util.ArrayList;

import javax.swing.*;

import exceptions.BBDDReadException;
import misc.Messages;
import misc.Pair;
import negocio.factura.TFactura;
import presentacion.*;
import presentacion.controlador.Controlador;

@SuppressWarnings("serial")
public class VistaBuscarFactura extends VistaDefault {
	private JLabel lIdFac;
	private JSpinner sIdFac;
	private JButton buscar;
	private JButton cancel;
	
	public VistaBuscarFactura() {
		this.setTitle("Buscar factura");
		ViewUtils.setAppIcon(this);
		this.lIdFac = new JLabel("Id factura:");
		this.sIdFac = ViewUtils.integerSpinner(0, 0, Integer.MAX_VALUE, 1);
		this.buscar = new JButton("Buscar");
		this.cancel = new JButton("Cancelar");
		
		ArrayList<Pair<JComponent, JComponent>> labeledComponents = new ArrayList<>();
		labeledComponents.add(new Pair<>(lIdFac, sIdFac));
		super.initComps(labeledComponents, buscar, cancel);
		
		buscar.addActionListener(e->{
			try {
				sIdFac.commitEdit();
				int idFac = (int)sIdFac.getValue();
				SwingUtilities.invokeLater(()->{Controlador.getInstance().accion(Evento.BUSCAR_FACTURA, idFac);});
				dispose();
			}
			catch(Exception ex) {
				ViewUtils.createInvalidFieldsPanel();
				sIdFac.updateUI();
			}
		});
		
		cancel.addActionListener(e->{dispose();});
		
		this.setVisible(true);
		this.setLocationRelativeTo(null);
	}
	
	@Override
	public void actualizar(Evento evento, Object datos) {
		if (evento == Evento.RES_OK) {
			ArrayList<String[]> colNames = new ArrayList<>();
			colNames.add(Messages.colNomsFactura);

			ArrayList<TFactura> singleFactura = new ArrayList<>();
			singleFactura.add((TFactura) datos);
			ArrayList<ArrayList<TFactura>> data = new ArrayList<>();
			data.add(singleFactura);

			new TablaDefault<>("Factura", colNames, data, false);
		}
		else if (evento == Evento.RES_KO) {
			String error;
			if (datos instanceof BBDDReadException) error = ((BBDDReadException)datos).getMessage();
			else error = Messages.ID_NO_ENCONTRADO.formatted((int) datos);
			ViewUtils.createErrorDialogMessage(Messages.X_BUSCAR_FACTURA + ' ' + Messages.MOTIVO.formatted(error));
		}
	}

}
