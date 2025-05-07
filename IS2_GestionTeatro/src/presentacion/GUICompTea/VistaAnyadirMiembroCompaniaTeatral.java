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
import negocio.miemCompTea.TCompT_MiemCompT;

public class VistaAnyadirMiembroCompaniaTeatral extends VistaDefault implements IGUI{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	public  VistaAnyadirMiembroCompaniaTeatral () {
		initGUI();
		this.setVisible(true);
	}
	
	private void initGUI() {
		
		this.setTitle("Vista BorrarMiembrodeCompaniaTeatral");//no se si dan problemas la verdad
		JButton anyadir= new JButton("Añadir");
		JButton cancelar = new JButton("Cancelar");
		JLabel idCompLabel= new JLabel("id compania :");
		JLabel idMiemLabel= new JLabel("id miembro:");
		JSpinner idComp= ViewUtils.integerSpinner(0, 0, Integer.MAX_VALUE, 1);
		JSpinner idMiem= ViewUtils.integerSpinner(0, 0, Integer.MAX_VALUE, 1);
		ArrayList<Pair<JComponent, JComponent>> campos = new ArrayList<>();
		campos.add(new Pair<>(idCompLabel, idComp));
		campos.add(new Pair<>(idMiemLabel, idMiem));
		anyadir.addActionListener(e ->{
			try {
				idComp.commitEdit();
				idMiem.commitEdit();
				int idCompInt = (int)idComp.getValue();
				int idMiemInt = (int)idMiem.getValue();
				TCompT_MiemCompT Tcmc = new TCompT_MiemCompT(idCompInt,idMiemInt); 
				SwingUtilities.invokeLater(()->{Controlador.getInstance().accion(Evento.ANYADIR_MIEMBRO_COMPANIA_TEATRAL, Tcmc);});
			}
			catch(Exception ex) {
				ViewUtils.createInvalidFieldsPanel();
				idComp.updateUI();
				idMiem.updateUI();
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
			ViewUtils.createDialogMessage(Messages.COMPANIA_MIEMBRO_ANYADIDO + '\n' + " Id: "+ (int)datos);

		}
		else if(evento==Evento.RES_KO) {
			ViewUtils.createErrorDialogMessage(Messages.X_ANYADIR_COMPANIA_MIEMBRO+ '\n' + Messages.ERROR.formatted(((Exception) datos).getMessage()));
		}
		
	}

}