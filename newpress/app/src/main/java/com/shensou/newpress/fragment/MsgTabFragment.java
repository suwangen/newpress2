package com.shensou.newpress.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.shensou.newpress.R;
import com.shensou.newpress.activity.home.ActivityDetailActivity;
import com.shensou.newpress.bean.MyOrderGson;
import com.shensou.newpress.bean.WebviewJsGson;
import com.shensou.newpress.bean.YuyueSpecialPersonGson;
import com.shensou.newpress.gobal.URL;
import com.shensou.newpress.permission.PermissionUtils;
import com.shensou.newpress.utils.JsonUtils;
import com.shensou.newpress.widget.AutoToolbar;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 圈子
 */
public class MsgTabFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener{
    @Bind(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @Bind(R.id.webview)
    WebView webView;
    @Bind(R.id.toolbar_title)
    TextView toolbar_title;
    @Bind(R.id.toolbar)
    AutoToolbar toolbar;
    @Bind(R.id.back)
    ImageView back;
    public static final int REQUEST_STORAGE_PERMISSION = 0X01;
    private YuyueSpecialPersonGson.ResponseBean.ActiveTypeBean freshThings;
    private List<MyOrderGson.ResponseBean> mList;
    private int p =0;
    private int clickPos;

    public static MsgTabFragment newInstance() {
        MsgTabFragment fragment = new MsgTabFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onRefresh() {
        if(webView!=null)
          webView.reload();
    }
    private void stopRefresh(){
        if (mSwipeRefreshLayout.isRefreshing()){
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        webView.destroy();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_tab_circle, container, false);
        ButterKnife.bind(this, view);
        //        Tools.setStatusBarTranslucent(lin,getActivity());
        init();
        return view;
    }


    public void setRefreshThingsBean(YuyueSpecialPersonGson.ResponseBean.ActiveTypeBean bean){
        freshThings = bean;
    }
    @Override
    public void onResume() {
        super.onResume();
    }
    @SuppressWarnings("deprecation")
    @SuppressLint({"JavascriptInterface", "SetJavaScriptEnabled"})
    private void Html(final String url) {
        final Handler mHandler = new Handler();
        WebSettings mWebSettings = webView.getSettings();
        mWebSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        mWebSettings.setDomStorageEnabled(true);
        mWebSettings.setAllowFileAccess(true);
        mWebSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        mWebSettings.setJavaScriptEnabled(true);
        mWebSettings.setBuiltInZoomControls(true); // 设置是否可缩放
        mWebSettings.setLightTouchEnabled(true); //设置用鼠标激活被选项
        mWebSettings.setSupportZoom(false); // 设置可以支持缩放
        webView.setHapticFeedbackEnabled(false);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        // mWebView.setInitialScale(0); // 改变这个值可以设定初始大小
        //重要,用于与页面交互!
        webView.addJavascriptInterface(new Object() {
            @JavascriptInterface
            public void nslog(final String data ) {//此处的参数可传入作为js参数
                Log.e("swg",data);
                WebviewJsGson result = JsonUtils.deserialize(data, WebviewJsGson.class);
                Intent intent = null;
                if(result.getType().equals("hrefTo")){
                    intent = new Intent(getContext(),ActivityDetailActivity.class);
                    intent.putExtra("title",result.getTitle());
                    intent.putExtra("url", URL.IP2+result.getLink());
                    startActivity(intent);
                }

            }

        }, "ttf");
        webView.loadUrl(url);
//        webView.setWebChromeClient(new com.tencent.smtt.sdk.WebChromeClient(){
//            @Override
//            public boolean onJsAlert(com.tencent.smtt.sdk.WebView webView, String s, String s1, com.tencent.smtt.export.external.interfaces.JsResult jsResult) {
//                return super.onJsAlert(webView, s, s1, jsResult);
//            }
//
//            @Override
//            public boolean onJsConfirm(com.tencent.smtt.sdk.WebView webView, String s, String s1, com.tencent.smtt.export.external.interfaces.JsResult jsResult) {
//                return super.onJsConfirm(webView, s, s1, jsResult);
//            }
//
//            @Override
//            public boolean onJsPrompt(com.tencent.smtt.sdk.WebView webView, String s, String s1, String s2, com.tencent.smtt.export.external.interfaces.JsPromptResult jsPromptResult) {
//                return super.onJsPrompt(webView, s, s1, s2, jsPromptResult);
//            }
//        });
        webView.setWebViewClient(new WebViewClientDemo());//对js交互的对话框、title以及页面加载进度条的管理
        //        com.tencent.smtt.sdk.WebSettings webSettings = webview2.getSettings();
        //        webSettings.setJavaScriptEnabled(true);
        //        webview2.loadUrl(url);
        //        webview2.setWebViewClient(new com.tencent.smtt.sdk.WebViewClient() {
        //            @Override
        //            public boolean shouldOverrideUrlLoading(com.tencent.smtt.sdk.WebView webView, String s) {
        //                webview2.loadUrl(url);
        //                return true;
        //            }
        //
        //        });
    }

    @OnClick({
    })
    public void onClick(View view) {
        Intent intent=null;
        switch (view.getId()) {
        }
    }

    private void init() {
        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        mSwipeRefreshLayout.setRefreshing(false);
        mSwipeRefreshLayout.setOnRefreshListener(this);

        toolbar.setBackgroundColor(getContext().getResources().getColor(R.color.bg_white));
        toolbar.setVisibility(View.GONE);
        mList = new ArrayList<>();
        toolbar_title.setText("圈子");
        toolbar_title.setTextColor(getContext().getResources().getColor(R.color.text_color_black));
        back.setVisibility(View.INVISIBLE);
        Html(freshThings.getId());
    }
    public List<MyOrderGson.ResponseBean> getList(){
          return mList;
    }
    //在WebView中显示页面，而不是在默认的浏览器中显示页面

    private class WebViewClientDemo extends WebViewClient {

        //        @SuppressWarnings("unused")
        //        public boolean shouldOverideUrlLoading(WebView view, String url) {
        //            if (url.startsWith("http://tel:")|| url.startsWith("tel:")){
        //                Intent intent = new Intent(Intent.ACTION_VIEW,
        //                        Uri.parse(url));
        //                startActivity(intent);
        //            } else if(url.startsWith("http:") || url.startsWith("https:")) {
        //                view.loadUrl(url);
        //            }
        ////            return true;
        //            // 返回true表示停留在本WebView（不跳转到系统的浏览器）
        //            return false;
        //        }
        //        @Override
        //        public void onPageFinished(WebView view, String url) {
        //            // TODO Auto-generated method stub
        //            super.onPageFinished(view, url);
        //            dismissProgressDialog();
        //
        //        }

        @Override
        public void onPageFinished(WebView webView, String url) {
            stopRefresh();
            super.onPageFinished(webView, url);

        }

        /**
         * url重定向会执行此方法以及点击页面某些链接也会执行此方法
         * view           当前webview
         * url           即将重定向的url
         * @return true:表示当前url已经加载完成，即使url还会重定向都不会再进行加载 false 表示此url默认由系统处理，该重定向还是重定向，直到加载完成
         */
        @Override
        public boolean shouldOverrideUrlLoading(WebView webView, String url) {
            Log.e("urlssss",url);
            if (url.startsWith("http://tel:")){
                url=url.replace("http://","").trim();
                Intent intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse(url));
                startActivity(intent);
            } if( url.startsWith("tel:")){
                Intent intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse(url));
                startActivity(intent);
            }else if(url.startsWith("http:") || url.startsWith("https:")) {
                webView.loadUrl(url);
            }
            return true;
            // 返回true表示停留在本WebView（不跳转到系统的浏览器）
            //            return false;
            //            return super.shouldOverrideUrlLoading(view, url);
        }
        //        @Override
        //        public boolean shouldOverrideUrlLoading(WebView view, String url) {
        //            Log.e("urlssss",url);
        //            if (url.startsWith("http://tel:")){
        //                url=url.replace("http://","").trim();
        //                Intent intent = new Intent(Intent.ACTION_VIEW,
        //                        Uri.parse(url));
        //                startActivity(intent);
        //            } if( url.startsWith("tel:")){
        //                Intent intent = new Intent(Intent.ACTION_VIEW,
        //                        Uri.parse(url));
        //                startActivity(intent);
        //            }else if(url.startsWith("http:") || url.startsWith("https:")) {
        //                view.loadUrl(url);
        //            }
        //            return true;
        //            // 返回true表示停留在本WebView（不跳转到系统的浏览器）
        ////            return false;
        ////            return super.shouldOverrideUrlLoading(view, url);
        //        }
    }

    /**
     * 需要存储权限
     *
     * @param
     */
    public void needStoragePermissions() {
        String[] perms = new String[]{
                Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (PermissionUtils.hasPermissions(getActivity(), perms)) {

        } else {
            PermissionUtils.requestPermissions(this, getString(R.string.rationale_write), REQUEST_STORAGE_PERMISSION, perms);
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

}
