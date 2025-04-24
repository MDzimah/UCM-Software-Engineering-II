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
	
	//Constructores
	public TObra (int idObra, String titulo, String autor, String genero, String sinopsis) {
		this.IdObra = idObra;
		this.titulo = titulo;
		this.autor = autor;
		this.genero = genero;
		this.sinopsis = sinopsis;
	}
	public TObra (String titulo, String autor, String genero, String sinopsis) 
	{
		this(-1, titulo, autor, genero, sinopsis);
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
}
