package integracion.miemCompTea;

import java.util.Collection;

import exceptions.BBDDReadException;
import exceptions.BBDDWriteException;
import negocio.miemCompTea.TCompT_MiemCompT;

public interface DAOCompT_MiemCompT {
	public int create(TCompT_MiemCompT tCompT_MieCT) throws BBDDReadException, BBDDWriteException;
	public int delete(int id_rel) throws BBDDReadException, BBDDWriteException;
	public int delete_compania(int id_comp) throws BBDDReadException, BBDDWriteException;
	public int delete_miembro(int id_miem) throws BBDDReadException, BBDDWriteException;
	public Collection<TCompT_MiemCompT> read_compania(int id_comp) throws BBDDReadException;
	public Collection<TCompT_MiemCompT> read_miembro(int id_miem) throws BBDDReadException;
	public Collection<TCompT_MiemCompT> readAll() throws BBDDReadException;
	public int update(TCompT_MiemCompT tCompT_MieCT) throws BBDDReadException, BBDDWriteException;
	
}
