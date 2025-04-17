package integracion.obra;

import java.util.LinkedList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import integracion.factoria.FactoriaAbstractaIntegracion;
import integracion.pase.DAOPase;
import misc.OpsBBDD;
import negocio.obra.TObra;

public class DAOObraImp implements DAOObra {

	
	/**
	 * @Override
	 * @param modifica el IdObra a uno nuevo para la BD
	 * @return -1 si hay error, IdNuevo si todo bien
	 */
	public int create(TObra tObra) {
		try{
			JSONObject bdObras = OpsBBDD.read("BDObra.json");
			int lastPos = bdObras.getInt("LastKey");
			JSONObject nuevaObra = new JSONObject();
			
			nuevaObra.put("IdObra", lastPos+1);
			nuevaObra.put("Titulo", tObra.getTitulo());
			nuevaObra.put("Autor", tObra.getAutor());
			nuevaObra.put("Genero", tObra.getGenero());
			nuevaObra.put("Sinopsis", tObra.getSinopsis());
			nuevaObra.put("ListPases", tObra.getPases());
			nuevaObra.put("Activo", tObra.isActiva());
			tObra.setIdObra(lastPos+1);
			
			bdObras.put(String.valueOf(lastPos+1), nuevaObra);
			bdObras.put("LastKey", lastPos+1);
			
			OpsBBDD.write(bdObras, "BDObra.json");
			
			return lastPos+1;
		}
		catch(Exception e) {
			return -1;
		}
	}

	
	/**
	 * @Override elimina los pases asociados con el
	 * @return <0 si hay error, >0 si todo bien
	 */
	public int delete(int id) {
		JSONObject bdObras = OpsBBDD.read("BDObra.json");
		if(bdObras.has(String.valueOf(id))) {
			TObra obra = readJSON(bdObras.getJSONObject(String.valueOf(id)));
			
			DAOPase daoPase = FactoriaAbstractaIntegracion.getInstance().crearDAOPase();
			for(int id1 : obra.getPases()) {
				daoPase.delete(id1);
			}
			
			bdObras.remove(String.valueOf(id));
			OpsBBDD.write(bdObras, "BDObra.json");
			return 1;
		}
		else
			return -1;
	}

	
	/**
	@Override
	@return Devuelve null si no existe y se lee bien la BD
	*/
	public TObra read(int id) {
		JSONObject bdObras = OpsBBDD.read("BDObra.json");
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
	*/
	public int update(TObra tObra) {
		JSONObject bdObras = OpsBBDD.read("BDObra.json");
		if(bdObras.has(String.valueOf(tObra.getIdObra()))) {
			
			JSONObject nuevaObra = new JSONObject();
			
			nuevaObra.put("IdObra", tObra.getIdObra());
			nuevaObra.put("Titulo", tObra.getTitulo());
			nuevaObra.put("Autor", tObra.getAutor());
			nuevaObra.put("Genero", tObra.getGenero());
			nuevaObra.put("Sinopsis", tObra.getSinopsis());
			nuevaObra.put("ListPases", tObra.getPases());
			nuevaObra.put("Activo", tObra.isActiva());
			tObra.setIdObra(tObra.getIdObra());
			
			bdObras.put(String.valueOf(tObra.getIdObra()), nuevaObra);
			return 1;
		}
		else
			return -1;
	}

	@Override
	public List<TObra> readActive() {
		JSONObject bdObras = OpsBBDD.read("BDObra.json");
		List<TObra> obrasActivas = new LinkedList<TObra>();
		
		for(String key : JSONObject.getNames(bdObras)) {
			if(key!="LastKey") {
				JSONObject val = bdObras.getJSONObject(key);
				if(val.getBoolean("Activo"))
					obrasActivas.add(readJSON(val));
			}
		}
		return obrasActivas;
	}

	/**@Override
	 *  Busca por prioridad de criterios de izquierda a derecha, con null si no quieres ese criterio
	 */
	public List<TObra> search(Integer Id, String titulo, String autor, String genero, Boolean activo) {
		JSONObject bdObras = OpsBBDD.read("BDObra.json");
		List<TObra> obras = new LinkedList<TObra>();
		if(Id != null) {
			if(bdObras.has(String.valueOf(Id))) {
				obras.add(readJSON(bdObras.getJSONObject(String.valueOf(Id))));
			}
			return obras;
		}
		else {
			if(titulo!=null)
				busquedaLineal(bdObras, "Titulo", obras, titulo);					
			if(autor!=null)
				busquedaLineal(bdObras, "Autor", obras, autor);					
			if(genero!=null)
				busquedaLineal(bdObras, "Genero", obras, genero);					
			if(activo!=null)
				busquedaLineal(bdObras, "Activo", obras, activo);
			return obras;
		}
	}
	
	private TObra readJSON(JSONObject obra) {
		JSONArray pases = obra.getJSONArray("ListaPases");
		List<Integer> listaBuena = new LinkedList<Integer>();
		for (int i = 0; i < pases.length(); i++) {
		    listaBuena.add(pases.getInt(i));
		}
		
		return new TObra(obra.getInt("IdObra"), obra.getString("Titulo"), obra.getString("Autor"), obra.getString("Genero"), obra.getString("Sinopsis"), listaBuena);	
	}
	
	//Metodos privados
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
