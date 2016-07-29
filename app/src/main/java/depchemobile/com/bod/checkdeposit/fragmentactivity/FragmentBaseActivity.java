package depchemobile.com.bod.checkdeposit.fragmentactivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
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
import depchemobile.com.bod.checkdeposit.fragment.PrincipalFragment;
import depchemobile.com.bod.checkdeposit.utils.Utiles;
import depchemobile.com.bod.checkdeposit.utils.BodConstants;
import depchemobile.com.bod.checkdeposit.web.WebConstants;
import depchemobile.com.bod.checkdeposit.utils.Singleton;
import depchemobile.com.bod.checkdeposit.utils.LoadingDialog;

import depchemobile.com.bod.checkdeposit.activity.ContactoAmigoActivity;
import depchemobile.com.bod.checkdeposit.activity.LoginActivity;
import depchemobile.com.bod.checkdeposit.activity.RedesSocialesActivity;

import com.neopixl.pixlui.components.textview.TextView;

import java.util.ArrayList;

/**
 * Created by Rony Díaz
 */
public class FragmentBaseActivity extends FragmentActivity {

    protected ArrayList<ImageButton> buttonMenuList;
    protected ImageButton button0;
    protected ImageButton button1;
    protected ImageButton button2;
    protected ImageButton button3;
    protected ImageButton button4;
    protected ImageButton button5;
    protected Boolean menuOut;
    protected int buttonPosition;
    protected FragmentBaseActivity parentActivity;
    protected int position;
    protected Button btn;
    protected Animation fadeIn;
    protected boolean dontUseThisAgain;
    protected View childMenuView;
    public View progressView;
    public ViewGroup progressGroup;

    private Singleton singleton = null;

    public ArrayList childList = new ArrayList();

    private boolean isDirectory;

    private PrincipalFragment principalFragment;
    /*
    private CuentaListaFragment cuentaListaFragment;
    private TransferenciaContactoListaFragment transferenciaContactoListaFragment;
    private TransferenciaContactoCuentaFragment transferenciaContactoCuentaFragment;
*/
    public LinearLayout ll_header_directory;


