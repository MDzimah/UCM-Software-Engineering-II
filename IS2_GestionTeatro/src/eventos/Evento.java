package eventos;

import java.util.HashMap;
import java.util.Map;

public enum Evento {
	

	//Orden alfabético
	//Se hace en rangos para que la inserción de cosas nuevas se pueda realizar preservando orden alfabético
	
	/*----MÉTODOS----*/
	
	//Factura [0 - 100)
	
	ANYADIR_PASE_A_VENTA(0), 
	BUSCAR_FACTURA(10),
	CERRAR_VENTA(20), 
	MOSTRAR_FACTURAS(30), 
	QUITAR_PASE_DE_VENTA(40),
	
	
	//Cliente [100 - 200)
	
	ALTA_CLIENTE(100), 
	BUSCAR_CLIENTE(110), 
	ELIMINAR_CLIENTE(120), 
	MOSTRAR_CLIENTE(130), 
	ACTUALIZAR_CLIENTE(140),
	
	//Taquillero [200 - 300)
	
	
	//Obra [300 - 400)
	
	
	//CompTea [400 - 500)
	
	
	//MiemCompTea [500 - 600)
	
	
	
	/*----RESPUESTAS----*/
	
	//Factura [700 - 800)
	
	RES_ANYADIR_PASE_A_VENTA_OK(700), 
	RES_ANYADIR_PASE_A_VENTA_KO(710),
	RES_BUSCAR_FACTURA_OK(720), 
	RES_BUSCAR_FACTURA_KO(730),
	RES_CERRAR_VENTA_OK(740), 
	RES_CERRAR_VENTA_KO(750),
	RES_MOSTRAR_FACTURAS_OK(760), 
	RES_MOSTRAR_FACTURAS_KO(770),
	RES_QUITAR_PASE_DE_VENTA_OK(780), 
	RES_QUITAR_PASE_DE_VENTA_KO(790),
	
	//Cliente [800 - 900)
	
	RES_ALTA_CLIENTE_OK(800), 
	RES_ALTA_CLIENTE_KO(810);
	
	//Taquillero [900 - 1000)
	
	
	//Obra [1000 - 1200)
	
	
	//CompTea [1200 - 1300)
	
	
	//MiemCompTea [1300 - 1400)
	
	private final int indEvento;
	
	private static final Map<Integer, Evento> map = new HashMap<>();
	
	//Cuando se llama al class load de Evento, meter todos los valores en el mapa
	static { for (Evento e : Evento.values()) map.put(e.indEvento, e); }
	
	Evento(int ind) { this.indEvento = ind; }
	
	public int getind() { return indEvento; }
	
	public static Evento intAEvento(int indEvento) { return map.get(indEvento); }
}
