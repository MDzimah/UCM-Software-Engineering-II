package integracion.compTea;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import exceptions.BBDDReadException;
import exceptions.BBDDWriteException;
import integracion.factoria.FactoriaAbstractaIntegracion;
import integracion.factura.DAOLineaFactura;
import integracion.miemCompTea.DAOMiemCompTea;
import integracion.pase.DAOPase;
import misc.Messages;
import misc.OpsBBDD;
import negocio.compTea.TCompTea;
import negocio.factura.TFactura;
import negocio.factura.TLineaFactura;
import negocio.miemCompTea.TMiemCompTea;
import negocio.pase.TPase;


public class DAOCompTeaImp implements DAOCompTea {

	@Override
	public int create(TCompTea tCompTea) throws BBDDReadException, BBDDWriteException  {
		
			JSONObject bdCompania = OpsBBDD.read(Messages.BDCT);
			int lastPos = bdCompania.getInt(Messages.KEY_lastId);
			JSONObject nuevaCompania = new JSONObject();
			
			nuevaCompania.put(Messages.KEY_idCompTea, lastPos+1);
			nuevaCompania.put(Messages.KEY_CompTea, tCompTea.getNombre());
			nuevaCompania.put(Messages.KEY_direccion, tCompTea.getDireccion());
			nuevaCompania.put(Messages.KEY_coste, tCompTea.getCosteContratacion());
			nuevaCompania.put(Messages.KEY_act, tCompTea.isActivo());
			JSONArray s= new JSONArray();
			DAOPase daoPases=FactoriaAbstractaIntegracion.getInstance().crearDAOPase();
			int i=0;
			for(TPase pas: tCompTea.getPases()) {
				//if(daoPases.readByDNI(mc.getDNI())==null) {
				int id=daoPases.create(pas);
				s.put(i,id);
				//}
			}//Comprobar esto porque no tengo ni idea de como se hace??? 
			
			nuevaCompania.put(Messages.KEY_pases, s);
			
		
			
			bdCompania.put(String.valueOf(lastPos+1), nuevaCompania);
			bdCompania.put(Messages.KEY_lastId, lastPos+1);
			
			OpsBBDD.write(bdCompania, Messages.BDCT);
			
			return lastPos+1;
	}

	@Override
	public int delete(int id) throws BBDDReadException, BBDDWriteException {
		JSONObject bdCompania = OpsBBDD.read(Messages.BDCT);
		JSONObject compania = bdCompania.getJSONObject(Integer.toString(id));
        
		if (compania.has(Integer.toString(id))) {
	        compania.put(Messages.KEY_act, false);
	        OpsBBDD.write(bdCompania, Messages.BDCT);
	        return id;
		}
        return -1;
	}

	@Override
	public TCompTea read(int id) throws BBDDReadException {
    JSONObject bdCompania= OpsBBDD.read(Messages.BDCT);

		TCompTea tCompTea = null;
		
		if (bdCompania.has(Integer.toString(id))) {
			JSONObject compania = bdCompania.getJSONObject(Integer.toString(id));
			Collection<TPase> paseslista = new ArrayList<TPase>();
			DAOPase daoPase = FactoriaAbstractaIntegracion.getInstance().crearDAOPase();
			
			JSONArray pases = compania.getJSONArray(Messages.KEY_pases);
			for (int i = 0; i < pases.length(); i++) {
				int idPase = pases.getInt(i);
				TPase tLineaFactura = daoPase.read(idPase);
				paseslista.add(tLineaFactura);}
			tCompTea = new TCompTea(id, compania.getString(Messages.KEY_CompTea), compania.getString(Messages.KEY_direccion),compania.getBoolean(Messages.KEY_act), compania.getFloat(Messages.KEY_coste), paseslista);
		}
		
		return tCompTea;
	
	}

	@Override
	public Collection<TCompTea> readAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int update(TCompTea tCompTea) {
		// TODO Auto-generated method stub
		return 0;
	}

}
