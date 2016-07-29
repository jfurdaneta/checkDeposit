package depchemobile.com.bod.checkdeposit.fragment;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import depchemobile.com.bod.checkdeposit.R;
import depchemobile.com.bod.checkdeposit.activity.ListaChequesActivity;
import depchemobile.com.bod.checkdeposit.entidades.Cheque;

/**
 * A placeholder fragment containing a simple view.
 */
public class ChequeScanFragment extends Fragment {

    public ChequeScanFragment() {
    }

    private static final int ACTION_TAKE_PHOTO_FRONT = 1;
    private static final int ACTION_TAKE_PHOTO_BACK = 2;
    private static final int ACTION_TAKE_VIDEO = 3;

    private  final int PIC_CROP_FRONT = 4;
    private  final int PIC_CROP_BACK = 5;
    private final int CAMERA_CAPTURE = 1;

    Cheque chequeObject;

    private static final String BITMAP_STORAGE_KEY = "viewbitmap";
    private static final String IMAGEVIEW_VISIBILITY_STORAGE_KEY = "imageviewvisibility";
    private ImageView mImageView,mImageViewFront,mImageViewBack;
    private Bitmap mImageBitmap;
    Button btnContinuar;
    Button btnFrontal;
    Button   btnAnverso;
    EditText txtMonto;
    LinearLayout divMonto;

    private static final String VIDEO_STORAGE_KEY = "viewvideo";
    private static final String VIDEOVIEW_VISIBILITY_STORAGE_KEY = "videoviewvisibility";

    private Uri mVideoUri;

    private Uri picUri;

    private String mCurrentPhotoPath;

    private static final String JPEG_FILE_PREFIX = "IMG_";
    private static final String JPEG_FILE_SUFFIX = ".jpg";

    private AlbumStorageDirFactory mAlbumStorageDirFactory = null;


    /* Photo album for this application */
    private String getAlbumName() {
        return getString(R.string.album_name);
    }


    private File getAlbumDir() {
        File storageDir = null;

        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {

            storageDir = mAlbumStorageDirFactory.getAlbumStorageDir(getAlbumName());

            if (storageDir != null) {
                if (! storageDir.mkdirs()) {
                    if (! storageDir.exists()){
                        Log.d("CameraSample", "failed to create directory");
                        return null;
                    }
                }
            }

        } else {
            Log.v(getString(R.string.app_name), "External storage is not mounted READ/WRITE.");
        }

        return storageDir;
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = JPEG_FILE_PREFIX + timeStamp + "_";
        File albumF = getAlbumDir();
        File imageF = File.createTempFile(imageFileName, JPEG_FILE_SUFFIX, albumF);
        return imageF;
    }

    private File setUpPhotoFile() throws IOException {

        File f = createImageFile();
        mCurrentPhotoPath = f.getAbsolutePath();

        if (f.exists())
        {
            Log.v(this.getClass().getName(),"SI EXISTE- mCurrentPhotoPath:" + mCurrentPhotoPath);
        }
        else
        {
            Log.v(this.getClass().getName(),"NO EXISTE " + mCurrentPhotoPath);
        }

        Log.v(this.getClass().getName(),"setUpPhotoFile - mCurrentPhotoPath:" + mCurrentPhotoPath);

        return f;
    }

