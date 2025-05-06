package presentacion.GUICompTea;

import java.util.ArrayList;
import java.util.Collection;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSpinner;
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
		JLabel pregunta= new JLabel("id de la compania para buscar");
		
		JSpinner IdField = ViewUtils.integerSpinner(0, 0, Integer.MAX_VALUE, 1);
		
		
		
		ArrayList<Pair<JComponent, JComponent>> campos = new ArrayList<>();
		campos.add(new Pair<>(pregunta, IdField));
		anyadir.addActionListener(e ->{
			
			
			try {
				IdField.commitEdit();
				int id = (int)IdField.getValue();
				SwingUtilities.invokeLater(()->{Controlador.getInstance().accion(Evento.BUSCAR_COMPANIA_TEATRAL, id);});
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
		

		initComps(campos, anyadir, cancelar);//no se si tiene que ser fullscreen...
	
	}
	
	
	@Override
	public void actualizar(Evento evento, Object datos) {
		if (evento == Evento.RES_OK) {
			String[] nomCols = Messages.colNomsCompTea;
			ArrayList<String[]>listaColumnas= new ArrayList<String[]>();
			listaColumnas.add(nomCols);
			ArrayList<TCompTea> compania = new ArrayList<>();
			compania.add((TCompTea) datos);
			ArrayList<ArrayList<TCompTea>> data = new ArrayList<>();
			data.add(compania);

			new TablaDefault("Compañía", listaColumnas, data, false).setVisible(true);
		}
		else if (evento == Evento.RES_KO) {
			//ERRORES VAN EN MESSAGES Y NO SE ENVÍAN STRING A TRAVÉS EN OBJECT
			ViewUtils.createErrorDialogMessage("NO EXISTEN LAS COMPAÑÍAS:.\n" + ((Exception)datos).getMessage());
		}
	}

}