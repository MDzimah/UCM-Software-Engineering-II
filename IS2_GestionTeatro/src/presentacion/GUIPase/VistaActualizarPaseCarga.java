package presentacion.GUIPase;

import java.awt.Component;
import java.util.ArrayList;
import java.util.Collection;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import exceptions.BBDDReadException;
import exceptions.BBDDWriteException;
import misc.JSwingUtils;
import misc.Messages;
import misc.Pair;
import negocio.factoria.FactoriaAbstractaNegocio;
import negocio.factura.TFactura;
import negocio.pase.TPase;
import presentacion.Evento;
import presentacion.VistaDefault;
import presentacion.controlador.Controlador;

public class VistaActualizarPaseCarga extends VistaDefault {
	
	private JLabel IDPaseL;
	private JTextField IDPaseT;
	private JButton actualizar;
	private JButton cancelar;
	
	public VistaActualizarPaseCarga() {
		this.setTitle("ACTUALIZAR PASE");
		this.IDPaseL = new JLabel("Id Pase:");
		this.IDPaseT = new JTextField(20);
		this.actualizar = new JButton("Aceptar");
		this.cancelar = new JButton("Cancelar");
		
		ArrayList<Pair<JComponent, JComponent>> pairComponents = new ArrayList<>();
		pairComponents.add(new Pair<>(IDPaseL, IDPaseT));
		
		super.initComps(pairComponents, actualizar, cancelar);
		
		this.setVisible(true);
		
		actualizar.addActionListener(e->{
			Controlador.getInstance().accion(Evento.ACTUALIZAR_PASE_CARGA, Integer.valueOf(IDPaseT.getText()));
			dispose();
		});
		
		cancelar.addActionListener(e->{this.setVisible(false); dispose();});
	}
	
	@Override
	public void actualizar(Evento evento, Object datos) {
		if(evento==Evento.RES_KO) {
			JSwingUtils.createErrorDialogMessage(Messages.X_PASE_ACTUALIZADO + ' ' + Messages.MOTIVO.formatted((String)datos));
		}
	}
}
