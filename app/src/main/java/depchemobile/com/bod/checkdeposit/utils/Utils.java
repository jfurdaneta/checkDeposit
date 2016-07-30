package depchemobile.com.bod.checkdeposit.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.ImageView;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Utils {
    public static void CopyStream(InputStream is, OutputStream os)
    {
        final int buffer_size=1024;
        try
        {
            byte[] bytes=new byte[buffer_size];
            for(;;)
            {
              int count=is.read(bytes, 0, buffer_size);
              if(count==-1)
                  break;
              os.write(bytes, 0, count);
            }
        }
        catch(Exception ex){}
    }

    public  static String FormateadorFecha(Date fecha)
    {
        SimpleDateFormat formatter;
        formatter = new SimpleDateFormat("dd/MM/yyyy\t", Locale.getDefault());

        return formatter.format(fecha);

    }


    public Bitmap setPic(ImageView im, String mCurrentPhotoPath) {

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

    public static void backButtonHandler(final Activity context) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                context);
        // Setting Dialog Title
        alertDialog.setTitle("¿Salir de la aplicación?");
        // Setting Dialog Message
        alertDialog.setMessage("¿Está seguro que desea salir de la aplicación?");
        // Setting Icon to Dialog

        // Setting Positive "Yes" Button
        alertDialog.setPositiveButton("SI",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        context.finish();
                    }
                });
        // Setting Negative "NO" Button
        alertDialog.setNegativeButton("NO",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Write your code here to invoke NO event
                        dialog.cancel();
                    }
                });
        // Showing Alert Message
        alertDialog.show();
    }
}