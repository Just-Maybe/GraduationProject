package com.library.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.library.R;
import com.library.utils.CheckUtil;
import com.library.widget.ProgressWebView;

public class WebViewActivity extends BaseActivity {

    private ImageView ivBack;
    private TextView tvTitle;
    private ProgressWebView webView;

    private String titleStr = "", url = "";

    public static void open(Context context, String title, String url) {
        Intent intent = new Intent(context, WebViewActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        bundle.putString("url", url);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    protected void onGetBundle(Bundle bundle) {
        if (bundle != null) {
            titleStr = bundle.getString("title");
            url = bundle.getString("url");
        }
    }

    @Override
    protected int getViewId() {
        return R.layout.activity_webview;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        webView = (ProgressWebView) findViewById(R.id.web_view);
        ivBack = (ImageView) findViewById(R.id.iv_back);
        tvTitle = (TextView) findViewById(R.id.tv_title);

        tvTitle.setText(titleStr);
        webView.defaultSetting();
        //加载需要显示的网页
        if (CheckUtil.checkURL(url) || CheckUtil.checkFileURL(url)) {
            webView.loadUrl(url);
        } else {
            webView.loadDataWithBaseURL(null, url, "text/html", "UTF-8", null);
        }
    }

    @Override
    protected void initListener() {
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}
