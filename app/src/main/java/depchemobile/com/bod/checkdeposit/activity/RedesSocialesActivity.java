package depchemobile.com.bod.checkdeposit.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import depchemobile.com.bod.checkdeposit.R;
import depchemobile.com.bod.checkdeposit.utils.Singleton;




import com.neopixl.pixlui.components.textview.TextView;

/**
 * Created by Rony Diaz
 */
public class RedesSocialesActivity extends BaseActivity implements View.OnClickListener {

    private TextView facebookLink;
    private TextView twitterLink;
    private TextView wordpressLink;
    private TextView youtubeLink;
    private TextView instagram;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.redes_sociales_layout);

//        setSingleton(((Singleton) getApplicationContext()));

        //singleton.resetTimeTask_main(this);

  //      getSingleton().setRedesAct(this);

        facebookLink = (TextView) findViewById(R.id.facebook_link);
        twitterLink = (TextView) findViewById(R.id.twitter_link);
        wordpressLink = (TextView) findViewById(R.id.wordpress_link);
        youtubeLink = (TextView) findViewById(R.id.youtube_link);
        instagram = (TextView) findViewById(R.id.instagram_link);

        facebookLink.setOnClickListener(this);
        twitterLink.setOnClickListener(this);
        wordpressLink.setOnClickListener(this);
        youtubeLink.setOnClickListener(this);
        instagram.setOnClickListener(this);

        parentActivity = this;
        setDownUpMenu();

    }

    @Override
    public void onClick(View v) {


        Intent mainItent = new Intent().setClass(RedesSocialesActivity.this, WebViewActivity.class);
        Bundle b = new Bundle();
        if (v == facebookLink) {
            b.putString("url", "https://es-la.facebook.com/BODOficial");
        } else if (v == twitterLink) {

            b.putString("url", "https://twitter.com/bodoficial");

        } else if (v == wordpressLink) {

            b.putString("url", "https://accionsocialbod.wordpress.com/");
        } else if (v == youtubeLink) {

            b.putString("url", "https://www.youtube.com/user/BodBancoUniversal");
        } else if (v == instagram) {

            b.putString("url", "https://www.instagram.com/bodoficial/");
        }


        mainItent.putExtras(b);
        startActivity(mainItent);
    }

    @Override
    public void onBackPressed() {
        //getSingleton().redesAct = null;
        this.finish();
    }


}
