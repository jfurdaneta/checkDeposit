package depchemobile.com.bod.checkdeposit.web;

/**
 * Created by Rony Diaz
 */
public class WebConstants {

    public final static String GATEWAY_URL = "http://192.168.226.144/GatewayBodApp/webresources/generic"; //TODO Calidad
    //public final static String GATEWAY_URL = "http://192.168.104.132/GatewayBodApp/webresources/generic"; //TODO Produccion
    //public final static String GATEWAY_URL = "http://192.168.226.140/GatewayBodApp/webresources/generic"; //TODO Desarrollo
    //public final static String GATEWAY_URL = "http://192.168.226.144/GatewayBodApp/webresources/generic"; //TODO Base/Cero
    //public final static String GATEWAY_URL = "http://app.bancadigitalbod.com/GatewayBodApp/webresources/generic"; //TODO Publica
    //public final static String GATEWAY_URL = "http://200.59.184.16/GatewayBodApp/webresources/generic"; //TODO Publica
    //TODO Gateway - Desarrollo - Carlos
    //public final static String GATEWAY_URL = "http://10.0.48.72:7010/GatewayBodApp/webresources/generic";
    //TODO Gateway - Desarrollo - Yaher
    //public final static String GATEWAY_URL = "http://10.0.32.122:7001/GatewayBodApp/webresources/generic";

    //TODO Gateway - Desarrollo - Rony Diaz
    //public final static String GATEWAY_URL = "http://10.0.32.59:7001/GatewayBodApp/webresources/generic";


    public static String EXPIRED_SESSION_COD = "8820";
    public static String INACTIVITY_SESSION_COD = "8830";
    public static int TIPO_BUSQUEDA_MENU = 1;
    public static int TIPO_BUSQUEDA_CONTACTO = 2;
    public static int TIPO_BUSQUEDA_COMPROBANTE = 3;

    public static String SERVICE_ERROR_MENSAJE_KEY = "Mensaje";
    public static String SERVICE_ERROR = "E";
    public static String SERVICE_CORRECT = "C";

    public static String SERVICE_OK = "0";
    public static String SERVICE_RESULTADO_KEY = "Resultado";
    public static String stringEmpty = "";

    public static int TDCPAGO_PAGOMINIMO_ID = 0;
    public static int TDCPAGO_DEUDATOTAL_ID = 1;
    public static int TDCPAGO_OTROMONTO_ID = 2;

    public static String TDCPAGO_DEUDATOTAL_TEXT = "Deuda Total";
    public static String TDCPAGO_PAGOMINIMO_TEXT = "Pago M\u00ednimo";
    public static String TDCPAGO_OTROMONTO_TEXT = "Otro Monto";

    public static String TRANSFERENCIA_ESTADO_RECHAZADO_TEXT = "Rechazada";

    //keys
    public final static String COMP_ID = "compId";
    public final static String COMP_TYPE = "compType";
    public final static String METHOD_ID = "methodId";
    public final static String NOMBRE_KEY = "nombre";
    public final static String VALOR_KEY = "valor";
    public final static String PARAM_KEY = "param";
    public final static String CODIGO_KEY = "codigo";

    //values+
    public final static String COMP_ID_CUENTAS = "Cuentas";
    public final static String COMP_ID_TARJETAS = "TDC";
    public final static String COMP_TYPE_SOAP = "SOAP";
    public final static String PARTY_ID = "PartyId";
    public final static String NUMERO_CUENTA = "NroCuenta";
    public final static String NUMERO_TARJETA = "NroTDC";
    public final static String COMP_ID_LOGIN = "Login";
    public final static String COMP_ID_CREDITOS = "Creditos";
    public final static String COMP_ID_DIRECTORIO_GLOBAL = "Directorio Global";
    public final static String COMP_ID_TRANSFERENCIA = "Transferencia";
    public final static String TIPO_OPERACION_ID = "TipoOperacion";

