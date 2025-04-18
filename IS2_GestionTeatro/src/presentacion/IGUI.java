package presentacion;

import eventos.Evento;

public interface IGUI {
	public default void  actualizar(Evento evento, Object datos) {}
}
