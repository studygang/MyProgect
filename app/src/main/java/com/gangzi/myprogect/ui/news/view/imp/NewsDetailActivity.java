package com.gangzi.myprogect.ui.news.view.imp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.gangzi.myprogect.R;
import com.gangzi.myprogect.utils.MyProgressDialog;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewsDetailActivity extends AppCompatActivity {

    @BindView(R.id.webView)
    WebView mWebView;
    @BindView(R.id.detail_toobar)
    Toolbar mToolbar;
    @BindView(R.id.progress)
    ProgressBar mProgressBar;

    private String category,url,title;
    private MyProgressDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        ButterKnife.bind(this);
        mDialog=new MyProgressDialog(this,"正在加载中...");
        mDialog.show();
        url=getIntent().getStringExtra("url");
        category=getIntent().getStringExtra("category");
        title=getIntent().getStringExtra("title");
        mProgressBar.setVisibility(View.VISIBLE);
        System.out.println("------url--------"+url);
        setToolBar();
        if (url!=null||!url.equals("")){
           // mDialog.dismiss();
            setWebView();
        }
        test();

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
        mDialog.dismiss();
        mProgressBar.setVisibility(View.GONE);

        mWebView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                mDialog.dismiss();
                return super.shouldOverrideUrlLoading(view, request);
            }
        });
    }
    // JS 调用原生方法
    //JS 调用源生的方法时该方法必须要加上 @JavascriptInterface 注解，
    // 我们可以定义一个类或者接口来单独存放用于 JS 调用的方法，这里我以接口为例。
    // 接口中提供一个 getUrl(String url) 方法用于提供给 JS 调用，传递一个 String 类型的值给源生方法：


    /**
     * Created by gangzi on 2017/3/22.
     * JS 接口方法定义
     * 接口中定义方法时不用加注解，使用时才加注解
     */
    public interface JavaScriptFunction{
        void getUrl(String string);
    }
    // 此代码片段为 PandaEye 中点击加载的 html 数据中的图片跳转到新的 Activity 显示图片的功能
    private void test(){
        mWebView.addJavascriptInterface(new JavaScriptFunction() {
            @Override
            public void getUrl(String string) {
               // LogWritter.LogStr(imageUrl);
                Intent intent = new Intent();
               // intent.putExtra("imageUrls", mImageUrls);
               // intent.putExtra("curImageUrl", imageUrl);
                //intent.setClass(ZhihuStoryInfoActivity.this, PhotoViewActivity.class);
                startActivity(intent);
            }
        },"JavaScriptFunction");
    }

    // 在 HTML 中图片的点击事件 JS 方法中就可以执行如下代码来调用源生的接口方法
   // function(){
    //    window.JavaScriptFunction.getUrl(this.src);
    //}

    //原生调用 JS

}
