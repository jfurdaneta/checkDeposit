package depchemobile.com.bod.checkdeposit.activity;

/**
 * Created by Rony Díaz
 */

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import depchemobile.com.bod.checkdeposit.R;
import depchemobile.com.bod.checkdeposit.utils.Singleton;
import depchemobile.com.bod.checkdeposit.web.WebConstants;

import com.neopixl.pixlui.components.textview.TextView;


/**
 * Created by Rony Díaz
 */
public class ContactoAmigoActivity extends BaseActivity implements View.OnClickListener {

    private TextView call263;
    private TextView call0501;
    private TextView mailLink;
    private TextView tv_app_version;
    private TextView tv_app_ip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.contacto_amigo_layout);

        parentActivity = this;
//        setSingleton(((Singleton) getApplicationContext()));

  //      getSingleton().setInfoAct(this);

        call263 = (TextView) findViewById(R.id.tv_call_263);
        call0501 = (TextView) findViewById(R.id.tv_call_0501);
        mailLink = (TextView) findViewById(R.id.tv_mail_link);

        tv_app_version = (TextView) findViewById(R.id.tv_app_version);
       // tv_app_ip = (TextView) findViewById(R.id.tv_app_ip);

        call263.setOnClickListener(this);
        call0501.setOnClickListener(this);
        mailLink.setOnClickListener(this);

        //tv_app_ip.setText(WebConstants.GATEWAY_URL);

        try {
            String fecha = getResources().getString(R.string.app_version_date);

            PackageManager manager = this.getPackageManager();
            PackageInfo info = manager.getPackageInfo(this.getPackageName(), 0);
            tv_app_version.setText("Versi\u00f3n " + info.versionName + "." + fecha);

        } catch (Exception e) {
            tv_app_version.setText("");
        }

        setDownUpMenu();
    }

    @Override
    public void onClick(View v) {

        if (getSingleton().isMenuOpen()) {
            return;
        }

        if (v == call263) {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:*263"));
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } else if (v == call0501) {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:05019200000"));
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } else if (v == mailLink) {
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("message/rfc822");
            i.putExtra(Intent.EXTRA_EMAIL, new String[]{"contactoamigo@bod.com.ve"});
            i.putExtra(Intent.EXTRA_SUBJECT, "Contacto BOD");
            i.putExtra(Intent.EXTRA_TEXT, "");
            try {
                startActivity(Intent.createChooser(i, "Escribir mensaje aqu�"));
            } catch (android.content.ActivityNotFoundException ex) {
                Toast.makeText(ContactoAmigoActivity.this, "No hay un cliente de email instalado", Toast.LENGTH_SHORT).show();
            }

        }

    }

    @Override
    public void onBackPressed() {
      //  getSingleton().infoAct = null;
        this.finish();
    }

}
