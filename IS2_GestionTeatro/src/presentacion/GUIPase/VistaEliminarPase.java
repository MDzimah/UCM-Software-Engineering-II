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
import misc.Evento;
import misc.Messages;
import misc.Pair;
import negocio.factura.TFactura;
import negocio.pase.TPase;
import presentacion.IGUI;
import presentacion.VistaDefault;
import presentacion.controlador.Controlador;
import presentacion.factoria.FactoriaAbstractaPresentacion;

public class VistaEliminarPase extends VistaDefault {

	private JLabel IDPaseL;
	private JTextField IDPaseT;
	private JButton buscar;
	private JButton cancelar;
	
	public VistaEliminarPase() {
		this.setTitle("ELIMINAR PASE");
		this.IDPaseL = new JLabel("Id Pase:");
		this.IDPaseT = new JTextField(20);
		this.buscar = new JButton("Aceptar");
		this.cancelar = new JButton("Cancelar");
		
		ArrayList<Pair<JComponent, JComponent>> pairComponents = new ArrayList<>();
		pairComponents.add(new Pair<>(IDPaseL, IDPaseT));
		
		super.initComps(pairComponents, buscar, cancelar, false);
		
		this.setVisible(true);
		
		buscar.addActionListener(e->{
			TPase tPase = new TPase(Integer.valueOf(IDPaseT.getText()));
			Controlador.getInstance().accion(Evento.ELIMINAR_PASE, tPase);
			dispose();
		});
		
		cancelar.addActionListener(e->{dispose(); this.setVisible(false);});
	}
	
	@Override
	public void actualizar(Evento evento, Object datos) {
		if(evento==Evento.RES_OK) {
			FactoriaAbstractaPresentacion.getInstance().createDialogMessage(Messages.EX_PASE_ELIMINADO);
		}
		else if(evento==Evento.RES_KO) {
			FactoriaAbstractaPresentacion.getInstance().createErrorDialogMessage(Messages.X_PASE_ELIMINADO + ' ' + Messages.MOTIVO.formatted((String)datos));
		}
	}


}
