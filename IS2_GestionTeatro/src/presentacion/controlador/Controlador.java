package presentacion.controlador;

public abstract class Controlador {
	private static Controlador instancia = null;
	
	public static Controlador getInstance() {
		if (instancia == null) instancia = new ControladorImp();
		return instancia;
	}
	
	public abstract void accion(int evento, Object datos);
}
