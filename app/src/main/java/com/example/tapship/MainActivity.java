package com.example.tapship;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.os.Bundle;
import android.webkit.WebViewClient;
import android.webkit.WebSettings;
import android.graphics.Bitmap;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    WebView mywebview;
    ImageView imageView;
    ProgressBar progressBar;
    TextView textView;
    ActionBar actionBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setTitle("");
        /*getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.driver);*/

        actionBar=getSupportActionBar();
        actionBar.hide();

        mywebview = (WebView) findViewById(R.id.webview);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        textView = findViewById(R.id.text);
        WebSettings webSettings = mywebview.getSettings();
        webSettings.setJavaScriptEnabled(true);
        mywebview.setWebViewClient(new WebViewClient());
        mywebview.loadUrl("https://troupertech.com");
        imageView = (ImageView) findViewById(R.id.image);

        mywebview.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                progressBar.setProgress(progress);
                if (progress == 100) {
                    progressBar.setVisibility(View.GONE);
                    textView.setVisibility(View.GONE);
                    mywebview.setVisibility(View.VISIBLE);
                    actionBar.show();

                } else {
                    progressBar.setVisibility(View.VISIBLE);
                    textView.setVisibility(View.VISIBLE);
                    mywebview.setVisibility(View.GONE);
                    actionBar.hide();

                }
            }
        });

        mywebview.setWebViewClient(new WebViewClient() {
            public void onReceivedError(WebView webView, int errorCode, String description, String failingUrl) {
                try {
                    webView.stopLoading();
                } catch (Exception e) {
                }

                if (webView.canGoBack()) {
                    webView.goBack();
                }

                imageView.setVisibility(View.VISIBLE);

                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                        startActivity(getIntent());
                    }
                });

                webView.loadUrl("about:blank");
                super.onReceivedError(webView, errorCode, description, failingUrl);
            }
        });
    }
    public class myWebClient extends WebViewClient
    {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            progressBar.setVisibility(View.VISIBLE);
            textView.setVisibility(View.VISIBLE);
            actionBar.hide();

        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            progressBar.setVisibility(View.VISIBLE);
            textView.setVisibility(View.VISIBLE);
            actionBar.hide();
            view.loadUrl(url);
            return true;

        }


        @Override
        public void onPageFinished(WebView view, String url) {

            // TODO Auto-generated method stub
            super.onPageFinished(view, url);
            progressBar.setVisibility(View.GONE);
            textView.setVisibility(View.GONE);
            actionBar.show();

        }
    }
        @Override
        // This method is used to detect back button
        public void onBackPressed () {
            if (mywebview.canGoBack()) {
                mywebview.goBack();
            } else {
                // Let the system handle the back button
                super.onBackPressed();
            }
        }

}