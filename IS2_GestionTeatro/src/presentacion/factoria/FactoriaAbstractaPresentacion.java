package presentacion.factoria;

import presentacion.Evento;
import presentacion.IGUI;

public abstract class FactoriaAbstractaPresentacion {
	private static FactoriaAbstractaPresentacion instancia = null;
		
	public static FactoriaAbstractaPresentacion getInstance() {
		if (instancia == null) instancia = new FactoriaPresentacion();
		return instancia;
	}
	
	public abstract IGUI createVista(Evento evento);
	
	//Para ventanas que son meramente informativas o que no van a admitir input del usuario
	public abstract void createNonIGUIVistas(Evento evento, Object datos);
}
