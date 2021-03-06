package com.zhboy.moreprocessdemo;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.zhboy.moreprocessdemo.data.WebBean;
import com.zhboy.moreprocessdemo.process.ProcessUtil;


/**
 * @author zhou_hao
 * @date 2020/10/20
 * @description: 黄历详情页
 */

public class WebViewActivity extends AppCompatActivity {
    private WebView webview;

    WebViewClient mWebViewClient = new WebViewClient() {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            try {
                if (url.startsWith("http:") || url.startsWith("https:")) {
                    view.loadUrl(url);
                } else {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(intent);
                }
                return true;
            } catch (Exception e) {
                return false;
            }
        }
    };

    WebChromeClient wvcc = new WebChromeClient() {
        @Nullable
        @Override
        public Bitmap getDefaultVideoPoster() {
            if (super.getDefaultVideoPoster() == null) {
                return BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
            }
            return super.getDefaultVideoPoster();
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);

        //设置进度条的布局
        webview = (WebView) findViewById(R.id.web_simple);
        WebSettings setting = webview.getSettings();
        webview.setWebChromeClient(wvcc);
        setting.setDomStorageEnabled(true);
        webview.setSaveEnabled(false);
        webview.setWebViewClient(mWebViewClient);

        Bundle mBundle = getIntent().getBundleExtra("bundleData");
        WebBean webBean = (WebBean) mBundle.getSerializable("INTENT_SIMPLE_WEB_URL");
        webview.loadUrl(webBean.getUrl());

        String processName = ProcessUtil.getCurrentProcessName(this);
        System.out.println("----WebViewActivity:所在进程:   " + processName + "    获取到了主进程传递的值");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (webview != null) {
            webview.setWebChromeClient(null);
            webview.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            webview.clearCache(true);
            webview.clearHistory();
            webview.removeAllViews();
            ((ViewGroup) webview.getParent()).removeAllViews();
            webview.destroy();
        }

    }

}
