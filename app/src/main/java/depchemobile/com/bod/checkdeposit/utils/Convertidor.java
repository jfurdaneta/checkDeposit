package depchemobile.com.bod.checkdeposit.utils;

import android.database.Cursor;
import android.net.Uri;

import java.util.Date;

import depchemobile.com.bod.checkdeposit.data.ChequeContract;
import depchemobile.com.bod.checkdeposit.entidades.Cheque;

/**
 * Created by rony_2 on 31/7/2016.
 */
public class Convertidor {





    public static Cheque llenarCheque(Cursor cursor)
    {
        try
        {
            int chequeID = cursor.getInt(cursor.getColumnIndexOrThrow(ChequeContract.ChequeEntry._ID));
            double montoCheque = cursor.getDouble(cursor.getColumnIndexOrThrow(ChequeContract.ChequeEntry.MONTO));
            long numLote = cursor.getLong(cursor.getColumnIndexOrThrow(ChequeContract.ChequeEntry.NUMERO_LOTE));
            String numCuenta= cursor.getString(cursor.getColumnIndexOrThrow(ChequeContract.ChequeEntry.NUMERO_CUENTA));
            String nombreBanco= cursor.getString(cursor.getColumnIndexOrThrow(ChequeContract.ChequeEntry.NOMBRE_BANCO));
            boolean mismoBanco = cursor.getInt(cursor.getColumnIndexOrThrow(ChequeContract.ChequeEntry.MISMO_BANCO))>0;
            long fechaCheque = cursor.getLong((int) new Date(cursor.getColumnIndexOrThrow(ChequeContract.ChequeEntry.FECHA_PROCESO)).getTime());
            String uriFrente = cursor.getString(cursor.getColumnIndexOrThrow(ChequeContract.ChequeEntry.IMAGEN_CHEQUE_FRENTE));
            String uriTrasera= cursor.getString(cursor.getColumnIndexOrThrow(ChequeContract.ChequeEntry.IMAGEN_CHEQUE_TRASERA));

            Cheque cheque = new Cheque();
            cheque.setId(chequeID);
            cheque.setNumLote(numLote);
            cheque.setNombreBanco(nombreBanco);
            cheque.setMismoBanco(mismoBanco);
            cheque.setNumCuenta(numCuenta);
            cheque.setFechaProceso(new Date(fechaCheque));
            cheque.setImgChequeFront(Uri.parse(uriFrente));
            cheque.setImgChequeBack(Uri.parse(uriTrasera));
            cheque.setMonto(montoCheque);
            return  cheque;

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }
}
