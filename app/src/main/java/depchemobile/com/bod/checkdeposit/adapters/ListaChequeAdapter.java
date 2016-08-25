package depchemobile.com.bod.checkdeposit.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import depchemobile.com.bod.checkdeposit.R;
import depchemobile.com.bod.checkdeposit.data.ChequeContract;
import depchemobile.com.bod.checkdeposit.entidades.Cheque;
import depchemobile.com.bod.checkdeposit.utils.Convertidor;
import depchemobile.com.bod.checkdeposit.utils.ImageLoader;
import depchemobile.com.bod.checkdeposit.utils.Utils;

/**
 * Created by rony_2 on 26/7/2016.
 */
public class ListaChequeAdapter extends CursorAdapter {


    Cursor cursor;
    Activity context;
    private static LayoutInflater inflater=null;
    public ImageLoader imageLoader;

    Utils utilidades;
  //  boolean[] itemChecked;

    public ListaChequeAdapter(Activity context, Cursor cursor) {
        super(context, cursor, 0);
        this.cursor = cursor;
        this.context = context;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        imageLoader=new ImageLoader(context.getApplicationContext());

      //  this.itemChecked = new boolean[lista.size()];;
    }



    private class ViewHolder {


    }


    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    public Object getItem(int position) {
        return position;
    }





    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {

        return LayoutInflater.from(context).inflate(R.layout.list_row, parent, false);
    }

    @Override
    public void bindView(View convertView, final Context context, final Cursor cursor) {

        final ViewHolder holder;
        //LayoutInflater inflater = context.getLayoutInflater();

        ImageView mImageViewFront;
        ImageView mImageViewBack;
        CheckBox ck1;
        TextView txtMonto;
        TextView txtfechaCheque;
        TextView txtCuenta;

        mImageViewFront = (ImageView) convertView
                .findViewById(R.id.imageViewFrente);
        mImageViewBack = (ImageView) convertView
                .findViewById(R.id.imageViewTrasera);
        txtMonto = (TextView) convertView
                .findViewById(R.id.lblMonto);
        txtfechaCheque = (TextView) convertView
                .findViewById(R.id.txtFechaCheque);
        txtCuenta = (TextView) convertView
                .findViewById(R.id.txtCuenta);
        ck1 = (CheckBox) convertView
                .findViewById(R.id.checkboxSelected);
        final Cheque cheque = Convertidor.llenarCheque(cursor);
        ck1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




            }
        });



        imageLoader.DisplayImage(cheque.getImgChequeFront().getPath()  , mImageViewFront);
        imageLoader.DisplayImage(cheque.getImgChequeBack().getPath()  , mImageViewBack);
        txtMonto.setText( NumberFormat.getNumberInstance(Locale.getDefault()).format(cheque.getMonto()) + " Bs.");
        txtfechaCheque.setText(Utils.FormateadorFechaLocal(cheque.getFechaProceso() ));
        ck1.setChecked(cheque.isSeleccionado());
        txtCuenta.setText(cheque.getNumCuenta());
        mImageViewFront.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.setDataAndType(cheque.getImgChequeFront(), "image/*");

                context.startActivity(intent);


            }
        });

        mImageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.setDataAndType(cheque.getImgChequeBack(), "image/*");

                context.startActivity(intent);


            }
        });





    }


}
