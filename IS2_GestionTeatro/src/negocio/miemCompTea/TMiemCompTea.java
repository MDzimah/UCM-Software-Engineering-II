package negocio.miemCompTea;

import presentacion.Convertable;

public class TMiemCompTea implements Convertable<TMiemCompTea> {
	
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

	@Override
	public Object getColumnValue(int columnIndex) {
		switch(columnIndex) {
			case 0: return this.idMiemComp;
			case 1: return this.nombre;
			case 2: return this.apellido;
			case 3: return this.edad;
			case 4: return this.DNI;
			case 5: return this.email;
			default: return this.genero.toString();
		}
	}

	@Override
	public void setColumnValue(int col, String value) throws Exception {
		switch(col) {
			case 0: this.idMiemComp = Integer.valueOf(value); break;
			case 1: this.nombre = value; break;
			case 2: this.apellido = value; break;
			case 3: this.edad = Integer.valueOf(value); break;
			case 4: this.DNI = value; break;
			case 5: this.email = value; break;
			default: this.genero = Genero.valueOf(value);
		}
		
	}
}
