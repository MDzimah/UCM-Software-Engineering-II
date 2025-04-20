package presentacion;

public interface IGUI {
	public default void actualizar(Evento evento, Object datos) {}
}
