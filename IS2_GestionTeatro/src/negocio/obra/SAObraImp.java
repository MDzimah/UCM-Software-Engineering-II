package negocio.obra;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.json.JSONObject;

import exceptions.BBDDReadException;
import exceptions.BBDDWriteException;
import exceptions.UnknownObraException;
import integracion.factoria.FactoriaAbstractaIntegracion;
import integracion.obra.DAOObra;
import integracion.pase.DAOPase;
import misc.Messages;
import negocio.factoria.FactoriaAbstractaNegocio;
import negocio.pase.SAPase;

public class SAObraImp implements SAObra {

	@Override
	public int create(TObra o) throws BBDDReadException, BBDDWriteException {
		DAOObra daoObra = FactoriaAbstractaIntegracion.getInstance().crearDAOObra();
		return daoObra.create(o);
	}

	@Override
	public TObra read(int id) throws BBDDReadException, UnknownObraException {
		DAOObra daoObra = FactoriaAbstractaIntegracion.getInstance().crearDAOObra();
		TObra obra = daoObra.read(id);
		if(obra==null)
			throw new UnknownObraException();
		else
			return obra;
	}

	@Override
	public int update(TObra o) throws BBDDReadException, BBDDWriteException, UnknownObraException {
		DAOObra daoObra = FactoriaAbstractaIntegracion.getInstance().crearDAOObra();
		int salida = daoObra.update(o);
		if(salida<=0)
			throw new UnknownObraException();
		else
			return salida;
	}

	@Override
	public int delete(int id) throws BBDDReadException, BBDDWriteException, UnknownObraException {
		 DAOObra daoObra = FactoriaAbstractaIntegracion.getInstance().crearDAOObra();
		 SAPase saPase = FactoriaAbstractaNegocio.getInstance().crearSAPase();
		 saPase.deletePorObra(id);
		 int salida = daoObra.delete(id);
		if(salida<=0)
			throw new UnknownObraException();
		else
			return salida;			
	}

	@Override
	public Collection<TObra> readAll() throws BBDDReadException, UnknownObraException {
		DAOObra daoObra = FactoriaAbstractaIntegracion.getInstance().crearDAOObra();
		
		List<TObra> lista = daoObra.getAll();
		if(lista==null || lista.isEmpty())
			throw new UnknownObraException();
		else
			return lista;
	}

	/** Busca por prioridad de criterios de izquierda a derecha, con null si no quieres ese criterio
	 *  @param params - String titulo, String autor, String genero
	 */
	public Collection<TObra> search(List<String> params) throws BBDDReadException, UnknownObraException{
		DAOObra daoObra = FactoriaAbstractaIntegracion.getInstance().crearDAOObra();
		
		List<TObra> obras = daoObra.getAll();		

		if(!params.get(0).equals(""))
			busquedaLineal(Messages.KEY_titulo, obras, params.get(0));					
		if(!params.get(1).equals(""))
			busquedaLineal(Messages.KEY_autor, obras, params.get(1));					
		if(!params.get(2).equals(""))
			busquedaLineal(Messages.KEY_generoObra, obras, params.get(2));							
		
		if(obras==null || obras.isEmpty())
			throw new UnknownObraException();
		else
			return obras;
	}

	//Metodo auxiliares
	
	private void busquedaLineal(String criterio, List<TObra> obras, Object clave ) {
		int i =0;
		while(i<obras.size()) {
			if(!obras.get(i).genericGetter(criterio).equals(clave))
				obras.remove(i);
			else
				++i;
		}
	}
}
