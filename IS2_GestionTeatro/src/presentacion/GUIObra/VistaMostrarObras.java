package presentacion.GUIObra;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.JButton;
import javax.swing.SwingUtilities;

import misc.JSwingUtils;
import misc.Messages;
import negocio.obra.TObra;
import presentacion.Evento;
import presentacion.IGUI;
import presentacion.TablaDefault;
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
			
			ArrayList<TObra> obras = new ArrayList<TObra>();
			for(TObra o : (List<TObra>)datos)
				obras.add(o);
			
            TablaDefault<TObra> tabla = new TablaDefault<>("OBRAS", Messages.colNomsObra, obras, false);
            tabla.setVisible(true);
		}
		else if(evento==Evento.RES_KO) {
			JSwingUtils.createErrorDialogMessage("No se han podido mostrar las obras.\n" + "Error: " +((Exception) datos).getMessage());
		}
	}
}
