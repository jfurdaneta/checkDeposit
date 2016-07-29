package depchemobile.com.bod.checkdeposit.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import depchemobile.com.bod.checkdeposit.MockData;
import depchemobile.com.bod.checkdeposit.R;
import depchemobile.com.bod.checkdeposit.adapters.ListaChequeAdapter;
import depchemobile.com.bod.checkdeposit.entidades.Cheque;

public class ListaChequesActivity extends Activity {

    ArrayList<Cheque> listaChequeData;
    ListView list;
    ListaChequeAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v(this.getClass().getName(), "onCreate - " + "Iniciando");
        setContentView(R.layout.activity_lista_cheques);

        //Intent intent = getIntent();
        Log.v(this.getClass().getName(), "onCreate - " + "Obtieniendo intent");

        //Cheque chequeObject = intent.getExtras().getParcelable("cheque");

        MockData d = new MockData();
        listaChequeData = d.datos();

        Log.v(this.getClass().getName(), "onCreate - " + "Parseado chequeObject");

        if (listaChequeData == null)
            listaChequeData = new ArrayList<Cheque>();




        list = (ListView) findViewById(R.id.list);
        adapter = new ListaChequeAdapter(ListaChequesActivity.this, listaChequeData);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.v(getClass().getName(),"setOnItemClickListener - Seleccionado " +  String.valueOf(position)  );
                Toast.makeText(ListaChequesActivity.this, "Cheque tocado "  +  String.valueOf(position) , Toast.LENGTH_SHORT).show();

                Intent intent = new Intent();
                intent.setClass(getBaseContext() ,PrincipalActivity.class);
                Bundle mBundle = new Bundle();
                mBundle.putParcelable("cheque",listaChequeData.get(position));
                intent.putExtras(mBundle);
                startActivity(intent);
            }
        });


    }

}
