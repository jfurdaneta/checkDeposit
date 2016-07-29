package depchemobile.com.bod.checkdeposit.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v8.renderscript.RenderScript;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import depchemobile.com.bod.checkdeposit.R;
import depchemobile.com.bod.checkdeposit.utils.Singleton;


import depchemobile.com.bod.checkdeposit.utils.Utiles;

import com.neopixl.pixlui.components.textview.TextView;

import java.util.ArrayList;

/**
 * Created by Rony Díaz
 */
public class BaseActivity extends Activity {

    protected BaseActivity parentActivity;

    protected ArrayList<ImageButton> buttonMenuList;
    protected ImageButton button0;
    protected ImageButton button1;
    protected ImageButton button2;
    protected ImageButton button3;
    protected ImageButton button4;
    protected ImageButton button5;
    protected Boolean menuOut;
    protected int buttonPosition;
    protected Animation leftSlide;
    protected int position;
    protected Button btn;
    protected Animation fadeIn;
    protected boolean dontUseThisAgain;
    protected View childMenuView;
    protected View progressView;
    protected ViewGroup progressGroup;

    public RelativeLayout mainLayout;

    public ArrayList childList = new ArrayList();

    private Activity activityActive;

    private Singleton singleton = null;

    //public LoginEquipoFrecuenteActivity loginPreguntaActivity;

    //--------------------------------------------------------

//    private Singleton singleton = null;

