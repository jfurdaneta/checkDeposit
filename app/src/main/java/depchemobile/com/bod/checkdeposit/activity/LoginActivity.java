package depchemobile.com.bod.checkdeposit.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v8.renderscript.RenderScript;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import depchemobile.com.bod.checkdeposit.R;
import depchemobile.com.bod.checkdeposit.web.WebConstants;
import depchemobile.com.bod.checkdeposit.utils.Utiles;
import depchemobile.com.bod.checkdeposit.utils.BodConstants;
import depchemobile.com.bod.checkdeposit.utils.LoadingDialog;


import com.neopixl.pixlui.components.edittext.EditText;
import com.neopixl.pixlui.components.textview.TextView;

import java.util.ArrayList;

public class LoginActivity extends BaseActivity {

    //TODO Login - inicio

    //public Usuario usuario = new Usuario();
    private String username;

    private final Activity _activity = this;
    private Handler mHandler = new Handler();

    private ImageView ivNext;
    private TextView tvNext;
    private EditText etUsername;
    private TextView tvLoginText;
    private ImageView initLogo;
    private LinearLayout transparentLayer;



    private boolean appAction = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_v3_02c);

        parentActivity = this;
        //setSingleton(((Singleton) getApplicationContext()));


        //getSingleton().stopTimeOut();



        //getSingleton().cleanSession();

        AlphaAnimation alpha = new AlphaAnimation(0, 0);
        alpha.setDuration(0);
        alpha.setFillAfter(true);

        ivNext = (ImageView) findViewById(R.id.buttonImage);
        etUsername = (EditText) findViewById(R.id.et_username);
        tvNext = (TextView) findViewById(R.id.tv_next);
        tvLoginText = (TextView) findViewById(R.id.textViewLogin);
        mainLayout = (RelativeLayout) findViewById(R.id.inicioMainRelativeLayout);
        initLogo = (ImageView) findViewById(R.id.iv_init_logo);
        transparentLayer = (LinearLayout) findViewById(R.id.ll_transparent_layer);

        ivNext.animate().alpha(0).setDuration(0);
        etUsername.animate().alpha(0).setDuration(0);
        tvNext.animate().alpha(0).setDuration(0);
        transparentLayer.animate().alpha(0).setDuration(0);

        ivNext.setVisibility(View.GONE);
        etUsername.setVisibility(View.GONE);
        tvNext.setVisibility(View.GONE);
        transparentLayer.setVisibility(View.GONE);
        tvLoginText.setVisibility(View.GONE);

        setActivityActive(this);

        childList = new ArrayList();
        childList.add(etUsername);

        final View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                /*if (getSingleton().isMenuOpen()) {
                    return;
                }
                */

                /*
                Intent mainIntent = new Intent().setClass(LoginActivity.this, AyudaFragmentActivity.class);
                startActivity(mainIntent);
                */

                username = etUsername.getText().toString().trim();
                etUsername.setText(username);
                if (username != null && !username.equals("")) {
                    if (username.length() < 33 && username.length() > 5) {
                        Utiles.hideSoftKeyboard(parentActivity);
                        if (!appAction) {
                            appAction = true;
                           Intent intent = new Intent();
                            intent.setClass(LoginActivity.this,PerfilActivity.class);
                            intent.putExtra("username",username);
                            startActivity(intent);
                            finish();

                        }
                    } else {
                        Utiles.generateAlertDialog(BodConstants.tituloMensaje, "Por favor el nombre de usuario no debe ser menor a 6, ni mayor a 32 caracteres", parentActivity);
                    }
                } else {
                    Utiles.generateAlertDialog(BodConstants.tituloMensaje, "Por favor indique su nombre de usuario.", parentActivity);
                }


            }
        };

        tvNext.setOnClickListener(clickListener);
        ivNext.setOnClickListener(clickListener);

        final ImageView ivNextf = ivNext;

        EditText.OnEditorActionListener editorListener = new android.widget.TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(android.widget.TextView textView, int i, KeyEvent keyEvent) {

                if (!textView.getText().equals("")) {

                    if (textView.getText().length() < 33 && textView.getText().length() > 5) {
                        int imageResource = getResources().getIdentifier("@drawable/continuar_fondoverde", null, _activity.getPackageName());
                        Drawable res = getResources().getDrawable(imageResource);
                        ivNextf.setImageDrawable(res);
                    } else {
                        int imageResource = getResources().getIdentifier("@drawable/continuar_fondonegro", null, _activity.getPackageName());
                        Drawable res = getResources().getDrawable(imageResource);
                        ivNextf.setImageDrawable(res);
                    }


                }

                return false;
            }
        };

        etUsername.setOnEditorActionListener(editorListener);

        etUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (!s.toString().equals("")) {

                    if (s.toString().length() < 33 && s.toString().length() > 5) {
                        int imageResource = getResources().getIdentifier("@drawable/continuar_fondoverde", null, _activity.getPackageName());
                        Drawable res = getResources().getDrawable(imageResource);
                        ivNextf.setImageDrawable(res);
                    } else {
                        int imageResource = getResources().getIdentifier("@drawable/continuar_fondonegro", null, _activity.getPackageName());
                        Drawable res = getResources().getDrawable(imageResource);
                        ivNextf.setImageDrawable(res);
                    }

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


        int delay = 2000;

        if (getIntent().getExtras() != null)
            delay = 50;

        mHandler.postDelayed(new Runnable() {
            public void run() {
                parentActivity.runOnUiThread(new Runnable() {
                    @Override
                    public final void run() {
                        showViews();
                    }
                });
            }
        }, delay);


        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            if (bundle.containsKey("mensaje")) {
                //Utiles.generateAlertDialog(bundle.getString("titulo"), bundle.getString("mensaje"), parentActivity);
            }
            if (bundle.containsKey("mensajeError")) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Utiles.generateAlertDialog_gotoLogin(BodConstants.tituloMensaje, "Ah ocurrido un error intentelo mas tarde.", parentActivity);
                    }
                }, 2000);
            }
        }

        //cedula: 17097698
        //etUsername.setText("ejbeltm");
        //etUsername.setText("arrieta");

        //etUsername.setText("rodolforojas");
        //etUsername.setText("noxell");
        //etUsername.setText("mvillalobos");

    }


    public void showViews() {

        initLogo.setVisibility(View.GONE);

        RenderScript rs = RenderScript.create(this);
        //Utiles.blur(mainLayout, rs, mainLayout, this, 6f);

        int imageResource = getResources().getIdentifier("@drawable/fondo_inicio", null, _activity.getPackageName());
        Drawable res = getResources().getDrawable(imageResource);

        int sdk = android.os.Build.VERSION.SDK_INT;
        if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
            mainLayout.setBackgroundDrawable(res);
        } else {
            mainLayout.setBackground(res);
        }

        initLogo.setVisibility(View.VISIBLE);

        ivNext.setVisibility(View.VISIBLE);
        etUsername.setVisibility(View.VISIBLE);
        tvNext.setVisibility(View.VISIBLE);
        tvLoginText.setVisibility(View.VISIBLE);
        transparentLayer.setVisibility(View.VISIBLE);

        ivNext.animate().alpha(1).setDuration(500);
        etUsername.animate().alpha(1).setDuration(500);
        tvNext.animate().alpha(1).setDuration(500);
        tvLoginText.animate().alpha(1).setDuration(500);
        transparentLayer.animate().alpha(1).setDuration(500);

        initLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //String mensaje = "";
                //Utiles.generateAlertDialog(BodConstants.tituloMensaje, mensaje, parentActivity);
            }
        });

        setDownUpMenu();
    }



    private class HttpAsyncTask extends AsyncTask<String, Void, String> {

        //Usuario gUsuario = new Usuario();
        LoadingDialog ldialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            /*
            LayoutInflater layoutInflater = (LayoutInflater) getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);
            progressView = layoutInflater.inflate(R.layout.custom_progres_dialog_layout_2, null);
            getWindow().setLayout(WindowManager.LayoutParams.FILL_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
            getWindow().setBackgroundDrawable(new ColorDrawable(0));
            progressGroup.addView(progressView);*/
            ldialog = new LoadingDialog(_activity);
            ldialog.show();
        }

        @Override
        protected String doInBackground(String... urls) {


            return "";
        }

        @Override
        protected void onPostExecute(String resultCode) {

          /*  ldialog.dismiss();

            Intent mainIntent = null;
            Bundle bundle = new Bundle();
            mainIntent = new Intent().setClass(LoginActivity.this, LoginEquipoFrecuenteActivity.class);






                    getSingleton().initializeTimeTask_main(_activity);

                    mainIntent.putExtras(bundle);
                    startActivity(mainIntent);
                    finish();

            //TODO fin test

            appAction = false;
        }
        */
    }

    private class HttpAsyncTask_DetectID extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            LayoutInflater layoutInflater = (LayoutInflater) getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);
            progressView = layoutInflater.inflate(R.layout.custom_progres_dialog_layout_2, null);
            progressGroup.addView(progressView);

        }

        @Override
        protected String doInBackground(String... urls) {

            return WebConstants.SERVICE_ERROR;
        }

        @Override
        protected void onPostExecute(String resultCode) {


            ((RelativeLayout) progressView.getParent()).removeView(progressView);
        }
    }


    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.menu_login_v3, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        super.onBackPressed();
        finish();
    }


}