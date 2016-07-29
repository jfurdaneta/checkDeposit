package depchemobile.com.bod.checkdeposit.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import depchemobile.com.bod.checkdeposit.R;

/**
 * Created by Rony R. Diaz M.  on 28/04/2015.
 */
public class CustomProgressDialog extends ProgressDialog {


    public CustomProgressDialog(Context context) {
        super(context);
    }

    public static ProgressDialog constructor(Context context) {
        CustomProgressDialog dialog = new CustomProgressDialog(context);
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        return dialog;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_progress_dialog_layout);
    }

    public void show() {
        super.show();
       // animation.start();
    }

    @Override
    public void dismiss() {
        super.dismiss();
       // animation.stop();
    }
}
