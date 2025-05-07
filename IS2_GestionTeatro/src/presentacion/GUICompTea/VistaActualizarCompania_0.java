package presentacion.GUICompTea;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import misc.Messages;
import misc.Pair;
import presentacion.Evento;
import presentacion.IGUI;
import presentacion.ViewUtils;
import presentacion.TablaDefault;
import presentacion.controlador.Controlador;
import presentacion.factoria.FactoriaAbstractaPresentacion;
import presentacion.VistaDefault;
import negocio.compTea.TCompTea;

public class VistaActualizarCompania_0 extends VistaDefault implements IGUI{
	
	
	public VistaActualizarCompania_0() {
		initGUI();
		this.setVisible(true);
	}
	
	private void initGUI() {
		
		this.setTitle("Añadir Compañia Teatral");//no se si dan problemas la verdad
		JButton anyadir= new JButton("Añadir");
		JButton cancelar = new JButton("Cancelar");
		JLabel Idlabel= new JLabel("Id Compania Teatral");
		
		JSpinner IdField = ViewUtils.integerSpinner(0, 0, Integer.MAX_VALUE, 1);
		
		ArrayList<Pair<JComponent, JComponent>> campos = new ArrayList<>();
		campos.add(new Pair<>(Idlabel, IdField));
		anyadir.addActionListener(e ->{
			
			try {
				IdField.commitEdit();
				int id = (int)IdField.getValue();
				SwingUtilities.invokeLater(()->{Controlador.getInstance().accion(Evento.ACTUALIZAR_COMPANIA_TEATRAL_0, id);});
				dispose();
			}
			catch(Exception ex) {
				ViewUtils.createInvalidFieldsPanel();
				IdField.updateUI();
			}
			this.dispose();	
		});
		cancelar.addActionListener(e ->{
			this.dispose();
		});
		

		initComps(campos, anyadir, cancelar);
	
	}
	
	
	@Override
	public void actualizar(Evento evento, Object datos) {
		if(evento==Evento.RES_OK) {
			FactoriaAbstractaPresentacion.getInstance().createVista(Evento.ACTUALIZAR_COMPANIA_TEATRAL_1);
		}
		else if(evento==Evento.RES_KO) {
			
			ViewUtils.createErrorDialogMessage("No existen compañias con id: "+((Exception) datos).getMessage());
			
		}
		
	}

}