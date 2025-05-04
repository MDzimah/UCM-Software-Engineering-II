package presentacion.GUICompTea;

import java.util.ArrayList;
import java.util.Collection;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import integracion.factoria.FactoriaAbstractaIntegracion;
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

public class VistaBuscarCompania extends VistaDefault implements IGUI{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	public VistaBuscarCompania() {
		initGUI();
		this.setVisible(true);
	}
	
	private void initGUI() {
		
		this.setTitle("Buscar Compañia Teatral");//no se si dan problemas la verdad
		JButton anyadir= new JButton("Buscar");
		JButton cancelar = new JButton("Cancelar");
		JLabel pregunta= new JLabel("Nombre de la compania para buscar");
		
		JTextField respuesta = new JTextField();
		
		
		ArrayList<Pair<JComponent, JComponent>> campos = new ArrayList<>();
		campos.add(new Pair<>(pregunta, respuesta));
		anyadir.addActionListener(e ->{
			String nombreString = respuesta.getText(); 
			FactoriaAbstractaIntegracion.getInstance().crearDAOCompTea();
			
			SwingUtilities.invokeLater(()->Controlador.getInstance().accion(Evento.BUSCAR_COMPANIA_TEATRAL, nombreString));
			this.dispose();	
		});
		cancelar.addActionListener(e ->{
			this.dispose();
		});
		

		initComps(campos, anyadir, cancelar);//no se si tiene que ser fullscreen...
	
	}
	
	
	@Override
	public void actualizar(Evento evento, Object datos) {
		if(evento==Evento.RES_OK) {
			TablaDefault j= new TablaDefault("COMPAÑÍA", Messages.colNomsCompTea, (Collection<TCompTea>)datos, true, false);
			
			JTable t= j.getTable();
			JButton b =j.getOkButton();
			 b.addActionListener(e->{
				 j.dispose();
			 });
			
		}
		else if(evento==Evento.RES_KO) {
			ViewUtils.createErrorDialogMessage("NO EXISTEN LAS COMPAÑÍAS:.\n" +(String)datos);
		}
		
	}

}