package presentacion.controlador;

import eventos.Evento;

public abstract class Controlador {
	private static Controlador instancia = null;
	
	public static Controlador getInstance() {
		if (instancia == null) instancia = new ControladorImp();
		return instancia;
	}
	
	public abstract void accion(Evento evento, Object datos);
}