    public final static String TIPO_OPERACION_TRANSFERENCIA = "T";
    public final static String TIPO_OPERACION_PAGO = "P";

    public final static String COMP_PANEL_FINANCIERO = "bitacora.app.panel.financiero";

    public final static String MODULO_ORIGEN_ID = "ModuloOrigen";

    public final static String DISPOSITIVO_ID = "Dispositivo";

    public final static String COMP_ID_VALIDAR_PREGUNTAS_DESAFIO = "validarPreguntasDesafio";
    public final static String COMP_ID_PREGUNTA_DESAFIO = "preguntasDesafio";
    public final static String COMP_ID_SEGURIDAD = "seguridad";

    public final static String COMP_ID_TIMESESSIONAPP = "tiempoSesionApp";
    public final static String COMP_ID_DATAMASK = "enmascararDatos";

    public final static String NOMBRE_LOGIN_KEY = "NombreLogin";

    //keys for status usuario
    public final static String PARAM_NOMBRE_USUARIO = "NombreUsuario";
    public final static String PARAM_NOMBRE_USUARIO_TRANSFER = "nombreUsuario";
    public final static String ESTATUS_RESULT_KEY = "Estatus";

    public final static String IMAGE_RESULT_KEY = "ImagenBase64";
    public final static String IMAGE_NOMBRE_RESULT_KEY = "ImagenDescripcion";

    //keys for validacion contrasenia
    public final static String PARAM_CONTRASENIA = "Contrasenia";
    public final static String FECHA_AFILIACION_RESULT_KEY = "FechaAfiliacion";
    public final static String USER_ID_RESULT_KEY = "Id";
    public final static String LOGIN_RESULT_KEY = "login";
    public final static String NOMBRE_USER__RESULT_KEY = "Nombre";
    public final static String TIPO_IDENTIFICACION_RESULT_KEY = "TipoIdentificacion";
    public final static String ULTIMO_LOGIN_RESULT_KEY = "UltimoLogin";
    public final static String AVATAR_RESULT_KEY = "Avatar";
    public final static String TIENE_PERFIL_KEY = "tienePerfil";

    //keys for cuentas
    public final static String ID_CUENTA = "Id";
    public final static String SALDO_DISPONIBLE = "SaldoDisponible";
    public final static String SALDO_LIBROS = "SaldoEnLibros";
    public final static String SALDO_TOTAL = "SaldoTotal";
    public final static String TIPO_CUENTA = "TypeCta";
    public final static String MONTO_RETENIDO = "MontoRetenido";
    public final static String MONTO_DIFERIDO = "MontoDiferido";
    public final static String MONTO_PROMEDIO = "MontoPromedio";
    public final static String MONTO_COMISION = "MontoComision";
    public final static String LISTA_MOVIMIENTOS = "ListadoMovimientos";
    public final static String BINDATA_ID = "BinData";
    public final static String FECHA_INICIO_ID = "fechaInicio";
    public final static String FECHA_FIN_ID = "fechaFin";

    //keys for tarjetas
    public final static String AFFINITY_NAME = "affinityName";
    public final static String CARD_TYPE = "cardType";
    public static final String CARD_STATUS_CODE = "cardStatusCode";
    public static final String COURT_DT = "courtDt";
    public static final String DT_LIMIT_PAYMENT = "dtLimitPayment";
    public static final String CURRENT_BALANCE = "currentBalance";
    public static final String LIMIT_DOLLARS = "limitDollars";
    public static final String BALANCE_AVAILABLE = "balanceAvailable";
    public static final String CHARGES_AMOUNT = "chargesAmount";
    public static final String AMOUNT_PAYMENT_MONTH = "amountPaymentMonth";
    public static final String PAYMENT_MIN = "paymentMin";
    public static final String AVERAGE_BALANCE = "averageBalance";
    public static final String PAYMENT_EXPIREDS = "paymentsExpireds";
    public static final String CARD_NUM = "cardNum";
    public static final String BALANCE_AT_CUT = "balanceAtCut";
    public static final String CARD_EXPIRED = "cardExpired";
    public static final String BALANCE_EXPIRED = "balanceExpired";
    public static final String LIMIT_BOLIVARES = "limitBolivares";
    public static final String PAYMENT_ALL = "paymentAll";
    public static final String LAST_PAYMENT_AMOUNT = "lastPaymentAmount";
    public static final String AVERAGE_CONSUMPTION = "averageConsumption";
    public static final String AMOUNTS_EXPIREDS = "amountsExpireds";
    public final static String LISTA_MOVIMIENTOS_TDC = "listaMovimientos";

