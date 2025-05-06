package presentacion.GUIPase;

import java.util.ArrayList;
import java.util.Collection;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTextField;

import misc.Messages;
import misc.Pair;
import negocio.pase.TPase;
import presentacion.Evento;
import presentacion.ViewUtils;
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
	    if (evento == Evento.RES_OK) {
	        ArrayList<String[]> colNames = new ArrayList<>();
	        colNames.add(Messages.colNomsPase);

	        ArrayList<TPase> pases = new ArrayList<>();
	        pases.addAll((ArrayList<TPase>) datos);

	        ArrayList<ArrayList<TPase>> data = new ArrayList<>();
	        data.add(pases);

	        TablaDefault<TPase> tabla = new TablaDefault<>("PASES", colNames, data, false);
	        tabla.setVisible(true);

	    } else if (evento == Evento.RES_KO) {
	        ViewUtils.createErrorDialogMessage(Messages.X_MOSTRAR_PASES + ' ' + Messages.MOTIVO.formatted(((Exception) datos).getMessage()));
	    }
	}
}