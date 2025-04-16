package integracion.obra;

import java.util.Collection;
import java.util.List;

import negocio.obra.TObra;

public interface DAOObra {
	public int create(TObra tObra);
	public int delete(int id);
	public TObra read(int id);
	public Collection<TObra> readActive();
	public int update(TObra tObra);
	public List<TObra> search(Integer Id, String titulo, String autor, String genero, Boolean activo);
}
