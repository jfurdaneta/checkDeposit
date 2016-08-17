package depchemobile.com.bod.checkdeposit.fragment;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import depchemobile.com.bod.checkdeposit.R;
import depchemobile.com.bod.checkdeposit.activity.ListaChequesActivity;
import depchemobile.com.bod.checkdeposit.data.CheckDepositDbHelper;
import depchemobile.com.bod.checkdeposit.entidades.Cheque;
import depchemobile.com.bod.checkdeposit.fragmentactivity.PrincipalFragmentActivity;
import depchemobile.com.bod.checkdeposit.utils.BodConstants;
import depchemobile.com.bod.checkdeposit.utils.Convertidor;
import depchemobile.com.bod.checkdeposit.utils.MarshMallowPermission;
import depchemobile.com.bod.checkdeposit.utils.STEditText;
import depchemobile.com.bod.checkdeposit.utils.Utiles;

/**
 * A placeholder fragment containing a simple view.
 */

public class ChequeScanFragment extends Fragment {

    public ChequeScanFragment() {
    }

    MarshMallowPermission marshMallowPermission;

    private PrincipalFragmentActivity parentActivity;

    private static final int ACTION_TAKE_PHOTO_FRONT = 1;
    private static final int ACTION_TAKE_PHOTO_BACK = 2;
    private static final int ACTION_TAKE_VIDEO = 3;

    CheckDepositDbHelper depositDbHelper;

    private boolean modoEdit;
    private STEditText montoEditText;
    private RelativeLayout layoutMonto;

    private boolean isCanSave;

    private  final int PIC_CROP_FRONT = 4;
    private  final int PIC_CROP_BACK = 5;
    private final int CAMERA_CAPTURE = 1;

    Cheque chequeObject;
    long chequeID;

    boolean montoEnabled;
    private ImageView montoEditButton;
    private boolean editing;
    private String montoString;
    private double monto;
    private int montoLenght;
    private String formato_bsF = "Bs. ";

    private TextView cancelTransferText;
    private ImageView cancelTransferImage;

    private ImageView continueTransferImageView;
    private TextView continueTransferTextView;

    private static final String BITMAP_STORAGE_KEY = "viewbitmap";
    private static final String IMAGEVIEW_VISIBILITY_STORAGE_KEY = "imageviewvisibility";
    private ImageView mImageView,mImageViewFront,mImageViewBack;
    private Bitmap mImageBitmap;

    Button btnFrontal;
    Button   btnAnverso;
    EditText txtMonto;
    LinearLayout divMonto;
    String account;
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




    private void galleryAddPic(String mCurrentPhotoPath) {
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




    //Guarda la imagen en la ruta DCIM \nombre de la app
    private void dispatchTakePictureIntent(int actionCode) {
        if (!marshMallowPermission.checkPermissionForCamera())
            marshMallowPermission.requestPermissionForCamera();
        else
            if (!marshMallowPermission.checkPermissionForExternalStorage())
                marshMallowPermission.requestPermissionForExternalStorage();
            else
            {
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
                    Uri hacked_uri = Uri.parse("file://" + chequeObject.getImgChequeFront().getPath());
                    intent.setDataAndType(hacked_uri, "image/*");
                    startActivity(intent);


                }
            };
    ImageView.OnClickListener mViewPicBackOnClickListener =
            new ImageView.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    Uri hacked_uri = Uri.parse("file://" + chequeObject.getImgChequeBack().getPath());

                    intent.setDataAndType( hacked_uri, "image/*");
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






    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);







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
            Log.v(getClass().getName(),"onActivityResult - f.getAbsolutePath " + f.getAbsolutePath());





            switch (requestCode) {
                case ACTION_TAKE_PHOTO_FRONT: {
                    chequeObject.setImgChequeFront(picUri);
                    loadBitmap(picUri.getPath(),mImageViewFront);
                    btnAnverso.setEnabled(true);
                    mImageViewFront.setVisibility(View.VISIBLE);
                    mImageViewFront.setClickable(true);
                    //performCrop(PIC_CROP_FRONT);
                    //handleBigCameraPhoto();
                    break;
                } // ACTION_TAKE_PHOTO_B

                case ACTION_TAKE_PHOTO_BACK: {
                    chequeObject.setImgChequeBack(picUri);
                    loadBitmap(picUri.getPath(),mImageViewBack);
                    mImageViewBack.setVisibility(View.VISIBLE);
                    mImageViewBack.setClickable(true);
                    activateLayoutMonto();
                    break;
                } // ACTION_TAKE_PHOTO_S
            } // switch

