package negocio.miemCompTea;

public class TMiemCompTea {
	
	private int idMiemComp;
	
	private String DNI;
	private String nombre;
	private String apellido;
	private String email;
	private int edad;
	private boolean activo;
	
	public enum Genero {HOMBRE,MUJER};
	private Genero genero;
	
	public TMiemCompTea(String dni, String nom, String apell, String mail, int ed, boolean act, Genero gen) {
		this.DNI = dni;
		this.nombre = nom;
		this.apellido = apell;
		this.email = mail;
		this.edad = ed;
		this.activo = act;
		this.genero = gen;
	}
	
	//GETTERS
	
	public int getIdMiembComp() { return this.idMiemComp; }
	
	public String getDNI() { return this.DNI; }
	
	public String getNombre() { return this.nombre; }
	
	public String getApellido() { return this.apellido; }
	
	public String getEmail() { return this.email; }
	
	public int getEdad() { return this.edad; }
	
	public boolean getActivo() { return this.activo; }
	
	public Genero getGenero() { return this.genero; }
	
	//SETTERS
	
	public void setIdMiembComp(int id) { this.idMiemComp = id; }
	
	public void setDNI(String dni) { this.DNI = dni; }
	
	public void setNombre(String nom) { this.nombre = nom; }
	
	public void setApellido(String apell) { this.apellido = apell; }
	
	public void setEmail(String mail) { this.email = mail; }
	
	public void setEdad(int ed) { this.edad = ed; }
	
	public void setActivo(boolean act) { this.activo = act; }
	
	public void setGenero(Genero gen) { this.genero = gen; }
	
	@Override
	public String toString() { return Integer.toString(this.idMiemComp); }
}
