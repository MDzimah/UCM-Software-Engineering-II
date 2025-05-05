package presentacion.GUITaquillero;

import java.util.ArrayList;

import javax.swing.JButton;

import misc.Messages;
import negocio.taquillero.TTaquillero;
import presentacion.Evento;
import presentacion.TablaDefault;
import presentacion.ViewUtils;
import presentacion.VistaDefault;
import presentacion.controlador.Controlador;

public class VistaMostrarTaquilleros extends VistaDefault {

	private JButton mostrar, cancelar;
	
	public VistaMostrarTaquilleros() {
		initGUI();
		this.setVisible(true);
	}
	
	private void initGUI() {
		this.setTitle("Mostrar taquilleros");
		mostrar = new JButton("Mostrar");
		cancelar = new JButton("Cancelar");
		super.initComps(null, mostrar, cancelar);
		
		//oyentes
		mostrar.addActionListener(e->{
			Controlador.getInstance().accion(Evento.MOSTRAR_TAQUILLEROS, null);
			VistaMostrarTaquilleros.this.dispose();
		});
		
		cancelar.addActionListener(e->{
			VistaMostrarTaquilleros.this.dispose();
		});
	}

	@Override
	public void actualizar(Evento evento, Object datos) {
		if(evento == Evento.RES_OK) {
			ArrayList<TTaquillero> taqs = new ArrayList<>();
			
			for(TTaquillero tTaq : taqs) {
				taqs.add(tTaq);
			}
			
			//creamos la tabla
			TablaDefault<TTaquillero> tabla = new TablaDefault<TTaquillero>("Taquilleros", Messages.colNomsTaquillero, taqs, false);
			tabla.setVisible(true);
			
		} else if (evento == Evento.RES_KO) {
			ViewUtils.createErrorDialogMessage("No se han podido mostrar los taquilleros.\n" + "Error: " +((Exception) datos).getMessage());
		}
		
	}

}
