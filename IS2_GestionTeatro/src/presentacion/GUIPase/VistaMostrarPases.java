package presentacion.GUIPase;

import java.util.ArrayList;
import java.util.Collection;

import javax.swing.JButton;
import javax.swing.JPanel;

import misc.Constants;
import misc.Messages;
import negocio.factura.TFactura;
import negocio.pase.TPase;
import presentacion.Evento;
import presentacion.IGUI;
import presentacion.ViewUtils;
import presentacion.TablaDefault;
import presentacion.VistaDefault;
import presentacion.controlador.Controlador;
import presentacion.factoria.FactoriaAbstractaPresentacion;

public class VistaMostrarPases extends VistaDefault {
	private JButton mostrar;
	private JButton cancel;
	
	public VistaMostrarPases() {
		this.setTitle("mostrar pases");
		this.mostrar = new JButton("Mostrar");
		this.cancel = new JButton("Cancelar");
		super.initComps(null, mostrar, cancel);
		this.setVisible(true);
		mostrar.addActionListener(e->{
			Controlador.getInstance().accion(Evento.MOSTRAR_PASES, null);
			dispose();
		});
		
		cancel.addActionListener(e->{
			this.setVisible(false);
			dispose();
		});
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void actualizar(Evento evento, Object datos) {
		if (evento == Evento.RES_OK) {
			new TablaDefault<TPase>("PASES", Messages.colNomsPase, (ArrayList<TPase>)datos, false, false).setVisible(true);
		}
		else if(evento == Evento.RES_KO) {
			ViewUtils.createErrorDialogMessage(Messages.X_PASE_CREADO + ' ' + Messages.MOTIVO.formatted((String)datos));
		}
	}

}
