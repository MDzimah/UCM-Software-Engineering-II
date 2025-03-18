package integracion.factoria;

public abstract class FactoriaAbstractaIntegracion {
	private static FactoriaAbstractaIntegracion instancia = null;
		
	public static FactoriaAbstractaIntegracion getInstance() {
		if (instancia == null) instancia = new FactoriaIntegracion();
		return instancia;
	}
	
}
