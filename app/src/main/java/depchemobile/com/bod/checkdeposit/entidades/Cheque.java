package depchemobile.com.bod.checkdeposit.entidades;

import android.content.ContentValues;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.util.Date;

import depchemobile.com.bod.checkdeposit.data.ChequeContract;

/**
 * Created by rony_2 on 24/7/2016.
 */
public class Cheque implements Parcelable{


    int id ;
    Date fechaProceso;
    Double monto;
    String numCuenta;
    Uri imgChequeFront;
    Uri imgChequeBack;
    boolean mismoBanco;

    //esta variable es para determinar si el cheque es seleccionado del listview
    boolean seleccionado = false;
    String nombreBanco;
    long numLote;

    public boolean isSeleccionado() {
        return seleccionado;
    }

    public void setSeleccionado(boolean seleccionado) {
        this.seleccionado = seleccionado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getNumLote() {
        return numLote;
    }

    public void setNumLote(long numLote) {
        this.numLote = numLote;
    }

    public boolean isMismoBanco() {
        return mismoBanco;
    }

    public void setMismoBanco(boolean mismoBanco) {
        this.mismoBanco = mismoBanco;
    }

    public String getNombreBanco() {
        return nombreBanco;
    }

    public void setNombreBanco(String nombreBanco) {
        this.nombreBanco = nombreBanco;
    }

    protected Cheque(Parcel in) {

        try
        {
            id = in.readInt();
            Uri.Builder builder = new Uri.Builder();
            numCuenta = in.readString();
            monto = in.readDouble();
            fechaProceso = new Date(in.readLong());
            imgChequeFront = builder.path(in.readString()).build();
            imgChequeBack = builder.path(in.readString()).build();

            mismoBanco = in.readByte() != 0;
            nombreBanco = in.readString();
            numLote = in.readLong();



        }catch (Exception e)
        {

            Log.e(this.getClass().getName(),"Cheque",e);
        }



    }

    public static final Creator<Cheque> CREATOR = new Creator<Cheque>() {
        @Override
        public Cheque createFromParcel(Parcel in) {
            return new Cheque(in);
        }

        @Override
        public Cheque[] newArray(int size) {
            return new Cheque[size];
        }
    };

    public Uri getImgChequeFront() {
        return imgChequeFront;
    }

    public void setImgChequeFront(Uri imgChequeFront) {

        Log.v(getClass().getName(),"URI de imgChequeFront: " + imgChequeFront.getPath());
        this.imgChequeFront = imgChequeFront;
    }

    public Uri getImgChequeBack() {
        return imgChequeBack;
    }

    public void setImgChequeBack(Uri imgChequeBack) {
        Log.v(getClass().getName(),"URI de imgChequeBack: " + imgChequeBack.getPath());
        this.imgChequeBack = imgChequeBack;
    }

    public String getNumCuenta() {
        return numCuenta;
    }

    public void setNumCuenta(String numCuenta) {
        this.numCuenta = numCuenta;
    }



    public Date getFechaProceso() {
        return fechaProceso;
    }

    public void setFechaProceso(Date fechaProceso) {
        this.fechaProceso = fechaProceso;
    }

    public Double getMonto() {
        return monto;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }





    public Cheque() {

    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeInt(id);

        dest.writeString(numCuenta);
        dest.writeDouble(monto);
        dest.writeLong(fechaProceso.getTime());
        dest.writeString(imgChequeFront.getPath());
        dest.writeString(imgChequeBack.getPath());
        dest.writeByte((byte) (mismoBanco ? 1 : 0));
        dest.writeString(nombreBanco);
        dest.writeLong(numLote);


    }


    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();

        values.put(ChequeContract.ChequeEntry.FECHA_PROCESO, fechaProceso.toString());
        values.put(ChequeContract.ChequeEntry.MONTO, monto);
        values.put(ChequeContract.ChequeEntry.NUMERO_CUENTA, numCuenta);
        values.put(ChequeContract.ChequeEntry.IMAGEN_CHEQUE_FRENTE, imgChequeFront.getPath());
        values.put(ChequeContract.ChequeEntry.IMAGEN_CHEQUE_TRASERA, imgChequeBack.getPath());
        values.put(ChequeContract.ChequeEntry.MISMO_BANCO, mismoBanco);
        values.put(ChequeContract.ChequeEntry.NOMBRE_BANCO, nombreBanco);
        values.put(ChequeContract.ChequeEntry.NUMERO_LOTE, numLote);



        return values;
    }
}