    //keys for movimientos
    public static final String DESCRIPCION_MOV = "DescripMov";
    public static final String FECHA_MOV = "FechaMov";
    public static final String MONTO_MOV = "MontoMov";
    public static final String REFERENCIA_MOV = "ReferenciaMov";
    public static final String SALDO_MOV = "SaldoMov";
    public static final String T_OPERACION_MOV = "ToperacionMov";
    public static final String DESCRIPCION_MOV_TDC = "descripcionMov";
    public static final String FECHA_MOV_TDC = "fechaMov";
    public static final String MONTO_MOV_TDC = "montoMov";

    //constantes creditos detalle
    public static final String CUENTA_CREDITO_PARAM_KEY = "CuentaCredito";
    public static final String CUOTAS_PAGADAS_RESULT_KEY = "cuotasPagadas";
    public static final String CUOTAS_POR_PAGAR_RESULT_KEY = "cuotasPorPagar";
    public static final String DESCRIPCION_PLAZO_RESULT_KEY = "descripPlazo";
    public static final String FECHA_APERTURA_RESULT_KEY = "fapertura";
    public static final String FECHA_VENCIMIENTO_CRED_RESULT_KEY = "fvencimiento";
    public static final String ID_SOLICITUD_RESULT_KEY = "idSolicitud";
    public static final String INTERES_MORA_RESULT_KEY = "interesMora";
    public static final String INTERES_VENCIDO_RESULT_KEY = "interesVencido";
    public static final String MONTO_ORIGINAL_RESULT_KEY = "montoOriginalCredito";
    public static final String MONTO_PAGADO_A_FECHA_RESULT_KEY = "montoPagadoALaFecha";
    public static final String PLAZO_CREDITO_RESULT_KEY = "plazoCredito";
    public static final String DESCRIPCION_PRODUCTO_RESULT_KEY = "producto";
    public static final String SALDO_A_LA_FECHA_RESULT_KEY = "saldoTotalALaFecha";
    public static final String TASA_CREDITO_RESULT_KEY = "tazaCredito";
    public static final String TIPO_CREDITO_RESULT_KEY = "tipoCredito";
    public static final String LISTA_CUOTAS_RESULT_KEY = "listadoCuotas";

    //constantes para lista creditos, esto no deberia ser pero las claves de la lista no tiene nada que ver con el detalle
    public static final String FECHA_VENCIMIENTO_CREDL_RESULT_KEY = "f_vencimiento";
    public static final String ID_CREDL_RESULT_KEY = "id";
    public static final String PAGO_MINIMO_CREDL_RESULT_KEY = "pagoMinimo";
    public static final String PAGO_TOTAL_CREDL_RESULT_KEY = "pagoTotal";
    public static final String RAZON_CREDITO_CREDL_RESULT_KEY = "razonCredito";
    public static final String SALDO_DISPONIBLE_CREDL_RESULT_KEY = "saldoDisponible";
    public static final String SALDO_ORIGINAL_CREDL_RESULT_KEY = "saldoOriginal";

