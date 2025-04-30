package presentacion.GUICompTea;

import java.util.ArrayList;
import java.util.Collection;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTextField;

import misc.Pair;
import presentacion.Evento;
import presentacion.IGUI;
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
			Controlador.getInstance().accion(Evento.MOSTRAR_COMPANIA_TEATRAL, null);//TODO no se si esto es por nombre
			this.dispose();	//Igual cambio algo de aqui porque el problema es que como esta ahora se ejecuta el controller antes de cerrar la ventana
		});
		cancelar.addActionListener(e ->{
			this.dispose();
		});
		

		initComps(campos, anyadir, cancelar, false);//no se si tiene que ser fullscreen...
	
	}
	
	
	@Override
	public void actualizar(Evento evento, Object datos) {
		if(evento==Evento.RES_OK) {
			String[] nomCols = {"ID","TITULO", "AUTOR", "GENERO", "SINOPSIS"};
			FactoriaAbstractaPresentacion.getInstance().createTabla("CONSULTAR OBRA", nomCols, (Collection<Object>)datos, true);			
		}
		else if(evento==Evento.RES_KO) {
			FactoriaAbstractaPresentacion.getInstance().createErrorDialogMessage("NO SE PUDO LISTAR LAS COMPANIAS TEATRALES");
		}
	}

}