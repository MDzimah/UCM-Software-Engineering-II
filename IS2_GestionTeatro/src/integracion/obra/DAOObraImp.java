package integracion.obra;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import integracion.factoria.FactoriaAbstractaIntegracion;
import integracion.factura.DAOLineaFactura;
import misc.OpsBBDD;
import negocio.obra.TObra;
import negocio.pase.TPase;

public class DAOObraImp implements DAOObra {

	@Override
	/**
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

	@Override
	public int delete(int id) {
		// TODO Auto-generated method stub
		return 0;
	}

	
	/**
	@Override
	@return Devuelve null si no existe y se lee bien la BD
	*/
	public TObra read(int id) {
		try{
			JSONObject bdObras = OpsBBDD.read("BDObra.json");
			
			
			JSONObject obra = bdObras.getJSONObject(String.valueOf(id));	//•JSONException si no es un JSON o no existe
			
			return readJSON(obra);
		}
		catch(Exception e) {
			return null;
		}
	}

	@Override
	public int update(TObra tObra) {
		// TODO Auto-generated method stub
		return 0;
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

	@Override
	public List<TObra> search(String titulo, String autor, String genero, boolean activo) {
		// TODO Auto-generated method stub
		return null;
	}
	
	private TObra readJSON(JSONObject obra) {
		JSONArray pases = obra.getJSONArray("ListaPases");
		List<Integer> listaBuena = new LinkedList<Integer>();
		for (int i = 0; i < pases.length(); i++) {
		    listaBuena.add(pases.getInt(i));
		}
		
		return new TObra(obra.getInt("IdObra"), obra.getString("Titulo"), obra.getString("Autor"), obra.getString("Genero"), obra.getString("Sinopsis"), listaBuena);	}
}
