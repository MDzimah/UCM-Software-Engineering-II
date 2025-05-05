package presentacion.GUICompTea;

import java.util.ArrayList;
import java.util.Collection;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
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

public class VistaMostrarCompania extends VistaDefault implements IGUI{
	
	
	public VistaMostrarCompania() {
		initGUI();
		this.setVisible(true);
	}
	
	private void initGUI() {
		
		this.setTitle("Mostrar Compañia Teatral");//no se si dan problemas la verdad
		JButton anyadir= new JButton("Mostrar");
		JButton cancelar = new JButton("Cancelar");
		ArrayList<Pair<JComponent, JComponent>> campos = new ArrayList<>();
		campos.add(null);//TODO otra vista la verdad
		anyadir.addActionListener(e ->{
			SwingUtilities.invokeLater(()->Controlador.getInstance().accion(Evento.MOSTRAR_COMPANIA_TEATRAL, null));
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
			TablaDefault t= new TablaDefault("CONSULTAR OBRA", Messages.colNomsCompTea, (ArrayList<TCompTea>)datos, false);	
		}
		else if(evento==Evento.RES_KO) {
			ViewUtils.createErrorDialogMessage("NO SE PUDO LISTAR LAS COMPANIAS TEATRALES");
		}
	}

}