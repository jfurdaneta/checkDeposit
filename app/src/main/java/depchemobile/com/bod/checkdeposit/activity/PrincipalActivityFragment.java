package depchemobile.com.bod.checkdeposit.activity;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import depchemobile.com.bod.checkdeposit.R;


/**
 * A placeholder fragment containing a simple view.
 */
public class PrincipalActivityFragment extends Fragment {

    public PrincipalActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.cheque_scan_fragment,container,false);
        return rootView;
    }
}
