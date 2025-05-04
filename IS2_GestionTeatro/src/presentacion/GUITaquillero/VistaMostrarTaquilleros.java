package presentacion.GUITaquillero;

import javax.swing.JButton;

import presentacion.Evento;
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
		// TODO Auto-generated method stub
		
	}

}
