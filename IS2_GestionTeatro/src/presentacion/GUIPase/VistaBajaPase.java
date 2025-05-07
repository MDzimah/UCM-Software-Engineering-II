package presentacion.GUIPase;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.Collection;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;

import misc.Messages;
import misc.Pair;
import negocio.factura.TFactura;
import negocio.pase.TPase;
import presentacion.Evento;
import presentacion.IGUI;
import presentacion.ViewUtils;
import presentacion.VistaDefault;
import presentacion.controlador.Controlador;
import presentacion.factoria.FactoriaAbstractaPresentacion;

public class VistaBajaPase extends VistaDefault {
	
	private JSpinner id;
	private JButton aceptar;
	private JButton cancelar;
	
	public VistaBajaPase() {
		this.setTitle("ELIMINAR PASE");
		this.aceptar = new JButton("Aceptar");
		this.cancelar = new JButton("Cancelar");
		JLabel id1 = new JLabel("Id");
		id = ViewUtils.integerSpinner(0, 0, Integer.MAX_VALUE, 1);
		
		ArrayList<Pair<JComponent, JComponent>> pairComponents = new ArrayList<>();
		pairComponents.add(new Pair<>(id1, id));
		
		super.initComps(pairComponents, aceptar, cancelar);
		
		this.setVisible(true);
		
		aceptar.addActionListener(e->{
			Controlador.getInstance().accion(Evento.BAJA_PASE, Integer.valueOf((int)id.getValue()));
			dispose();
		});
		
		cancelar.addActionListener(e->{this.setVisible(false); dispose();});
	}
	
	@Override
	public void actualizar(Evento evento, Object datos) {
		if(evento==Evento.RES_OK) {
			ViewUtils.createDialogMessage(Messages.EX_PASE_ELIMINADO);
		}
		else if(evento==Evento.RES_KO) {
			String error;
			if (datos instanceof Exception) error = ((Exception)datos).getMessage();
			else error = Messages.EXC_UNKNOWN_PASE;
			ViewUtils.createErrorDialogMessage(Messages.X_PASE_ELIMINADO + ' ' + Messages.MOTIVO.formatted(error));
		}
	}


}
