package depchemobile.com.bod.checkdeposit.utils;

import android.app.Activity;
import android.app.Application;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v8.renderscript.RenderScript;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;

import depchemobile.com.bod.checkdeposit.R;
import depchemobile.com.bod.checkdeposit.web.WebConstants;
import depchemobile.com.bod.checkdeposit.activity.LoginActivity;


import com.neopixl.pixlui.components.textview.TextView;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**

 */
public class Singleton extends Application {

    //private Boolean isProcessing = false;
    //

    //
    private String urlSoftToken;
    //

    //
    public Activity _activity;
    private ViewPager actualScrolledViewPager;
    //


    //TODO timeout
    public long milisegMax;
    public long milisegAlert;
    public boolean online = false;
    //
    Timer timerMAX;
    TimerTask timerTaskMAX;
    final Handler handlerMAX = new Handler();
    //
    Timer timerAlert;
    TimerTask timerTaskAlert;
    final Handler handlerAlert = new Handler();

    private boolean isPanel;

    public HttpParams httpParameters = new BasicHttpParams();
    public HttpClient httpClient = null;
    public HttpPost post = null;

    private static Context mContext;

    public Activity redesAct = null;
    public Activity infoAct = null;

    private boolean menuOpen = false;

    @Override
    public void onCreate() {
        super.onCreate();
       // setEnmascaraCuenta(new EnmascaraCuenta());
        //milisegAlert = Utiles.SegToMiliSeg(10000);
        //milisegMax = Utiles.SegToMiliSeg(30000);
        //setListadoBusqueda(new ArrayList<Dato>());
        //setListadoBusqueda(MockFile.getMockBusquedaGlobal());

        //mContext = getApplicationContext();

       // setClient();

    }

