package negocio.pase;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import exceptions.BBDDReadException;
import exceptions.BBDDWriteException;
import exceptions.UnknownClienteException;
import exceptions.UnknownCompTeaException;
import exceptions.UnknownObraException;
import exceptions.UnknownPaseException;
import exceptions.UnknownTaquilleroException;
import integracion.factoria.FactoriaAbstractaIntegracion;
import integracion.obra.DAOObra;
import integracion.compTea.*;
import integracion.pase.DAOPase;
import negocio.compTea.SACompTea;
import negocio.compTea.TCompTea;
import negocio.factoria.FactoriaAbstractaNegocio;
import negocio.obra.SAObra;
import negocio.obra.TObra;

public class SAPaseImp implements SAPase {

	@Override
	public int create(TPase tPase) throws UnknownObraException, UnknownCompTeaException, BBDDReadException, BBDDWriteException {
		int id = -1;
		
		/*SAObra saObra = FactoriaAbstractaNegocio.getInstance().crearSAObra();
		TObra lecturaObra = saObra.read(tPase.getIdObra());
		SACompTea saCompTea = FactoriaAbstractaNegocio.getInstance().crearSACompTea();
		TCompTea lecturaCompTea = saCompTea.read(tPase.getIdCompanyaTeatral());
		if (lecturaObra == null) {
			throw new UnknownObraException();
		}
		if (lecturaCompTea == null) {
			throw new UnknownCompTeaException();
		}
		*/
		
		if (tPase.getStock() > 0 && tPase.getPrecio() >= 0) { //sino devolvere -1
			DAOPase daoPas = FactoriaAbstractaIntegracion.getInstance().crearDAOPase();
			id = daoPas.create(tPase);
		}
		
		return id;
	}

	@Override
	public TPase read(int id) throws BBDDReadException {
		DAOPase daoPas = FactoriaAbstractaIntegracion.getInstance().crearDAOPase();
		return daoPas.read(id);
	}

	@Override
	public int update(TPase tPase) throws BBDDReadException, BBDDWriteException, UnknownPaseException {
		if (tPase.getStock() < 0 && tPase.getPrecio() < 0) return -1;
		DAOPase daoPas = FactoriaAbstractaIntegracion.getInstance().crearDAOPase();
		int idPase = daoPas.update(tPase);
		if(idPase < 0) return -1;
		else return idPase;
	}

	@Override
	public int delete(int id) throws BBDDReadException, BBDDWriteException {
		DAOPase daoPas = FactoriaAbstractaIntegracion.getInstance().crearDAOPase();
		int idPase = daoPas.delete(id);
		if(idPase < 0) return -1;
		else return idPase;
	}

	@Override
	public ArrayList<TPase> readAll() throws BBDDReadException, UnknownPaseException {
		DAOPase daoPas = FactoriaAbstractaIntegracion.getInstance().crearDAOPase();
		ArrayList<TPase> lista = daoPas.readAll();
		if(lista==null || lista.isEmpty()) return null;
		else return lista;
	}

	@Override
	public int comprar(int id, int cantidad) throws BBDDReadException, BBDDWriteException {
		DAOPase daoPas = FactoriaAbstractaIntegracion.getInstance().crearDAOPase();
		TPase tPase = daoPas.read(id);
		
		if (tPase != null) {
			int stock = tPase.getStock();
			int cantidadReal;
			if (stock - cantidad < 0) cantidadReal = stock;
			else cantidadReal = cantidad;
			tPase.setStock(cantidadReal);
			daoPas.update(tPase);
			return cantidadReal;
		}
		else return -1;
	}

	@Override
	public int deletePorObra(int idObra) throws BBDDReadException, BBDDWriteException {
		SAObra saObra = FactoriaAbstractaNegocio.getInstance().crearSAObra();
		TObra tObra = saObra.read(idObra);
		if (tObra == null) return -1;
		
		DAOPase daoPas = FactoriaAbstractaIntegracion.getInstance().crearDAOPase();
		daoPas.deletePorObra(idObra);
		return idObra;
	}
	
	@Override
	public ArrayList<TPase> allPasesPorObra(int idObra) throws BBDDReadException {
		SAObra saObra = FactoriaAbstractaNegocio.getInstance().crearSAObra();
		TObra tObra = saObra.read(idObra);
		if (tObra == null) return null;
		
		DAOPase daoPase = FactoriaAbstractaIntegracion.getInstance().crearDAOPase();
		ArrayList<TPase> pases = (ArrayList<TPase>)daoPase.readAll();

		ArrayList<TPase> pasesPorObra = new ArrayList<TPase>();
		
		for (TPase tPase : pases) {
			if (tPase.getIdObra() == idObra) pasesPorObra.add(tPase);
		}
		
		return pasesPorObra;
	}

	@Override
	public int deletePorCompTea(int idCompTea) throws BBDDReadException, BBDDWriteException {
		SACompTea saCompTea = FactoriaAbstractaNegocio.getInstance().crearSACompTea();
		TCompTea tCompTea = saCompTea.read(idCompTea);
		if (tCompTea == null) return -1;
		
		DAOPase daoPas = FactoriaAbstractaIntegracion.getInstance().crearDAOPase();
		daoPas.deletePorCompTea(idCompTea);
		return idCompTea;
	}
}
