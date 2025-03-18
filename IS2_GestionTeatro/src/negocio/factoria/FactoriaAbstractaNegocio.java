package negocio.factoria;

public abstract class FactoriaAbstractaNegocio {
	private static FactoriaAbstractaNegocio instancia = null;
		
	public static FactoriaAbstractaNegocio getInstance() {
		if (instancia == null) instancia = new FactoriaNegocio();
		return instancia;
	}
	
}
