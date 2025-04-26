package presentacion.GUIPase;

import java.util.Collection;

import javax.swing.JButton;
import javax.swing.JPanel;

import misc.Constants;
import misc.Evento;
import misc.JSwingUtils;
import misc.Messages;
import presentacion.IGUI;
import presentacion.VistaDefault;
import presentacion.controlador.Controlador;
import presentacion.factoria.FactoriaAbstractaPresentacion;

public class VistaListarPases extends VistaDefault implements IGUI{
	private JButton mostrar;
	private JButton cancel;
	
	public VistaListarPases() {
		this.setTitle("mostrar pases");
		this.mostrar = new JButton("Mostrar");
		this.cancel = new JButton("Cancelar");
		JPanel responsePanel = JSwingUtils.createResponsePair(this.mostrar, this.cancel);
		responsePanel.setSize(Constants.getScaledScreenDimension(2, 2));
		this.setVisible(true);
		mostrar.addActionListener(e->{
			Controlador.getInstance().accion(Evento.LISTAR_PASES, null);
			dispose();
		});
		
		cancel.addActionListener(e->{
			dispose();
			this.setVisible(false);
		});
	}
	
	@Override
	public void actualizar(Evento evento, Object datos) {
		if (evento == Evento.RES_OK) {
			Collection<Object> pases = (Collection<Object>)datos;
			String[] head = {"ID","ID COMPANYA TEATRAL", "ID OBRA", "FECHA", "STOCK", "PRECIO"};
			FactoriaAbstractaPresentacion.getInstance().createTabla("MOSTRAR PASES", head, pases);
		}
		else if(evento == Evento.RES_KO) {
			FactoriaAbstractaPresentacion.getInstance().createErrorDialogMessage(Messages.X_MOSTRAR_FACTURAS + ' ' + Messages.MOTIVO.formatted((String)datos));
		}
	}

}
