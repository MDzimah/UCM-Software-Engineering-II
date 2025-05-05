package presentacion.GUICompTea;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
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

public class VistaActualizarCompania0 extends VistaDefault implements IGUI{
	
	
	public VistaActualizarCompania0() {
		initGUI();
		this.setVisible(true);
	}
	
	private void initGUI() {
		
		this.setTitle("Añadir Compañia Teatral");//no se si dan problemas la verdad
		JButton anyadir= new JButton("Añadir");
		JButton cancelar = new JButton("Cancelar");
		JLabel Idlabel= new JLabel("Id Compania Teatral");
		
		JTextField IdField = new JTextField();
		
		ArrayList<Pair<JComponent, JComponent>> campos = new ArrayList<>();
		campos.add(new Pair<>(Idlabel, IdField));
		anyadir.addActionListener(e ->{
			Integer id = Integer.valueOf((IdField.getText())); 
			SwingUtilities.invokeLater(()->Controlador.getInstance().accion(Evento.	ACTUALIZAR0_COMPANIA_TEATRAL, id));
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
			FactoriaAbstractaPresentacion.getInstance().createVista(Evento.ACTUALIZAR1_COMPANIA_TEATRAL);
		}
		else if(evento==Evento.RES_KO) {
			
			ViewUtils.createErrorDialogMessage("NO EXISTEN COMPANIAS CON ID: "+(int) datos);
			
		}
		
	}

}