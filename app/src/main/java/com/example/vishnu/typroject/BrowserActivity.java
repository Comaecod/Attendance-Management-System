package com.example.vishnu.typroject;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.webkit.CookieManager;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

public class BrowserActivity extends AppCompatActivity {

    WebView wv;
    ImageButton b;
    EditText et;
    ProgressBar pb;
    SwipeRefreshLayout srl;
    Button clr;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browser);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        wv = (WebView) findViewById(R.id.webView);
        b = (ImageButton) findViewById(R.id.button);
        et = (EditText) findViewById(R.id.editText);
        pb = (ProgressBar) findViewById(R.id.progressBar);
        pb.setMax(100);
        pb.setVisibility(View.GONE);
        clr = (Button) findViewById(R.id.clear_text);

        wv.loadUrl("https://www.google.co.in/?gfe_rd=cr&dcr=0&ei=lUZjWuTgA-2eX6nvumg");

        et.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                et.selectAll();
            }
        });
        et.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_SEARCH) {

                    et.clearFocus();
                    InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    in.hideSoftInputFromWindow(textView.getWindowToken(), 0);
                    if (et.getText().toString().contains(".") == false)
                        wv.loadUrl("https://www.google.com/search?q=" + et.getText().toString());
                    else if (et.getText().toString().contains(".com") || et.getText().toString().contains(".org") || et.getText().toString().contains(".in"))
                        wv.loadUrl("https://" + et.getText().toString());
                    else
                        wv.loadUrl("https://google.com/");
                    et.setText(wv.getOriginalUrl().toString());
                    return true;
                } else
                    return false;

            }
        });
        clr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                et.setText("");
            }
        });

        srl = (SwipeRefreshLayout) findViewById(R.id.swipe);
        srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                LoadWeb();
            }
        });
        LoadWeb();

    }

    @Override
    public void onBackPressed() {
        if (wv.canGoBack())
            wv.goBack();
        else
        super.onBackPressed();
    }
    public void LoadWeb() {
        wv.getSettings().setJavaScriptEnabled(true);
        wv.getSettings().setSupportZoom(true);
        wv.getSettings().setBuiltInZoomControls(false);
        wv.getSettings().setLoadWithOverviewMode(true);
        wv.getSettings().setUseWideViewPort(true);
        wv.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        wv.setBackgroundColor(Color.WHITE);
        wv.loadUrl(wv.getUrl());
        srl.setRefreshing(true);

        wv.setWebViewClient(new ourViewClient());
        wv.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView wv, int progress) {
                pb.setProgress(progress);
                if (progress < 100 /*&& pb.getVisibility() == ProgressBar.GONE*/) {
                    pb.setVisibility(ProgressBar.VISIBLE);
                } else
                    pb.setVisibility(ProgressBar.GONE);
            }

        });

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromInputMethod(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                if (et.getText().toString().contains(".") == false)
                    wv.loadUrl("https://www.google.com/search?q=" + et.getText().toString());
                else if (et.getText().toString().contains(".com") || et.getText().toString().contains(".org") || et.getText().toString().contains(".in"))
                    wv.loadUrl("https://" + et.getText().toString());
                else
                    wv.loadUrl("https://google.com/");
                et.setText(wv.getOriginalUrl().toString());
            }
        });
    }

    public class ourViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            CookieManager.getInstance().setAcceptCookie(true);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            srl.setRefreshing(false);
            super.onPageFinished(view, url);
        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            wv.loadUrl("https://google.com/");
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        wv.saveState(outState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_browser, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_back:
                if (wv.canGoBack())
                    wv.goBack();
                return true;
            case R.id.item_forward:
                if (wv.canGoForward())
                    wv.goForward();
                return true;
            case R.id.item_home:
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromInputMethod(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                wv.loadUrl("https://google.com");
                et.setText(wv.getUrl().toString());

            default:
                return super.onOptionsItemSelected(item);
        }

    }
}
