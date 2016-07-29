package depchemobile.com.bod.checkdeposit.entidades;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.util.Date;

/**
 * Created by rony_2 on 24/7/2016.
 */
public class Cheque implements Parcelable{


    Date fechaProceso;
    Double monto;
    String numCuenta;
    Uri imgChequeFront;
    Uri imgChequeBack;

    protected Cheque(Parcel in) {

        try
        {
            Uri.Builder builder = new Uri.Builder();
            numCuenta = in.readString();
            monto = in.readDouble();
            fechaProceso = new Date(in.readLong());
            imgChequeFront = builder.path(in.readString()).build();
            imgChequeBack = builder.path(in.readString()).build();



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
        this.imgChequeFront = imgChequeFront;
    }

    public Uri getImgChequeBack() {
        return imgChequeBack;
    }

    public void setImgChequeBack(Uri imgChequeBack) {
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

        dest.writeString(numCuenta);
        dest.writeDouble(monto);
        dest.writeLong(fechaProceso.getTime());
        dest.writeString(imgChequeFront.getPath());
        dest.writeString(imgChequeBack.getPath());

        /*Log.v(this.getClass().getName(),"writeToParcel - " + "numCuenta");

        Log.v(this.getClass().getName(),"writeToParcel - " + "imgChequeBack");


        Log.v(this.getClass().getName(),"writeToParcel - " + "imgChequeFront");





        Log.v(this.getClass().getName(),"writeToParcel - " + "fechaProceso y fin");*/


    }
}