    private Bitmap setPic(ImageView im) {

		/* There isn't enough memory to open up more than a couple camera photos */
		/* So pre-scale the target bitmap into which the file is decoded */

        Log.v(this.getClass().getName(),"setPic - Inicando:");
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

    private void galleryAddPic(String mCurrentPhotoPath) {
        Log.v(this.getClass().getName(),"galleryAddPic - Iniciando");
        Intent mediaScanIntent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
        File f = new File(mCurrentPhotoPath);
        Uri contentUri = Uri.fromFile(f);

        mediaScanIntent.setData(contentUri);
        getContext().sendBroadcast(mediaScanIntent);
        Log.v(this.getClass().getName(),"galleryAddPic - Final");
    }

    private void galleryDeletePic(String mCurrentPhotoPath) {
        Log.v(this.getClass().getName(),"galleryAddPic - Iniciando");
        Intent mediaScanIntent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
        File f = new File(mCurrentPhotoPath);
        Uri contentUri = Uri.fromFile(f);

        mediaScanIntent.setData(contentUri);
        getContext().sendBroadcast(mediaScanIntent);
        Log.v(this.getClass().getName(),"galleryAddPic - Final");
    }

    private void performCrop(int pic_crop){

        try {

            Intent cropIntent = new Intent("com.android.camera.action.CROP");
            //indicate image type and Uri
            //File f = new File(mCurrentPhotoPath);
            //Uri contentUri = Uri.fromFile(f);

            cropIntent.setDataAndType(picUri, "image/*");

            //set crop properties
            cropIntent.putExtra("crop", "true");
            cropIntent.putExtra("scaleType", "centerCrop");
            //indicate aspect of desired crop
            cropIntent.putExtra("aspectX", 0);
            cropIntent.putExtra("aspectY", 0);
            //indicate output X and Y
            cropIntent.putExtra("outputX", 300);
            cropIntent.putExtra("outputY", 300);
            cropIntent.putExtra("scale", true);
            //retrieve data on return
            cropIntent.putExtra("return-data", true);
            //start the activity - we handle returning in onActivityResult
            startActivityForResult(cropIntent, pic_crop);

        }
        catch(ActivityNotFoundException anfe){
            //display an error message
            String errorMessage = "Whoops - your device doesn't support the crop action!";
            Toast toast = Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_SHORT);
            toast.show();
        }

    }



    private void dispatchTakePictureIntent(int actionCode) {

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);


        File f = null;

        try {
            f = setUpPhotoFile();
            mCurrentPhotoPath = f.getAbsolutePath();
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
        } catch (IOException e) {
            e.printStackTrace();
            f = null;
            mCurrentPhotoPath = null;
        }


