package negocio.obra;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import misc.Messages;
import negocio.factura.TLineaFactura;
import negocio.pase.TPase;
import presentacion.Convertable;

public class TObra implements Convertable<TObra>{
	//Atributos
	private int idObra;
	private String titulo;
	private String autor;
	private String genero;
	private String sinopsis;
	
	//Constructores
	public TObra (int idObra, String titulo, String autor, String genero, String sinopsis) {
		this.idObra = idObra;
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
		return idObra;
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
	
	public String getSinopsis() {
		return sinopsis;
	}
	
	public Object genericGetter(String key) {
		switch(key) {
		case Messages.KEY_idObra:
			return Integer.valueOf(idObra);
		case Messages.KEY_autor:
			return autor;
		case Messages.KEY_titulo:
			return titulo;
		case Messages.KEY_generoObra:
			return genero;
		case Messages.KEY_sinopsis:
			return sinopsis;
		}
		return null;
	}
	
	//Setters	
	public void setIdObra(int id) {
		this.idObra=id;
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
	
	//Metodos de Convertable para la tabla
	@Override
	public Object getColumnValue(int columnIndex) {
		switch(columnIndex) {
		case 0:
			return idObra;
		case 1:
			return autor;
		case 2:
			return titulo;
		case 3:
			return genero;
		case 4:
			return sinopsis;
		}
		return null;
	}
	
	@Override
	public void setColumnValue(int col, String value) throws Exception {
		switch(col) {
		case 0:
			idObra=Integer.valueOf(value);
		case 1:
			autor= value; break;
		case 2:
			titulo=value; break;
		case 3:
			genero=value; break;
		case 4:
			sinopsis=value; break;
		}
	}
}
