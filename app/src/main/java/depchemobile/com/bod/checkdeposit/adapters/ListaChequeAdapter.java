package depchemobile.com.bod.checkdeposit.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import depchemobile.com.bod.checkdeposit.R;
import depchemobile.com.bod.checkdeposit.entidades.Cheque;
import depchemobile.com.bod.checkdeposit.utils.ImageLoader;
import depchemobile.com.bod.checkdeposit.utils.Utils;

/**
 * Created by rony_2 on 26/7/2016.
 */
public class ListaChequeAdapter extends BaseAdapter {

    ArrayList<Cheque> lista;
    Activity context;
    private static LayoutInflater inflater=null;
    public ImageLoader imageLoader;

    Utils utilidades;
  //  boolean[] itemChecked;

    public ListaChequeAdapter(Activity context, ArrayList<Cheque> lista ) {
        this.lista = lista;
        this.context = context;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        imageLoader=new ImageLoader(context.getApplicationContext());

      //  this.itemChecked = new boolean[lista.size()];;
    }



    private class ViewHolder {
        ImageView mImageViewFront;
        ImageView mImageViewBack;
        CheckBox ck1;
        TextView txtMonto;
        TextView txtfechaCheque;

    }

    @Override
    public int getCount() {
        return lista.size();
    }

    @Override
    public Object getItem(int position) {
        return lista.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        LayoutInflater inflater = context.getLayoutInflater();

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_row, null);
            holder = new ViewHolder();

            holder.mImageViewFront = (ImageView) convertView
                    .findViewById(R.id.imageViewFrente);
            holder.mImageViewBack = (ImageView) convertView
                    .findViewById(R.id.imageViewTrasera);
            holder.txtMonto = (TextView) convertView
                    .findViewById(R.id.lblMonto);
            holder.txtfechaCheque = (TextView) convertView
                    .findViewById(R.id.txtFechaCheque);

            holder.ck1 = (CheckBox) convertView
                    .findViewById(R.id.checkboxSelected);


            convertView.setTag(holder);

        } else {

            holder = (ViewHolder) convertView.getTag();
        }

        utilidades = new Utils();
        final Cheque tmp = (Cheque) getItem(position);


        holder.mImageViewFront.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.setDataAndType(tmp.getImgChequeFront(), "image/*");
                context.startActivity(intent);


            }
        });

        holder.mImageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.setDataAndType(tmp.getImgChequeBack(), "image/*");
                context.startActivity(intent);


            }
        });


        imageLoader.DisplayImage(tmp.getImgChequeFront().getEncodedPath() , holder.mImageViewFront);
        imageLoader.DisplayImage(tmp.getImgChequeBack().getEncodedPath() , holder.mImageViewBack);


        holder.txtMonto.setText( NumberFormat.getNumberInstance(Locale.getDefault()).format(tmp.getMonto()) + " Bs.");

        holder.txtfechaCheque.setText(Utils.FormateadorFecha(tmp.getFechaProceso()));
        holder.ck1.setChecked(false);

        return convertView;
    }
}
