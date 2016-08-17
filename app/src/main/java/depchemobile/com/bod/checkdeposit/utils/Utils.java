package depchemobile.com.bod.checkdeposit.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.net.Uri;
import android.util.Base64;
import android.util.Log;
import android.widget.ImageView;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class    Utils {
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



    /*Tecnoware*/
    public static String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }
    public static Bitmap loadBitmap(Context context, Uri uri)
    {
        Bitmap bm = null;
        InputStream is = null;
        BufferedInputStream bis = null;
        try
        {

            is = context.getContentResolver().openInputStream( Uri.parse("file://" +uri));
            bis = new BufferedInputStream(is, 1024);
            bm = BitmapFactory.decodeStream(bis);

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally {
            if (bis != null)
            {
                try
                {
                    bis.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
            if (is != null)
            {
                try
                {
                    is.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
        return bm;
    }

    public static void resize_image(Bitmap bm, Uri uri)
    {


        File f = new File(uri.getPath());

        if (f.exists())
        {
            f.delete();
            try {
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();

               // bm = toGrayscale(bm);
                // Compress the image further
                bm.compress(Bitmap.CompressFormat.JPEG, 10, bytes);
                // Create a new file for the resized bitmap (`getPhotoFileUri` defined above)

                f.createNewFile();
                FileOutputStream fos = new FileOutputStream(f);
                // Write the bytes of the bitmap to file
                fos.write(bytes.toByteArray());
                fos.flush();
                fos.close();
               // return true;
            } catch (IOException e) {
                e.printStackTrace();
                //return  false;
            }
        }







    }

    public static Bitmap toGrayscale(Bitmap bmpOriginal)
    {
        int width, height;
        height = bmpOriginal.getHeight();
        width = bmpOriginal.getWidth();

        Bitmap bmpGrayscale = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
        Canvas c = new Canvas(bmpGrayscale);
        Paint paint = new Paint();
        ColorMatrix cm = new ColorMatrix();
        cm.setSaturation(0);
        ColorMatrixColorFilter f = new ColorMatrixColorFilter(cm);
        paint.setColorFilter(f);
        c.drawBitmap(bmpOriginal, 0, 0, paint);
        return bmpGrayscale;
    }
}