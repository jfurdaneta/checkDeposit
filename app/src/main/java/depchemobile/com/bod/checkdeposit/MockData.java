package depchemobile.com.bod.checkdeposit;

import android.net.Uri;

import java.util.ArrayList;
import java.util.Date;

import depchemobile.com.bod.checkdeposit.entidades.Cheque;

/**
 * Created by rony_2 on 28/7/2016.
 */
public class MockData {

    Cheque cheque;
    ArrayList <Cheque>lista;


    public MockData()
    {

    }

    public ArrayList<Cheque> datos()
    {
        Uri.Builder builder = new Uri.Builder();
        lista = new ArrayList<Cheque>();

        Uri uri = builder.path("/storage/emulated/0/dcim/DepCheq/IMG_20160728_213824_997015474.jpg").build();

        cheque = new Cheque();
        cheque.setNumCuenta("3465446");
        cheque.setFechaProceso(new Date());
        cheque.setMonto(154.51);
        cheque.setImgChequeBack(uri);
        cheque.setImgChequeFront(uri);
        lista.add(cheque);
        cheque = new Cheque();
        cheque.setNumCuenta("242323");
        cheque.setFechaProceso(new Date());
        cheque.setMonto(541545.65);
        cheque.setImgChequeBack(uri);
        cheque.setImgChequeFront(uri);
        lista.add(cheque);

        cheque = new Cheque();
        cheque.setNumCuenta("34535345");
        cheque.setFechaProceso(new Date());
        cheque.setMonto(23234.51);
        cheque.setImgChequeBack(builder.path("/storage/emulated/0/dcim/DepCheq/IMG_20160728_213824_997015474.jpg").build());
        cheque.setImgChequeFront(builder.path("/storage/emulated/0/dcim/DepCheq/IMG_20160728_213824_997015474.jpg").build());
        lista.add(cheque);
        cheque = new Cheque();
        cheque.setNumCuenta("345358");
        cheque.setFechaProceso(new Date());
        cheque.setMonto(45567.6);
        cheque.setImgChequeBack(builder.path("/storage/emulated/0/dcim/DepCheq/IMG_20160728_213824_997015474.jpg").build());
        cheque.setImgChequeFront(builder.path("/storage/emulated/0/dcim/DepCheq/IMG_20160728_213824_997015474.jpg").build());
        lista.add(cheque);



        return lista;

    }
}
