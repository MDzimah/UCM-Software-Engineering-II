package presentacion.GUIObra;

import java.util.Collection;

import javax.swing.JButton;
import javax.swing.SwingUtilities;

import misc.JSwingUtils;
import negocio.obra.TObra;
import presentacion.Evento;
import presentacion.IGUI;
import presentacion.VistaDefault;
import presentacion.controlador.Controlador;
import presentacion.factoria.FactoriaAbstractaPresentacion;

public class VistaMostrarObras extends VistaDefault implements IGUI{
	private JButton mostrar, cancelar;

	public VistaMostrarObras() {
		
		this.setTitle("Mostrar obras");
		mostrar = new JButton("Mostrar");
		cancelar = new JButton("Cancelar");
				
		super.initComps(null, mostrar, cancelar);
		mostrar.addActionListener(e ->{
			SwingUtilities.invokeLater(()->{Controlador.getInstance().accion(Evento.MOSTRAR_OBRAS, null);});
			VistaMostrarObras.this.dispose();
		});
		
		cancelar.addActionListener(e ->{
			VistaMostrarObras.this.dispose();
		});	

		this.setVisible(true);
	}
	
	@Override
	public void actualizar(Evento evento, Object datos) {
		if(evento==Evento.RES_OK) {
			String[] nomCols = {"ID","TITULO", "AUTOR", "GENERO", "SINOPSIS"};
			JSwingUtils.createTabla("OBRAS", nomCols, (Collection<Object>)datos, true, false);
		}
		else if(evento==Evento.RES_KO) {
			JSwingUtils.createErrorDialogMessage("No se han podido mostrar las obras.\n" + (String)datos);
		}
	}
}
