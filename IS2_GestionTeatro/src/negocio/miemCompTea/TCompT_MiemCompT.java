package negocio.miemCompTea;

public class TCompT_MiemCompT {
	
	private int idRelacionMiemComp;
	
	private int idCompTea;
	private int idMiemComp;
	
	public TCompT_MiemCompT(int idComp, int idMiemb) {
		this.idCompTea = idComp;
		this.idMiemComp = idMiemb;
	}
	
	//GETTERS
	
	public int getIdRelacionMiemComp() { return this.idRelacionMiemComp; }
	
	public int getIdCompania() { return this.idCompTea; }
	
	public int getIdMiembroComp() { return this.idMiemComp; }
	
	//SETTERS
	
	public void setIdRelacionMiemComp(int idRela) { this.idRelacionMiemComp = idRela; }
	
	public void setIdCompania(int idComp) { this.idCompTea = idComp; }
	
	public void setIdMiembroComp(int idMiemb) { this.idMiemComp = idMiemb; }
}
