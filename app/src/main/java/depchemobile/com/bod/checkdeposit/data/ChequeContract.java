package depchemobile.com.bod.checkdeposit.data;

import android.provider.BaseColumns;

/**
 * Created by Rony Diaz on 29-07-2016.
 */
public class ChequeContract {

    public static abstract class ChequeEntry implements BaseColumns {
        public static final String TABLE_NAME ="cheque";
        public static final String ID ="id";

        public static final String FECHA_PROCESO = "fechaProceso";
        public static final String MONTO = "monto";
        public static final String NUMERO_CUENTA = "numCuenta";
        public static final String IMAGEN_CHEQUE_FRENTE = "imgChequeFront";
        public static final String IMAGEN_CHEQUE_TRASERA = "imgChequeBack";
        public static final String MISMO_BANCO = "mismoBanco";
        public static final String NOMBRE_BANCO = "nombreBanco";
        public static final String NUMERO_LOTE = "numLote";

    }
}
