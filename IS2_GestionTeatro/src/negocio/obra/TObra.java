package negocio.obra;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import negocio.factura.TLineaFactura;
import negocio.pase.TPase;

public class TObra {
	//Atributos
	private int IdObra;
	private String titulo;
	private String autor;
	private String genero;
	private String sinopsis;
	private boolean activa;
	private LinkedList<Integer> pases;
	
	//Constructores
	public TObra (int idObra, String titulo, String autor, String genero, String sinopsis) {
		this.IdObra = idObra;
		this.titulo = titulo;
		this.autor = autor;
		this.genero = genero;
		this.sinopsis = sinopsis;
		this.pases = new LinkedList<Integer>();
	}
	public TObra (String titulo, String autor, String genero, String sinopsis) {
		this(-1,titulo, autor, genero, sinopsis);
	}
	
	public TObra (int idObra, String titulo, String autor, String genero, String sinopsis, List<Integer> pases) 
	{
		this(idObra, titulo, autor, genero, sinopsis);
		this.pases = (LinkedList<Integer>) pases;
		activa = !pases.isEmpty();
	}
	
	public TObra (String titulo, String autor, String genero, String sinopsis, List<Integer> pases) 
	{
		this(-1, titulo, autor, genero, sinopsis);
		this.pases = (LinkedList<Integer>) pases;
		activa = !pases.isEmpty();
	}

	//Getters
	public int getIdObra() {
		return IdObra;
	}
	
	public String getAutor() {
		return autor;
	}

	public String getTitulo() {
		return titulo;
	}
	
	public String getGenero() {
		return genero;
	}

	public LinkedList<Integer> getPases() {
		return (LinkedList<Integer>) Collections.unmodifiableCollection(pases);
	}
	
	public boolean isActiva() {
		return activa;
	}
	
	public String getSinopsis() {
		return sinopsis;
	}
	
	public Object genericGetter(String key) {
		switch(key) {
		case "IdObra":
			return Integer.valueOf(IdObra);
		case "Autor":
			return autor;
		case "Titulo":
			return titulo;
		case "Genero":
			return genero;
		case "Sinopsis":
			return sinopsis;
		case "Activo":
			return activa;
		}
		return null;
	}
	
	//Setters	
	public void setIdObra(int id) {
		this.IdObra=id;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}

	public void setSinopsis(String sinopsis) {
		this.sinopsis = sinopsis;
	}
	
	//Modificacion de pases
	public void agregarPase(Integer pase) {
		pases.add(pase);
	}
	
	public boolean eliminarPase(Integer pase) {
		return pases.remove(pase);
	}
}
