package presentacion.GUIPase;

import java.util.ArrayList;
import java.util.Collection;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTextField;

import misc.JSwingUtils;
import misc.Messages;
import misc.Pair;
import negocio.pase.TPase;
import presentacion.Evento;
import presentacion.TablaDefault;
import presentacion.VistaDefault;
import presentacion.controlador.Controlador;

public class VistaMostrarPasesPorObra extends VistaDefault {
	private JLabel IDObraL;
	private JTextField IDObraT;
	private JButton buscar;
	private JButton cancelar;
	
	public VistaMostrarPasesPorObra() {
		this.setTitle("MOSTRAR PASES POR OBRA");
		this.IDObraL = new JLabel("Id Obra:");
		this.IDObraT = new JTextField(20);
		this.buscar = new JButton("Aceptar");
		this.cancelar = new JButton("Cancelar");
		
		ArrayList<Pair<JComponent, JComponent>> pairComponents = new ArrayList<>();
		pairComponents.add(new Pair<>(IDObraL, IDObraT));
		
		super.initComps(pairComponents, buscar, cancelar);
		
		this.setVisible(true);
		
		buscar.addActionListener(e->{
			Controlador.getInstance().accion(Evento.MOSTRAR_PASES_POR_OBRA, Integer.valueOf(IDObraT.getText()));
			dispose();
		});
		
		cancelar.addActionListener(e->{this.setVisible(false); dispose();});
	}
	
	@Override
	public void actualizar(Evento evento, Object datos) {
		if(evento==Evento.RES_OK) {
			new TablaDefault("PASES", Messages.colNomsPase, (Collection<TPase>)datos, false, false);
		}
		else if(evento==Evento.RES_KO) {
			JSwingUtils.createErrorDialogMessage(Messages.X_PASE_BUSCADO + ' ' + Messages.MOTIVO.formatted((String)datos));
		}
	}

}