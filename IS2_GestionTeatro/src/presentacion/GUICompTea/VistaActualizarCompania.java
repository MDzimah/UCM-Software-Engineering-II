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
import presentacion.VistaDefault;
import negocio.compTea.TCompTea;

public class VistaActualizarCompania extends VistaDefault implements IGUI{

	@Override
	public void actualizar(Evento evento, Object datos) {
		// TODO Auto-generated method stub
		
	}
	
	
	/*public VistaActualizarCompania() {
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
			this.dispose();	//Igual cambio algo de aqui porque el problema es que como esta ahora se ejecuta el controller antes de cerrar la ventana
		});
		cancelar.addActionListener(e ->{
			this.dispose();
		});
		

		initComps(campos, anyadir, cancelar);//no se si tiene que ser fullscreen...
	
	}
	
	
	@Override
	public void actualizar(Evento evento, Object datos) {
		if(evento==Evento.RES_OK) {
			if(datos instanceof TCompTea) {
			
			TablaDefault j= new TablaDefault("COMPAÑÍA", Messages.colNomsCompTea, (Collection<TCompTea>)datos, true, true);
			JButton b =j.getOkButton();
			 b.addActionListener(e->{
				List<TCompTea> list =j.<TCompTea>getTransfersFromTable();
				TCompTea element1= list.get(0);
				SwingUtilities.invokeLater(()->Controlador.getInstance().accion(Evento.ACTUALIZAR1_COMPANIA_TEATRAL, (TCompTea)element1));
			 });
			}
			 else {
				ViewUtils.createDialogMessage(Messages.COMPANIA_ACTUALIZADA); 
			 }
			}
		else if(evento==Evento.RES_KO) {
			if(datos instanceof Exception) {
			ViewUtils.createErrorDialogMessage( ((Exception) datos).getMessage());
			}
			else {
				ViewUtils.createErrorDialogMessage("NO EXISTEN COMPANIAS CON ID: "+(int) datos);
			}
		}
		
	}*/

}