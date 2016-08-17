package depchemobile.com.bod.checkdeposit.activity;

import android.app.Activity;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;


import com.neopixl.pixlui.components.checkbox.CheckBox;

import java.util.ArrayList;

import depchemobile.com.bod.checkdeposit.adapters.ListaChequeArraylistAdapter;
import depchemobile.com.bod.checkdeposit.data.CheckDepositDbHelper;
import depchemobile.com.bod.checkdeposit.data.MockData;
import depchemobile.com.bod.checkdeposit.R;
import depchemobile.com.bod.checkdeposit.adapters.ListaChequeAdapter;
import depchemobile.com.bod.checkdeposit.entidades.Cheque;
import depchemobile.com.bod.checkdeposit.utils.Convertidor;
import depchemobile.com.bod.checkdeposit.utils.Utils;

public class ListaChequesActivity extends Activity {


    ListView list;
    ListaChequeAdapter adapter2;
    ArrayList<Cheque>listaCheques = new ArrayList<Cheque>();
    ListaChequeArraylistAdapter adapter;
    private CheckDepositDbHelper mCheckDepositDbHelper;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v(this.getClass().getName(), "onCreate - " + "Iniciando");
        setContentView(R.layout.activity_lista_cheques);


         Intent intent = getIntent();
        Log.v(this.getClass().getName(), "onCreate - " + "Obtieniendo intent");

        //Cheque chequeObject = intent.getExtras().getParcelable("cheque");
        list = (ListView) findViewById(R.id.list);
        list.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        //Se registra menu contextual
        registerForContextMenu(list);

        mCheckDepositDbHelper = new CheckDepositDbHelper(this.getBaseContext());
        // mCheckDepositDbHelper.datos();
        //Cursor c = mCheckDepositDbHelper.getCheques();
        //adapter2 = new ListaChequeAdapter(ListaChequesActivity.this, null);


        Log.v(this.getClass().getName(), "onCreate - " + "adapter Instanciado");
      //  list.setAdapter(adapter2);

       list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.v(getClass().getName(), "setOnItemClickListener - Seleccionado " + String.valueOf(position));
                //Toast.makeText(ListaChequesActivity.this, "Cheque tocado " + String.valueOf(position), Toast.LENGTH_SHORT).show();
                CheckBox cb = (CheckBox) view.findViewById(R.id.checkboxSelected);
                cb.setChecked(true);
                // ir_a_EditarCheque(position);
                //ir_a_EditarCheque(position);
            }
        });



        cargarCheques();

        Button btnAceptar = (Button) findViewById(R.id.btnAceptar);

        btnAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int cont = 0;

                for (Cheque bean : listaCheques) {
                    if(bean.isSeleccionado())
                        cont++;
                }
                Toast.makeText(ListaChequesActivity.this, "Cheques seleccionados " + String.valueOf(cont), Toast.LENGTH_SHORT).show();


            }
        });
    }

    private void ir_a_EditarCheque(int position)
    {
        Intent intent = new Intent();
        intent.setClass(getBaseContext(), PrincipalActivity.class);
        intent.putExtra("chequeID", adapter.getItemId(position));
        //intent.putExtra("chequeID", adapter2.getItemId(position));
        startActivity(intent);

    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        MenuInflater inflater = getMenuInflater();
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
                    listaCheques.add(Convertidor.llenarCheque(cursor));
                }
                adapter = new ListaChequeArraylistAdapter(ListaChequesActivity.this, listaCheques);
                list.setAdapter(adapter);



            } else {
                // Mostrar empty state
            }
        }
    }


    @Override
    public void onBackPressed() {
        //Display alert message when back button has been pressed
        Utils.backButtonHandler(ListaChequesActivity.this);
        return;
    }




}