    public void gotoLoginActivity() {

        getSingleton().resetTimeTask_main(this);

        Bundle bundle = new Bundle();
        bundle.putBoolean("argument", true);
        Intent intent = new Intent(this, LoginActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }

    public Runnable runTimeOutAlert = new Runnable() {
        @Override
        public void run() {
            try {
                showDialog_TimeOut();
            } catch (Exception e) {
//
            }
        }
    };

    public Runnable runCloseAPP = new Runnable() {
        @Override
        public void run() {
            try {
                finish();
            } catch (Exception e) {
                //
            }
        }
    };

    public void resetTimeOut() {
        //singleton.timeOutHandler = new Handler();
        //singleton.timeOutMaxHandler = new Handler();
        //
        //singleton.timeOutHandler.postDelayed(runTimeOutAlert, singleton.timeOutMil);
        //singleton.timeOutMaxHandler.postDelayed(runCloseAPP, singleton.timeOutMilMax);
    }

    //--------------------------------------------------------

    public void setDownUpMenu() {

        ViewGroup menuGroup = (ViewGroup) findViewById(R.id.inicioMainRelativeLayout);
        progressGroup = (ViewGroup) findViewById(R.id.inicioMainRelativeLayout);

        childMenuView = LayoutInflater.from(this).inflate(
                R.layout.circle_menu_layout, menuGroup);

        menuOut = false;
        fadeIn = AnimationUtils.loadAnimation(this,
                R.anim.alpha_animation);
        position = 1;

        leftSlide = AnimationUtils.loadAnimation(this, R.anim.slide_outside_left);
        leftSlide.setFillAfter(true);

        buttonMenuList = new ArrayList<ImageButton>();
        button0 = (ImageButton) findViewById(R.id.button_menu0);
        button1 = (ImageButton) findViewById(R.id.button_menu1);

        button3 = (ImageButton) findViewById(R.id.button_menu3);
        button4 = (ImageButton) findViewById(R.id.button_menu4);
        button5 = (ImageButton) findViewById(R.id.button_menu5);

        buttonMenuList.add(button0);
        buttonMenuList.add(button1);

        buttonMenuList.add(button3);
        buttonMenuList.add(button4);
        buttonMenuList.add(button5);

        btn = (Button) findViewById(R.id.button_id);

        dontUseThisAgain = false;
        ViewTreeObserver vto = button3.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {

                if (!dontUseThisAgain) {
                    dontUseThisAgain = true;
                    float width = button1.getWidth();
                    float height = button1.getHeight();

                    int cuenta = 1;
                    for (ImageButton boton : buttonMenuList) {
                        boton.setY(boton.getY() + height);
                    }
                    btn.setY(btn.getY() + (btn.getHeight() / 2) + 10);

                    buttonMenuList.remove(button0);
                    buttonMenuList.remove(button1);

                    buttonMenuList.remove(button4);
                }
            }
        });


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (menuOut) {
                    //TODO closing

                   /* if (loginPreguntaActivity != null) {
                        loginPreguntaActivity.listItem_setEneable();
                    }
                    */
                    Utiles.view_setEneable(childList, true);
                    //Utiles.unlockKeyboard(parentActivity);

                    buttonPosition = 1;
                    btn.animate().rotation(360).setDuration(300).start();
                    for (ImageButton boton : buttonMenuList) {
                        translateMenu(boton, buttonPosition);
                        buttonPosition++;
                    }
                    transition_manager();

                    //getSingleton().setMenuOpen(!menuOut);
                    menuOut = false;
                } else {
                    //TODO opening
/*
                    if (loginPreguntaActivity != null) {
                        loginPreguntaActivity.listItem_setEneable();
                    }*/
                    Utiles.view_setEneable(childList, false);

                    buttonPosition = 1;
                    btn.animate().rotation(180).setDuration(300).start();
                    for (ImageButton boton : buttonMenuList) {
                        translateMenu(boton, buttonPosition);
                        buttonPosition++;
                    }
                    transition_manager();

                    //getSingleton().setMenuOpen(!menuOut);
                    menuOut = true;
                }

            }


        });


        button1.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

           //     singleton.initializeTimeTask_main(parentActivity);

             //   getSingleton().closeActivities();

                generateAcercaDe();

                closeMenu();
            }
        });

      /*  button1.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                generateTemporalDialog("Recuperación de usuario");
                closeMenu();
            }

        }); */

        button3.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

              /*  singleton.initializeTimeTask_main(parentActivity);

                if (getSingleton().redesAct == null) {

                    //singleton.closeActivities();
                    if (getSingleton().infoAct != null) {
                        getSingleton().infoAct.finish();
                        getSingleton().infoAct = null;
                    }*/

                    Intent mainIntent = new Intent().setClass(
                            parentActivity, RedesSocialesActivity.class);
                    startActivity(mainIntent);
               // }


                closeMenu();
            }
        });

     /*   button4.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                generateTemporalDialog("Red bod");
                closeMenu();
            }
        }); */

        button5.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                //singleton.initializeTimeTask_main(parentActivity);

                /*if (getSingleton().infoAct == null) {

                    //singleton.closeActivities();
                    if (getSingleton().redesAct != null) {
                        getSingleton().redesAct.finish();
                        getSingleton().redesAct = null;
                    }
*/
                    Intent mainIntent = new Intent().setClass(
                            parentActivity, ContactoAmigoActivity.class);
                    startActivity(mainIntent);

  //              }

                closeMenu();
            }
        });

    }

    private void translateMenu(View view, int offsetFactor) {

        DisplayMetrics dm = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(dm);

        int originalPos[] = new int[2];
        view.getLocationOnScreen(originalPos);

        int yDest = 0;
        yDest = (dm.heightPixels - btn.getMeasuredHeight() / 2 - (view.getMeasuredHeight() * offsetFactor)) - 20 * offsetFactor + (int) (btn.getMeasuredHeight() * 0.25);
        // LinearLayout loginBox = (LinearLayout) parentActivity.findViewById(R.id.ll_login_box);
        if (!menuOut) {
            // Utiles.disable(loginBox);
            view.animate().y(yDest - (btn.getHeight() / 2)).alpha(1).setDuration(500).start();
        } else {
            //  Utiles.enable(loginBox);
            view.animate().y(dm.heightPixels).alpha(0).setDuration(500).start();
            // loginBox.setEnabled(true);
        }
    }

    public void closeMenu() {

        Utiles.view_setEneable(childList, true);
        //this.eneableItemListView(true);

        buttonPosition = 1;
        for (ImageButton boton : buttonMenuList) {
            translateMenu(boton, buttonPosition);
            buttonPosition++;
        }
        btn.animate().rotation(360).setDuration(300).start();
        transition_manager();
        menuOut = false;
//        getSingleton().setMenuOpen(menuOut);
    }

    public void transition_manager() {
        RelativeLayout menuLayout = (RelativeLayout) parentActivity.findViewById(R.id.menuLayout);
        TransitionDrawable transition = (TransitionDrawable) menuLayout.getBackground();
        if (!menuOut) {
            transition.startTransition(500);
        } else {
            transition.reverseTransition(500);
        }
    }

    public void generateTemporalDialog(String title) {

        final Dialog dialog = new Dialog(parentActivity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.temporal_popup);

        TextView titleTitle = (TextView) dialog.findViewById(R.id.funcion_title);
        titleTitle.setText(title);

        ImageView image = (ImageView) dialog.findViewById(R.id.icon_cerrar);
        TextView dialogButton = (TextView) dialog.findViewById(R.id.tv_back);
        // if button is clicked, close the custom dialog
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

            }
        });

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

            }
        });

        dialog.show();
    }

    //------------------------------------------------------------

    public void showDialog_TimeOut() {

        final Dialog dialog = new Dialog(parentActivity, R.style.MyThemeDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_cerrar_timeout);

        RenderScript rs = RenderScript.create(this);
        LinearLayout bgLayout = (LinearLayout) dialog.findViewById(R.id.ll_image_layout);
        Utiles.blur(this.getWindow().getDecorView().findViewById(android.R.id.content), rs, bgLayout, this, 4f);

        TextView tvTitulo = (TextView) dialog.findViewById(R.id.dialog_titulo);
        //tvTitulo.setText(BodConstants.tituloMensaje);

        //TextView tvMensaje = (TextView) dialog.findViewById(R.id.dialog_mensaje);
        //tvTitulo.setText("En breves momentos se va a cerrar la aplicacion");

        ImageView image = (ImageView) dialog.findViewById(R.id.icon_cerrar);

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
                resetTimeOut();
                dialog.dismiss();
            }
        });

        text_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetTimeOut();
                dialog.dismiss();

            }
        });

        dialog.show();
    }

    public void generateAcercaDe() {

        final Dialog dialog = new Dialog(parentActivity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_acerca_de);

        ImageView image = (ImageView) dialog.findViewById(R.id.iv_cerrar);
        TextView dialogButton = (TextView) dialog.findViewById(R.id.tv_cerrar);
        // if button is clicked, close the custom dialog
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

            }
        });

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

            }
        });

        dialog.show();
    }


    private void callService_urlSoftoken() {
       /* if (Utiles.isConnected(this)) {
            HttpAsyncTask_urlSoftoken task = new HttpAsyncTask_urlSoftoken();
            task.execute(WebConstants.GATEWAY_URL);
        } else {
            Utiles.generateAlertDialog(BodConstants.tituloMensaje, "Por favor verifique su conexi\u00f3n e intente de nuevo.", parentActivity);
        }

        */
    }

    public Activity getActivityActive() {
        return activityActive;
    }

    public void setActivityActive(Activity activityActive) {
        this.activityActive = activityActive;
    }

    private class HttpAsyncTask_urlSoftoken extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            LayoutInflater layoutInflater = (LayoutInflater) getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);
            progressView = layoutInflater.inflate(R.layout.custom_progres_dialog_layout_2, null);
            progressGroup.addView(progressView);
        }

        @Override
        protected String doInBackground(String... urls) {


            /* AWBODServicesObjects servicesObjects = new AWBODServicesObjects();

            Usuario usuario = servicesObjects.getURLSoftToken(urls[0]);

            if (!usuario.hasError()) {

                getSingleton().setUrlSoftToken(usuario.getResultadoURLSoftToken());

                return WebConstants.SERVICE_CORRECT;


            } else {

                return WebConstants.SERVICE_ERROR;

            }

            */
            return "";

        }

        @Override
        protected void onPostExecute(String resultCode) {

        }
    }

    public Singleton getSingleton() {
        return singleton;
    }

    public void setSingleton(Singleton singleton) {
        this.singleton = singleton;
    }
}
