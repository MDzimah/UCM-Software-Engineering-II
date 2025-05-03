package misc;

public class Messages {
	//Ops. realizadas con éxito
	private static final String SHP = "Se ha %s correctamente.";
		//Factura
	public static final String EX_VENTA_CERRADA = Messages.SHP.formatted("cerrado la venta");
	public static final String EX_PASE_ANYADIDO_A_VENTA = Messages.SHP.formatted("añadido al carrito el pase con id \"%s\"");
	public static final String EX_PASE_QUITADO_DE_VENTA = Messages.SHP.formatted("quitado del carrito el pase con id \"%s\"");

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

		//Obra (SEGURAMENTE LO CAMBIE DESPUÉS)
	public static final String EX_OBRA_ANYADIDA_CORRECT = Messages.SHP.formatted("registrado la obra");
	public static final String EX_OBRA_BORRADA_CORRECT = Messages.SHP.formatted("borrado la obra");
	public static final String EX_OBRA_ANYADIDA_ERROR = Messages.NSHP.formatted("registrar la obra.");
	public static final String EX_OBRA_BORRADA_ERROR = Messages.NSHP.formatted("borrar la obra.");


	
	//Ops. fracasadas
	private static final String NSHP = "No se ha podido %s";
		//Factura
	public static final String X_ANYADIR_PASE_A_VENTA = Messages.NSHP.formatted("añadir el pase al carrito.");
	public static final String X_VENTA_CERRADA = Messages.NSHP.formatted("realizar la venta.");
	public static final String X_QUITAR_PASE_DE_VENTA = Messages.NSHP.formatted("quitar el pase del carrito.");
	public static final String X_BUSCAR_FACTURA = Messages.NSHP.formatted("encotrar la factura.");
	public static final String X_MOSTRAR_FACTURAS = Messages.NSHP.formatted("mostrar las facturas.");
	
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

	
		//Motivos por ops fracasadas
	public static final String MOTIVO = "Motivo: \"%s\".";
	public static final String ID_NO_ENCONTRADO = "El id \"%s\" no se ha encontrado";
	public static final String NO_HAY_DATOS = "No hay datos para mostrar";
	public static final String NO_EN_CARRITO = "El pase no se encuentra en el carrito";

	//Columnas de las tablas
	public static final String[] colNomsFactura = {"ID","ID CLIENTE", "ID TAQUILLERO", "FECHA", "IMPORTE", "SUBTOTAL"};
	public static final String[] colNomsCliente = {/*COMPLETAR*/};
	public static final String[] colNomsCompTea = {/*COMPLETAR*/};
	public static final String[] colNomsMiemCompTea = {"ID","NOMBRE", "APELLIDO", "EDAD", "DNI", "EMAIL", "GENERO"};
	public static final String[] colNomsObra = {"ID","TITULO", "AUTOR", "GENERO", "SINOPSIS"};
	public static final String[] colNomsPase = {"ID","ID COMPANYA", "ID OBRA", "FECHA", "STOCK", "PRECIO"};
	
	//Errores
	public static final String ERROR_LECTURA_BBDD = "Error al leer la BBDD de %s";
	public static final String ERROR_ESCRITURA_BBDD = "Error al escribr en la BBDD de %s";
	public static final String ERROR = "ERROR: %s"; 
	
	//BBDD
	public static final String PATH_TO_BBDD = "resources/BBDD/%s";
	
		//Nombres
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
	public static final String KEY_cliNorms = "clientes";
	public static final String KEY_cliVIPs = "clientes";
	public static final String KEY_pases = "pases";
	public static final String KEY_miembros = "miembros";
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
