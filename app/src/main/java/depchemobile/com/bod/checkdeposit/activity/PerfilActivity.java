package depchemobile.com.bod.checkdeposit.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import depchemobile.com.bod.checkdeposit.R;
import depchemobile.com.bod.checkdeposit.fragmentactivity.PrincipalFragmentActivity;
import depchemobile.com.bod.checkdeposit.utils.Utiles;
import depchemobile.com.bod.checkdeposit.utils.BodConstants;
import depchemobile.com.bod.checkdeposit.web.WebConstants;
import depchemobile.com.bod.checkdeposit.utils.Singleton;
import depchemobile.com.bod.checkdeposit.utils.LoadingDialog;

import com.neopixl.pixlui.components.edittext.EditText;
import com.neopixl.pixlui.components.textview.TextView;

import java.util.ArrayList;

/**
 * Created by Rony Díaz
 */
public class PerfilActivity extends BaseActivity {

    //TODO Login Validar Contraseña

    private final String SHARED_PREFERENCES_KEY = "APP_BOD_data";
    private final String SHARED_PREFERENCES_KEY_HELP = "APP_BOD_help";

    private EditText etPass;

    private String username;
    private String pass;
    private ImageView ivNext;
    private boolean intentoFallido;
    private boolean registrarPerfil;


    private Activity activity;

    Bundle bundle;

    private boolean appAction = false;

    private final Activity _activity = this;

    int ayuda;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inicio_perfil);

        //singleton = ((Singleton) getApplicationContext());
        //resetTimeOut();

        //setSingleton(((Singleton) getApplicationContext()));
        //getSingleton().resetTimeTask_main(this);

        registrarPerfil = false;

        etPass = (EditText) findViewById(R.id.et_pass_field);

        childList = new ArrayList();
        childList.add(etPass);

        bundle = getIntent().getExtras();

        username = bundle.getString("username");
        //usuario = (Usuario) bundle.getSerializable("usuario");

        activity = this;

        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

/*                if (getSingleton().isMenuOpen()) {
                    return;
                }
*/
                pass = etPass.getText().toString().trim();
                if (pass != null && !pass.equals("")) {

                    // getSingleton().resetTimeTask_main(parentActivity);

                    if (!appAction) {
                        appAction = true;
                       //Aquí si va algo asíncrono contra el server
                        // callService();
                        Intent intent = new Intent();
                        intent.setClass(PerfilActivity.this,PrincipalFragmentActivity.class);
                        intent.putExtra("username",username);

                        startActivity(intent);
                        finish();
                    }

                } else {
                    Utiles.generateAlertDialog(BodConstants.tituloMensaje, "Estimado cliente, por favor introduzca su contrase\u00f1a.", parentActivity);
                }
            }
        };

        TextView tvnext = (TextView) findViewById(R.id.tv_next);
        ivNext = (ImageView) findViewById(R.id.iv_next);
        tvnext.setOnClickListener(clickListener);
        ivNext.setOnClickListener(clickListener);





        final ImageView ivNextf = ivNext;

        EditText.OnEditorActionListener editorListener = new android.widget.TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(android.widget.TextView textView, int i, KeyEvent keyEvent) {

                if (!textView.getText().equals("")) {

                    int imageResource = getResources().getIdentifier("@drawable/continuar_fondoverde", null, _activity.getPackageName());
                    Drawable res = getResources().getDrawable(imageResource);
                    ivNextf.setImageDrawable(res);
                }

                return false;
            }
        };

        etPass.setOnEditorActionListener(editorListener);

        etPass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (!s.toString().equals("")) {

                    int imageResource = getResources().getIdentifier("@drawable/continuar_fondoverde", null, _activity.getPackageName());
                    Drawable res = getResources().getDrawable(imageResource);
                    ivNextf.setImageDrawable(res);

                    //ivNextf.setBackgroundResource(R.drawable.continuar_fondoverde);
                } else {
                    int imageResource = getResources().getIdentifier("@drawable/continuar_fondonegro", null, _activity.getPackageName());
                    Drawable res = getResources().getDrawable(imageResource);
                    ivNextf.setImageDrawable(res);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        parentActivity = this;
        setDownUpMenu();

        //etPass.setText("Julio16.");
        //etPass.setText("Prueba13*");

        //etPass.setText("Prueba11*");
        //etPass.setText("Prueba3$");
        //etPass.setText("Prueba6$");

        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(SHARED_PREFERENCES_KEY, Context.MODE_PRIVATE);
        ayuda = sharedPref.getInt(SHARED_PREFERENCES_KEY_HELP, 99);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.menu_login_v3, menu);
        return true;
    }

    @Override
    public void onBackPressed() {

        getSingleton().resetTimeTask_main(this);
        pass = "b0d1234";
        intentoFallido = true;
        callService();
        return;
    }


    private void callService() {

        etPass.setText("");

        if (Utiles.isConnected(this)) {

            // phoneConfig = new PhoneConfig(parentActivity);

            HttpAsyncTask task = new HttpAsyncTask();
            task.execute(WebConstants.GATEWAY_URL);
        } else {

            appAction = false;

            Utiles.generateAlertDialog(BodConstants.tituloMensaje, "Por favor verifique su conexi\u00f3n e intente de nuevo.", parentActivity);
        }
    }

    private class HttpAsyncTask extends AsyncTask<String, Void, String> {

        LoadingDialog ldialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //progressDialog.show();
            /*
            LayoutInflater layoutInflater = (LayoutInflater) getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);
            progressView = layoutInflater.inflate(R.layout.custom_progres_dialog_layout_2, null);
            progressGroup.addView(progressView);
            */
            ldialog = new LoadingDialog(_activity);
            ldialog.show();
        }

        @Override
        protected String doInBackground(String... urls) {


            return "";
        }

        @Override
        protected void onPostExecute(String resultCode) {


        }

        public Bitmap getResizedBitmap(Bitmap bm, int newHeight, int newWidth) {
            int width = bm.getWidth();
            int height = bm.getHeight();
            float scaleWidth = ((float) newWidth) / width;
            float scaleHeight = ((float) newHeight) / height;
            // create a matrix for the manipulation
            Matrix matrix = new Matrix();
            // resize the bit map
            matrix.postScale(scaleWidth, scaleHeight);
            // recreate the new Bitmap
            Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);
            return resizedBitmap;
        }


    }
}
