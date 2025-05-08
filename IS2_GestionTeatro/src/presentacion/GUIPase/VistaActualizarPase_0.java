package presentacion.GUIPase;

import java.awt.Component;
import java.util.ArrayList;
import java.util.Collection;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.JTextField;

import exceptions.BBDDReadException;
import exceptions.BBDDWriteException;
import misc.Messages;
import misc.Pair;
import negocio.factoria.FactoriaAbstractaNegocio;
import negocio.factura.TFactura;
import negocio.pase.TPase;
import presentacion.Evento;
import presentacion.ViewUtils;
import presentacion.VistaDefault;
import presentacion.controlador.Controlador;

public class VistaActualizarPase_0 extends VistaDefault {
	
	private JSpinner id;
	private JButton actualizar;
	private JButton cancelar;
	
	public VistaActualizarPase_0() {
		this.setTitle("ACTUALIZAR PASE");
		this.actualizar = new JButton("Aceptar");
		this.cancelar = new JButton("Cancelar");
		JLabel id1 = new JLabel("Id");
		id = ViewUtils.integerSpinner(0, 0, Integer.MAX_VALUE, 1);
		
		ArrayList<Pair<JComponent, JComponent>> pairComponents = new ArrayList<>();
		pairComponents.add(new Pair<>(id1, id));
		
		super.initComps(pairComponents, actualizar, cancelar);
		
		this.setVisible(true);
		
		actualizar.addActionListener(e->{
			Controlador.getInstance().accion(Evento.ACTUALIZAR_PASE_0, Integer.valueOf((int)id.getValue()));
			dispose();
		});
		
		cancelar.addActionListener(e->{dispose();});
		
		this.setVisible(true);
		this.setLocationRelativeTo(null);
	}
	
	@Override
	public void actualizar(Evento evento, Object datos) {
		if(evento==Evento.RES_KO) {
			ViewUtils.createErrorDialogMessage(Messages.X_PASE_ACTUALIZADO + ' ' + Messages.MOTIVO.formatted(((Exception) datos).getMessage()));
		}
	}
}
