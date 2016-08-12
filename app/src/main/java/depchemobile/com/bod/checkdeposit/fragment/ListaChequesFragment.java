package depchemobile.com.bod.checkdeposit.fragment;

        import android.app.Activity;
        import android.app.Dialog;
        import android.app.ProgressDialog;
        import android.graphics.Bitmap;
        import android.graphics.drawable.ColorDrawable;
        import android.support.v4.app.Fragment;
        import android.support.v4.app.FragmentTransaction;
        import android.support.v4.content.ContextCompat;

        import android.content.Context;
        import android.content.Intent;
        import android.database.Cursor;
        import android.os.AsyncTask;
        import android.os.Bundle;
        import android.support.annotation.Nullable;
        import android.support.v4.app.FragmentTransaction;
        import android.support.v4.content.ContextCompat;
        import android.support.v8.renderscript.RenderScript;
        import android.util.Log;
        import android.view.ContextMenu;
        import android.view.LayoutInflater;
        import android.view.MenuInflater;
        import android.view.MenuItem;
        import android.view.View;
        import android.view.ViewGroup;
        import android.view.Window;
        import android.view.WindowManager;
        import android.widget.AdapterView;
        import android.widget.Button;
        import android.widget.ImageView;
        import android.widget.LinearLayout;
        import android.widget.ListView;
        import android.widget.Toast;


        import android.support.v4.app.FragmentTransaction;
        import android.support.v4.content.ContextCompat;

        import com.android.volley.AuthFailureError;
        import com.android.volley.Request;
        import com.android.volley.RequestQueue;
        import com.android.volley.Response;
        import com.android.volley.VolleyError;
        import com.android.volley.VolleyLog;
        import com.android.volley.toolbox.JsonObjectRequest;
        import com.android.volley.toolbox.StringRequest;
        import com.android.volley.toolbox.Volley;
        import com.neopixl.pixlui.components.textview.TextView;

        import org.json.JSONException;
        import org.json.JSONObject;

        import java.util.ArrayDeque;
        import java.util.ArrayList;
        import java.util.Hashtable;
        import java.util.Map;
        import java.util.Queue;

        import depchemobile.com.bod.checkdeposit.MainActivity;
        import depchemobile.com.bod.checkdeposit.R;
        import depchemobile.com.bod.checkdeposit.activity.PrincipalActivity;
        import depchemobile.com.bod.checkdeposit.adapters.ListaChequeAdapter;
        import depchemobile.com.bod.checkdeposit.adapters.ListaChequeArraylistAdapter;
        import depchemobile.com.bod.checkdeposit.data.CheckDepositDbHelper;
        import depchemobile.com.bod.checkdeposit.entidades.Cheque;
        import depchemobile.com.bod.checkdeposit.fragmentactivity.PrincipalFragmentActivity;
        import depchemobile.com.bod.checkdeposit.utils.Convertidor;
        import depchemobile.com.bod.checkdeposit.utils.Utiles;
        import depchemobile.com.bod.checkdeposit.utils.Utils;
        import depchemobile.com.bod.checkdeposit.web.WebConstants;


/**
 * Created by rony_2 on 1/8/2016.
 */
public class ListaChequesFragment extends Fragment {

    ListView list;
    ListaChequeAdapter adapter2;
    ArrayList<Cheque> listaCheques = new ArrayList<Cheque>();
    ListaChequeArraylistAdapter adapter;
    private CheckDepositDbHelper mCheckDepositDbHelper;
    private PrincipalFragmentActivity parentActivity;
    double montoTotalDeposito;
    Queue <Cheque> sendQueue;

    private String formato_bsF = "Bs. ";

    public View rootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        rootView = inflater.inflate(R.layout.activity_lista_cheques,container,false);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        parentActivity = (PrincipalFragmentActivity) getActivity();
        montoTotalDeposito = 0;



