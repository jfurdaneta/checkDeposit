package depchemobile.com.bod.checkdeposit.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;


import java.util.ArrayList;

import depchemobile.com.bod.checkdeposit.data.CheckDepositDbHelper;
import depchemobile.com.bod.checkdeposit.data.MockData;
import depchemobile.com.bod.checkdeposit.R;
import depchemobile.com.bod.checkdeposit.adapters.ListaChequeAdapter;
import depchemobile.com.bod.checkdeposit.entidades.Cheque;
import depchemobile.com.bod.checkdeposit.utils.Utils;

public class ListaChequesActivity extends Activity {


    ListView list;
    ListaChequeAdapter adapter;
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
        mCheckDepositDbHelper = new CheckDepositDbHelper(this.getBaseContext());
        // mCheckDepositDbHelper.datos();
        Cursor c = mCheckDepositDbHelper.getCheques();
        adapter = new ListaChequeAdapter(ListaChequesActivity.this, c);
        Log.v(this.getClass().getName(), "onCreate - " + "adapter Instanciado");
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.v(getClass().getName(), "setOnItemClickListener - Seleccionado " + String.valueOf(position));
                Toast.makeText(ListaChequesActivity.this, "Cheque tocado " + String.valueOf(position), Toast.LENGTH_SHORT).show();

                Intent intent = new Intent();
                intent.setClass(getBaseContext(), PrincipalActivity.class);
                intent.putExtra("chequeID", adapter.getItemId(position));
                startActivity(intent);
            }
        });


        cargarCheques();



    }


    private void cargarCheques() {
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
                adapter.swapCursor(cursor);
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
