package misc;

public class Messages {
	//Ops. realizadas con éxito
	public static final String EX_PASE_ANYADIDO_A_VENTA = "Se ha añadido correctamente el pase al carrito.";
	public static final String EX_VENTA_CERRADA = "Se ha cerrado la venta correctamente.";
	public static final String EX_PASE_QUITADO_DE_VENTA = "Se ha quitado correctamente el pase del carrito.";
	public static final String EX_PASE_CREADO = "Se ha creado el pase correctamente.";
	public static final String EX_PASE_BUSCADO = "Se ha encontrado el pase de forma exitosa.";
	public static final String EX_PASE_ELIMINADO = "Se ha eliminado el pase de forma exitosa.";
	
	public static final String EX_MIEMBRO_CONTRATADO = "El nuevo miembro ha sido contratado exitosamente.";
	public static final String EX_MIEMBRO_ENCONTRADO = "Se ha encontrado el miembro de forma exitosa.";
	public static final String EX_MIEMBRO_ACTUALIZADO = "Se ha actualizado el miembro de forma exitosa.";
	public static final String EX_MIEMBRO_DESPEDIDO = "El miembro ha sido despedido exitosamente.";
	public static final String EX_MIEMBROS_LISTADOS = "Se han listados los miembros de forma exitosa.";
	
	// NOTE: seguramente lo cambie mas adelante
	public static final String EX_OBRA_ANYADIDA_CORRECT = "La obra ha sido registrada correctamente.";
	public static final String EX_OBRA_BORRADA_CORRECT = "La obra ha sido borrada correctamente.";

	
	//Ops. canceladas
	public static final String X_ANYADIR_PASE_A_VENTA = "No se ha podido añadir el pase con id \"%s\" al carrito.";
	public static final String X_VENTA_CERRADA = "No se ha podido realizar la venta.";
	public static final String X_QUITAR_PASE_DE_VENTA = "No se ha podido quitar el pase del carrito.";
	public static final String X_BUSCAR_FACTURA = "No se ha encontrado la factura con dicho id.";
	public static final String X_MOSTRAR_FACTURAS = "No hay facturas para mostrar.";
	public static final String X_MOSTRAR_PASES= "No se han podido mostrar pases.";
	public static final String X_PASE_CREADO = "No se ha podido crear el pase.";
	public static final String X_PASE_BUSCADO = "No se ha podido encontrar el pase solicitado.";
	public static final String X_PASE_ELIMINADO = "No se ha podido eliminar el pase solicitado.";
	
	public static final String X_MIEMBRO_CONTRATADO = "No se ha podido contratar al nuevo miembro exitosamente.";
	public static final String X_MIEMBRO_ENCONTRADO = "No se ha podido encontrar el miembro solicitado.";
	public static final String X_MIEMBRO_ACTUALIZADO = "No se ha podido actualizar el miembro solicitado.";
	public static final String X_MIEMBRO_DESPEDIDO = "No se ha podido despedir al miembro solicitado.";
	public static final String X_MIEMBROS_LISTADOS = "No se ha podido listar a los miembros.";
	
	//Motivos por ops canceladas
	public static final String MOTIVO = "Motivo: \"%s\".";
	public static final String ID_NO_ENCONTRADO = "El id \"%s\" no se ha encontrado.";
	public static final String NO_HAY_DATOS = "No hay datos para mostrar.";
	public static final String STOCK_INSUF = "Stock insuficiente.";
	public static final String NO_EN_CARRITO = "El pase no se encuentra en el carrito.";

	
	// NOTE: seguramente lo cambie mas adelante
	public static final String EX_OBRA_ANYADIDA_ERROR = "Ha habido un error en el registro de la obra.";
	public static final String EX_OBRA_BORRADA_ERROR = "La obra ha sido borrada correctamente.";
	
	//Errores
	public static final String ERROR_CAMPOS_INCORRECTOS = "Campos incorrectos. Por favor, complete todos los campos.";
	public static final String ERROR_LECTURA_BBDD = "Error al leer la BBDD de %s";
	public static final String ERROR_ESCRITURA_BBDD = "Error al escribr en la BBDD de %s";
	public static final String ERROR = "ERROR: %s"; 
	
	//Path a file
	public static final String PATH_TO_BBDD = "resources/BBDD/%s";
	
	//Nombres de BBDD
	public static final String BDCliNorm = "ClienteNormal.json";
	public static final String BDCliVIP = "ClienteVIP.json";
	public static final String BDCT = "CompTea.json";
	public static final String BDMCT = "MiemCompTea.json";
	public static final String BDOb = "Obra.json";
	public static final String BDFac = "Factura.json";
	public static final String BDLinFac = "LineaFactura.json";
	public static final String BDPase = "Pase.json";
	public static final String BDTaq = "Taquillero.json";
	public static final String BDCT_MCT = "Taquillero.json";	
	
	//CLAVES PARA ACCEDER A LA BBDD
	
	//Ids
	public static final String KEY_idCli = "idCliente";
	public static final String KEY_idFac = "idFactura";
	public static final String KEY_idLinFac = "idLineaFactura";
	public static final String KEY_idTaq = "idTaquillero";
	public static final String KEY_idPase = "idPase";
	public static final String KEY_idMiemComp = "idMiemComp";
	public static final String KEY_idCompTea = "idCompTea";
	public static final String KEY_lastId = "lastId";
	public static final String KEY_idObra = "IdObra"; 
	public static final String KEY_DNI = "DNI";
	public static final String KEY_idRelComp_Miem = "idRelacionMiemComp"; 
	
	//Listas en ficheros
	public static final String KEY_facs = "facturas";
	public static final String KEY_LineasFac = "lineas";
	public static final String KEY_MiembCompTea = "miembros de la compania teatral";
	public static final String KEY_CompTea = "compania teatral";
	public static final String KEY_CliNorms = "clientes";
	public static final String KEY_CliVIPs = "clientes";
	public static final String KEY_pases = "pases";
	public static final String KEY_miembros = "miembros";
	public static final String KEY_RelComp_Miem = "relacion miembro-compania";
	
	//Atributos
	public static final String KEY_act = "activo";
	
		//Transacciones financieras
	public static final String KEY_subtotal = "subtotal";
	public static final String KEY_importe = "importe";
	public static final String KEY_precioVenta = "precioVenta";
	public static final String KEY_precioPase = "precioPase";
	public static final String KEY_cuentaBancaria = "cuenta";
	public static final String KEY_costeMensual = "mensualidad";
	public static final String KEY_ctdad = "cantidad";
	public static final String KEY_stock = "stock";
	public static final String KEY_coste = "coste";
	public static final String KEY_puntosAcum = "puntos";
	public static final String KEY_nivelVIP = "nivel";
	
		//Identificación adicional del subs
	public static final String KEY_nombre = "nombre";
	public static final String KEY_apellido = "apellido";
	public static final String KEY_genero = "genero";	
	public static final String KEY_edad = "edad";
	public static final String KEY_fecha = "fecha";
	public static final String KEY_direccion = "direccion";
	public static final String KEY_email = "email";
	public static final String KEY_titulo = "titulo"; 
	public static final String KEY_autor = "autor"; 
	public static final String KEY_Genero = "genero"; 
	public static final String KEY_sinopsis = "sinopsis";
}