    public void close() {
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
        childMenuView = LayoutInflater.from(this).inflate(
                R.layout.circle_menu_layout, menuGroup);

        //progressGroup = (ViewGroup) findViewById(R.id.inicioMainRelativeLayout);
        progressGroup = menuGroup;

        menuOut = false;
        fadeIn = AnimationUtils.loadAnimation(this,
                R.anim.alpha_animation);
        position = 1;

        buttonMenuList = new ArrayList<ImageButton>();
        button0 = (ImageButton) findViewById(R.id.button_menu0);
        button1 = (ImageButton) findViewById(R.id.button_menu1);

        button3 = (ImageButton) findViewById(R.id.button_menu3);
        button4 = (ImageButton) findViewById(R.id.button_menu4);
        button5 = (ImageButton) findViewById(R.id.button_menu5);

        buttonMenuList.add(button0);
        buttonMenuList.add(button1);
        buttonMenuList.add(button2);
        buttonMenuList.add(button3);
        buttonMenuList.add(button4);
        buttonMenuList.add(button5);

        btn = (Button) findViewById(R.id.button_id);

        dontUseThisAgain = false;
        ViewTreeObserver vto = button3.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {


                //TODO ocultar barra
                /*
                View decorView = getWindow().getDecorView();
                int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN;
                decorView.setSystemUiVisibility(uiOptions);
                */
                //

                if (!dontUseThisAgain) {
                    dontUseThisAgain = true;
                    float width = button1.getWidth();

                    float height = 0;

                    if (!isDirectory) {
                        height = button1.getHeight();
                    } else {
                        height = button1.getHeight() + ll_header_directory.getHeight();
                    }

                    int cuenta = 1;
                    for (ImageButton boton : buttonMenuList) {
                        boton.setY(boton.getY() + height);
                    }

                    btn.setY(btn.getY() + (btn.getHeight() / 2) + 10);

                    /*
                    if (!isDirectory) {
                        btn.setY(btn.getY() + (btn.getHeight() / 2) + 10);
                    } else {
                        btn.setY(btn.getY() + (btn.getHeight() + ll_header_directory.getHeight() / 2) + 10);
                    }
                    */

                    buttonMenuList.remove(button0);
                    buttonMenuList.remove(button1);
                    //buttonMenuList.remove(button2);
                    buttonMenuList.remove(button4);
                }
            }
        });


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (menuOut) {
                //    getSingleton().setMenuOpen(!menuOut);
                    //Utiles.unlockKeyboard(parentActivity);

                    Utiles.view_setEneable(childList, true);

                    buttonPosition = 1;
                    btn.animate().rotation(360).setDuration(300).start();
                    for (ImageButton boton : buttonMenuList) {
                        translateMenu(boton, buttonPosition);
                        buttonPosition++;
                    }
                    transition_manager();
                    menuOut = false;
                } else {
                    getSingleton().setMenuOpen(!menuOut);
                    //Utiles.lockKeyboard(parentActivity);

                    Utiles.view_setEneable(childList, false);

                    buttonPosition = 1;
                    btn.animate().rotation(180).setDuration(300).start();
                    for (ImageButton boton : buttonMenuList) {
                        translateMenu(boton, buttonPosition);
                        buttonPosition++;
                    }
                    transition_manager();
                    menuOut = true;
                }

            }


        });

        button2.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {





                closeMenu();
            }
        });



        button1.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                generateAcercaDe();
                closeMenu();
            }
        });


        button3.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                setSingleton(((Singleton) getApplicationContext()));

                if (getSingleton().redesAct == null) {

                    //singleton.closeActivities();
                    if (getSingleton().infoAct != null) {
                        getSingleton().infoAct.finish();
                        getSingleton().infoAct = null;
                    }

                    Intent mainIntent = new Intent().setClass(
                            parentActivity, RedesSocialesActivity.class);
                    startActivity(mainIntent);
                }


                closeMenu();


            }
        });

        button4.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                generateTemporalDialog("Red bod");
                closeMenu();
            }
        });

        button5.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                /*
                singleton.closeActivities();

                Intent mainIntent = new Intent().setClass(
                        parentActivity, ContactoAmigoActivity.class);
                startActivity(mainIntent);

                closeMenu();
                */

                setSingleton(((Singleton) getApplicationContext()));

                if (getSingleton().infoAct == null) {

                    //singleton.closeActivities();
                    if (getSingleton().redesAct != null) {
                        getSingleton().redesAct.finish();
                        getSingleton().redesAct = null;
                    }

                    Intent mainIntent = new Intent().setClass(
                            parentActivity, ContactoAmigoActivity.class);
                    startActivity(mainIntent);

                }

                closeMenu();

            }
        });
    }

    public float getHeight() {

        return 0;

    }


    public void setDowUpMenu_Visibility(int visibility) {
        ViewGroup menuGroup = (ViewGroup) findViewById(R.id.inicioMainRelativeLayout);
        RelativeLayout rl_menu_circle = (RelativeLayout) menuGroup.findViewWithTag("circle_menu_layout");
        if (rl_menu_circle != null) {
            rl_menu_circle.setVisibility(visibility);
        }
    }

    private void translateMenu(View view, int offsetFactor) {

        DisplayMetrics dm = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(dm);

        int originalPos[] = new int[2];
        view.getLocationOnScreen(originalPos);

        int yDest = 0;
        yDest = (dm.heightPixels - btn.getMeasuredHeight() / 2 - (view.getMeasuredHeight() * offsetFactor)) - 10 * offsetFactor + (int) (btn.getMeasuredHeight() * 0.0);

        //RelativeLayout menuLayout = (RelativeLayout) parentActivity.findViewById(R.id.menuLayout);
        // LinearLayout loginBox = (LinearLayout) parentActivity.findViewById(R.id.ll_login_box);
        //TransitionDrawable transition = (TransitionDrawable) menuLayout.getBackground();
        if (!menuOut) {
            //transition.startTransition(500);
            // Utiles.disable(loginBox);
            view.animate().y(yDest - (btn.getHeight() / 2)).alpha(1).setDuration(500).start();

        } else {
            //transition.reverseTransition(500);
            //  Utiles.enable(loginBox);
            view.animate().y(dm.heightPixels).alpha(0).setDuration(500).start();
            // loginBox.setEnabled(true);
        }
    }

    public void closeMenu() {



        Utiles.view_setEneable(childList, true);

        buttonPosition = 1;
        for (ImageButton boton : buttonMenuList) {
            translateMenu(boton, buttonPosition);
            buttonPosition++;
        }
        btn.animate().rotation(360).setDuration(300).start();
        transition_manager();
        menuOut = false;
        getSingleton().setMenuOpen(menuOut);
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

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }

    //TODO

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

        TextView tvMensaje = (TextView) dialog.findViewById(R.id.dialog_mensaje);
        //tvTitulo.setText("En breves momentos se va a cerrar la aplicacion.");

        ImageView image = (ImageView) dialog.findViewById(R.id.icon_cerrar);

        ImageView image_next = (ImageView) dialog.findViewById(R.id.iv_next);
        TextView text_next = (TextView) dialog.findViewById(R.id.tv_next);

        ImageView image_back = (ImageView) dialog.findViewById(R.id.iv_back);
        TextView text_back = (TextView) dialog.findViewById(R.id.tv_back);

        image_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetTimeOut();
                dialog.dismiss();
            }
        });

        text_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetTimeOut();
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

    public void transition_manager() {
        RelativeLayout menuLayout = (RelativeLayout) parentActivity.findViewById(R.id.menuLayout);
        TransitionDrawable transition = (TransitionDrawable) menuLayout.getBackground();
        if (!menuOut) {
            transition.startTransition(500);
        } else {
            transition.reverseTransition(500);
        }
    }

    public void refreshActivity() {
        //TODO
        finish();
        startActivity(getIntent());
    }

    public Singleton getSingleton() {
        return singleton;
    }

    public void setSingleton(Singleton singleton) {
        this.singleton = singleton;
    }

    public void gotoLoginActivity() {
        Bundle bundle = new Bundle();
        bundle.putBoolean("argument", true);
        Intent intent = new Intent(this, LoginActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }

    private void callService_urlSoftoken() {
        if (Utiles.isConnected(this)) {
            HttpAsyncTask_urlSoftoken task = new HttpAsyncTask_urlSoftoken();
            task.execute(WebConstants.GATEWAY_URL);
        } else {
            Utiles.generateAlertDialog(BodConstants.tituloMensaje, "Por favor verifique su conexi\u00f3n e intente de nuevo.", parentActivity);
        }
    }

    public PrincipalFragment getPrincipalFragment() {
        return principalFragment;
    }

    public void setPrincipalFragment(PrincipalFragment principalFragment) {
        this.principalFragment = principalFragment;
    }



    public boolean isDirectory() {
        return isDirectory;
    }

    public void setIsDirectory(boolean isDirectory) {
        this.isDirectory = isDirectory;
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
        return "";


        }

        @Override
        protected void onPostExecute(String resultCode) {

        }
    }

}
