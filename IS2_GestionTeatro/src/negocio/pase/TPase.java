package negocio.pase;

import java.time.LocalDateTime;

public class TPase {
	private String nombre;
	private LocalDateTime fecha;
	private int precio;	
	
	@Override
	public String toString() { return this.nombre + ' ' + this.fecha.toString() + ' ' + this.precio; }
}
