package presentacion.GUIFactura;

import java.util.Collection;

import javax.swing.*;

import misc.*;
import misc.JSwingUtils;
import presentacion.IGUI;
import presentacion.controlador.Controlador;
import presentacion.factoria.FactoriaAbstractaPresentacion;


@SuppressWarnings("serial")
public class VistaMostrarFacs extends JFrame implements IGUI {
	private JButton mostrar;
	private JButton cancel;
	
	public VistaMostrarFacs() {
		super("MOSTRAR FACTURAS");
		JSwingUtils.setAppIcon(this);
		this.mostrar = new JButton("Mostrar");
		this.cancel = new JButton("Cancelar");
		JPanel responsePanel = JSwingUtils.createResponsePair(this.mostrar, this.cancel);
		responsePanel.setSize(Constants.getScaledScreenDimension(2, 2));
		
		mostrar.addActionListener(e->{
			Controlador.getInstance().accion(Evento.MOSTRAR_FACTURAS, null);
			dispose();
		});
		
		cancel.addActionListener(e->{dispose();});
		
		this.setVisible(true);
		this.setLocationRelativeTo(null);
	}
	
	@Override
	public void actualizar(Evento evento, Object datos) {
		if (evento == Evento.RES_OK) {
			@SuppressWarnings("unchecked")
			Collection<Object> castedData = (Collection<Object>)datos;
			String[] nomCols = {"ID","ID CLIENTE", "ID TAQUILLERO", "FECHA", "IMPORTE"};
			FactoriaAbstractaPresentacion.getInstance().createTabla("MOSTRAR FACTURAS", nomCols, castedData);
		}
		else if(evento == Evento.RES_KO) {
			FactoriaAbstractaPresentacion.getInstance().createErrorDialogMessage(Messages.X_MOSTRAR_FACTURAS + ' ' + Messages.MOTIVO.formatted((String)datos));
		}
	}

}
