package misc;

public class Messages {
	//Ops. realizadas con éxito
	private static final String SHP = "Se ha %s correctamente.";
		//Factura
	public static final String EX_VENTA_CERRADA = Messages.SHP.formatted("cerrado la venta con id \"%s\"");

		//Pase
	public static final String EX_PASE_CREADO = Messages.SHP.formatted("creado el pase");
	public static final String EX_PASE_BUSCADO = Messages.SHP.formatted("encontrado el pase");
	public static final String EX_PASE_ELIMINADO = Messages.SHP.formatted("eliminado el pase");
	public static final String EX_PASE_ACTUALIZADO = Messages.SHP.formatted("actualizado el pase");

		//Miembros
	public static final String EX_MIEMBRO_CONTRATADO = Messages.SHP.formatted("contratado al nuevo miembro");
	public static final String EX_MIEMBRO_ENCONTRADO = Messages.SHP.formatted("encontrado el miembro");
	public static final String EX_MIEMBRO_ACTUALIZADO = Messages.SHP.formatted("actualizado el miembro");
	public static final String EX_MIEMBRO_DESPEDIDO = Messages.SHP.formatted("despedido al miembro");
	public static final String EX_MIEMBROS_LISTADOS = Messages.SHP.formatted("listado los miembros");
       //Compania Teatral
	public static final String COMPANIA_ACTUALIZADA = Messages.SHP.formatted("compania actualizada");
	public static final String COMPANIA_ENCONTRADA = Messages.SHP.formatted("compania encontrada");
	public static final String COMPANIA_CREADA = Messages.SHP.formatted("compania creada");
	public static final String COMPANIA_BUSCADA = Messages.SHP.formatted("compania buscada");
	public static final String COMPANIA_MOSTRADA = Messages.SHP.formatted("compania mostrada");
		
	//Obra
	public static final String EX_OBRA_ANYADIDA_CORRECT = Messages.SHP.formatted("registrado la obra ");
	public static final String EX_OBRA_BORRADA_CORRECT = Messages.SHP.formatted("borrado la obra ");
	public static final String EX_OBRA_ACTUALIZADA_CORRECT = Messages.SHP.formatted("actualizado la obra ");
	
	
	//Ops. fracasadas
	private static final String NSHP = "No se ha podido %s";
		//Factura
	public static final String X_VENTA_CERRADA = Messages.NSHP.formatted("realizar la venta.");
	public static final String X_BUSCAR_FACTURA = Messages.NSHP.formatted("encotrar la factura.");
	public static final String X_MOSTRAR_FACTURAS = Messages.NSHP.formatted("mostrar las facturas.");
	//CompTea
	public static final String X_MOSTRAR_COMPANIAS = Messages.NSHP.formatted("mostrar companias.");
	public static final String X_CREAR_COMPANIA= Messages.NSHP.formatted("crear compania.");
	public static final String X_ELIMINAR_COMPANIA = Messages.NSHP.formatted("dar de baja compania.");
	public static final String X_BUSCAR_COMPANIA= Messages.NSHP.formatted("buscar compania.");
	public static final String X_ACTUALIZAR_COMPANIA = Messages.NSHP.formatted("actualizar compania.");
	//Pase
	public static final String X_MOSTRAR_PASES = Messages.NSHP.formatted("mostrar los pases.");
	public static final String X_PASE_CREADO = Messages.NSHP.formatted("crear el pase.");
	public static final String X_PASE_BUSCADO = Messages.NSHP.formatted("encontrar el pase solicitado.");
	public static final String X_PASE_ELIMINADO = Messages.NSHP.formatted("eliminar el pase solicitado.");
	public static final String X_PASE_ACTUALIZADO = Messages.NSHP.formatted("actualizar el pase solicitado.");

	//Miembros de la comp. tea.
	public static final String X_MIEMBRO_CONTRATADO = Messages.NSHP.formatted("contratar al nuevo miembro.");
	public static final String X_MIEMBRO_ENCONTRADO = Messages.NSHP.formatted("encontrar el miembro solicitado.");
	public static final String X_MIEMBRO_ACTUALIZADO = Messages.NSHP.formatted("actualizar el miembro solicitado.");
	public static final String X_MIEMBRO_DESPEDIDO = Messages.NSHP.formatted("despedir al miembro solicitado.");
	public static final String X_MIEMBROS_LISTADOS = Messages.NSHP.formatted("mostrar a los miembros.");
	//Obras
	public static final String EX_OBRA_ANYADIDA_ERROR = Messages.NSHP.formatted("registrar la obra.");
	public static final String EX_OBRA_BORRADA_ERROR = Messages.NSHP.formatted("borrar la obra.");
	public static final String EX_OBRA_ACTUALIZADA_ERROR = Messages.NSHP.formatted("actualizar la obra.");
	public static final String EX_OBRA_BUSCAR_ERROR = Messages.NSHP.formatted("encontrar obras.");
	public static final String EX_OBRA_MOSTRAR_ERROR = Messages.NSHP.formatted("mostrar las obras.");
	public static final String EX_OBRA_CONSULTAR_ERROR = Messages.NSHP.formatted("acceder a la obra.");

	
		//Motivos por ops fracasadas
	public static final String MOTIVO = "Motivo: \"%s\".";
	public static final String ID_NO_ENCONTRADO = "El id \"%s\" no se ha encontrado";
	public static final String NO_HAY_DATOS = "No hay datos para mostrar";
	public static final String NO_HAY_FACS_CLI = "No hay facturas para el id \"%s\" de cliente";
	public static final String NO_EN_CARRITO = "El pase no se encuentra en el carrito";	
	public static final String CARRITO_VACIO = "Carrito vacío";
	public static final String ERROR_EDICION_TABLA = "Error al editar el valor en la tabla. Verifica el formato del dato ingresado.";
    public static final String ERROR_DATOS_INVALIDOS = "No se han proporcionado datos válidos para mostrar en la tabla.";
    public static final String ERROR_MISMATCH_COLUMNAS_DATOS = "El número de definiciones de columnas no coincide con el número de conjuntos de datos.";
    public static final String ERROR_MISMATCH_EDITABLES_DATOS = "El número de definiciones de columnas editables no coincide con el número de conjuntos de datos.";
    public static final String ERROR_SOLO_UNA_FILA_PARA_EDICION = "Sólo se permite la edición cuando hay una única fila en la tabla.";
    public static final String ERROR_ACCION_NO_PERMITIDA = "Esta acción no está permitida en el modo actual.";


