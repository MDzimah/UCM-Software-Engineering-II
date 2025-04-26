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

import misc.Constants;
import misc.Evento;
import misc.JSwingUtils;
import misc.Messages;
import misc.Pair;
import negocio.factura.TFactura;
import negocio.pase.TPase;
import presentacion.IGUI;
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
		
		super.initComps(pairComponents, buscar, cancelar, false);
		
		this.setVisible(true);
		
		buscar.addActionListener(e->{
			TPase tPase = new TPase(Integer.valueOf(IDPaseT.getText()));
			Controlador.getInstance().accion(Evento.BUSCAR_PASE, tPase);
			dispose();
		});
		
		cancelar.addActionListener(e->{dispose(); this.setVisible(false);});
	}
	
	@Override
	public void actualizar(Evento evento, Object datos) {
		if(evento==Evento.RES_OK) {
			Collection<Object> p = new ArrayList<Object>();
			p.add((TFactura)datos);
			String[] nomCols = {"ID","ID COMPANYA", "ID OBRA", "FECHA", "STOCK", "PRECIO"};
			FactoriaAbstractaPresentacion.getInstance().createTabla("BUSCAR PASE", nomCols, p);			
		}
		else if(evento==Evento.RES_KO) {
			FactoriaAbstractaPresentacion.getInstance().createErrorDialogMessage(Messages.X_BUSCAR_FACTURA + ' ' + Messages.MOTIVO.formatted((String)datos));
		}
	}

}
