package integracion.obra;

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
	 * @Override
	 * @param modifica el IdObra a uno nuevo para la BD
	 * @return -1 si hay error, IdNuevo si todo bien
	 * @throws BBDDReadException 
	 * @throws BBDDWriteException 
	 */
	public int create(TObra tObra) throws BBDDReadException, BBDDWriteException {
		JSONObject bdObras = OpsBBDD.read(Messages.BDOb);
		int lastPos = bdObras.getInt("LastKey");
		JSONObject nuevaObra = new JSONObject();
		
		nuevaObra.put(Messages.KEY_idObra, lastPos+1);
		nuevaObra.put(Messages.KEY_titulo, tObra.getTitulo());
		nuevaObra.put(Messages.KEY_autor, tObra.getAutor());
		nuevaObra.put(Messages.KEY_Genero, tObra.getGenero());
		nuevaObra.put(Messages.KEY_sinopsis, tObra.getSinopsis());
		nuevaObra.put(Messages.KEY_ListaPases, tObra.getPases());
		nuevaObra.put(Messages.KEY_Activo, tObra.isActiva());
		tObra.setIdObra(lastPos+1);
		
		bdObras.put(String.valueOf(lastPos+1), nuevaObra);
		bdObras.put("LastKey", lastPos+1);
		
		OpsBBDD.write(bdObras, Messages.BDOb);
		
		return lastPos+1;
	}

	
	/**
	 * @Override elimina los pases asociados con el
	 * @return <0 si hay error, >0 si todo bien
	 * @throws BBDDReadException 
	 * @throws BBDDWriteException 
	 */
	public int delete(int id) throws BBDDReadException, BBDDWriteException {
		JSONObject bdObras = OpsBBDD.read(Messages.BDOb);
		if(bdObras.has(String.valueOf(id))) {
			TObra obra = readJSON(bdObras.getJSONObject(String.valueOf(id)));
			
			DAOPase daoPase = FactoriaAbstractaIntegracion.getInstance().crearDAOPase();
			for(int id1 : obra.getPases()) {
				daoPase.delete(id1);
			}
			
			bdObras.remove(String.valueOf(id));
			OpsBBDD.write(bdObras, Messages.BDOb);
			return 1;
		}
		else
			return -1;
	}

	
	/**
	@Override
	@return Devuelve null si no existe y se lee bien la BD
	 * @throws BBDDReadException 
	*/
	public TObra read(int id) throws BBDDReadException {
		JSONObject bdObras = OpsBBDD.read(Messages.BDOb);
		if(bdObras.has(String.valueOf(id))){
			return readJSON(bdObras.getJSONObject(String.valueOf(id)));
		}
		else
			return null;
	}

	/**
	@Override
	@param tObra debe tener un Id ya existente
	@return <0 si no existe, >0 si todo bien
	 * @throws BBDDReadException 
	 * @throws BBDDWriteException 
	*/
	public int update(TObra tObra) throws BBDDReadException, BBDDWriteException {
		JSONObject bdObras = OpsBBDD.read(Messages.BDOb);
		if(bdObras.has(String.valueOf(tObra.getIdObra()))) {
			
			JSONObject nuevaObra = new JSONObject();
			
			nuevaObra.put(Messages.KEY_idObra, tObra.getIdObra());
			nuevaObra.put(Messages.KEY_titulo, tObra.getTitulo());
			nuevaObra.put(Messages.KEY_autor, tObra.getAutor());
			nuevaObra.put(Messages.KEY_Genero, tObra.getGenero());
			nuevaObra.put(Messages.KEY_sinopsis, tObra.getSinopsis());
			nuevaObra.put(Messages.KEY_ListaPases, tObra.getPases());
			nuevaObra.put(Messages.KEY_Activo, tObra.isActiva());
			tObra.setIdObra(tObra.getIdObra());
			
			bdObras.put(String.valueOf(tObra.getIdObra()), nuevaObra);
			OpsBBDD.write(nuevaObra, Messages.BDOb);
			return 1;
		}
		else
			return -1;
	}

	@Override
	public List<TObra> readActive() throws BBDDReadException {
		JSONObject bdObras = OpsBBDD.read(Messages.BDOb);
		List<TObra> obrasActivas = new LinkedList<TObra>();
		
		for(String key : JSONObject.getNames(bdObras)) {
			if(key!="LastKey") {
				JSONObject val = bdObras.getJSONObject(key);
				if(val.getBoolean(Messages.KEY_Activo))
					obrasActivas.add(readJSON(val));
			}
		}
		return obrasActivas;
	}

	/**@throws BBDDReadException 
	 * @Override
	 *  Busca por prioridad de criterios de izquierda a derecha, con null si no quieres ese criterio
	 *  @param params - Integer Id, String titulo, String autor, String genero, Boolean activo
	 */
	public List<TObra> search(List<String> params) throws BBDDReadException {
		JSONObject bdObras = OpsBBDD.read(Messages.BDOb);
		List<TObra> obras = new LinkedList<TObra>();
		if(Integer.valueOf(params.get(0)) != null) {
			if(bdObras.has(String.valueOf(Integer.valueOf(params.get(0))))) {
				obras.add(readJSON(bdObras.getJSONObject(String.valueOf(Integer.valueOf(params.get(0))))));
			}
			return obras;
		}
		else {
			if(params.get(1)!=null)
				busquedaLineal(bdObras, Messages.KEY_titulo, obras, params.get(1));					
			if(params.get(2)!=null)
				busquedaLineal(bdObras, Messages.KEY_autor, obras, params.get(2));					
			if(params.get(3)!=null)
				busquedaLineal(bdObras, Messages.KEY_Genero, obras, params.get(3));					
			if(Boolean.valueOf(params.get(4)) != null )
				busquedaLineal(bdObras, Messages.KEY_Activo, obras, Boolean.valueOf(params.get(4)));
			return obras;
		}
	}
	
	//Metodos privados
	
	private TObra readJSON(JSONObject obra) {
		JSONArray pases = obra.getJSONArray(Messages.KEY_Activo);
		List<Integer> listaBuena = new LinkedList<Integer>();
		for (int i = 0; i < pases.length(); i++) {
		    listaBuena.add(pases.getInt(i));
		}
		
		return new TObra(obra.getInt(Messages.KEY_idObra), obra.getString(Messages.KEY_titulo), obra.getString(Messages.KEY_autor), obra.getString(Messages.KEY_Genero), obra.getString(Messages.KEY_sinopsis), listaBuena);	
	}
	
	private void busquedaLineal(JSONObject bdObras, String criterio, List<TObra> obras, Object clave ) {
		if(obras.isEmpty())
			for(String key : JSONObject.getNames(bdObras)) {
				if(key!="LastKey") {
					JSONObject val = bdObras.getJSONObject(key);
					if(val.get(criterio) == clave)
						obras.add(readJSON(val));
				}
			}
		else {
			int i =0;
			while(i<obras.size()) {
				if(obras.get(i).genericGetter(criterio)!=clave)
					obras.remove(i);
				else
					++i;
			}
		}
	}
}
