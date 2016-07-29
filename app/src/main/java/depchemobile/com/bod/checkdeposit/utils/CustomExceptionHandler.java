package depchemobile.com.bod.checkdeposit.utils;

import android.util.Log;

/**
 * Created by Rony Diaz on 28/04/16.
 */
public class CustomExceptionHandler implements Thread.UncaughtExceptionHandler {

    private Thread.UncaughtExceptionHandler defaultUEH;

    public CustomExceptionHandler() {
        this.defaultUEH = Thread.getDefaultUncaughtExceptionHandler();
    }

    public void uncaughtException(Thread t, Throwable e) {
        Log.e("Tag", "ERROR-APP" + " " + e.getMessage().toString(), e);
        defaultUEH.uncaughtException(t, e);
    }
}
