package negocio.pase;

import java.time.LocalDateTime;
import java.util.Collection;

public interface SAPase {
	public int create(TPase a);
	public TPase read(int id);
	public int update(TPase a);
	public int delete (int id);
	public Collection<TPase> readAll();
	public TPase readByTitleAndDate(String title, LocalDateTime t); //Llama a los métodos q tenga Obra para el título de la obra y comprueba internamente lo del time
}
