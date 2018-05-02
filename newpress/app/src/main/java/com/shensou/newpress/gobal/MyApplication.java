package com.shensou.newpress.gobal;

import android.app.Application;
import android.util.Log;

import com.shensou.newpress.utils.ImageLoadProxy;
import com.tencent.smtt.sdk.QbSdk;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;
import com.zhy.autolayout.config.AutoLayoutConifg;


/**
 */
public class MyApplication extends Application {

    public static MyApplication mInstance;



    private boolean isGroupAndContactListenerRegisted;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance=this;
        UMShareAPI.get(mInstance);
        ImageLoadProxy.initImageLoader(this);
        AutoLayoutConifg.getInstance().useDeviceSize();
        QbSdk.initX5Environment(this, new QbSdk.PreInitCallback() {
            @Override
            public void onCoreInitFinished() {
                //x5内核初始化完成回调接口，此接口回调并表示已经加载起来了x5，有可能特殊情况下x5内核加载失败，切换到系统内核。

            }

            @Override
            public void onViewInitFinished(boolean b) {
                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
                Log.e("swg","加载内核是否成功:"+b);
            }
        });
    }

    public static MyApplication getInstance() {
        return mInstance;
    }
    //各个平台的配置，建议放在全局Application或者程序入口
    {
        PlatformConfig.setWeixin("wxdc1e388c3822c80b", "3baf1193c85774b3fd9d18447d76cab0");
        PlatformConfig.setSinaWeibo("3921700954", "04b48b094faeb16683c32669824ebdad","http://sns.whalecloud.com");
        PlatformConfig.setQQZone("1106847952", "Av6tHcvga5xYbpIC");
    }
}
