package depchemobile.com.bod.checkdeposit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.special.ResideMenu.ResideMenu;
import com.special.ResideMenu.ResideMenuItem;

import depchemobile.com.bod.checkdeposit.utils.Utiles;

public class MainActivity extends AppCompatActivity {
    private ResideMenu resideMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadMENU();
    }
    public void loadMENU() {
        Log.v(this.getClass().getName(),"loadMENU - Entrando");
        resideMenu = new ResideMenu(this);
        resideMenu.setBackground(R.drawable.fondo_menu);
        resideMenu.attachToActivity(this);
        resideMenu.setScaleValue(0.5f);
        resideMenu.setSwipeDirectionDisable(ResideMenu.DIRECTION_RIGHT);
        // create menu items;
        String titles[] = { "Cerrar sesión" };
        int icon[] = { R.drawable.cerrar_sesion_white};

        for (int i = 0; i < titles.length; i++){
            ResideMenuItem item = new ResideMenuItem(this, icon[i], titles[i]);
            //item.setOnClickListener(this);
            resideMenu.addMenuItem(item,  ResideMenu.DIRECTION_LEFT); // or  ResideMenu.DIRECTION_RIGHT
        }
    }
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return resideMenu.dispatchTouchEvent(ev);
    }
}