    //CONSTANTES SERVICIO ASOCIADOS (DIRECTORIO                                                GLOBAL)/////
    public static final String NOMBRE_USUARIO_DG_KEY = "NombreUsuario";
    public static final String NOMBRE_ASOC_RESULT_KEY = "Nombre";
    public static final String EMAIL_ASOC_REULT_KEY = "Email";
    public static final String TIPO_IDEN_ASOC_RESULT_KEY = "TipoIdentificaci\u00f3n";
    public static final String ALIAS_ASOC_RESULT_KEY = "Alias";
    public static final String TELEFONO_ASOC_RESULT_KEY = "Telefono";
    public static final String IDENTIFICACION_ASOC_RESULT_KEY = "Identificaci\u00f3n";
    public static final String ID_ASOC_RESULT_KEY = "Id";
    public static final String CLIENTE_ID_ASOC_RESULT_KEY = "ClientesId";
    public static final String STATUS_ASOC_RESULT_KEY = "Estatus";
    public static final String LISTA_PROD_ASOC_RESULT_KEY = "ListadoProductos";

    //CONSTANTES PRODUCTOS ASOCIADOS DIRECTORIO GLOBAL
    public static final String BANCO_ID_PROD_ASOC = "BancosId";
    public static final String ESTATUS_PROD_ASOC = "Estatus";
    public static final String ID_PROD_ASOC = "Id";
    public static final String ID_CATEGIROA_PROD_ASOC = "IdCategoriaProductos";
    public static final String MONTO_MAXIMO_PROD_ASOC = "MontoMaximo";
    public static final String NOMBRE_CATEGORIA_PROD_ASOC = "NombreCategoriaProductos";
    public static final String NUMERO_PROD_ASOC = "Numero";
    public static final String BANCO_IMAGE_PROD_ASOC = "BancoImagen";
    public static final String ALIAS_PROD_ASOC = "Alias";
    public static final String BANCO_CODIGO_PROD_ASOC = "BancosCodigo";
    public static final String COD_CATEGORIA_PROD_ASOC = "CodigoCategoriaProductos";

    //keys for cuotas credito
    public static final String CAPITAL_CUOTA_RESULT_KEY = "capitalCuota";
    public static final String ESTATUS_CUOTA_RESULT_KEY = "estatus";
    public static final String FECHA_VENCIMIENT_CUOTA_RESULT_KEY = "fvencimiento";
    public static final String INTERES_CUOTA_RESULT_KEY = "interesCouta";
    public static final String MONTO_CUTA_RESULT_KEY = "montoCuota";
    public static final String OTROS_CARGOS_CUOTA_RESULT_KEY = "otrosCargos";

    //keys transferencia
    public static final String CUENTA_ORIGEN_TRANS_PARAM = "nroCuentaOrigen";
    public static final String CUENTA_DESTINO_TRANS_PARAM = "NroCuentaDestino";
    public static final String TIPO_ID_TRANS_PARAM = "tipoIdDestino";
    public static final String ID_DESTINO_TRANS_PARAM = "idDestino";
    public static final String MONTO_TRANS_PARAM = "Monto";
    public static final String CONCEPTO_TRANS_PARAM = "Concepto";
    public static final String TERCEROS_TRANS_PARAM = "Terceros";
    public static final String NOMBRE_TRANS_PARAM = "NombreDestino";

    //KEYS PARA RECIBO DE TRANSFERENCIAS
    public static final String NOMBRE_TITULAR_RECIBO_KEY = "Nombre";
    public static final String CUENTA_ORIGEN_RECIBO_KEY = "CuentaOrigen";
    public static final String CUENTA_DESTINO_RECIBO_KEY = "CuentaDestino";
    public static final String MONTO_RECIBO_KEY = "Monto";
    public static final String ESTADO_RECIBO_KEY = "observaciones";
    public static final String REFERENCIA_RECIBO_KEY = "Referencia";
    public static final String NOMBRE_TITULAR_KEY = "NombreTitular";

