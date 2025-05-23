package integracion.miemCompTea;

import java.util.Collection;

import exceptions.BBDDReadException;
import exceptions.BBDDWriteException;
import negocio.miemCompTea.TCompT_MiemCompT;

public interface DAOCompT_MiemCompT {
	public int create(TCompT_MiemCompT tCompT_MieCT) throws BBDDReadException, BBDDWriteException;
	public int delete_relacion(TCompT_MiemCompT tCompT_MieCT) throws BBDDReadException, BBDDWriteException;
	public int delete_compania(int idComp) throws BBDDReadException, BBDDWriteException;
	public int delete_miembro(int idMiem) throws BBDDReadException, BBDDWriteException;
	public Collection<TCompT_MiemCompT> read_compania(int idComp) throws BBDDReadException;
	public Collection<TCompT_MiemCompT> read_miembro(int idMiem) throws BBDDReadException;
	public Collection<TCompT_MiemCompT> readAll() throws BBDDReadException;
	public int update(TCompT_MiemCompT tCompT_MieCT) throws BBDDReadException, BBDDWriteException;
	
}
