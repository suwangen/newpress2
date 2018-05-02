package com.shensou.newpress.activity.home;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shensou.newpress.R;
import com.shensou.newpress.activity.BaseActivity;
import com.shensou.newpress.bean.WebviewJsGson;
import com.shensou.newpress.gobal.URL;
import com.shensou.newpress.utils.JsonUtils;
import com.shensou.newpress.utils.Tools;
import com.shensou.newpress.utils.UmengUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 活动详情
 */
public class ActivityDetailActivity extends BaseActivity {

    @Bind(R.id.webview)
    WebView webView;
    @Bind(R.id.toolbar_title)
    TextView mToolbarTitle;
    @Bind(R.id.right)
    TextView right;
    @Bind(R.id.back)
    ImageView ivBack;
    @Bind(R.id.lin_bottom)
    LinearLayout lin_bottom;



    private String title;
    private String url;
    private final static int FILECHOOSER_RESULTCODE = 1;
    private android.webkit.ValueCallback<Uri[]> mUploadCallbackAboveL;
    private ValueCallback<Uri> mUploadMessage;
    private Uri imageUri;

    private String phoneManufacturer;//手机厂商
    private UmengUtils umengUtils;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action_detail);
        ButterKnife.bind(this);
        Tools.setStatusBarTranslucent((Activity) context);
        //        lin_bottom.setPadding(0, 0, 0, Tools.getNavigationBarHeight(context));//虚拟底部按键高度
        init();
    }

    private void init(){
        title=getIntent().getStringExtra("title");
        url=getIntent().getStringExtra("url");
        mToolbarTitle.setText(title);

        Html(url);


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //        webView.destroy();
    }

    @SuppressWarnings("deprecation")
    @SuppressLint({"JavascriptInterface", "SetJavaScriptEnabled"})
    private void Html(final String url) {
        showProgressDialog();
        final Handler mHandler = new Handler();
        WebSettings mWebSettings = webView.getSettings();
        mWebSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        mWebSettings.setDomStorageEnabled(true);
        mWebSettings.setAllowContentAccess(true);
        mWebSettings.setAllowFileAccess(true);
        mWebSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        // 是否允许通过file url加载的Javascript读取本地文件，默认值 false
        mWebSettings.setAllowFileAccessFromFileURLs(true);
        // 是否允许通过file url加载的Javascript读取全部资源(包括文件,http,https)，默认值 false
        mWebSettings.setAllowUniversalAccessFromFileURLs(true);
        mWebSettings.setJavaScriptEnabled(true);
        mWebSettings.setBuiltInZoomControls(true); // 设置是否可缩放
        mWebSettings.setLightTouchEnabled(true); //设置用鼠标激活被选项
        mWebSettings.setSupportZoom(false); // 设置可以支持缩放
        mWebSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
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
                    intent = new Intent(context,ActivityDetailActivity.class);
                    intent.putExtra("title",result.getTitle());
                    intent.putExtra("url", URL.IP2+result.getLink());
                    startActivity(intent);
                }else if(result.getType().equals("weixin_circle")){
                    if(umengUtils==null){
                        umengUtils = new UmengUtils(context);
                    }
                    umengUtils.setParams(result.getLink(),
                            result.getImg()
                            ,result.getTitle()
                            ,result.getInfo());
                    umengUtils.ShareWeixinCircle();
                    //                    umengUtils.handleShare();

                }else if(result.getType().equals("weixin_friend")){
                    if(umengUtils==null){
                        umengUtils = new UmengUtils(context);
                    }
                    umengUtils.setParams(result.getLink(),
                            result.getImg()
                            ,result.getTitle()
                            ,result.getInfo());
                    umengUtils.ShareWeixin();
                    //                    umengUtils.handleShare();

                }

            }
        }, "ttf");
        webView.loadUrl(url);
        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public boolean onJsAlert(WebView webView, String s, String s1, JsResult jsResult) {
                return super.onJsAlert(webView, s, s1, jsResult);
            }

            @Override
            public boolean onJsConfirm(WebView webView, String s, String s1, JsResult jsResult) {
                return super.onJsConfirm(webView, s, s1, jsResult);
            }

            @Override
            public boolean onJsPrompt(WebView webView, String s, String s1, String s2, JsPromptResult jsPromptResult) {
                return super.onJsPrompt(webView, s, s1, s2, jsPromptResult);
            }

            public void openFileChooser(ValueCallback<Uri> uploadMsg) {//警告：腾讯webview，无法调起相册，只能用原生的
                mUploadMessage = uploadMsg;
                Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                i.addCategory(Intent.CATEGORY_OPENABLE);
                i.setType("image/*");
                ActivityDetailActivity.this.startActivityForResult(Intent.createChooser(i, "File Chooser"), FILECHOOSER_RESULTCODE);
            }
            public void openFileChooser( ValueCallback uploadMsg, String acceptType ) {
                mUploadMessage = uploadMsg;
                Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                i.addCategory(Intent.CATEGORY_OPENABLE);
                i.setType("image/*");
                ActivityDetailActivity.this.startActivityForResult(
                        Intent.createChooser(i, "File Browser"),
                        FILECHOOSER_RESULTCODE);
            }
            public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture){
                mUploadMessage = uploadMsg;
                Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                i.addCategory(Intent.CATEGORY_OPENABLE);
                i.setType("image/*");
                ActivityDetailActivity.this.startActivityForResult( Intent.createChooser( i, "File Browser" ), ActivityDetailActivity.FILECHOOSER_RESULTCODE );
            }

            // For Android 5.0+
            public boolean onShowFileChooser (WebView webView, ValueCallback<Uri[]> filePathCallback, WebChromeClient.FileChooserParams fileChooserParams) {
                mUploadCallbackAboveL = filePathCallback;
                Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                i.addCategory(Intent.CATEGORY_OPENABLE);
                i.setType("image/*");
                ActivityDetailActivity.this.startActivityForResult(
                        Intent.createChooser(i, "File Browser"),
                        FILECHOOSER_RESULTCODE);
                return true;
            }
        });
        webView.setWebViewClient(new WebViewClientDemo());//对js交互的对话框、title以及页面加载进度条的管理

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==FILECHOOSER_RESULTCODE)
        {
            if (null == mUploadMessage && null == mUploadCallbackAboveL) return;
            Uri result = data == null || resultCode != RESULT_OK ? null : data.getData();
            if (mUploadCallbackAboveL != null) {
                onActivityResultAboveL(requestCode, resultCode, data);
            }
            else  if (mUploadMessage != null) {
                mUploadMessage.onReceiveValue(result);
                mUploadMessage = null;
            }
        }
    }
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void onActivityResultAboveL(int requestCode, int resultCode, Intent data) {
        if (requestCode != FILECHOOSER_RESULTCODE
                || mUploadCallbackAboveL == null) {
            return;
        }
        Uri[] results = null;
        if (resultCode == Activity.RESULT_OK) {
            if (data == null) {
            } else {
                String dataString = data.getDataString();
                ClipData clipData = data.getClipData();
                if (clipData != null) {
                    results = new Uri[clipData.getItemCount()];
                    for (int i = 0; i < clipData.getItemCount(); i++) {
                        ClipData.Item item = clipData.getItemAt(i);
                        results[i] = item.getUri();
                    }
                }
                if (dataString != null)
                    results = new Uri[]{Uri.parse(dataString)};
            }
        }
        mUploadCallbackAboveL.onReceiveValue(results);
        mUploadCallbackAboveL = null;
        return;
    }
    @OnClick({R.id.back,R.id.right})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.back:
                int count=webView.copyBackForwardList().getSize();
                if (count>2){
                    //                    mTvClose.setVisibility(View.VISIBLE);
                    //                    //                    ivBack.setVisibility(View.GONE);
                    webView.goBack();// 返回前一个页面
                }else
                    finish();

                break;
            case R.id.right:
                break;
        }

    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
            webView.goBack();// 返回前一个页面
            //            mTvClose.setVisibility(View.VISIBLE);
            //            ivBack.setVisibility(View.GONE);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }





    //在WebView中显示页面，而不是在默认的浏览器中显示页面

    private class WebViewClientDemo extends WebViewClient {
        @Override
        public void onPageFinished(android.webkit.WebView view, String url) {
            dismissProgressDialog();
            super.onPageFinished(view, url);

        }

        @Override
        public boolean shouldOverrideUrlLoading(android.webkit.WebView view, String url) {
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
            // 返回true表示停留在本WebView（不跳转到系统的浏览器）
            //            return false;
            //            return super.shouldOverrideUrlLoading(view, url);
            return true;
        }

        /**
         * url重定向会执行此方法以及点击页面某些链接也会执行此方法
         * view           当前webview
         * url           即将重定向的url
         * @return true:表示当前url已经加载完成，即使url还会重定向都不会再进行加载 false 表示此url默认由系统处理，该重定向还是重定向，直到加载完成
         */
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


}
