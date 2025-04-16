package negocio.miemCompTea;

public class TCompT_MiemCompT {
	
	private int idCompania;
	private int idMiembroComp;
	
	public TCompT_MiemCompT(int idComp, int idMiemb) {
		this.idCompania = idComp;
		this.idMiembroComp = idMiemb;
	}
	
	//GETTERS
	
	public int getIdCompania() { return this.idCompania; }
	
	public int getIdMiembroComp() { return this.idMiembroComp; }
	
	//SETTERS
	
	public void setIdCompania(int idComp) { this.idCompania = idComp; }
	
	public void setIdMiembroComp(int idMiemb) { this.idMiembroComp = idMiemb; }
}
