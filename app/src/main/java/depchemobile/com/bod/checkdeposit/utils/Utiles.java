package depchemobile.com.bod.checkdeposit.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Environment;
import android.support.v8.renderscript.Allocation;
import android.support.v8.renderscript.Element;
import android.support.v8.renderscript.RenderScript;
import android.support.v8.renderscript.ScriptIntrinsicBlur;

import android.text.Html;
import android.text.format.Formatter;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import com.special.ResideMenu.ResideMenu;




import com.neopixl.pixlui.components.edittext.EditText;
import com.neopixl.pixlui.components.textview.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import depchemobile.com.bod.checkdeposit.R;

/**
 * Created by Rony  Diaz  on 14/04/2015, Rony Diaz on 29/07/2015.
 */
public class Utiles {

    public  Bitmap setPic(ImageView im, String mCurrentPhotoPath) {

		/* There isn't enough memory to open up more than a couple camera photos */
		/* So pre-scale the target bitmap into which the file is decoded */
        Log.v(this.getClass().getName(),"setPic - Inicando:");

        File f = new File(mCurrentPhotoPath);
        if(f.exists())
        {
            Log.v(this.getClass().getName(),"Set Pic - Si existe la ruta: " + mCurrentPhotoPath);
        }
        else
        {
            Log.v(this.getClass().getName(),"Set Pic - NO EXISTE LA RUTA : " + mCurrentPhotoPath);
        }




        im.setAdjustViewBounds(true);


		/* Get the size of the ImageView */
        int targetW = im.getWidth();
        int targetH = im.getHeight();

		/* Get the size of the image */
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

		/* Figure out which way needs to be reduced less */
        int scaleFactor = 1;
        if ((targetW > 0) || (targetH > 0)) {
            scaleFactor = Math.min(photoW/targetW, photoH/targetH);
        }

		/* Set bitmap options to scale the image decode target */
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

		/* Decode the JPEG file into a Bitmap */
        Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        Log.v(this.getClass().getName(),"setPic - Final:");
        return bitmap;

		/* Associate the Bitmap to the ImageView */
        // mImageView.setImageBitmap(bitmap);
        // mVideoUri = null;
        //mImageView.setVisibility(View.VISIBLE);



    }

    private static RelativeLayout fondoMenu;

    public static void disable(ViewGroup layout) {
        layout.setEnabled(false);
        for (int i = 0; i < layout.getChildCount(); i++) {
            View child = layout.getChildAt(i);
            if (child instanceof ViewGroup) {
                disable((ViewGroup) child);
            } else {
                child.setEnabled(false);
            }
        }
    }

    public static void enable(ViewGroup layout) {
        layout.setEnabled(true);
        for (int i = 0; i < layout.getChildCount(); i++) {
            View child = layout.getChildAt(i);
            if (child instanceof ViewGroup) {
                enable((ViewGroup) child);
            } else {
                child.setEnabled(true);
            }
        }
    }

    public static void collapse(final View v) {
        final int initialHeight = v.getMeasuredHeight();

        Animation a = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if (interpolatedTime == 1) {
                    v.setVisibility(View.GONE);
                } else {
                    v.getLayoutParams().height = initialHeight - (int) (initialHeight * interpolatedTime);
                    v.requestLayout();
                }
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };


