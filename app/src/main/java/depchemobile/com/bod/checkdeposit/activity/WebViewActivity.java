package depchemobile.com.bod.checkdeposit.activity;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import  depchemobile.com.bod.checkdeposit.utils.Singleton;
import depchemobile.com.bod.checkdeposit.R;


/**
 * Created by Rony Díaz
 */
public class WebViewActivity extends BaseActivity {

    private String url;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.web_view_layout);

        //setSingleton(((Singleton) getApplicationContext()));

        Bundle b = getIntent().getExtras();
        url = b.getString("url");

        WebView webView = (WebView) findViewById(R.id.web_view);
        webView.setWebViewClient(new BODWebViewClient());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(url);

        parentActivity = this;
        setDownUpMenu();
    }

    private class BODWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
}
