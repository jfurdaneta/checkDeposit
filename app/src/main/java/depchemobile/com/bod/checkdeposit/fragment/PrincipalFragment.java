package depchemobile.com.bod.checkdeposit.fragment;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v8.renderscript.RenderScript;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import depchemobile.com.bod.checkdeposit.R;
import depchemobile.com.bod.checkdeposit.fragmentactivity.PrincipalFragmentActivity;
import depchemobile.com.bod.checkdeposit.utils.Utiles;
import depchemobile.com.bod.checkdeposit.utils.BodConstants;
import depchemobile.com.bod.checkdeposit.web.WebConstants;
import depchemobile.com.bod.checkdeposit.utils.Singleton;
import depchemobile.com.bod.checkdeposit.utils.LoadingDialog;
import com.neopixl.pixlui.components.textview.TextView;
import com.special.ResideMenu.ResideMenu;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Vector;

/**
 * Created by Juan Pablo Amorin M. on 10/03/2015, Rony Rodriguez on 29/07/2015.
 */
public class PrincipalFragment extends Fragment implements View.OnClickListener {

    public View view;
    private ResideMenu resideMenu;
    private PrincipalFragmentActivity parentActivity;


    private boolean doING = false;

    Singleton singleton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.first_main_layout, container, false);

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        parentActivity = (PrincipalFragmentActivity) getActivity();

        parentActivity.setPrincipalFragment(this);


        resideMenu = parentActivity.getResideMenu();

        parentActivity.setTile("Panel Principal");
        parentActivity.setHeaderIcon(getActivity().getResources().getDrawable(R.drawable.panel_financiero_white));







        return view;
    }





    @Override
    public void onClick(View v) {


    }



}