        // 1dp/ms
        a.setDuration(500);
        v.startAnimation(a);
    }


    public static void expand(final View v) {
        v.measure(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        final int targetHeight = v.getMeasuredHeight();

        v.getLayoutParams().height = 0;
        v.setVisibility(View.VISIBLE);
        Animation a = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                v.getLayoutParams().height = interpolatedTime == 1
                        ? RelativeLayout.LayoutParams.WRAP_CONTENT
                        : (int) (targetHeight * interpolatedTime);
                v.requestLayout();
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // 1dp/ms
        a.setDuration((int) (targetHeight / v.getContext().getResources().getDisplayMetrics().density));
        v.startAnimation(a);
    }

    @SuppressWarnings("deprecation")
    public static void blur(View v, RenderScript rs, View blurred, Context context, float ratio) {
        v.setDrawingCacheEnabled(true);
        v.buildDrawingCache();
        Bitmap bm = Bitmap.createBitmap(v.getDrawingCache());

        final Allocation input = Allocation.createFromBitmap(rs, bm);
        final Allocation output = Allocation.createTyped(rs, input.getType());
        final ScriptIntrinsicBlur script = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
        script.setRadius(ratio);
        script.setInput(input);
        script.forEach(output);
        output.copyTo(bm);

        if (android.os.Build.VERSION.SDK_INT >= 16) {
            blurred.setBackground(new BitmapDrawable(context.getResources(), bm));
        } else {
            blurred.setBackgroundDrawable(new BitmapDrawable(bm));
        }
        v.setDrawingCacheEnabled(false);
        v.destroyDrawingCache();

    }

    public static void unblur(View v) {

        v.setBackgroundResource(0);
        v.setBackgroundColor(Color.TRANSPARENT);

    }

    public static boolean isConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Activity.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        return ni != null && ni.isConnected();
    }

    public static Date formatStringToDate(String stringDate) {
        try {
            String mesDia = stringDate.substring(4, 10);
            String anio = stringDate.substring(24, 28);
            String fecha = mesDia + " " + anio;
            //
            DateFormat formatter = new SimpleDateFormat("MMM d yyyy", Locale.US);
            Date date = formatter.parse(fecha);
            return date;
        } catch (Exception e) {
            Log.e("DATE FORMATTER", e.getLocalizedMessage());
            //return null;
            return new Date();
            //Calendar calendar = Calendar.getInstance();
            //return calendar.getTime();
        }
    }

    public static Date formatStringToDate_v2(String stringDate) {
        //01-04-2015
        try {
            stringDate = "01-" + stringDate;
            DateFormat formatter = new SimpleDateFormat("dd-mm-yyyy", Locale.US);
            Date date = formatter.parse(stringDate);
            return date;
        } catch (Exception e) {
            Log.e("DATE FORMATTER", e.getLocalizedMessage());
            return null;
        }
    }


    public static String formatDateToString(Date date) {
      /*  Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        String dia = ""+Calendar.DATE;
        String mes = ""+Calendar.MONTH;
        if(dia.length() == 1){
            dia = "0"+dia;
        }
        if(mes.length() == 1){
            mes = "0"+mes;
        }

        return dia + "/" + mes + "/" +calendar.get(Calendar.YEAR); */
        if (date != null) {
            return android.text.format.DateFormat.format("dd/MM/yyyy", date).toString();
        } else {
            Calendar cal = Calendar.getInstance();
            return android.text.format.DateFormat.format("dd/MM/yyyy", cal.getTime()).toString();
        }

    }

    public static String formatDateToStringFile(Date date) {
      /*  Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        String dia = ""+Calendar.DATE;
        String mes = ""+Calendar.MONTH;
        if(dia.length() == 1){
            dia = "0"+dia;
        }
        if(mes.length() == 1){
            mes = "0"+mes;
        }

        return dia + "/" + mes + "/" +calendar.get(Calendar.YEAR); */

        //return android.text.format.DateFormat.format("dd-MM-yyyy", date).toString();

        //yyyy-MM-dd_hh:mm:ss

        return android.text.format.DateFormat.format("dd-MM-yyyy-hh-mm-ss", date).toString();

    }


    public static BigDecimal round(double d, int decimalPlace) {
        BigDecimal bd = new BigDecimal(Double.toString(d));
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
        return bd;
    }


    public static BitmapDrawable blurMenu(View v, RenderScript rs, Context context, float ratio) {
        v.setDrawingCacheEnabled(true);
        v.buildDrawingCache();
        Bitmap bm = Bitmap.createBitmap(v.getDrawingCache());

        final Allocation input = Allocation.createFromBitmap(rs, bm);
        final Allocation output = Allocation.createTyped(rs, input.getType());
        final ScriptIntrinsicBlur script = ScriptIntrinsicBlur.create(rs, input.getElement());
        script.setRadius(ratio);
        script.setInput(input);
        script.forEach(output);
        output.copyTo(bm);

        return new BitmapDrawable(context.getResources(), bm);


    }

    public static RelativeLayout getFondoMenu() {
        return fondoMenu;
    }

    public static void setFondoMenu(RelativeLayout fondoMenu) {
        Utiles.fondoMenu = fondoMenu;
    }

    public static int getWidth(Activity activity) {
        Display display = activity.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size.x;
    }


    public static String formatNumber(double number) {
        //MODIFICAR SEGUN IDIOMA
        NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.ITALY);
        DecimalFormatSymbols decimalFormatSymbols = ((DecimalFormat) nf).getDecimalFormatSymbols();
        decimalFormatSymbols.setCurrencySymbol("");
        ((DecimalFormat) nf).setDecimalFormatSymbols(decimalFormatSymbols);
        String value = nf.format(number).trim();
        return value;
    }

    public static String formatNumber(BigDecimal number) {
        //MODIFICAR SEGUN IDIOMA
        NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.ITALY);
        DecimalFormatSymbols decimalFormatSymbols = ((DecimalFormat) nf).getDecimalFormatSymbols();
        decimalFormatSymbols.setCurrencySymbol("");
        ((DecimalFormat) nf).setDecimalFormatSymbols(decimalFormatSymbols);
        String value = nf.format(number).trim();
        return value;
    }


    public static void generateAlertInformation(String title, String alertMessage, Activity activity) {

        final Dialog dialog = new Dialog(activity, R.style.MyThemeDialog);
        //final Dialog dialog = new Dialog(activity,android.R.style.Theme_Material_NoActionBar_Fullscreen);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.RED));
        dialog.setContentView(R.layout.dialog_informacion);

        TextView titleTitle = (TextView) dialog.findViewById(R.id.tv_titulo);
        titleTitle.setText(title);

        TextView message = (TextView) dialog.findViewById(R.id.tv_descripcion);
        message.setText(alertMessage);

        RenderScript rs = RenderScript.create(activity);
        LinearLayout bgLayout = (LinearLayout) dialog.findViewById(R.id.ll_image_layout);
        //blur(activity.getWindow().getDecorView().findViewById(android.R.id.content), rs, bgLayout, activity, 4f);

        ImageView image = (ImageView) dialog.findViewById(R.id.iv_cerrar);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    public static void generateAlertDialog(String title, String alertMessage, Activity activity) {

        final Dialog dialog = new Dialog(activity, R.style.MyThemeDialog);
        //final Dialog dialog = new Dialog(activity,android.R.style.Theme_Material_NoActionBar_Fullscreen);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.RED));
        dialog.setContentView(R.layout.alert_pop_up_layout);

        TextView titleTitle = (TextView) dialog.findViewById(R.id.tv_funcion_title);
        titleTitle.setText(title);

        TextView message = (TextView) dialog.findViewById(R.id.tv_alert_message);
        message.setText(alertMessage);

      /*  TextView message = (TextView) dialog.findViewById(R.id.tv_alert_message);
        message.setText(alertMessage); */

        RenderScript rs = RenderScript.create(activity);
        LinearLayout bgLayout = (LinearLayout) dialog.findViewById(R.id.ll_image_layout);
        blur(activity.getWindow().getDecorView().findViewById(android.R.id.content), rs, bgLayout, activity, 4f);

        ImageView image = (ImageView) dialog.findViewById(R.id.icon_cerrar);
        TextView dialogButton = (TextView) dialog.findViewById(R.id.tv_back);
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

    public static void generateAlertDialog_gotoLogin(String title, String alertMessage, Activity activity) {

        final Activity _act = activity;

        final Dialog dialog = new Dialog(activity, R.style.MyThemeDialog);
        //final Dialog dialog = new Dialog(activity,android.R.style.Theme_Material_NoActionBar_Fullscreen);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.RED));
        dialog.setContentView(R.layout.alert_pop_up_layout);

        TextView titleTitle = (TextView) dialog.findViewById(R.id.tv_funcion_title);
        titleTitle.setText(title);

        TextView message = (TextView) dialog.findViewById(R.id.tv_alert_message);
        message.setText(alertMessage);

      /*  TextView message = (TextView) dialog.findViewById(R.id.tv_alert_message);
        message.setText(alertMessage); */

        RenderScript rs = RenderScript.create(activity);
        LinearLayout bgLayout = (LinearLayout) dialog.findViewById(R.id.ll_image_layout);
        blur(activity.getWindow().getDecorView().findViewById(android.R.id.content), rs, bgLayout, activity, 4f);

        ImageView image = (ImageView) dialog.findViewById(R.id.icon_cerrar);
        TextView dialogButton = (TextView) dialog.findViewById(R.id.tv_back);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
               // Singleton singleton = ((Singleton) _act.getApplicationContext());
                //singleton.gotoLogin(_act);
            }
        });
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
               // Singleton singleton = ((Singleton) _act.getApplicationContext());
                //singleton.gotoLogin(_act);
            }
        });

        dialog.show();
    }

    public static void hideSoftKeyboard(Activity activity) {
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
        } catch (Exception e) {
            //TECLADO NO CREADO
        }
    }

    public static void setFocusOnEditText(EditText et, Context context) {
        et.setFocusableInTouchMode(true);
        et.requestFocus();
        final InputMethodManager inputMethodManager = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.showSoftInput(et, InputMethodManager.SHOW_IMPLICIT);
    }

    public static void setDinamicListHeight(ListView listView, ArrayAdapter adapter) {
        int numberOfItems = adapter.getCount();
        int totalItemsHeight = 0;
        for (int itemPos = 0; itemPos < numberOfItems; itemPos++) {
            View item = adapter.getView(itemPos, null, listView);
            item.measure(0, 0);
            totalItemsHeight += item.getMeasuredHeight();
        }
        int totalDividersHeight = listView.getDividerHeight() *
                (numberOfItems - 1);

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalItemsHeight + totalDividersHeight;
        listView.setLayoutParams(params);
        listView.requestLayout();
    }

    public static void setDinamicListHeight_V2(ListView listView, ArrayAdapter adapter) {
        int numberOfItems = adapter.getCount();
        int totalItemsHeight = 0;
        int itemHeight = 0;
        for (int itemPos = 0; itemPos < numberOfItems; itemPos++) {
            View item = adapter.getView(itemPos, null, listView);

            if (item != null) {
                //item.measure(0, 0);
                item.setLayoutParams(new ViewGroup.LayoutParams(0, 0));
                item.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));

                final int w = item.getMeasuredWidth();
                itemHeight = item.getMeasuredHeight();

                totalItemsHeight += itemHeight;
            }
        }
        //int totalDividersHeight = listView.getDividerHeight() *                (numberOfItems - 1);
        int totalDividersHeight = itemHeight / 2;

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalItemsHeight + totalDividersHeight;
        listView.setLayoutParams(params);
        listView.requestLayout();
    }


    public static void setDinamicListHeight_V2_xxxx(ListView listView, ArrayAdapter adapter) {
        int numberOfItems = adapter.getCount();
        int totalItemsHeight = 0;
        for (int itemPos = 0; itemPos < numberOfItems; itemPos++) {
            View item = adapter.getView(itemPos, null, listView);
            if (item != null) {
                item.measure(0, 0);
                totalItemsHeight += item.getMeasuredHeight();
            }
        }
        int totalDividersHeight = listView.getDividerHeight() *
                (numberOfItems - 1);

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalItemsHeight + totalDividersHeight;
        listView.setLayoutParams(params);
        listView.requestLayout();
    }

    public static long MinToMiliSeg(long min) {
        return SegToMiliSeg(min * 60);
    }

    public static long SegToMiliSeg(long seg) {
        return seg * 1000;
    }



    public static String enmascaraImagen(String value) {
        //
        if (value == null) {
            return "";
        }

        int prefijo = 0;
        int sufijo = 0;
        char caracter = '*';

        int medio = value.length() - prefijo - sufijo;
        if (medio <= 0) {
            return value;
        }

        StringBuilder sb = new StringBuilder();
        sb.append(value, 0, 0);
        for (int i = 0; i < medio; i++) {
            sb.append(caracter);
        }
        sb.append(value, value.length() - 0, value.length());

        return sb.toString();

    }

    public static String encodedText(String value) {
        try {
            return Html.fromHtml(new String(value.getBytes(), "UTF-8")).toString();
        } catch (Exception e) {
            return value;
        }
    }

    public static Bitmap getBitmap(String imageBase64) {
        byte[] decodedString = Base64.decode(imageBase64, Base64.DEFAULT);
        byte[] imageB = decodedString;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = false;
        options.inDither = false;
        options.inPreferredConfig = Bitmap.Config.ALPHA_8;
        options.inDensity = 3300;
        Bitmap decodedByte = BitmapFactory.decodeByteArray(imageB, 0, imageB.length, options);
        decodedByte.setHasAlpha(true);
        return decodedByte;
    }

    public static boolean isNullorEmpty(String value) {
        return value == null || value.equalsIgnoreCase("");
    }

    public static boolean isNullorEmpty(String value, String value2) {
        boolean bol1 = true;
        boolean bol2 = true;

        bol1 = value == null || value.equalsIgnoreCase("");

        bol2 = value2 == null || value2.equalsIgnoreCase("");

        return bol1 || bol2;
    }


    public static String getConfig_IP(Activity activity) {
        WifiManager wm = (WifiManager) activity.getSystemService(Context.WIFI_SERVICE);
        String ip = Formatter.formatIpAddress(wm.getConnectionInfo().getIpAddress());
        return ip;
    }

    public static String getConfig_Height(Activity activity) {
        DisplayMetrics metrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);

        return String.valueOf(metrics.heightPixels);
    }

    public static String getConfig_Width(Activity activity) {
        DisplayMetrics metrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);

        return String.valueOf(metrics.widthPixels);
    }

    public static String getConfig_userAgent(Activity activity) {
        return new WebView(activity).getSettings().getUserAgentString();
    }


    //TODO Captura de Pantalla

    public static void takeScreenshot(Activity _activity) {
        Date now = new Date();
        android.text.format.DateFormat.format("yyyy-MM-dd_hh:mm:ss", now);

        try {
            // image naming and path  to include sd card  appending name you choose for file
            String mPath = Environment.getExternalStorageDirectory().toString() + "/Pictures/Screenshots/BOD-" + formatDateToStringFile(now) + ".jpg";
            //String mPath = Environment.getExternalStorageDirectory() + File.separator + "Pictures/" + now + ".jpg";

            // create bitmap screen capture
            View v1 = _activity.getWindow().getDecorView().getRootView();
            v1.setDrawingCacheEnabled(true);
            Bitmap bitmap = Bitmap.createBitmap(v1.getDrawingCache());
            v1.setDrawingCacheEnabled(false);

            File imageFile = new File(mPath);

            FileOutputStream outputStream = new FileOutputStream(imageFile);
            int quality = 100;
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);
            outputStream.flush();
            outputStream.close();


            openScreenshot(_activity, imageFile);


            //_activity.sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED, Uri.parse("file://" + Environment.getExternalStorageDirectory().toString())));


            /*
            ContentValues values = new ContentValues();
            values.put(MediaStore.Images.Media.DATA, mPath);
            values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
            _activity.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            */

        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    private static void openScreenshot(Activity _activity, File imageFile) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        Uri uri = Uri.fromFile(imageFile);
        intent.setDataAndType(uri, "image/*");
        _activity.startActivity(intent);
    }

    public static String getNumber2Mes(String mesNum) {

        String value = "";

        switch (mesNum) {
            case "01":
                value = "Ene";
                break;
            case "02":
                value = "Feb";
                break;
            case "03":
                value = "Mar";
                break;
            case "04":
                value = "Abr";
                break;
            case "05":
                value = "May";
                break;
            case "06":
                value = "Jun";
                break;
            case "07":
                value = "Jul";
                break;
            case "08":
                value = "Ago";
                break;
            case "09":
                value = "Sep";
                break;
            case "10":
                value = "Oct";
                break;
            case "11":
                value = "Nov";
                break;
            case "12":
                value = "Dic";
                break;
        }
        return value;
    }

    public void refreshActivity() {
        //
    }

    public static BigDecimal BigDecimalFormat(String value) {
        try {

            DecimalFormatSymbols symbols = new DecimalFormatSymbols();
            symbols.setGroupingSeparator('.');
            symbols.setDecimalSeparator(',');
            String pattern = "#.##0,0#";
            DecimalFormat decimalFormat = new DecimalFormat(pattern, symbols);
            decimalFormat.setParseBigDecimal(true);

            return (BigDecimal) decimalFormat.parse(value);
        } catch (Exception e) {
            return new BigDecimal(0);
        }
    }


    public static boolean isExpiredSession() {
       return true;
    }

    /*
    public static boolean isExpiredSession(String code) {
        if (code.equalsIgnoreCase(WebConstants.EXPIRED_SESSION_COD)) {
            return true;
        } else {
            return false;
        }
    }
    */

    private static final String SHARED_PREFERENCES_KEY = "APP_BOD_data";
    private static final String SHARED_PREFERENCES_KEY_HELP = "APP_BOD_help";

    public static boolean showTourAyuda_v0(Activity activity) {
        SharedPreferences sPreferences = activity.getSharedPreferences(SHARED_PREFERENCES_KEY, Context.MODE_PRIVATE);
        String value = sPreferences.getString(SHARED_PREFERENCES_KEY_HELP, "0");
        if (value.equalsIgnoreCase("0")) {
            sPreferences.edit().putString(SHARED_PREFERENCES_KEY_HELP, "1").commit();
            return true;
        } else {
            return false;
        }
    }

    public static boolean string2boolean_json(String value) {
        if (value != null) {
            if (value.equalsIgnoreCase("1")) {
                return true;
            } else if (value.equalsIgnoreCase("0")) {
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }
    }

    public static boolean string2boolean_Menujson(String value) {
        if (value.equalsIgnoreCase("1")) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean showTourAyuda(Activity activity) {
        SharedPreferences sharedPref = activity.getSharedPreferences(SHARED_PREFERENCES_KEY, Context.MODE_PRIVATE);
        if (sharedPref.contains(SHARED_PREFERENCES_KEY_HELP)) {
            return false;
        } else {
            int value = sharedPref.getInt(SHARED_PREFERENCES_KEY_HELP, 0);
            return true;
        }
        /*
        int value = sharedPref.getInt(SHARED_PREFERENCES_KEY_HELP, 0);
        if (value == 0) {
            sharedPref.edit().remove(SHARED_PREFERENCES_KEY_HELP).commit();
            sharedPref.edit().putInt(SHARED_PREFERENCES_KEY_HELP, 1).commit();
            return true;
        } else {
            return false;
        }
        */
    }

    public static void lockKeyboard(Activity _activity) {
        _activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    public static void unlockKeyboard(Activity _activity) {
        _activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }


    public static void view_setEneable(ArrayList list, boolean eneable) {
        for (int i = 0; i < list.size(); i++) {
            try {

                if (list.get(i).getClass() == ResideMenu.class) {
                    ((ResideMenu) list.get(i)).setEnabled(eneable);
                } else if (list.get(i).getClass() == STEditText.class) {
                    /*
                    if (!eneable) {
                        ((STEditText) list.get(i)).setVisibility(View.GONE);
                    } else {
                        ((STEditText) list.get(i)).setVisibility(View.VISIBLE);
                    }
                    */
                    ((STEditText) list.get(i)).setFocusable(eneable);
                    ((STEditText) list.get(i)).setEnabled(eneable);
                    ((STEditText) list.get(i)).setFocusableInTouchMode(eneable);
                    //((STEditText) list.get(i)).setClickable(eneable);
                } else if (list.get(i).getClass() == ImageView.class) {
                    ((ImageView) list.get(i)).setClickable(eneable);
                } else if (list.get(i).getClass() == Switch.class) {
                    ((Switch) list.get(i)).setClickable(eneable);
                } else if (list.get(i).getClass() == EditText.class) {
                    ((EditText) list.get(i)).setClickable(eneable);
                    ((View) list.get(i)).setEnabled(eneable);
                } else if (list.get(i).getClass() == CustomViewPager.class) {
                    ((CustomViewPager) list.get(i)).setEnabled(eneable);
                    ((CustomViewPager) list.get(i)).setPagingEnabled(eneable);
                } else if (list.get(i).getClass() == LinearLayout.class) {
                    ((LinearLayout) list.get(i)).setEnabled(eneable);
                } else if (list.get(i).getClass() == View.class) {
                    ((View) list.get(i)).setEnabled(eneable);
                }

            } catch (Exception e) {
                Log.e("eneavleViewChild ", e.getMessage());
            }
        }
    }

    public static Drawable GetImage(Context c, String ImageName) {
        return c.getResources().getDrawable(c.getResources().getIdentifier(ImageName, "drawable", c.getPackageName()));
    }


    public static boolean aumentarDig(float value) {
        int iPart;
        float fPart;
        float unMedio = 0.5f;
        iPart = (int) value;
        fPart = value - iPart;
        if (fPart > unMedio) {
            return true;
        } else {
            return false;
        }
    }

}