            if(chequeObject.getImgChequeBack()!=null && chequeObject.getImgChequeFront()!=null)
            {

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

       // Intent intent = getActivity().getIntent();
        View rootView = inflater.inflate(R.layout.cheque_scan_fragment,container,false);

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        parentActivity = (PrincipalFragmentActivity) getActivity();

       marshMallowPermission = new MarshMallowPermission(parentActivity);




        btnAnverso = (Button) rootView.findViewById(R.id.btnIntendS);
        layoutMonto = (RelativeLayout) rootView.findViewById(R.id.layoutTransferenciaMonto);
        editing = false;
        montoLenght = 0;
        montoString = "";


        mImageViewBack = (ImageView) rootView.findViewById(R.id.imageViewTrasera);
        mImageViewFront = (ImageView) rootView.findViewById(R.id.imageViewFrente);

        setImgListenerOrDisable(mImageViewFront,mViewPicFrontOnClickListener,Intent.ACTION_VIEW);
        setImgListenerOrDisable(mImageViewBack,mViewPicBackOnClickListener,Intent.ACTION_VIEW);

        btnFrontal = (Button) rootView.findViewById(R.id.btnIntend);
        //txtMonto = (EditText) rootView.findViewById(R.id.txtMonto);

        montoEditText = (STEditText) rootView.findViewById(R.id.et_monto);

        mAlbumStorageDirFactory = new BaseAlbumDirFactory();
        modoEdit = false;
        depositDbHelper = new CheckDepositDbHelper(getActivity());
        chequeID = parentActivity.getChequeID();

        if(chequeID > 0)
        {
            modoEdit = true;

            Cursor cursor = depositDbHelper.getChequeById(chequeID);
            if(cursor!=null)
            {
                modoEdit = true;
                chequeObject = Convertidor.llenarCheque(cursor);
            }
        }
        else
            chequeObject = null;

        setBtnListenerOrDisable(
                btnFrontal,
                mTakePicOnClickListener,
                MediaStore.ACTION_IMAGE_CAPTURE
        );



        isCanSave = false;
        cancelTransferText = (TextView) rootView.findViewById(R.id.tv_back);
        cancelTransferText.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if(chequeID>0)
                {
                    ListaChequesFragment fragment = new ListaChequesFragment();
                    parentActivity.setTile("Listado de Cheques");
                    parentActivity.changeFragment(fragment);
                }
                else
                {
                    PrincipalFragment fragment = new PrincipalFragment();
                    parentActivity.changeFragment(fragment);
                }



            }
        });

        continueTransferImageView = (ImageView) rootView.findViewById(R.id.iv_next_transfer);
        continueTransferTextView = (TextView) rootView.findViewById(R.id.tv_next_transfer);

        continueTransferTextView.setVisibility(View.GONE);
        continueTransferImageView.setVisibility(View.GONE);



        continueTransferTextView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                if (!isCanSave)
                    return;

                if (monto == 0) {
                    Utiles.generateAlertDialog("Mensaje", "El monto debe ser mayor a cero", getActivity());
                    return;
                }

                chequeObject.setNumCuenta(account);
                chequeObject.setMismoBanco(true);

                if(!modoEdit)
                {
                    chequeObject.setNombreBanco("BODsillo");
                    depositDbHelper.insertarCheque(chequeObject);
                }
                else
                {
                    //Aqui va update
                    depositDbHelper.updateCheque(chequeObject,chequeID);
                }
                // startActivity(intent);
                //  getActivity().finish();

                Fragment fragment;
                fragment = new ListaChequesFragment();
                parentActivity.setTile("Listado de Cheques");
                parentActivity.setHeaderIcon(ContextCompat.getDrawable(getContext(), R.drawable.pagos_transferencias_white));
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_fragment, fragment, "fragment")
                        .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE).addToBackStack(null)
                        .commit();


            }
        });

        cancelTransferImage = (ImageView) rootView.findViewById(R.id.iv_back);
        cancelTransferImage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if(chequeID>0)
                {
                    ListaChequesFragment fragment = new ListaChequesFragment();
                    parentActivity.setTile("Listado de Cheques");
                    parentActivity.changeFragment(fragment);
                }
                else
                {
                    PrincipalFragment fragment = new PrincipalFragment();
                    parentActivity.changeFragment(fragment);
                }
            }
        });






        montoEditText.setOnEditorActionListener(new android.widget.TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(android.widget.TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {

                 chequeObject.setMonto(monto);
                    if (monto > 0) {
                        __action_monto();
                    } else {
                        Utiles.generateAlertDialog(BodConstants.tituloMensaje, "El monto debe ser mayor a cero", getActivity());
                    }
                }
                return false;
            }
        });

        montoEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               montoEditText.setSelection(montoEditText.getText().length());
            }
        });

        montoEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {



                if (!editing) {

                    editing = true;
                    String tempString = s.toString();
                    try {

                        if (s.length() > montoLenght) {
                            montoString = montoString + tempString.substring(tempString.length() - 1);
                            monto = Double.parseDouble(montoString);
                            monto = monto / 100.0d;
                            montoEditText.setText(formato_bsF + Utiles.formatNumber(monto));
                        } else {
                            if (montoString.length() > 0) {
                                montoString = montoString.substring(0, montoString.length() - 1);
                                monto = Double.parseDouble(montoString);
                                monto = monto / 100.0d;
                                montoEditText.setText(formato_bsF + Utiles.formatNumber(monto));
                            } else {

                                montoEditText.setText("");
                                montoString = "";
                                monto = 0;
                            }
                        }
                        montoLenght = montoEditText.getText().toString().length();
                    } catch (Exception e) {
                        montoEditText.setText(formato_bsF + Utiles.formatNumber(monto));
                    }
                    editing = false;
                    montoEditText.setSelection(montoEditText.getText().length());
                }
            }

        });


        montoEditButton = (ImageView) rootView.findViewById(R.id.ib_monto_edit_button);
        montoEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                montoEditText.setFocusableInTouchMode(true);
                montoEnabled = true;
                montoEditButton.animate().alpha(0).setDuration(500).start();
                montoEditText.setBackgroundColor(getActivity().getResources().getColor(R.color.bod_fondo_text));


                continueTransferTextView.setVisibility(View.GONE);
                continueTransferImageView.setVisibility(View.GONE);
            }
        });
        montoEditButton.animate().alpha(0).setDuration(0).start();
        montoEnabled = true;









        setBtnListenerOrDisable(
                btnAnverso,
                mTakePicSOnClickListener,
                MediaStore.ACTION_IMAGE_CAPTURE
        );

        Spinner spinner = (Spinner) rootView.findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getContext(), R.array.account_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                account = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        if(chequeObject==null)
        {
            chequeObject = new Cheque();
            Calendar calendar = Calendar.getInstance();
            Date date =  calendar.getTime();
            chequeObject.setFechaProceso(date);
            mImageViewFront.setClickable(false);
            mImageViewBack.setClickable(false);
            deactivateLayoutMonto();
            btnAnverso.setEnabled(false);
            mImageBitmap = null;
            mVideoUri = null;



        }
        else
        {

            monto = chequeObject.getMonto();
            editing = true;
            montoEditText.setText(formato_bsF + Utiles.formatNumber(monto));
            String tmpString =String.valueOf(monto*100);
            tmpString = tmpString.substring(0,tmpString.indexOf("."));
            editing = false;
            montoString =tmpString;
            activateLayoutMonto();

            btnAnverso.setEnabled(true);
            //Carga las imagenes asíncronas
            loadBitmap( chequeObject.getImgChequeBack().getPath() ,mImageViewBack);
            loadBitmap( chequeObject.getImgChequeFront().getPath() ,mImageViewFront);

            mImageViewFront.setClickable(true);
            mImageViewFront.setVisibility(View.VISIBLE);
            mImageViewBack.setVisibility(View.VISIBLE);
            mImageViewBack.setClickable(true);
            account = chequeObject.getNumCuenta();
            int spinnerPosition = adapter.getPosition(account);
            spinner.setSelection(spinnerPosition);




        }







        return rootView;

    }

    public void activateLayoutMonto() {
        layoutMonto.setVisibility(View.VISIBLE);
        layoutMonto.setPivotY(0);
        layoutMonto.animate().scaleY(1).alpha(1).setDuration(500).start();
        setEditableMontoedit();
    }
    public void setEditableMontoedit() {
        montoEditText.setFocusableInTouchMode(true);
        montoEnabled = true;
        montoEditButton.animate().alpha(0).setDuration(500).start();
        montoEditText.setBackgroundColor(getActivity().getResources().getColor(R.color.bod_fondo_text));
    }

    public void deactivateLayoutMonto() {
        layoutMonto.animate().alpha(0).scaleY(0).start();
        layoutMonto.setVisibility(View.GONE);

    }

    public void __action_monto() {

        continueTransferTextView.setVisibility(View.VISIBLE);
        continueTransferImageView.setVisibility(View.VISIBLE);

        int imageResource = getResources().getIdentifier("@drawable/continuar_fondoverde", null, parentActivity.getPackageName());
        Drawable res = getResources().getDrawable(imageResource);
        continueTransferImageView.setImageDrawable(res);

        isCanSave = true;
        montoEditText.setBackgroundColor(getActivity().getResources().getColor(R.color.bod_blanco));
        montoEditButton.animate().alpha(1).setDuration(400).start();
        montoEditText.setFocusable(false);
        Utiles.hideSoftKeyboard(getActivity());
        montoEnabled = false;
    }


    //Método que carga las imágenes de manera asíncrona para optimizar memoria
    public void loadBitmap(String path, ImageView imageView) {
        BitmapWorkerTask task = new BitmapWorkerTask(imageView);
        task.execute(path);
    }




    class BitmapWorkerTask extends AsyncTask<String, Void, Bitmap> {
        private final WeakReference<ImageView> imageViewReference;
        private String data = "";

        public BitmapWorkerTask(ImageView imageView) {
            // Use a WeakReference to ensure the ImageView can be garbage collected
            imageViewReference = new WeakReference<ImageView>(imageView);
        }



        @Override
        protected Bitmap doInBackground(String... params) {
            data = params[0];
            Bitmap bitmap =decodeBitmapFromFile(data, 100, 100);
            return bitmap;
        }

        // Once complete, see if ImageView is still around and set bitmap.
        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (imageViewReference != null && bitmap != null) {
                final ImageView imageView = imageViewReference.get();
                if (imageView != null) {
                    imageView.setImageBitmap(bitmap);
                }
            }
        }
    }

    public static Bitmap decodeBitmapFromFile(String path,int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return  BitmapFactory.decodeFile(path, options);
    }

    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) >= reqHeight
                    && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }
}