    private void setClient() {

        httpParameters = new BasicHttpParams();
        int timeoutConnection = 60000;
        HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);
        int timeoutSocket = 60000;
        HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);

        httpClient = new DefaultHttpClient(httpParameters);

        /*
        httpClient.addResponseInterceptor(new HttpResponseInterceptor() {
            public void process(final HttpResponse response,
                                final HttpContext context) throws HttpException,
                    IOException {
                HttpEntity entity = response.getEntity();
                Header encheader = entity.getContentEncoding();
                if (encheader != null) {
                    HeaderElement[] codecs = encheader.getElements();
                    for (int i = 0; i < codecs.length; i++) {
                        if (codecs[i].getName().equalsIgnoreCase("gzip")) {
                            response.setEntity(new GzipDecompressingEntity(
                                    entity));
                            return;
                        }
                    }
                }
            }
        });*/

        post = new HttpPost(WebConstants.GATEWAY_URL);
    }

    public static Context getContext() {
        return mContext;
    }


    public void cleanSession() {
        /*setTransferenciaSession(null);
        setPagoSession(null);
        setCuentaSession(null);
        setTarjetaSession(null);
        setCreditoSession(null);
        */
    }

    public void stopTimeOut() {
        if (timerMAX != null) {
            timerMAX.cancel();
        }
        timerMAX = null;
        if (timerAlert != null) {
            timerAlert.cancel();
        }
        timerAlert = null;
    }

    //TODO cargar servicios TimeOut

    public void initializeTimeTask_main(final Activity act) {
        this.initializeTimerTask(act);
        this.initializeTimerTaskAlert(act);
    }

    public boolean hasTimer() {
        return timerMAX != null && timerAlert != null;
    }

    private void initializeTimerTask(final Activity act) {
        if (timerMAX == null) {
            timerMAX = new Timer();
            timerTaskMAX = new TimerTask() {
                public void run() {
                    handlerMAX.post(new Runnable() {
                        public void run() {
                            Log.d("run", "FINISH");
                            cleanSession();
                            if (act.getClass() == LoginActivity.class) {
                                act.finish();
                            } else {
                                gotoLogin(act);
                            }
                        }
                    });
                }
            };
            timerMAX.schedule(timerTaskMAX, milisegMax);
        }
    }

    private void initializeTimerTaskAlert(final Activity act) {
        if (timerAlert == null) {
            timerAlert = new Timer();
            timerTaskAlert = new TimerTask() {
                public void run() {
                    handlerAlert.post(new Runnable() {
                        public void run() {
                            Log.d("run", "FINISH");
                            if (!act.isFinishing()) {
                                showDialog_TimeOut(act);
                            }
                            //act.finish();
                        }
                    });
                }
            };
            timerAlert.schedule(timerTaskAlert, milisegAlert);
        }
    }

    public void stopTimerTask() {
        if (timerMAX != null) {
            timerMAX.cancel();
            timerMAX = null;
        }
    }

    public void stopTimerTaskAlert() {
        if (timerAlert != null) {
            timerAlert.cancel();
            timerAlert = null;
        }
    }

    public void resetTimeTask_main(final Activity act) {
        resetTimeTaskAlert(act);
        resetTimeTaskMAX(act);
    }

    private void resetTimeTaskMAX(final Activity act) {
        stopTimerTask();
        initializeTimerTask(act);
    }

    private void resetTimeTaskAlert(final Activity act) {
        stopTimerTaskAlert();
        initializeTimerTaskAlert(act);
    }

    public void gotoLogin(final Activity parentActivity) {
        Bundle bundle = new Bundle();
        bundle.putBoolean("argument", true);
        Intent mainIntent = new Intent().setClass(
                parentActivity, LoginActivity.class);
        mainIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        mainIntent.putExtras(bundle);
        parentActivity.startActivity(mainIntent);
        parentActivity.finish();
        //startActivity(Intent.createChooser(mainIntent, "Activity").addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
    }

    public void closeAPP(final Activity parentActivity) {
        parentActivity.finish();
    }

    public void showDialog_TimeOut(final Activity parentActivity) {

        try {

            final Dialog dialog = new Dialog(parentActivity, R.style.MyThemeDialog);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.setContentView(R.layout.dialog_cerrar_timeout);

            RenderScript rs = RenderScript.create(this);
            LinearLayout bgLayout = (LinearLayout) dialog.findViewById(R.id.ll_image_layout);
            Utiles.blur(parentActivity.getWindow().getDecorView().findViewById(android.R.id.content), rs, bgLayout, this, 4f);

            TextView tvTitulo = (TextView) dialog.findViewById(R.id.dialog_titulo);
            //tvTitulo.setText(BodConstants.tituloMensaje);

            TextView tvMensaje = (TextView) dialog.findViewById(R.id.dialog_mensaje);
            //tvTitulo.setText("En breves momentos se va a cerrar la aplicacion.");

            //ImageView image = (ImageView) dialog.findViewById(R.id.icon_cerrar_graphic);

            ImageView image_next = (ImageView) dialog.findViewById(R.id.iv_next);
            TextView text_next = (TextView) dialog.findViewById(R.id.tv_next);

        /*
        ImageView image_back = (ImageView) dialog.findViewById(R.id.iv_back);
        TextView text_back = (TextView) dialog.findViewById(R.id.tv_back);

        image_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parentActivity.finish();
                dialog.dismiss();
            }
        });

        text_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parentActivity.finish();
                dialog.dismiss();
            }
        });

        */

            image_next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    resetTimeTask_main(parentActivity);
                    callService_reiniciar(parentActivity);
                    dialog.dismiss();
                }
            });

            text_next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    resetTimeTask_main(parentActivity);
                    callService_reiniciar(parentActivity);
                    dialog.dismiss();
                }
            });

            dialog.show();
        } catch (Exception e) {
            //TODO mensaje de alerta Error
        }
    }

    public void callService(Activity _activity) {
        this._activity = _activity;

        if (Utiles.isConnected(_activity)) {
            HttpAsyncTask task = new HttpAsyncTask();
            task.execute(WebConstants.GATEWAY_URL);
        } else {
            Utiles.generateAlertDialog(BodConstants.tituloMensaje, "Por favor verifique su conexi�n e intente de nuevo.", _activity);
        }

        this.online = true;
    }

    public String getUrlSoftToken() {
        return urlSoftToken;
    }

    public void setUrlSoftToken(String urlSoftToken) {
        this.urlSoftToken = urlSoftToken;
    }

    public boolean isPanel() {
        return isPanel;
    }

    public void setIsPanel(boolean isPanel) {
        this.isPanel = isPanel;
    }

    public Activity getRedesAct() {
        return redesAct;
    }

    public void setRedesAct(Activity redesAct) {
        this.redesAct = redesAct;
    }

    public Activity getInfoAct() {
        return infoAct;
    }

    public void setInfoAct(Activity infoAct) {
        this.infoAct = infoAct;
    }

    public boolean isMenuOpen() {
        return menuOpen;
    }

    public void setMenuOpen(boolean menuOpen) {
        this.menuOpen = menuOpen;
    }

    private class HttpAsyncTask extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... urls) {
           /* AWBODServicesObjects servicesObjects = new AWBODServicesObjects();

            AppSession apps = servicesObjects.getTiempoSesionApp(urls[0]);

            if (apps == null) {
                return WebConstants.SERVICE_ERROR;
            } else {
                milisegMax = Utiles.SegToMiliSeg(Long.parseLong(apps.getTimeOut()));
                milisegAlert = milisegMax - 20000;
            }

            EnmascaraCuenta dato = servicesObjects.getEnmascaramiento(urls[0]);
            if (dato == null) {
                return WebConstants.SERVICE_ERROR;
            }

*/
            //TODO 2016
            //setEnmascaraCuenta(dato);

            return WebConstants.SERVICE_CORRECT;

            /*
            if (apps != null) {
                timeOutMil =  Utiles.SegToMiliSeg(Long.parseLong(apps.getTimeOut()));
                //timeOutMil = Utiles.SegToMiliSeg(25);
                return WebConstants.SERVICE_CORRECT;
            }
            return WebConstants.SERVICE_ERROR;
            */
        }

        @Override
        protected void onPostExecute(String resultCode) {
            if (resultCode.equals(WebConstants.SERVICE_ERROR)) {
                Utiles.generateAlertDialog(BodConstants.tituloMensaje, "Por favor verifique su conexi�n e intente de nuevo.", _activity);
                Log.e("Error ", "Error en servicio");
            }
        }
    }

    public void callService_reiniciar(Activity _activity) {
        this._activity = _activity;

        if (Utiles.isConnected(_activity)) {
            HttpAsyncTask_Reiniciar task = new HttpAsyncTask_Reiniciar();
            task.execute(WebConstants.GATEWAY_URL);
        } else {
            Utiles.generateAlertDialog(BodConstants.tituloMensaje, "Por favor verifique su conexi�n e intente de nuevo.", _activity);
        }

        this.online = true;
    }

    private class HttpAsyncTask_Reiniciar extends AsyncTask<String, Void, String> {

        //Usuario user = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();


        }

        @Override
        protected String doInBackground(String... urls) {
/*
            AWBODServicesObjects servicesObjects = new AWBODServicesObjects();


            try {
                user = servicesObjects.getRegistroReinicioSession(urls[0], cliente.getNombre());

                if (user == null) {
                    return WebConstants.SERVICE_ERROR;
                } else {
                    return WebConstants.SERVICE_CORRECT;
                }
            } catch (Exception e) {
                return WebConstants.SERVICE_CORRECT;
            }

            */
            return WebConstants.SERVICE_CORRECT;

        }

        @Override
        protected void onPostExecute(String resultCode) {
            if (resultCode.equals(WebConstants.SERVICE_ERROR)) {
                Utiles.generateAlertDialog(BodConstants.tituloMensaje, "Mensaje", _activity);
                Log.e("Error ", "Error en servicio");
            }
        }
    }

    //TODO Propiedades


    public ViewPager getActualScrolledViewPager() {
        return actualScrolledViewPager;
    }

    public void setActualScrolledViewPager(ViewPager actualScrolledViewPager) {
        this.actualScrolledViewPager = actualScrolledViewPager;
    }



    public void closeActivities() {
        try {
            if (redesAct != null) {
                redesAct.finish();
            }
            if (infoAct != null) {
                infoAct.finish();
            }
        } catch (Exception e) {

        }
    }

}
