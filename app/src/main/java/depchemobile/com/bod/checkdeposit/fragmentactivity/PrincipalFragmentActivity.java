package depchemobile.com.bod.checkdeposit.fragmentactivity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v8.renderscript.RenderScript;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;


import depchemobile.com.bod.checkdeposit.R;
import depchemobile.com.bod.checkdeposit.activity.LoginActivity;
import depchemobile.com.bod.checkdeposit.activity.PrincipalActivityFragment;
import depchemobile.com.bod.checkdeposit.fragment.PrincipalFragment;
import depchemobile.com.bod.checkdeposit.model.Menu;
import depchemobile.com.bod.checkdeposit.utils.Utiles;
import depchemobile.com.bod.checkdeposit.utils.BodConstants;
import depchemobile.com.bod.checkdeposit.web.WebConstants;
import depchemobile.com.bod.checkdeposit.utils.Singleton;
import depchemobile.com.bod.checkdeposit.utils.LoadingDialog;
import depchemobile.com.bod.checkdeposit.utils.CustomScrollView;
import com.neopixl.pixlui.components.edittext.EditText;
import com.neopixl.pixlui.components.textview.TextView;
import com.special.ResideMenu.ResideMenu;
import com.special.ResideMenu.ResideMenuItem;

import java.util.ArrayList;

/**
 * Created by Rony Díaz
 */
public class PrincipalFragmentActivity extends FragmentBaseActivity implements View.OnClickListener {


    //TODO Posicion Consolida

    private static final int NUM_PAGES = 5;

    private ResideMenu resideMenu;
    private PrincipalFragmentActivity mContext;
    private int resideMenuActive;
    private TextView titleView;
    private boolean first;
    private boolean block;

    private final Activity _currentActivity = this;


    private String partyId = "";
    public Bundle bundle;
    private String username;
    private int m_base0 = 0;
    private int m_base1 = 1;
    private int m_base3 = 3;


    private ArrayList listaCuentas;
    private ArrayList listaTdc;
    private ArrayList listaCreditos;
    private ArrayList listaAsociados;
    private ArrayList listaAsociadosTarjetas;

    private ResideMenuItem panel;
    private ResideMenuItem cerrar;

    private boolean isConsultaCuentaVisible = false;
    private boolean isConsultaTarjetaVisible = false;
    private boolean isConsultaCreditosVisible = false;
    private boolean isTransferenciaVisible = false;
    private boolean isPagosVisible = false;
    private boolean isDirectorioGlobalVisible = false;

    private boolean isConsultaCuentaEneable = true;
    private boolean isConsultaTarjetaEneable = true;
    private boolean isConsultaCreditosEneable = true;
    private boolean isTransferenciaEneable = true;
    private boolean isPagosEneable = true;
    private boolean isDirectorioGlobalEneable = true;

    private Boolean isCuentasSubmenu;
    private Boolean isPagosSubmenu;
    private Boolean isIdiomasSubmenu;
    private Context context;
    private RenderScript rs;
    private RelativeLayout blurredLayout;
    private Boolean isMenuOpen;
    private LinearLayout userProfile;

    private LinearLayout profileListLAyout;
    private boolean isOpenList;
    private ListView lvProfileList;

    public CustomScrollView mainScroll;
    public LinearLayout topBar;

    private float initY;
    private ImageView headerIcon;
    //Variable para el header estatico de los detalles
    private LinearLayout headerDetalles;
    private float offset;
    private ArrayList<Menu> listadoMenu;
    private boolean appAction = false;
    private int tipo_operacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

//        setSingleton(((Singleton) this.getApplicationContext()));
        //resetTimeOut();

        bundle = getIntent().getExtras();
       // username = bundle.getString("username");

        context = this;
        rs = RenderScript.create(context);
        isMenuOpen = false;
        block = false;