	//Columnas de las tablas
	public static final String[] colNomsFactura = {"ID","ID CLIENTE", "ID TAQUILLERO", "FECHA", "IMPORTE", "SUBTOTAL"};
	public static final String[] colNomsClienteNormal = {"TIPO","ID", "NOMBRE", "APELLIDO", "DNI", "CUENTA BANCARIA", "PUNTOS ACUMULADOS"};
	public static final String[] colNomsClienteVIP = {"TIPO","ID", "NOMBRE", "APELLIDO", "DNI", "CUENTA BANCARIA", "NIVEL VIP", "CUOTA MENSUAL"};
	public static final String[] colNomsTaquillero = {"ID", "NOMBRE", "APELLIDO", "DNI", "NUM VENTAS", "SUELDO", "EDAD", "GENERO"};
	public static final String[] colNomsCompTea = {"ID","NOMBRE","DIRECCION","COSTE CONTRATACION"};
	public static final String[] colNomsMiemCompTea = {"ID","NOMBRE", "APELLIDO", "EDAD", "DNI", "EMAIL", "GENERO"};
	public static final String[] colNomsObra = {"ID","TITULO", "AUTOR", "GENERO", "SINOPSIS"};
	public static final String[] colNomsPase = {"ID","ID COMPANYA", "ID OBRA", "FECHA", "STOCK", "PRECIO"};
	
	//Excepciones 
	public static final String ERROR = "ERROR: %s"; 
	public static final String EXC_LECTURA_BBDD = "Error al leer la BBDD de %s";
	public static final String EXC_ESCRITURA_BBDD = "Error al escribr en la BBDD de %s";
	public static final String EXC_CAMPOS_INCORRECTOS = "Campos incorrectos";
	public static final String EXC_UNKNOWN_TAQ = "Taquillero desconocido";
	public static final String EXC_UNKNOWN_OBRA = "Obra desconocida";
	public static final String EXC_UNKNOWN_PASE = "Pase desconocido";
	public static final String EXC_EVENTO_TABLA = "Tipo de tabla incompatible con la operacion o con los datos";
	public static final String EXC_ELEM_DUPLICADO = "Elemento duplicado";

	//BBDD
	public static final String PATH_TO_BBDD = "resources/BBDD/%s";
	
		//Nombres
	public static final String BDCli = "Cliente.json";
	public static final String BDCT = "CompTea.json";
	public static final String BDMCT = "MiemCompTea.json";
	public static final String BDOb = "Obra.json";
	public static final String BDFac = "Factura.json";
	public static final String BDLinFac = "LineaFactura.json";
	public static final String BDPase = "Pase.json";
	public static final String BDTaq = "Taquillero.json";
	public static final String BDCT_MCT = "CompTea_MiemCompTea.json";	
	
		//Claves
	
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
	public static final String KEY_linFacs = "lineas";
	public static final String KEY_miembCompTea = "miembros de la compania teatral";
	public static final String KEY_compTea = "compania teatral";
	public static final String KEY_cliNorms = "clientesNormal";
	public static final String KEY_cliVIPs = "clientesVIP";
	public static final String KEY_pases = "pases";
	public static final String KEY_miembros = "miembros";
	public static final String KEY_taquilleros = "taquilleros";
	public static final String KEY_relComp_Miem = "relacion miembro-compania";
	
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
	public static final String KEY_ptosAcum = "puntos";
	public static final String KEY_nivelVIP = "nivel";
	public static final String KEY_numVentas = "numero de ventas";
	public static final String KEY_sueldo = "sueldo";
	
		//Identificación adicional
	public static final String KEY_nombre = "nombre";
	public static final String KEY_apellido = "apellido";
	public static final String KEY_genero = "genero";	
	public static final String KEY_edad = "edad";
	public static final String KEY_fecha = "fecha";
	public static final String KEY_direccion = "direccion";
	public static final String KEY_email = "email";
	public static final String KEY_titulo = "titulo"; 
	public static final String KEY_autor = "autor"; 
	public static final String KEY_generoObra = "genero"; 
	public static final String KEY_sinopsis = "sinopsis";
}
