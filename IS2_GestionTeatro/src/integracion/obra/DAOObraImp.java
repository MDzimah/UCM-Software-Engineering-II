package integracion.obra;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import exceptions.BBDDReadException;
import exceptions.BBDDWriteException;
import integracion.factoria.FactoriaAbstractaIntegracion;
import integracion.pase.DAOPase;
import misc.Messages;
import misc.OpsBBDD;
import negocio.obra.TObra;

public class DAOObraImp implements DAOObra {

	
	/**
	 * @param modifica el IdObra a uno nuevo para la BD
	 * @return -1 si hay error, IdNuevo si todo bien
	 * @throws BBDDReadException 
	 * @throws BBDDWriteException 
	 */
	 @Override
	public int create(TObra tObra) throws BBDDReadException, BBDDWriteException {
		
		if (OpsBBDD.isEmpty(Messages.BDOb)) {
			JSONObject bdObra = new JSONObject();
			bdObra.put("LastKey", 0);
			OpsBBDD.write(bdObra, Messages.BDOb);
		}
		
		JSONObject bdObras = OpsBBDD.read(Messages.BDOb);
		int lastPos = bdObras.getInt("LastKey");
		JSONObject nuevaObra = new JSONObject();
		
		nuevaObra.put(Messages.KEY_idObra, lastPos+1);
		nuevaObra.put(Messages.KEY_titulo, tObra.getTitulo());
		nuevaObra.put(Messages.KEY_autor, tObra.getAutor());
		nuevaObra.put(Messages.KEY_generoObra, tObra.getGenero());
		nuevaObra.put(Messages.KEY_sinopsis, tObra.getSinopsis());
		tObra.setIdObra(lastPos+1);
		
		bdObras.put(String.valueOf(lastPos+1), nuevaObra);
		bdObras.put("LastKey", lastPos+1);
		
		OpsBBDD.write(bdObras, Messages.BDOb);
		
		return lastPos+1;
	}

	
	/**
	 * elimina los pases asociados con el
	 * @return <0 si hay error, >0 si todo bien
	 * @throws BBDDReadException 
	 * @throws BBDDWriteException 
	 */
	 @Override
	public int delete(int id) throws BBDDReadException, BBDDWriteException {
		if (!OpsBBDD.isEmpty(Messages.BDOb)) {

			JSONObject bdObras = OpsBBDD.read(Messages.BDOb);
			if(bdObras.has(String.valueOf(id))) {
				TObra obra = readJSON(bdObras.getJSONObject(String.valueOf(id)));
				
				bdObras.remove(String.valueOf(id));
				OpsBBDD.write(bdObras, Messages.BDOb);
				return 1;
			}
			else return -1;
		}
		else
			return -1;
	}

	
	/**
	@return Devuelve null si no existe y se lee bien la BD
	 * @throws BBDDReadException 
	*/
	 @Override
	public TObra read(int id) throws BBDDReadException {
		if (!OpsBBDD.isEmpty(Messages.BDOb)) {

		JSONObject bdObras = OpsBBDD.read(Messages.BDOb);
		if(bdObras.has(String.valueOf(id))){
			return readJSON(bdObras.getJSONObject(String.valueOf(id)));
		}
		else
			return null;
		}
		else return null;
	}

	/**
	@param tObra debe tener un Id ya existente
	@return <0 si no existe, >0 si todo bien
	 * @throws BBDDReadException 
	 * @throws BBDDWriteException 
	*/
	 @Override
	public int update(TObra tObra) throws BBDDReadException, BBDDWriteException {
		if (!OpsBBDD.isEmpty(Messages.BDOb)) {
		JSONObject bdObras = OpsBBDD.read(Messages.BDOb);
		if(bdObras.has(String.valueOf(tObra.getIdObra()))) {
			
			JSONObject nuevaObra = new JSONObject();
			
			nuevaObra.put(Messages.KEY_idObra, tObra.getIdObra());
			nuevaObra.put(Messages.KEY_titulo, tObra.getTitulo());
			nuevaObra.put(Messages.KEY_autor, tObra.getAutor());
			nuevaObra.put(Messages.KEY_generoObra, tObra.getGenero());
			nuevaObra.put(Messages.KEY_sinopsis, tObra.getSinopsis());
			tObra.setIdObra(tObra.getIdObra());
			
			bdObras.put(String.valueOf(tObra.getIdObra()), nuevaObra);
			OpsBBDD.write(bdObras, Messages.BDOb);
			return 1;
		}
		else
			return -1;
		}
		else return -1;
	}
	
	@Override
	public List<TObra> getAll() throws BBDDReadException {
		if (!OpsBBDD.isEmpty(Messages.BDOb)) {
		JSONObject bdObras = OpsBBDD.read(Messages.BDOb);
		List<TObra> IdObras = new LinkedList<TObra>();
		
		Iterator<String> claves = bdObras.keys();
        while (claves.hasNext()) {
        	String clave = claves.next();
            if (!clave.equals("LastKey")) {
                IdObras.add(readJSON(bdObras.getJSONObject(clave)));
            }
        }
		return IdObras;
		}
		else return null;
	}
	
	//Metodos privados
	
	private TObra readJSON(JSONObject obra) {
		
		return new TObra(obra.getInt(Messages.KEY_idObra), obra.getString(Messages.KEY_titulo), obra.getString(Messages.KEY_autor), obra.getString(Messages.KEY_generoObra), obra.getString(Messages.KEY_sinopsis));	
	}
}
