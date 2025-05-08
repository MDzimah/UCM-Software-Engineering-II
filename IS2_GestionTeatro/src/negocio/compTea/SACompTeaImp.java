package negocio.compTea;

import java.util.Collection;

import exceptions.BBDDReadException;
import exceptions.BBDDWriteException;
import exceptions.UnknownCompTeaException;
import exceptions.UnknownMiemCompTeaException;
import integracion.compTea.DAOCompTea;
import integracion.factoria.FactoriaAbstractaIntegracion;
import integracion.miemCompTea.DAOMiemCompTea;
import negocio.miemCompTea.SAMiemCompTea;
import negocio.miemCompTea.TCompT_MiemCompT;
import integracion.miemCompTea.DAOCompT_MiemCompT;
import negocio.miemCompTea.TMiemCompTea;
import negocio.compTea.TCompTea;
import negocio.factoria.FactoriaAbstractaNegocio;

public class SACompTeaImp implements SACompTea {

	@Override
	public int create(TCompTea ct) throws BBDDReadException, BBDDWriteException {
		DAOCompTea daoCT=FactoriaAbstractaIntegracion.getInstance().crearDAOCompTea();
		return daoCT.create(ct);
	}

	@Override
	public TCompTea read(int id) throws BBDDReadException {
		DAOCompTea daoCT=FactoriaAbstractaIntegracion.getInstance().crearDAOCompTea();
		return daoCT.read(id);
	}

	@Override
	public int update(TCompTea ct) throws BBDDWriteException, BBDDReadException {
		DAOCompTea daoCT=FactoriaAbstractaIntegracion.getInstance().crearDAOCompTea();
		return daoCT.update(ct);
	}

	@Override
	public int delete(int id) throws BBDDReadException, BBDDWriteException {
		DAOCompTea daoCT=FactoriaAbstractaIntegracion.getInstance().crearDAOCompTea();
		return daoCT.delete(id);
	}

	@Override
	public Collection<TCompTea> readAll() throws BBDDReadException {
		DAOCompTea daoCT=FactoriaAbstractaIntegracion.getInstance().crearDAOCompTea();
		return daoCT.readAll();
	}
	@Override
	public int removeMember(int idcomp, int idmiem) throws BBDDReadException, BBDDWriteException {
		DAOCompT_MiemCompT daoCT=FactoriaAbstractaIntegracion.getInstance().crearDAOCompTea_MiemCompTea();
		return daoCT.delete_relacion(idcomp,idmiem);
	}
	@Override
	public int addMember(TCompT_MiemCompT cmt) throws BBDDReadException, BBDDWriteException {
		SAMiemCompTea saMiemCompTea= FactoriaAbstractaNegocio.getInstance().crearSAMiemCompTea();
		if(saMiemCompTea.read(cmt.getIdMiembroComp())!=null&&this.read(cmt.getIdCompania())!=null) {
		DAOCompT_MiemCompT daoCT=FactoriaAbstractaIntegracion.getInstance().crearDAOCompTea_MiemCompTea();
		return daoCT.create(cmt);
		}
		else return -1;
	}

}