    //KEYS PARAMS PAGOS TDC
    public static final String NOMBRE_USUARIO_PAGO_TDC_PARAM = "nombreUsuario";
    public static final String NRO_TDC_PAGO_TDC_PARAM = "NroTDCDestino";
    public static final String NRO_CUENTA_ORIGEN_PAGO_TDC_PARAM = "nroCuentaOrigen";
    public static final String TIPO_ID_PAGO_TDC_PARAM = "tipoIdDestino";
    public static final String ID_DESTINO_PAGO_TDC_PARAM = "idDestino";
    public static final String MONTO_PAGO_TDC_PARAM = "Monto";
    public static final String MONTO_AMT_RECIBO_KEY = "amt";
    public static final String TERCEROS_PAGO_TDC_PARAM = "Terceros";
    public static final String CONCEPTO_PAGO_TDC_PARAM = "Concepto";
    public static final String PAGO_PUNTOS_PAGO_TDC_PARAM = "PagoPuntos";
    public static final String PARTY_ID_PAGO_TDC_PARAM = "PartyId";
    public static final String BANCO_ID_PAGO_TDC_PARAM = "BancoIdDestino";
    public static final String NOMBRE_DESTINO_PAGO_TDC_PARAM = "NombreDestino";

    //KEYS PARAMS PARAMERTROS
    public static final String CARACTER_KEY = "Enmascara_Caracter";
    public static final String CORREO_KEY = "Enmascara_CorreoElectronico";
    public static final String PREFIJO_KEY = "Enmascara_Prefijo";
    public static final String SUFIJO_KEY = "Enmascara_Sufijo";
    public static final String TELF_PREFIJO_KEY = "Enmascara_TelefonoPrefijo";
    public static final String TELF_SUFIJO_KEY = "Enmascara_TelefonoSufijo";

    public static final String TDC_PARAM_KEY = "NroTDC";

    //Cuentas - listadoBalancePromedioCuenta
    public final static String LISTA_BALANCE_PROMEDIO_CUENTA = "listadoBalancePromedioCuenta";

    //KEYS BALANCE PROMEDIO CUENTA
    public static final String CUENTA_PARAM_KEY = "NroCuenta";
    public static final String FECHA_BALANCE_RESULT_KEY = "FechaBalance";
    public static final String MONTO_PROMEDIO_RESULT_KEY = "MontoPromedio";
    public static final String TIPO_BALANCE_KEY = "TipoBalance ";

    //keys movimientos fecha
    public static final String FECHA_INICIO_PARAM = "FechaInicio";
    public static final String FECHA_FIN_PARAM = "FechaFin";
    public static final String BINDATA_PARAM = "BinData";

    //keys preguntas desafio
    public static final String ID_KEY = "id";
    public static final String TEXTO_KEY = "texto";
    public static final String RESPUESTA_KEY = "respuesta";
    public static final String PREGUNTAS_KEY = "preguntas";
    public static final String PREGUNTA_KEY = "pregunta";

    public static final String CANTIDAD_PREGUNTAS_PREDENTERMINADAS_KEY = "cantidadPreguntaPredeterminadas";
    public static final String CANTIDAD_PREGUNTAS_PERSONALES_KEY = "cantidadPreguntaPersonales";
    public static final String CANTIDAD_PREGUNTAS_SELECTOR_KEY = "cantidadPreguntaSelector";

    //keys valdiacion positiva
    public final static String COMP_CONTINUAR_VALIDACION_POSITIVA = "continuarValidacionPositiva";
    public final static String COMP_INICIAR_VALIDACION_POSITIVA = "iniciarValidacionPositiva";

    public static final String FECHA_ENVIO_KEY = "fechaEnvio"; //No se esta usando por ahora. Rony
    public static final String OPCIONES_KEY = "opciones";
    public static final String OPCION_KEY = "opcion";
    public static final String TEXTO_PREGUNTA_KEY = "textoPregunta";
    public static final String TIPO_PREGUNTA_KEY = "tipoPregunta";
    public static final String STATUS_CICLO_KEY = "statusCiclo";