        //Cheque chequeObject = intent.getExtras().getParcelable("cheque");
        list = (ListView) rootView.findViewById(R.id.list);
        cargarCheques();
        list.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);



        //Se registra menu contextual
        registerForContextMenu(list);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.v(getClass().getName(), "setOnItemClickListener - Seleccionado " + String.valueOf(position));
                //Toast.makeText(ListaChequesActivity.this, "Cheque tocado " + String.valueOf(position), Toast.LENGTH_SHORT).show();

                // ir_a_EditarCheque(position);
                ir_a_EditarCheque(position);
            }
        });

        Button btnAceptar = (Button) rootView.findViewById(R.id.btnAceptar);
        sendQueue = new ArrayDeque<>();
        btnAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int cont = 0;



                for (Cheque bean : listaCheques) {
                    if(bean.isSeleccionado())
                        sendQueue.add(bean);
                        cont++;
                }

                dialog_transferir();
                //Toast.makeText(getActivity(), "Cheques seleccionados " + String.valueOf(cont), Toast.LENGTH_SHORT).show();


            }
        });

        Button  btnCapturar = (Button) rootView.findViewById(R.id.btnCapturar);
        btnCapturar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parentActivity.setChequeID(0);
                Fragment fragment;

                fragment = new ChequeScanFragment();
                parentActivity.setTile("Captura de Cheque");
                parentActivity.setHeaderIcon(ContextCompat.getDrawable(getActivity(), R.drawable.pagos_transferencias_white));
                getActivity(). getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_fragment, fragment, "fragment")
                        .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE).addToBackStack(null)
                        .commit();

            }
        });


        return  rootView;
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.menu_context_lista_cheques, menu);

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();


        switch (item.getItemId()) {
            case R.id.edit:
                ir_a_EditarCheque(info.position);
                return true;
            case R.id.delete:

                mCheckDepositDbHelper.eliminarCheque(adapter.getItemId(info.position));
                cargarCheques();

                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //  Intent intent = getIntent();
        Log.v(this.getClass().getName(), "onCreate - " + "Obtieniendo intent");
        mCheckDepositDbHelper = new CheckDepositDbHelper(getActivity());


        // mCheckDepositDbHelper.datos();
        //Cursor c = mCheckDepositDbHelper.getCheques();
        //adapter2 = new ListaChequeAdapter(ListaChequesActivity.this, null);
        //  list.setAdapter(adapter2);

    }

    private void ir_a_EditarCheque(int position)
    {

        parentActivity.setTile("Panel Principal");
        parentActivity.setHeaderIcon(ContextCompat.getDrawable(getActivity(), R.drawable.panel_financiero_white));
        parentActivity.setChequeID(adapter.getItemId(position));

        android.support.v4.app.Fragment fragment;
        fragment = new ChequeScanFragment();
        parentActivity.setTile("Captura de Cheque");
        parentActivity.setHeaderIcon(ContextCompat.getDrawable(getActivity(), R.drawable.pagos_transferencias_white));
        getActivity(). getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_fragment, fragment, "fragment")
                .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE).addToBackStack(null)
                .commit();

    }


    private void cargarCheques() {
        listaCheques.clear();
        new ChequesLoadTask().execute();
    }




    private class ChequesLoadTask extends AsyncTask<Void, Void, Cursor> {

        @Override
        protected Cursor doInBackground(Void... voids) {
            return mCheckDepositDbHelper.getCheques();
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            if (cursor != null && cursor.getCount() > 0) {
                //adapter2.swapCursor(cursor);


                while(cursor.moveToNext())
                {
                    Cheque chequetmp =Convertidor.llenarCheque(cursor);
                    montoTotalDeposito = montoTotalDeposito + chequetmp.getMonto();
                    listaCheques.add(chequetmp);
                }
                adapter = new ListaChequeArraylistAdapter(getActivity(), listaCheques);
                list.setAdapter(adapter);



            } else {
                // Mostrar empty state
            }
        }
    }




    public void dialog_transferir() {

        final Dialog dialog = new Dialog(rootView.getContext(), R.style.MyThemeDialog);
        //final Dialog dialog = new Dialog(getActivity(), android.R.style.Theme_Material_NoActionBar_Fullscreen);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.deposito_custom_dialog);

        RenderScript rs = RenderScript.create(getActivity());
        LinearLayout bgLayout = (LinearLayout) dialog.findViewById(R.id.ll_image_layout);
        Utiles.blur(getActivity().getWindow().getDecorView().findViewById(android.R.id.content), rs, bgLayout, getActivity(), 4f);

        TextView cantidadCheques = (TextView) dialog.findViewById(R.id.tv_CantidadCheques);
        TextView cuentaDestino = (TextView) dialog.findViewById(R.id.tv_cuenta_destino);
        TextView montoTextView = (TextView) dialog.findViewById(R.id.tv_monto);

        cantidadCheques.setText(String.valueOf(listaCheques.size()));


        TextView tv_textoSuperior = (TextView) dialog.findViewById(R.id.tv_textoSuperior);
        TextView tv_tituloProductoOrigen = (TextView) dialog.findViewById(R.id.tv_tituloProductoOrigen);


        String subTitulo = "Por favor confirme los datos de la transferencia que está por realizar:";



        montoTextView.setText(formato_bsF + Utiles.formatNumber(montoTotalDeposito)); //marzo 2016

        //Todo Validacion

        boolean valido = true;


        if (!valido) {
            Utiles.generateAlertDialog("Mensaje", "No se pudo realizar esta transferencia por datos invalido.", (Activity) rootView.getContext());
            return;
        }

        TextView dialogCloseButton = (TextView) dialog.findViewById(R.id.tv_back);
        ImageView dialogCloseImage = (ImageView) dialog.findViewById(R.id.iv_back);

        dialogCloseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                dialog.dismiss();
            }
        });

        dialogCloseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                dialog.dismiss();
            }
        });

        TextView dialogButton = (TextView) dialog.findViewById(R.id.tv_next_transfer_dialog);

        //TODO 2016 - Se estan intercambiando las imagenes al ser pago TDC

        tv_textoSuperior.setText("Por favor confirme los datos del deposito que est\u00e1 por realizar:");
                    /*
                    tv_tituloProductoOrigen.setText("Desde se cuenta:");
                    tv_tituloProductoDestino.setText("A su tarjeta:");
                    */
        tv_tituloProductoOrigen.setText("A su cuenta:");

        dialogButton.setText("Depositar");

        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                    uploadCheck(sendQueue.poll());


                //dialog.dismiss();


            }
        });

        dialog.show();
    }

    private void uploadCheck(Cheque cheque){
        final Cheque check = cheque;
        final ProgressDialog loading = ProgressDialog.show(getContext(),"Subiendo cheque " + String.valueOf(check.getMonto()),"Por favor Espere...",false,false);
        JsonObjectRequest req = new JsonObjectRequest(WebConstants.UPLOAD_URL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                            if(!sendQueue.isEmpty()){
                                uploadCheck(sendQueue.poll());
                            }
                            Toast.makeText(getContext(), response.toString() , Toast.LENGTH_LONG).show();
                            loading.dismiss();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), error.getMessage().toString(), Toast.LENGTH_LONG).show();
                loading.dismiss();

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());

        //Adding request to the queue
        requestQueue.add(req);
    }

}