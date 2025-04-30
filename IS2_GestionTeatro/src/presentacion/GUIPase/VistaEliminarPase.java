package presentacion.GUIPase;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.Collection;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import misc.Constants;
import misc.JSwingUtils;
import misc.Messages;
import misc.Pair;
import negocio.factura.TFactura;
import negocio.pase.TPase;
import presentacion.Evento;
import presentacion.IGUI;
import presentacion.VistaDefault;
import presentacion.controlador.Controlador;
import presentacion.factoria.FactoriaAbstractaPresentacion;

public class VistaEliminarPase extends VistaDefault {

	private JLabel IDPaseL;
	private JTextField IDPaseT;
	private JButton aceptar;
	private JButton cancelar;
	
	public VistaEliminarPase() {
		this.setTitle("ELIMINAR PASE");
		this.IDPaseL = new JLabel("Id Pase:");
		this.IDPaseT = new JTextField(20);
		this.aceptar = new JButton("Aceptar");
		this.cancelar = new JButton("Cancelar");
		
		ArrayList<Pair<JComponent, JComponent>> pairComponents = new ArrayList<>();
		pairComponents.add(new Pair<>(IDPaseL, IDPaseT));
		
		super.initComps(pairComponents, aceptar, cancelar);
		
		this.setVisible(true);
		
		aceptar.addActionListener(e->{
			Controlador.getInstance().accion(Evento.ELIMINAR_PASE, Integer.valueOf(IDPaseT.getText()));
			dispose();
		});
		
		cancelar.addActionListener(e->{this.setVisible(false); dispose();});
	}
	
	@Override
	public void actualizar(Evento evento, Object datos) {
		if(evento==Evento.RES_OK) {
			JSwingUtils.createDialogMessage(Messages.EX_PASE_ELIMINADO);
		}
		else if(evento==Evento.RES_KO) {
			JSwingUtils.createErrorDialogMessage(Messages.X_PASE_CREADO + ' ' + Messages.MOTIVO.formatted((String)datos));
		}
	}


}
