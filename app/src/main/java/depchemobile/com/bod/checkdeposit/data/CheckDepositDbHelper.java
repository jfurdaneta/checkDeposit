package depchemobile.com.bod.checkdeposit.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.util.Log;

import java.util.Date;

import depchemobile.com.bod.checkdeposit.entidades.Cheque;

/**
 * Created by Rony Diaz on 29-07-2016.
 */
public class CheckDepositDbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 2;
    public static final String DATABASE_NAME = "CheckDeposit.db";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + ChequeContract.ChequeEntry.TABLE_NAME;


    public CheckDepositDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }





    @Override
    public void onCreate(SQLiteDatabase db) {


        Log.v(getClass().getName(),"onCreate - Iniciando creación de tabla " + ChequeContract.ChequeEntry.TABLE_NAME);
        db.execSQL("CREATE TABLE " + ChequeContract.ChequeEntry.TABLE_NAME + " ("
                + ChequeContract.ChequeEntry._ID + " integer PRIMARY KEY AUTOINCREMENT,"

                + ChequeContract.ChequeEntry.FECHA_PROCESO + " date NOT NULL DEFAULT CURRENT_TIMESTAMP,"
                + ChequeContract.ChequeEntry.MONTO + " DOUBLE NOT NULL,"
                + ChequeContract.ChequeEntry.NUMERO_CUENTA + " TEXT NOT NULL,"
                + ChequeContract.ChequeEntry.IMAGEN_CHEQUE_FRENTE + " TEXT NOT NULL,"
                + ChequeContract.ChequeEntry.IMAGEN_CHEQUE_TRASERA+ " TEXT NOT NULL,"
                + ChequeContract.ChequeEntry.MISMO_BANCO + " boolean NOT NULL,"
                + ChequeContract.ChequeEntry.NUMERO_LOTE + " TEXT NOT NULL,"
                + ChequeContract.ChequeEntry.NOMBRE_BANCO + " TEXT NULL)");


        Log.v(getClass().getName(),"onCreate - " + ChequeContract.ChequeEntry.TABLE_NAME + "  Creada");



        Log.v(getClass().getName(),"onCreate - Final");



    }

    public void datos()
    {

        SQLiteDatabase db = getWritableDatabase();
        Uri.Builder builder = new Uri.Builder();
        Cheque cheque;

        Uri uri = builder.path("/storage/emulated/0/dcim/DepCheq/IMG_20160728_213824_997015474.jpg").build();
        Uri uri2 = builder.path("/storage/emulated/0/dcim/DepCheq/IMG_20160730_134902_-954230898.jpg").build();


        cheque = new Cheque();
        cheque.setNumCuenta("3465446");
        cheque.setMismoBanco(true);
        cheque.setNumLote(14564646);
        cheque.setNombreBanco("BOD");
        cheque.setFechaProceso(new Date());
        cheque.setMonto(154.51);
        cheque.setImgChequeBack(uri2);
        cheque.setImgChequeFront(uri);
        Log.v(getClass().getName(),"onCreate - Insertando datos de prueba");
        long id =mockCheque(db,cheque);

        cheque = new Cheque();
        cheque.setNumCuenta("242323");
        cheque.setMismoBanco(true);
        cheque.setNumLote(234234);
        cheque.setNombreBanco("BOD");
        cheque.setFechaProceso(new Date());
        cheque.setMonto(541545.65);
        cheque.setImgChequeBack(uri2);
        cheque.setImgChequeFront(uri);
        id = mockCheque(db,cheque);


        cheque = new Cheque();
        cheque.setNumCuenta("34535345");
        cheque.setMismoBanco(false);
        cheque.setNumLote(234234);
        cheque.setNombreBanco("BOD");
        cheque.setFechaProceso(new Date());
        cheque.setMonto(23234.51);
        cheque.setImgChequeBack(uri);
        cheque.setImgChequeFront(uri2);
        mockCheque(db,cheque);

        cheque = new Cheque();
        cheque.setMismoBanco(false);
        cheque.setNumLote(887787);
        cheque.setNombreBanco("BANESCO");
        cheque.setNumCuenta("345358");
        cheque.setFechaProceso(new Date());
        cheque.setMonto(45567.6);
        cheque.setImgChequeBack(uri);
        cheque.setImgChequeFront(uri2);
        mockCheque(db,cheque);
    }

    public long mockCheque(SQLiteDatabase db, Cheque pCheque) {
        return db.insert(
                ChequeContract.ChequeEntry.TABLE_NAME,
                null,
                pCheque.toContentValues());
    }

    public long insertarCheque(Cheque pCheque) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        return sqLiteDatabase.insert(
                ChequeContract.ChequeEntry.TABLE_NAME,
                null,
                pCheque.toContentValues());

    }

    public long updateCheque(Cheque pCheque, long chequeID) {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();

        // Which row to update, based on the ID
        String selection = ChequeContract.ChequeEntry._ID+ " =  ?";
        String[] selectionArgs = { String.valueOf(chequeID) };


        return sqLiteDatabase.update(
                ChequeContract.ChequeEntry.TABLE_NAME,
                pCheque.toContentValues(),
                selection,
                selectionArgs
                );

    }


    public Cursor getCheques()
    {
        Log.v(getClass().getName(),"getCheques - Iniciando");
        SQLiteDatabase db = this.getReadableDatabase();
       return  db.rawQuery("select * from " + ChequeContract.ChequeEntry.TABLE_NAME, null);

    }

    public Cursor getChequeById(long id)
    {
        Cursor mCursor;
        if(id >0)
        {
            SQLiteDatabase db = this.getReadableDatabase();
            String query  = "select * from " + ChequeContract.ChequeEntry.TABLE_NAME  + " WHERE _id=?";
            mCursor = db.rawQuery(query,new String[]{String.valueOf(id)});

            if(mCursor.getCount() == 1)
            {
                mCursor.moveToFirst();
                return mCursor;
            }


        }
        return null;



    }





    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);

    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
