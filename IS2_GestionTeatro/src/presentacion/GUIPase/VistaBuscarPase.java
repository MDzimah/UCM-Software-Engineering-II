package presentacion.GUIPase;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.Collection;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import misc.Messages;
import misc.Pair;
import negocio.factura.TFactura;
import negocio.pase.TPase;
import presentacion.Evento;
import presentacion.IGUI;
import presentacion.ViewUtils;
import presentacion.TablaDefault;
import presentacion.VistaDefault;
import presentacion.controlador.Controlador;
import presentacion.factoria.FactoriaAbstractaPresentacion;

public class VistaBuscarPase extends VistaDefault {
	private JLabel IDPaseL;
	private JTextField IDPaseT;
	private JButton buscar;
	private JButton cancelar;
	
	public VistaBuscarPase() {
		this.setTitle("BUSCAR PASE");
		this.IDPaseL = new JLabel("Id Pase:");
		this.IDPaseT = new JTextField(20);
		this.buscar = new JButton("Aceptar");
		this.cancelar = new JButton("Cancelar");
		
		ArrayList<Pair<JComponent, JComponent>> pairComponents = new ArrayList<>();
		pairComponents.add(new Pair<>(IDPaseL, IDPaseT));
		
		super.initComps(pairComponents, buscar, cancelar);
		
		this.setVisible(true);
		
		buscar.addActionListener(e->{
			Controlador.getInstance().accion(Evento.BUSCAR_PASE, Integer.valueOf(IDPaseT.getText()));
			dispose();
		});
		
		cancelar.addActionListener(e->{this.setVisible(false); dispose();});
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void actualizar(Evento evento, Object datos) {
		if(evento==Evento.RES_OK) {
			ArrayList<TPase> p = new ArrayList<TPase>();
			p.add((TPase)datos);
			//FactoriaAbstractaPresentacion.getInstance().createDialogMessage(Messages.EX_PASE_BUSCADO);
			new TablaDefault<TPase>("PASES", Messages.colNomsPase, p, false).setVisible(true); //se crea una tabla con una sola linea que contiene
																	  		 											   //la info del transfer del pase buscado
		}
		else if(evento==Evento.RES_KO) {
			ViewUtils.createErrorDialogMessage(Messages.X_PASE_BUSCADO + ' ' + Messages.MOTIVO.formatted(((Exception)datos).getMessage()));
		}
	}

}