        resideMenuActive = 1;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bod_menu_principal_v2);

        parentActivity = this;

        //TODO importante
        setIsDirectory(false);

        //Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread paramThread, Throwable paramThrowable) {

                // Utiles.generateAlertDialog_gotoLogin(BodConstants.tituloMensaje, "Ah ocurrido un error intentelo mas tarde.", parentActivity);

                Log.e("Tag", "ERROR-APP" + " | " + paramThrowable.getMessage().toString(), paramThrowable);
                getSingleton().gotoLogin(parentActivity);
                System.exit(2);
            }
        });

        /*
        if (singleton.isLoadMenu()) {
            singleton.setLoadMenu(false);
        }
        */

        //TODO

        loadMENU();

        Log.v(this.getClass().getName(),"onCreate - Pase loadMENU()");


        mContext = this;
       // getSingleton().resetTimeTask_main(this);

        changeFragment(new PrincipalFragment());
        //changeFragment(new ConfiguracionExcepcionLimitesFragment()); //TEST

        isCuentasSubmenu = false;
        isPagosSubmenu = false;
        isIdiomasSubmenu = false;

        titleView = (TextView) findViewById(R.id.tvTitleView);

        //setDownUpMenu();

        userProfile = (LinearLayout) findViewById(R.id.ll_user_profile);
        TextView textUserName = (TextView) findViewById(R.id.tv_profile_user_name);
        textUserName.setText("Luis Fernando Llerena");

        profileListLAyout = (LinearLayout) findViewById(R.id.ll_profile_list_layout);
        profileListLAyout.animate().scaleY(0).alpha(0).setDuration(0).start();
        profileListLAyout.setVisibility(View.GONE);

        com.pkmmte.view.CircularImageView avatar = (com.pkmmte.view.CircularImageView) findViewById(R.id.iv_profile_user_avatar_bar);
        avatar.setImageDrawable(ContextCompat.getDrawable(this.context,R.drawable.cuadrado_blanco));


        topBar = (LinearLayout) findViewById(R.id.ll_top_bar);
        mainScroll = (CustomScrollView) findViewById(R.id.sv_main_scroll);
        headerIcon = (ImageView) findViewById(R.id.iv_header_icon);


    }


    public void setSubMenuGone() {

        isPagosEneable = true;
        isDirectorioGlobalEneable = true;
    }

    @Override
    public void onClick(View view) {

        if (view == panel) { //TODO panel financiero

            changeFragment(new PrincipalFragment());

            resideMenu.closeMenu();
        } else if (view == cerrar) {

            showDialogConfirmClose();
        }

    }



    public void loadMENU() {
        Log.v(this.getClass().getName(),"loadMENU - Entrando");
        resideMenu = new ResideMenu(this);

        Log.v(this.getClass().getName(),"loadMENU - Pasé ResideMenu(this)");
        resideMenu.setBackground(R.drawable.fondo_menu);
        resideMenu.attachToActivity(this);
        resideMenu.setMenuListener(menuListener);
        resideMenu.setScaleValue(0.6f); //valid scale factor is between 0.0f and 1.0f. leftmenu'width is 150dip.
        resideMenu.setShadowVisible(false);


        panel   = new ResideMenuItem(this, R.drawable.panel_financiero_white, "Panel principal");
        cerrar  = new ResideMenuItem(this, R.drawable.cerrar_sesion_white,  "Cerrar sesion");


        panel.setOnClickListener(this);
        cerrar.setOnClickListener(this);
        resideMenu.addMenuItem(panel,  ResideMenu.DIRECTION_RIGHT); // or  ResideMenu.DIRECTION_RIGHT
        resideMenu.addMenuItem(cerrar,  ResideMenu.DIRECTION_RIGHT); // or  ResideMenu.DIRECTION_RIGHT


    }



    public void openRightMenu(){
        ImageView buttonTool = (ImageView) findViewById(R.id.open_drawer_right);
        buttonTool.setImageDrawable(getResources().getDrawable(R.drawable.menu_der));
        findViewById(R.id.open_drawer_right).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resideMenu.openMenu(ResideMenu.DIRECTION_RIGHT);
            }
        });
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        return resideMenu.dispatchTouchEvent(ev);
    }

    public void showDialogConfirmClose() {

        final Dialog dialog = new Dialog(parentActivity, R.style.MyThemeDialog);
        //final Dialog dialog = new Dialog(parentActivity,android.R.style.Theme_Material_NoActionBar_Fullscreen);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        // if(v == loQueTengoGraphicLayout || v == loQueDeboGraphicLayout)
        dialog.setContentView(R.layout.dialog_cerrar_sesion);
        // else
        //     dialog.setContentView(R.layout.promedio_graph_layout);

        RenderScript rs = RenderScript.create(this);
        LinearLayout bgLayout = (LinearLayout) dialog.findViewById(R.id.ll_image_layout);
        Utiles.blur(this.getWindow().getDecorView().findViewById(android.R.id.content), rs, bgLayout, this, 4f);

        //TextView titleTitle = (TextView) dialog.findViewById(R.id.titulo_grafico);
        ImageView image = (ImageView) dialog.findViewById(R.id.icon_cerrar);
        //TextView dialogButton = (TextView) dialog.findViewById(R.id.tv_back_graphic);

        ImageView image_next = (ImageView) dialog.findViewById(R.id.iv_next);
        TextView text_next = (TextView) dialog.findViewById(R.id.tv_next);

        ImageView image_back = (ImageView) dialog.findViewById(R.id.iv_back);
        TextView text_back = (TextView) dialog.findViewById(R.id.tv_back);

        image_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
            }
        });

        text_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
            }
        });

        image_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                resideMenu.closeMenu();
                dialog.dismiss();
                gotoLoginActivity();

            }
        });

        text_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                resideMenu.closeMenu();
                dialog.dismiss();
                gotoLoginActivity();
            }
        });

        dialog.show();
    }


    public void gotoLoginActivity() {

        Bundle bundle = new Bundle();
        bundle.putBoolean("argument", true);
        Intent mainIntent = new Intent().setClass(
                PrincipalFragmentActivity.this, LoginActivity.class);
        mainIntent.putExtras(bundle);
        startActivity(mainIntent);
        finish();
    }

    private void callService_closeApp() {
        /*
        if (Utiles.isConnected(this)) {
            HttpAsyncTask_closeApp task = new HttpAsyncTask_closeApp();
            task.execute(WebConstants.GATEWAY_URL);
        } else {
            Utiles.generateAlertDialog(BodConstants.tituloMensaje, "Por favor verifique su conexi\u00f3n e intente de nuevo.", parentActivity);
        }*/
    }

    private class HttpAsyncTask_closeApp extends AsyncTask<String, Void, String> {

        LoadingDialog ldialog;

        public HttpAsyncTask_closeApp(Context context) {
            ldialog = new LoadingDialog(context);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            ldialog.show();

        }

        @Override
        protected String doInBackground(String... urls) {

            return "";
        }

        @Override
        protected void onPostExecute(String resultCode) {
            try {
                //((RelativeLayout) progressView.getParent()).removeView(progressView);
                ldialog.dismiss();
            } catch (Exception e) {
                //
            }

            if (resultCode.equals(WebConstants.SERVICE_CORRECT)) {

                getSingleton().gotoLogin(_currentActivity);

            } else {

                getSingleton().gotoLogin(_currentActivity);


            }

        }
    }

    private ResideMenu.OnMenuListener menuListener = new ResideMenu.OnMenuListener() {

        @Override
        public void openMenu() {
            if (!isMenuOpen) {
                Utiles.hideSoftKeyboard(parentActivity);
                // Toast.makeText(mContext, "Menu is opened!", Toast.LENGTH_SHORT).show();
                LinearLayout mainLayout = (LinearLayout) findViewById(R.id.main_view);
                blurredLayout = (RelativeLayout) findViewById(R.id.blurred_view);
                hideProfile();
                Utiles.blur(mainLayout, rs, blurredLayout, context, 4f);
                isMenuOpen = true;
                block = false;
            }
        }

        @Override
        public void closeMenu() {
            if (isMenuOpen) {
                showProfile();
                Utiles.unblur(blurredLayout);
                isMenuOpen = false;
            }
            //           Toast.makeText(mContext, "Menu is closed!", Toast.LENGTH_SHORT).show();
        }
    };

    public void changeFragment(Fragment targetFragment) {
        //reiniciar timeout
        //singleton.initializeTimeTask_main(this);
       // getSingleton().resetTimeTask_main(this);

        resideMenu.clearIgnoredViewList();
        openRightMenu();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_fragment, targetFragment, "fragment")
                .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE).addToBackStack(null)
                .commit();

        if (mainScroll != null)
            mainScroll.scrollTo(0, 0);
        if (profileListLAyout != null)
            profileListLAyout.animate().scaleY(0).alpha(0).setDuration(10).start();


    }

    public void setTile(String title) {
        titleView.setText(title);
    }

    public ResideMenu getResideMenu() {
        return resideMenu;
    }

    public void hideProfile() {
        userProfile.setVisibility(View.INVISIBLE);
    }

    public void showProfile() {
        userProfile.setVisibility(View.VISIBLE);
    }

    public void setHeaderIcon(Drawable drawable) {

        headerIcon.setImageDrawable(drawable);

    }

    public void setHeaderDetalles(LinearLayout headerDetalles, float offset) {
        this.headerDetalles = headerDetalles;
        this.offset = offset;
    }

    //Asignar los menus ...
    public ResideMenuItem configResideMenuItem(int icon, String name) {
        return new ResideMenuItem(this, icon, name);
    }

    private class HttpAsyncTask_Menu extends AsyncTask<String, Void, String> {
        //Cliente clienteResponse;
        public Context context;
        View view;


        public HttpAsyncTask_Menu(View view) {
            this.view = view;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            block = true;
        }

        @Override
        protected String doInBackground(String... urls) {

            runOnUiThread(new Runnable() {
                @Override
                public void run() {


                }
            });

            return "";
        }

        @Override
        protected void onPostExecute(String resultCode) {

            block = false;
            resideMenu.closeMenu();

            Fragment fragment;
            Bundle bundle = new Bundle();

            if (view == panel) {
                bundle.putInt(BodConstants.bundle_cuenta, BodConstants.TIPO_CREDITO);
                fragment = new PrincipalActivityFragment();
                fragment.setArguments(bundle);
                changeFragment(fragment);
            } else if (view == cerrar) {
                showDialogConfirmClose();
            }

        }
    }

    private void callService_changePerfil() {
        if (Utiles.isConnected(this)) {
            HttpAsyncTask task = new HttpAsyncTask(this);
            task.context = this;
            task.execute(WebConstants.GATEWAY_URL);
        } else {
            Utiles.generateAlertDialog(BodConstants.tituloMensaje, "Por favor verifique su conexi\u00f3n e intente de nuevo.", parentActivity);
        }
    }

    private class HttpAsyncTask extends AsyncTask<String, Void, String> {
        //Cliente clienteResponse;
        public Context context;
        LoadingDialog ldialog;

        public HttpAsyncTask(Context context) {
            ldialog = new LoadingDialog(context);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            /*
            LayoutInflater layoutInflater = (LayoutInflater) getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);
            progressView = layoutInflater.inflate(R.layout.custom_progres_dialog_layout_2, null);
            progressGroup.addView(progressView);
            */
            ldialog.show();
        }

        @Override
        protected String doInBackground(String... urls) {

            String status = WebConstants.SERVICE_ERROR;


            return status;
        }


        @Override
        protected void onPostExecute(String resultCode) {

            try {
                //((RelativeLayout) progressView.getParent()).removeView(progressView);
                ldialog.dismiss();
            } catch (Exception e) {

            }


        }


        private void callService_PanelFinanciero() {

        }

        private class HttpAsyncTask_PanelFinanciero extends AsyncTask<String, Void, String> {


            LoadingDialog ldialog;

            public HttpAsyncTask_PanelFinanciero(Context context) {
                ldialog = new LoadingDialog(context);
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                ldialog.show();
            }

            @Override
            protected String doInBackground(String... urls) {

                return "";
            }

            @Override
            protected void onPostExecute(String resultCode) {
                try {
                    //((RelativeLayout) progressView.getParent()).removeView(progressView);
                    ldialog.dismiss();
                } catch (Exception e) {

                }


            }

            public void __clean_singleton() {


            }


        }
    }
}