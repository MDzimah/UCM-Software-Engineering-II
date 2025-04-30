package presentacion.GUIFactura;

import java.util.Collection;

import javax.swing.*;

import exceptions.BBDDReadException;
import misc.*;
import presentacion.VistaDefault;
import presentacion.controlador.Controlador;


@SuppressWarnings("serial")
public class VistaMostrarFacs extends VistaDefault {
	private JButton mostrar;
	private JButton cancel;
	
	public VistaMostrarFacs() {
		this.setTitle("Mostrar facturas");
		JSwingUtils.setAppIcon(this);
		this.mostrar = new JButton("Mostrar");
		this.cancel = new JButton("Cancelar");
		this.initComps(null, mostrar, cancel);
		
		mostrar.addActionListener(e->{
			SwingUtilities.invokeLater(()->{Controlador.getInstance().accion(Evento.MOSTRAR_FACTURAS, null);});
			dispose();
		});
		
		cancel.addActionListener(e->{dispose();});
		
		this.setVisible(true);
	}
	
	@Override
	public void actualizar(Evento evento, Object datos) {
		if (evento == Evento.RES_OK) {
			@SuppressWarnings("unchecked")
			Collection<Object> castedData = (Collection<Object>)datos;
			String[] nomCols = {"ID","ID CLIENTE", "ID TAQUILLERO", "FECHA", "IMPORTE"};
			
			JSwingUtils.createTabla("MOSTRAR FACTURAS", nomCols, castedData, false, false);
		}
		else if(evento == Evento.RES_KO) {
			String error;
			if (datos instanceof BBDDReadException) error = ((BBDDReadException)datos).getMessage();
			else error = Messages.NO_HAY_DATOS;
			JSwingUtils.createErrorDialogMessage(Messages.X_MOSTRAR_FACTURAS + ' ' + Messages.MOTIVO.formatted(error));
		}
	}
}
