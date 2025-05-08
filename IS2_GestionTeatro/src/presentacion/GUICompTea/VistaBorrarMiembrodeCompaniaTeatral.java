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
		
		this.setTitle("Vista Borrar Miembro de Compania Teatral");//no se si dan problemas la verdad
		JButton anyadir= new JButton("Borrar");
		JButton cancelar = new JButton("Cancelar");
		JLabel idcomp= new JLabel("id compania:");
		JLabel idmiem= new JLabel("id miembro:");
		JSpinner idcompSpin= ViewUtils.integerSpinner(0, 0, Integer.MAX_VALUE, 1);
		JSpinner idmiemSpin=ViewUtils.integerSpinner(0, 0, Integer.MAX_VALUE, 1);
		ArrayList<Pair<JComponent, JComponent>> campos = new ArrayList<>();
		campos.add(new Pair<>(idcomp,idcompSpin));
		campos.add(new Pair<>(idmiem,idmiemSpin));
		anyadir.addActionListener(e ->{
			try {
				idcompSpin.commitEdit();
				idmiemSpin.commitEdit();
				int intidcomp = (int)idcompSpin.getValue();
				int intidmiem = (int)idmiemSpin.getValue();
				TCompT_MiemCompT tcompmiem= new TCompT_MiemCompT (intidcomp,intidmiem);
				SwingUtilities.invokeLater(()->{Controlador.getInstance().accion(Evento.BORRAR_MIEMBRO_COMPANIA_TEATRAL, tcompmiem);});
			}
			catch(Exception ex) {
				ViewUtils.createInvalidFieldsPanel();
				idcompSpin.updateUI();
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