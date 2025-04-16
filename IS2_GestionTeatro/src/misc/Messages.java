package misc;

public class Messages {
	//Ops. realizadas con éxito
	public static final String EX_PASE_ANYADIDO_A_VENTA = "Se ha añadido correctamente el pase al carrito.";
	public static final String EX_VENTA_CERRADA = "Se ha cerrado la venta correctamente.";
	public static final String EX_PASE_QUITADO_DE_VENTA = "Se ha quitado correctamente el pase del carrito.";
	public static final String EX_ESCRITURA_BBDD = "Se ha escrito correctamente en la BBDDD de %s.";
	
	// NOTE: seguramente lo cambie mas adelante
	public static final String EX_OBRA_ANYADIDA_CORRECT = "La obra ha sido registrada correctamente.";
	public static final String EX_OBRA_BORRADA_CORRECT = "La obra ha sido borrada correctamente.";

	
	//Ops. canceladas
	public static final String X_PASE_ANYADIDO_A_VENTA = "No se ha podido añadir el pase \"%s\" al carrito.";
	public static final String X_VENTA_CERRADA = "No se ha podido realizar la venta.";
	public static final String X_PASE_QUITADO_DE_VENTA = "No se ha podido quitar el pase \"%s\" del carrito.";
	public static final String X_BUSCAR_FACTURA = "No se ha encontrado la factura con id \"%s\".";
	public static final String X_MOSTRAR_FACTURAS = "No hay facturas para mostrar.";
	
	// NOTE: seguramente lo cambie mas adelante
	public static final String EX_OBRA_ANYADIDA_ERROR = "Ha habido un error en el registro de la obra.";
	public static final String EX_OBRA_BORRADA_ERROR = "La obra ha sido borrada correctamente.";
	
	//Errores
	public static final String ERROR_CAMPOS_INCORRECTOS = "Campos incorrectos. Por favor, complete todos los campos.";
	public static final String ERROR_LECTURA_BBDD = "Error al leer la BBDD de %s";
	public static final String ERROR_ESCRITURA_BBDD = "Error al escribr en la BBDD de %s";
	
	//Path a file
	public static final String PATH_TO_BBDD = "resources/BBDD/%s";
	
	//Nombres de BBDD
	public static final String BDCliNorm = "ClienteNormal.json";
	public static final String BDCliVIP = "ClienteVIP.json";
	public static final String BDCT = "CompTea.json";
	public static final String BDMCT = "MiemCompTea.json";
	public static final String BDOb = "Obra.json";
	public static final String BDFac = "Factura.json";
	public static final String BDLFac = "LineaFactura.json";
	public static final String BDPase = "Pase.json";
	public static final String BDTaq = "Taquillero.json";
	
	//Claves para acceder BBDD
	public static final String KEY_idCli = "idCliente";
	public static final String KEY_idFac = "idFactura";
	public static final String KEY_idLinFac = "idLineaFactura";
	public static final String KEY_idTaq = "idTaquillero";
	public static final String KEY_idPase = "idPase";
	public static final String KEY_lastId = "lastId";
	
	public static final String KEY_facs = "facturas";
	public static final String KEY_facL = "lineas";
	
	public static final String KEY_fecha = "fecha";
	public static final String KEY_act = "activo";
	public static final String KEY_carrito = "carrito";
	public static final String KEY_importe = "importe";
	public static final String KEY_LF_precio = "precioVenta";
	public static final String KEY_ctdad = "cantidad";
}
