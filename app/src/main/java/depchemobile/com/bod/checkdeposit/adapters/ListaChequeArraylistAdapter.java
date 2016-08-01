package depchemobile.com.bod.checkdeposit.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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
 * Created by rony_2 on 31/7/2016.
 */
public class ListaChequeArraylistAdapter extends BaseAdapter {

    ArrayList<Cheque> lista;
    Activity context;
    private static LayoutInflater inflater=null;
    public ImageLoader imageLoader;
    //  boolean[] itemChecked;
    public ListaChequeArraylistAdapter(Activity context, ArrayList<Cheque> lista ) {
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
        return lista.get(position).getId();
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
            convertView.setTag(R.id.checkboxSelected, holder.ck1);

            holder.ck1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton vw,
                                             boolean isChecked) {
                    int getPosition = (Integer) vw.getTag();
                    lista.get(getPosition).setSeleccionado(
                            vw.isChecked());
                }
            });




        } else {

            holder = (ViewHolder) convertView.getTag();
        }
        holder.ck1.setTag(position);


        final Cheque cheque = (Cheque) getItem(position);

        imageLoader.DisplayImage(cheque.getImgChequeFront().getPath()  , holder.mImageViewFront);
        imageLoader.DisplayImage(cheque.getImgChequeBack().getPath()  , holder.mImageViewBack);
        holder.txtMonto.setText( NumberFormat.getNumberInstance(Locale.getDefault()).format(cheque.getMonto()) + " Bs.");
        holder.txtfechaCheque.setText(Utils.FormateadorFecha(cheque.getFechaProceso() ));
        holder.ck1.setChecked(cheque.isSeleccionado());
        holder.mImageViewFront.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                Uri hacked_uri = Uri.parse("file://" + cheque.getImgChequeFront().getPath());
                intent.setDataAndType(hacked_uri, "image/*");

                context.startActivity(intent);


            }
        });

        holder.mImageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                Uri hacked_uri = Uri.parse("file://" + cheque.getImgChequeBack().getPath());
                intent.setDataAndType(hacked_uri, "image/*");

                context.startActivity(intent);


            }
        });

        return convertView;
    }
}