    //keys equipo uso frecuente
    public final static String COMP_ID_VALIDAR_EQUIPO_USO_FRECUENTE = "validarEquipoUsoFrecuente";
    public static final String PARAM_FINGER_PRINT = "FingerPrint";
    public static final String PARAM_VALOR = "valor";
    public static final String PARAM_COOKIE = "Cookie";

    //keys registro de equipo uso frecuente
    public final static String COMP_ID_REGISTRO_EQUIPO_USO_FRECUENTE = "registrarEquipoUsoFrecuente";
    public static final String PARAM_DESCRIPCION = "Descripcion";
    public static final String RESPONSE_REGISTRAR_EQUIPO = "login_registrar_equipo";
    public static final String RESPONSE_EQUIPO_FRECUENTE = "1";
    public static final String RESPONSE_NO_EQUIPO_FRECUENTE = "0";
    public static final String ENROLLMENTQUERY_KEY = "EnrollmentQuery";

    //keys imagenes
    public final static String COMP_ID_CONSULTAR_IMAGEN = "consultarImagenSeguridad";
    public static final String IMAGENES_KEY = "Imagenes";
    public final static String IMAGENID_KEY = "ImagenId";

    public final static String COMP_ID_CONFIGURAR_IMAGEN = "configurarImagenSeguridad";
    public final static String IMAGEN_MODIFICA_VALUE = "imagen_modificada";
    public final static String IDIMAGEN_KEY = "IdImagen";
    public final static String DESCRIPCIONIMAGEN_KEY = "DescripcionImagen";

    // JSON HEADER
    public static final String CODIGO_ERROR_KEY = "CodigoError";
    public static final String MENSAJE_ERROR_KEY = "MensajeError";
    public static final String MENSAJE_KEY = "Mensaje";
    public static final String BODY_KEY = "Body";
    public static final String RESULTADO_KEY = "Resultado";

    //Preguntas master de desafio
    public final static String COMP_ID_PREGUNTAS_MASTER = "preguntasMaster";

    //Registrar Preguntas master de desafio
    public final static String COMP_ID_REGISTRAR_PREGUNTAS_DESAFIO = "configurarPreguntas";
    public static final String RESPONSE_REGISTRO_PREGUNTAS_DESAFIO = "pregunta_registrada";
    public static final String PERSONALIZADA_KEY = "personalizada";

    public final static String COMP_ID_USUARIO = "usuario";

    //Perfil
    public final static String COMP_ID_OBTENER_PERFIL = "obtenerPerfil";
    public static final String PARTYID_KEY = "partyId";
    public static final String AVATAR_KEY = "avatar";
    public static final String PERFILES_KEY = "perfiles";

    public final static String COMP_ID_CONFIGURAR_PERFIL = "configurarPerfil";

    //TODO perfil nuevo
    public final static String COMP_ID_TIENE_PERFIL = "tienePerfil";
    public final static String COMP_ID_OBTENER_PERFIL_REGISTRO = "obtenerPerfilRegistro";
    public final static String COMP_ID_REGISTRO_PERFIL_PREDETERMINADO = "registroPerfilPredeterminado";

    //Perfil Cambio
    public final static String COMP_ID_OBTENER_PERFIL_CAMBIO = "obtenerPerfilesCambio";
    public final static String COMP_ID_REGISTRO_PERFIL_CAMBIO = "registroCambioPerfil";
    public final static String COMP_ID_FORMATEAR_REGISTRO_PREDETERMINADO = "formatearPerfilPredeterminado";

    //Obtener URL DetectID
    public final static String COMP_ID_SOAP = "SOAP";
    public final static String COMP_ID_GENERADOR_CODIGOS = "generadorCodigos";
    public final static String PARAM_OBTENER_URL = "obtenerUrl";
    public final static String URL_KEY = "url";

