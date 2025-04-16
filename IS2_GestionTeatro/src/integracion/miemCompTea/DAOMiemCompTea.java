package integracion.miemCompTea;

import java.util.Collection;

import negocio.cliente.TCliente;
import negocio.miemCompTea.TMiemCompTea;

public interface DAOMiemCompTea {
	public int create(TMiemCompTea tMieCT);
	public int delete(int id);
	public TMiemCompTea read(int id);
	public Collection<TMiemCompTea> readAll();
	public int update(TMiemCompTea tMieCT);
	public TMiemCompTea readByDNI(String dni);
}
