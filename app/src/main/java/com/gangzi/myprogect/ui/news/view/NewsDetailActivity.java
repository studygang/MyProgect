package com.gangzi.myprogect.ui.news.view;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.gangzi.myprogect.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewsDetailActivity extends AppCompatActivity {

    @BindView(R.id.webView)
    WebView mWebView;
    @BindView(R.id.detail_toobar)
    Toolbar mToolbar;

    private String category,url,title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        ButterKnife.bind(this);
        url=getIntent().getStringExtra("url");
        category=getIntent().getStringExtra("category");
        title=getIntent().getStringExtra("title");

        System.out.println("------url--------"+url);
        setToolBar();
        setWebView();
    }

    private void setToolBar() {
        mToolbar.setTitle(category);
        setSupportActionBar(mToolbar);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        mToolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void setWebView() {
       WebSettings settings= mWebView.getSettings();

        //如果访问的页面中要与Javascript交互，则webview必须设置支持Javascript
        settings.setJavaScriptEnabled(true);
        //设置自适应屏幕，两者合用
        settings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        settings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小
        //缩放操作
        settings.setSupportZoom(true); //支持缩放，默认为true。是下面那个的前提。
        settings.setBuiltInZoomControls(true); //设置内置的缩放控件。若为false，则该WebView不可缩放
        settings.setDisplayZoomControls(false); //隐藏原生的缩放控件

        //其他细节操作
        settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK); //关闭webview中缓存
        settings.setAllowFileAccess(true); //设置可以访问文件
        settings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
        settings.setLoadsImagesAutomatically(true); //支持自动加载图片
        settings.setDefaultTextEncodingName("utf-8");//设置编码格式

        mWebView.loadUrl(url);

        mWebView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return super.shouldOverrideUrlLoading(view, request);
            }
        });
    }

}
