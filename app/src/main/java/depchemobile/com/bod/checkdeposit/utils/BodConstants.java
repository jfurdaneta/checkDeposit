package depchemobile.com.bod.checkdeposit.utils;

/**

 */
public class BodConstants {

    public static final long SPLASH_SCREEN_DELAY = 2000;


    public static final int GOTO_PANEL_FINANCIERO = 0;
    public static final int GOTO_DIRECTORIO_GLOBAL = 1;
    public static final int GOTO_PANEL_FINANCIERO_PF = 2;
    public static final int GOTO_DIRECTORIO_GLOBAL_CONTACTO_LECTURA = 2;
    public static final int GOTO_DIRECTORIO_GLOBAL_CONTACTO_EDICION = 3;
    public static final int GOTO_DIRECTORIO_GLOBAL_CONTACTO_NUEVO = 4;
    public static final int GOTO_DIRECTORIO_GLOBAL_PRODUCTOS = 5;
    public static final int GOTO_DIRECTORIO_GLOBAL_PRODUCTO_EDICION = 6;
    public static final int GOTO_DIRECTORIO_GLOBAL_PRODUCTO_NUEVO = 7;
    public static final int GOTO_DIRECTORIO_GLOBAL_PRODUCTO_NUEVO2 = 8;

    public static final int TIPO_CUENTA = 1;
    public static final int TIPO_TARJETA = 2;
    public static final int TIPO_CREDITO = 3;

    public static final int TENGO_VS_DEBO_GRAPHIC_OPTION = 1;
    public static final int TENGO_GRAPHIC_OPTION = 2;
    public static final int DEBO_GRAPHIC_OPTION = 3;

    public static final String bundle_goto = "go_fragment";
    public static final String bundle_cuenta = "cuenta_data";

    public static final String HTTP_SERVICE_BAD_REQUEST_STRING = "400";
    public static final int HTTP_SERVICE_BAD_REQUEST = 400;
    public static final int HTTP_SERVICE_SUCCESFULL_REQUEST = 200;

    public static final String CODIGO_CUENTA_CORRIENTE = "CCTE";
    public static final String CODIGO_CUENTA_AHORRO = "CAHO";

    public static final String CODIGO_USUARIO_REGISTRADO = "R";

    public static final String CODIGO_ESTATUS_USUARIO = "login_validar_equipo";

    public static final String CODIGO_RUTA_ACCIONES = "accionesRealizar";
    public static final String CODIGO_RUTA_HOME = "home";
    public static final String CODIGO_RUTA_ZONA = "zona_segura";
    public static final String CODIGO_RUTA_LOGIN = "login_selecPerfil";
    //

    public static final int TDC_STATUS_CODE_VIGENTE = 0;

    public static final int PRODUCTS_TABLE_LIMIT_ROW = 10;

    public static final int SERVICES_USES_WEB = 0;
    public static final int SERVICES_USES_MOCK = 1;
    public static final int SERVICES_USES = SERVICES_USES_WEB;
    public static final String TRANSFERENCIA_OTRO_BANCO = "S";

    public static final String TIPO_OPERACION_BUNDLE = "bumdle_operacion";
    public static final String RECIBO_BUNDLE = "bumdle_recibo";

    public static final int TIPO_OPERACION_TRANSFERENCIA = 1;
    public static final int TIPO_OPERACION_PAGO_TDC = 2;
    public static final int TIPO_OPERACION_DIRECTORIO_GLOBAL = 3;

    public static final int CATEGORIA_CUENTA = 65;
    public static final int CATEGORIA_PRUEBAS = 84;
    public static final int CATEGORIA_TARJETA = 66;
    public static final int CATEGORIA_CREDITO = 68;

    public final static String COD_CATEGORIA_CUENTA = "cta";
    public final static String COD_CATEGORIA_TARJETA = "tdc";
    public final static String COD_CATEGORIA_CREDITO = "cdt";

    public static final String BOD_BANCO_ID = "0116";

    public static final String tituloMensaje = "Atenci\u00f3n";

    public static final int TDC_DESCONOCIDO = -1;
    public static final int TDC_BOD = 1;
    public static final int TDC_OTROBANCO = 2;


}
