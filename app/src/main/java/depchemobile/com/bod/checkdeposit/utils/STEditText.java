package depchemobile.com.bod.checkdeposit.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;

import com.neopixl.pixlui.components.edittext.EditText;

/**
 * Created by Rony R. Diaz M.  on 25/05/2015.
 */
public class STEditText extends EditText {
    public STEditText(Context context) {
        super(context);
    }

    public STEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public STEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onKeyPreIme(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
            return true;
        }
        return super.dispatchKeyEvent(event);
    }
}
