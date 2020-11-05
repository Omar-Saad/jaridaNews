package com.news.akhbar;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

public class WebActivity extends AppCompatActivity {

    WebView webView;
    String url;
    ProgressDialog progressDialog;
    TinyDB tinyDB;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tinyDB = new TinyDB(WebActivity.this);
        CommonTasks.changeLanguage(WebActivity.this ,new String[]{ tinyDB.getString("lang")} , new String[]{ tinyDB.getString("country")});
        setContentView(R.layout.activity_web);

        webView = findViewById(R.id.webView);

       progressDialog = new ProgressDialog(WebActivity.this ,R.style.MyAlertDialogStyle);
        progressDialog.setTitle(getResources().getString(R.string.load));
        progressDialog.setMessage(getResources().getString(R.string.please_wait));
        progressDialog.show();

         url = getIntent().getStringExtra("url");
      webView.getSettings().setLoadsImagesAutomatically(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setDisplayZoomControls(false);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);

        webView.setWebViewClient(new WebViewClient(){

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                progressDialog.dismiss();

            }
        });

        webView.loadUrl(url);

    }


}