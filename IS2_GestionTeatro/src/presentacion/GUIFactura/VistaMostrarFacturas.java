package presentacion.GUIFactura;

import java.util.ArrayList;

import javax.swing.*;

import exceptions.BBDDReadException;
import misc.*;
import negocio.factura.TFactura;
import presentacion.Evento;
import presentacion.ViewUtils;
import presentacion.TablaDefault;
import presentacion.VistaDefault;
import presentacion.controlador.Controlador;


@SuppressWarnings("serial")
public class VistaMostrarFacturas extends VistaDefault {
	private JButton mostrar;
	private JButton cancel;
	
	public VistaMostrarFacturas() {
		this.setTitle("Mostrar facturas");
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
			new TablaDefault<TFactura>("FACTURAS", Messages.colNomsFactura, (ArrayList<TFactura>)datos, false).setVisible(true);;
		}
		else if(evento == Evento.RES_KO) {
			String error;
			if (datos instanceof BBDDReadException) error = ((BBDDReadException)datos).getMessage();
			else error = Messages.NO_HAY_DATOS;
			ViewUtils.createErrorDialogMessage(Messages.X_MOSTRAR_FACTURAS + ' ' + Messages.MOTIVO.formatted(error));
		}
	}
}
