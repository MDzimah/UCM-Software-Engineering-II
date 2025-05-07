package presentacion.GUICompTea;

import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import misc.Messages;
import misc.Pair;
import presentacion.Evento;
import presentacion.IGUI;
import presentacion.ViewUtils;
import presentacion.controlador.Controlador;
import presentacion.VistaDefault;
import negocio.compTea.TCompTea;

public class VistaBorrarMiembrodeCompaniaTeatral extends VistaDefault implements IGUI{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	public VistaBorrarMiembrodeCompaniaTeatral () {
		initGUI();
		this.setVisible(true);
	}
	
	private void initGUI() {
		
		this.setTitle("Vista BorrarMiembrodeCompaniaTeatral");//no se si dan problemas la verdad
		JButton anyadir= new JButton("Borrar");
		JButton cancelar = new JButton("Cancelar");
		JLabel costeContratacionLabel= new JLabel("id:");
		JSpinner id= ViewUtils.integerSpinner(0, 0, Integer.MAX_VALUE, 1);
		ArrayList<Pair<JComponent, JComponent>> campos = new ArrayList<>();
		campos.add(new Pair<>(costeContratacionLabel, id));
		anyadir.addActionListener(e ->{
			try {
				id.commitEdit();
				int intid = (int)id.getValue();
				
				SwingUtilities.invokeLater(()->{Controlador.getInstance().accion(Evento.BORRAR_MIEMBRO_COMPANIA_TEATRAL, intid);});
			}
			catch(Exception ex) {
				ViewUtils.createInvalidFieldsPanel();
				id.updateUI();
			}
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
			ViewUtils.createDialogMessage(Messages.COMPANIA_MIEMBRO_BORRADO + '\n' + " Id: "+ (int)datos);

		}
		else if(evento==Evento.RES_KO) {
			ViewUtils.createErrorDialogMessage(Messages.X_BORRAR_COMPANIA_MIEMBRO+ '\n' + Messages.ERROR.formatted(((Exception) datos).getMessage()));
		}
		
	}

}