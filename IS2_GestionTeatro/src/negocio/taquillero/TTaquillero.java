package negocio.taquillero;

import misc.Genero;

public class TTaquillero {
	private int idTaquillero;
	private String DNI;
	private String nombre;
	private String apellido;
	private boolean activo;
	private int edad;
	private float sueldo;
	private int numVentas;
	private Genero genero;
	
	public TTaquillero() {}
	
	public TTaquillero(int idTaquillero, boolean activo, String DNI, String nombre, String apellido, int numVentas, 
			float sueldo, int edad, Genero genero) {
		this.idTaquillero = idTaquillero;
		this.activo = activo;
		this.DNI = DNI;
		this.nombre = nombre;
		this.apellido = apellido;
		this.numVentas = numVentas;
		this.sueldo = sueldo;
		this.edad = edad;
		this.genero = genero;
	}
	
	//getters
	public int getIdTaquillero() { return idTaquillero; }
	public String getDNI() { return DNI; }
	public String getNombre() { return nombre; }
	public String getApellido() { return apellido; }
	public boolean getActivo() { return activo; }
	public int getEdad() { return edad; }
	public float getSueldo() { return sueldo; }
	public int getNumVentas() { return numVentas; }
	public Genero getGenero() { return genero; }
	
	//setters
	public void setIdTaquillero(int idTaquillero) { this.idTaquillero = idTaquillero; }
	public void setDNI(String DNI) { this.DNI = DNI; }
	public void setNombre(String nombre) { this.nombre = nombre; }
	public void setApellido(String apellido) { this.apellido = apellido; }
	public void setActivo(boolean activo) { this.activo = activo; }
	public void setEdad(int edad) { this.edad = edad; }
	public void setSueldo(float sueldo) { this.sueldo = sueldo; }
	public void setNumVentas(int numVentas) { this.numVentas = numVentas; }
	public void setGenero(Genero genero) { this.genero = genero; }
	
 }