    //Registro del Dispositivo
    public final static String COMP_ID_CONFIGURACION = "configuracion";
    public final static String COMP_ID_CONFIGURACION_REGISTRO_DISPOSITIVO = "registroEquipo";

    public final static String PARAM_IP = "ip";
    public final static String PARAM_ALTOPANTALLA = "altoPantalla";
    public final static String PARAM_ANCHOPANTALLA = "anchoPantalla";
    public final static String PARAM_USERAGENT = "userAgent";

    public final static String COMP_ID_CERRAR_SESSION = "cerrarSesion";

    public final static String COMP_ID_TIEMPO_SESSION_APP = "tiempoSesionApp";
    public final static String COMP_ID_REGISTRO_ACTIVIDAD = "registroActividad";

    //Menu Opciones
    public final static String COMP_ID_MENU_OPCIONES = "menuOpciones";

    //Menu Dinamico

    public final static String MENU_CONSULTAS = "app.consultas";
    public final static String MENU_PAGOS_TRASNFERENCIAS = "app.pagos.transferencias";
    public final static String MENU_PANEL_FINANCIERO = "app.panel.financiero";
    //
    public final static String MENU_SUB_CONSULTAS_CUENTAS = "app.consultas.cuentas";
    public final static String MENU_SUB_CONSULTAS_TARJETAS = "app.consultas.tarjetas";
    public final static String MENU_SUB_CONSULTAS_CREDITOS = "app.consultas.creditos";
    public final static String MENU_SUB_TRANSFERENCIAS = "app.transferencias";
    public final static String MENU_SUB_PAGOS = "app.pagos.tdc";

    //JOSE URDANETA
    public static final String SOFTOKEN_VENDOR = "BOD";
    public static final String SOFTOKEN_URL = "http://192.168.222.133:8080/detect/public/mobileServices.htm?code=";//desarrollo
    //public static final String SOFTOKEN_URL = "http://192.168.226.137:8080/detect/public/mobileServices.htm?code=";//calidad
    public static final String SOFTOKEN_GOOGLE_PROJECT_NUMBER = "338402013229";

    public static final String DID_PARAMETER_MISSING = "98";
    public static final String DID_SERVICE_ERROR = "99";
    public static final String DID_TOKEN_NOT_FOUND = "822";
    public static final String DID_TOKEN_HAS_RETRIEVED_SUCESSFULY = "825";
    public static final String DID_TOKEN_URL_CODE_NOT_FOUND = "930";
    public static final String DID_ACTIVATION_CODE_PENDING = "936";
    public static final String DID_ACTIVATION_CODE_ALREADY_USED = "945";
    public static final String DID_ACTIVATION_CODE_EXPIRED = "946";
    public static final String DID_ACTIVATION_CODE_NOT_FOUND = "947";
    public static final String DID_DEVICE_ALREADY_REGISTERED = "948";
    public static final String DID_SUCCESS_PUSH_DEVICE_REGISTER = "950";
    public static final String DID_REACHED_MAX_DEVICES = "956";
    public static final String DID_SUCCESS_DUAL_DEVICE_REGISTER = "960";
    public static final String DID_ACTIVATION_NOT_FOUND = "962";

    public static final String JSON_API = "http://192.168.104.132/GatewayBodApp/webresources/generic";
    public static final String JSON_API_REST_V2 = "http://192.168.104.132/GatewayBodApp/webresources/generic";
    public static final String JSON_API_REST_V3 = "http://192.168.104.132/GatewayBodApp/webresources/generic";

    public static final String MENSAJE_MONTO_CERO = "?El monto debe ser mayor a cero";

    /*
    if (Utiles.isExpiredSession(WebConstants.EXPIRED_SESSION_COD)) {
                Utiles.generateAlertDialog_gotoPerfil(BodConstants.tituloMensaje, "Session Expirada", parentActivity);
                return;
            }
    */


}