        startActivityForResult(takePictureIntent, actionCode);
    }

    private void dispatchTakeVideoIntent() {
        Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        startActivityForResult(takeVideoIntent, ACTION_TAKE_VIDEO);
    }

    private void handleSmallCameraPhoto(Intent intent) {
        Bundle extras = intent.getExtras();
        mImageBitmap = (Bitmap) extras.get("data");
        mImageView.setImageBitmap(mImageBitmap);
        mVideoUri = null;
        mImageView.setVisibility(View.VISIBLE);

    }

    private void handleBigCameraPhoto() {

        Log.v(this.getClass().getName(),"handleBigCameraPhoto - mCurrentPhotoPath: " + mCurrentPhotoPath);
        if (mCurrentPhotoPath != null) {
            //setPic();
            //performCrop();
            //galleryAddPic();
            mCurrentPhotoPath = null;
        }

    }



    Button.OnClickListener mTakePicOnClickListener =
            new Button.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dispatchTakePictureIntent(ACTION_TAKE_PHOTO_FRONT);
                }
            };




    ImageView.OnClickListener mViewPicFrontOnClickListener =
            new ImageView.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.setDataAndType( chequeObject.getImgChequeFront(), "image/*");
                    startActivity(intent);


                }
            };
    ImageView.OnClickListener mViewPicBackOnClickListener =
            new ImageView.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.setDataAndType( chequeObject.getImgChequeBack(), "image/*");
                    startActivity(intent);



                }
            };


    Button.OnClickListener mTakePicSOnClickListener =
            new Button.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dispatchTakePictureIntent(ACTION_TAKE_PHOTO_BACK);
                }
            };

    Button.OnClickListener mTakeVidOnClickListener =
            new Button.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dispatchTakeVideoIntent();
                }
            };



    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getActivity().getIntent();
        if(intent.hasExtra("cheque"))
        {
            chequeObject = intent.getExtras().getParcelable("cheque");
        }






    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == Activity.RESULT_OK)
        {


            File f = new File(mCurrentPhotoPath);
            picUri = Uri.fromFile(f);

            Log.v(getClass().getName(),"onActivityResult - picUri.getEncodedPath " + picUri.getEncodedPath());
            Log.v(getClass().getName(),"onActivityResult - picUri.getPath " + picUri.getPath());
            Log.v(getClass().getName(),"onActivityResult - picUri.toString " + picUri.toString());

            Bitmap myBitmap = BitmapFactory.decodeFile(mCurrentPhotoPath);




            switch (requestCode) {
                case ACTION_TAKE_PHOTO_FRONT: {



                    chequeObject.setImgChequeFront(picUri);
                    mImageViewFront.setImageBitmap(setPic(mImageViewFront));
                    btnAnverso.setEnabled(true);


                    mImageViewFront.setVisibility(View.VISIBLE);
                    mImageViewFront.setClickable(true);




                    //performCrop(PIC_CROP_FRONT);
                    //handleBigCameraPhoto();

                    break;
                } // ACTION_TAKE_PHOTO_B

                case ACTION_TAKE_PHOTO_BACK: {


                    chequeObject.setImgChequeBack(picUri);
                    mImageViewBack.setImageBitmap(setPic(mImageViewBack));
                    mImageViewBack.setVisibility(View.VISIBLE);
                    mImageViewBack.setClickable(true);

                    // handleSmallCameraPhoto(data);

                    //performCrop(PIC_CROP_BACK);


                    break;
                } // ACTION_TAKE_PHOTO_S
                case PIC_CROP_FRONT:
                {
                    Bundle extras = data.getExtras();
                    Bitmap thePic = extras.getParcelable("data");
                    try {
                        FileOutputStream fOut = new FileOutputStream(f);
                        thePic.compress(Bitmap.CompressFormat.JPEG,100,fOut);
                        fOut.flush();
                        fOut.close();

                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }




                    chequeObject.setImgChequeFront(picUri);
                    mImageViewFront.setImageBitmap(thePic);
                    mImageViewFront.setVisibility(View.VISIBLE);
                    break;
                }
                case PIC_CROP_BACK:
                {
                    Bundle extras = data.getExtras();
                    Bitmap thePic = extras.getParcelable("data");
                    mImageViewBack.setImageBitmap(thePic);

                    try {
                        FileOutputStream fOut = new FileOutputStream(f);
                        thePic.compress(Bitmap.CompressFormat.JPEG,80,fOut);
                        fOut.flush();
                        fOut.close();

                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    chequeObject.setImgChequeBack(picUri);
                    mImageViewBack.setVisibility(View.VISIBLE);


                    break;

                }



            } // switch

            if(chequeObject.getImgChequeBack()!=null && chequeObject.getImgChequeFront()!=null)
            {
                divMonto.setEnabled(true);
                txtMonto.setEnabled(true);

            }



        }





    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(BITMAP_STORAGE_KEY, mImageBitmap);
        outState.putParcelable(VIDEO_STORAGE_KEY, mVideoUri);
        outState.putBoolean(IMAGEVIEW_VISIBILITY_STORAGE_KEY, (mImageBitmap != null) );
        outState.putBoolean(VIDEOVIEW_VISIBILITY_STORAGE_KEY, (mVideoUri != null) );
        super.onSaveInstanceState(outState);
    }




    /**
     * Indicates whether the specified action can be used as an intent. This
     * method queries the package manager for installed packages that can
     * respond to an intent with the specified action. If no suitable package is
     * found, this method returns false.
     * http://android-developers.blogspot.com/2009/01/can-i-use-this-intent.html
     *
     * @param context The application's environment.
     * @param action The Intent action to check for availability.
     *
     * @return True if an Intent with the specified action can be sent and
     *         responded to, false otherwise.
     */
    public static boolean isIntentAvailable(Context context, String action) {
        final PackageManager packageManager = context.getPackageManager();
        final Intent intent = new Intent(action);
        List<ResolveInfo> list =
                packageManager.queryIntentActivities(intent,
                        PackageManager.MATCH_DEFAULT_ONLY);
        return list.size() > 0;
    }

    private void setBtnListenerOrDisable(
            Button btn,
            Button.OnClickListener onClickListener,
            String intentName
    ) {
        if (isIntentAvailable(getActivity(), intentName)) {
            btn.setOnClickListener(onClickListener);
        } else {
            btn.setText(
                    getText(R.string.cannot).toString() + " " + btn.getText());
            btn.setClickable(false);
        }
    }


    private void setImgListenerOrDisable(
            ImageView img,
            ImageView.OnClickListener onClickListener,
            String intentName
    ) {
        if (isIntentAvailable(getActivity(), intentName)) {
            img.setOnClickListener(onClickListener);
        } else {

            img.setClickable(false);
        }
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.lista_cheques_fragment,container);
        mImageViewBack = (ImageView) rootView.findViewById(R.id.imageViewTrasera);
        mImageViewFront = (ImageView) rootView.findViewById(R.id.imageViewFrente);
        divMonto = (LinearLayout)  rootView.findViewById(R.id.divMonto);
        btnFrontal = (Button) rootView.findViewById(R.id.btnIntend);
        txtMonto = (EditText) rootView.findViewById(R.id.txtMonto);
        btnContinuar = (Button) rootView.findViewById(R.id.btnContinuar);
        btnAnverso = (Button) rootView.findViewById(R.id.btnIntendS);
        setImgListenerOrDisable(mImageViewFront,mViewPicFrontOnClickListener,Intent.ACTION_VIEW);
        setImgListenerOrDisable(mImageViewBack,mViewPicBackOnClickListener,Intent.ACTION_VIEW);
        mAlbumStorageDirFactory = new BaseAlbumDirFactory();

        setBtnListenerOrDisable(
                btnFrontal,
                mTakePicOnClickListener,
                MediaStore.ACTION_IMAGE_CAPTURE
        );



        setBtnListenerOrDisable(
                btnAnverso,
                mTakePicSOnClickListener,
                MediaStore.ACTION_IMAGE_CAPTURE
        );

        txtMonto.setOnEditorActionListener(new TextView.OnEditorActionListener() {


            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_DONE)
                {
                    if(!txtMonto.getText().toString().trim().equals(""))
                    {


                        try {
                            Double fMonto = Double.parseDouble(txtMonto.getText().toString().trim());

                            if(fMonto>0)
                            {

                                chequeObject.setMonto(fMonto);
                                btnContinuar.setEnabled(true);
                                return false;
                            }
                            else
                            {
                                btnContinuar.setEnabled(false);
                            }

                        }
                        catch (Exception e)
                        {
                            btnContinuar.setEnabled(false);
                            return true;

                        }


                    }
                    else
                        btnContinuar.setEnabled(false);


                }
                return false;
            }
        });

        btnContinuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getContext(),ListaChequesActivity.class);
                Bundle mBundle = new Bundle();

                chequeObject.setNumCuenta("11998490");
                //galleryAddPic(chequeObject.getImgChequeFront().getPath());
                //galleryAddPic(chequeObject.getImgChequeBack().getPath());


                mBundle.putParcelable("cheque",chequeObject);
                intent.putExtras(mBundle);
                startActivity(intent);
            }
        });

        if(chequeObject==null)
        {
            chequeObject = new Cheque();
            chequeObject.setFechaProceso(new Date());
            mImageViewFront.setClickable(false);
            mImageViewBack.setClickable(false);

            divMonto.setEnabled(false);
            txtMonto.setEnabled(false);
            btnAnverso.setEnabled(false);
            mImageBitmap = null;
            mVideoUri = null;
            btnContinuar.setEnabled(false);


        }
        else
        {
            mImageViewFront.setClickable(true);
            mImageViewBack.setClickable(true);
            divMonto.setEnabled(true);
            txtMonto.setEnabled(true);
            btnContinuar.setEnabled(true);
            mImageViewBack.setImageURI(chequeObject.getImgChequeBack());
            mImageViewFront.setImageURI(chequeObject.getImgChequeFront());
            txtMonto.setText(chequeObject.getMonto().toString());
            btnAnverso.setEnabled(true);
        }




        return rootView;

    }
}
