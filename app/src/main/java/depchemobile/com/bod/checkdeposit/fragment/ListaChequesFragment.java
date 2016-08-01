package depchemobile.com.bod.checkdeposit.fragment;

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
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;


import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;

import depchemobile.com.bod.checkdeposit.R;
import depchemobile.com.bod.checkdeposit.activity.PrincipalActivity;
import depchemobile.com.bod.checkdeposit.adapters.ListaChequeAdapter;
import depchemobile.com.bod.checkdeposit.adapters.ListaChequeArraylistAdapter;
import depchemobile.com.bod.checkdeposit.data.CheckDepositDbHelper;
import depchemobile.com.bod.checkdeposit.entidades.Cheque;
import depchemobile.com.bod.checkdeposit.fragmentactivity.PrincipalFragmentActivity;
import depchemobile.com.bod.checkdeposit.utils.Convertidor;
import depchemobile.com.bod.checkdeposit.utils.Utils;


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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.activity_lista_cheques,container,false);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        parentActivity = (PrincipalFragmentActivity) getActivity();



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

        btnAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int cont = 0;

                for (Cheque bean : listaCheques) {
                    if(bean.isSeleccionado())
                        cont++;
                }
                Toast.makeText(getActivity(), "Cheques seleccionados " + String.valueOf(cont), Toast.LENGTH_SHORT).show();


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
                    listaCheques.add(Convertidor.llenarCheque(cursor));
                }
                adapter = new ListaChequeArraylistAdapter(getActivity(), listaCheques);
                list.setAdapter(adapter);



            } else {
                // Mostrar empty state
            }
        }
    }


}
