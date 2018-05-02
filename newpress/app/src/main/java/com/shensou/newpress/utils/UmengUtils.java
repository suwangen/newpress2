package com.shensou.newpress.utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.widget.Toast;

import com.shensou.newpress.R;
import com.shensou.newpress.widget.dialog.ShareDialog;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.umeng.socialize.utils.SocializeUtils;

import java.util.Map;


/**
 * Created by Administrator on 2017/07/31 0031.
 */

public class UmengUtils {
    private ProgressDialog dialog;
    private Context context;
    private UMImage thumb;
    private UMWeb web;
    public UmengUtils(Context context ){
        this.context = context;
        dialog = new ProgressDialog(context);
    }
    /**
     * @param url 链接
     * @param thumb_img 默认图片
     * @param title 标题
     * @param content 内容
     */
    public void setParams(String url,String thumb_img,String title,String content){
        UMImage thumb = new UMImage(context,thumb_img);
        web = new UMWeb(url);
        web.setThumb(thumb);
        web.setDescription(content);
        web.setTitle(title);
    }

    /**
     * 分享
     */
    public void handleShare() {
        final ShareDialog dialog = new ShareDialog(context);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setTitle(R.string.share_to);
        dialog.setOnPlatformClickListener(new ShareDialog.OnSharePlatformClick() {
            @Override
            public void onPlatformClick(int id) {
                switch (id) {
                    case R.id.ly_share_weichat_circle:
                        ShareWeixinCircle();
                        break;
                    case R.id.ly_share_weichat:
                        ShareWeixin();
                        break;
                    case R.id.ly_share_sina_weibo:
                        ShareWeibo();
                        break;
                    case R.id.ly_share_qq:
                        ShareQQ();
                        break;
                    default:
                        break;
                }
                dialog.dismiss();
            }
        });
        dialog.show();
    }
    public void ShareQQ() {
        new ShareAction((Activity) context).withMedia(web).setPlatform((SHARE_MEDIA) SHARE_MEDIA.QQ.toSnsPlatform().mPlatform).setCallback(shareListener).share();
    }
    public void ShareWeixin() {
        new ShareAction((Activity) context).withMedia(web).setPlatform((SHARE_MEDIA) SHARE_MEDIA.WEIXIN.toSnsPlatform().mPlatform).setCallback(shareListener).share();
    }
    public void ShareWeixinCircle() {
        new ShareAction((Activity) context).withMedia(web).setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE.toSnsPlatform().mPlatform).setCallback(shareListener).share();
    }
    public void ShareWeibo() {
        new ShareAction((Activity) context).withMedia(web).setPlatform(SHARE_MEDIA.SINA.toSnsPlatform().mPlatform).setCallback(shareListener).share();
    }
    private UMShareListener shareListener = new UMShareListener() {
        /**
         * @descrption 分享开始的回调
         * @param platform 平台类型
         */
        @Override
        public void onStart(SHARE_MEDIA platform) {
            SocializeUtils.safeShowDialog(dialog);
        }

        /**
         * @descrption 分享成功的回调
         * @param platform 平台类型
         */
        @Override
        public void onResult(SHARE_MEDIA platform) {
            ToastUtil.showMyLongToast("成功了");
            SocializeUtils.safeCloseDialog(dialog);
        }

        /**
         * @descrption 分享失败的回调
         * @param platform 平台类型
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            SocializeUtils.safeCloseDialog(dialog);
            String message = t.getMessage();
            String[] str = message.split("：");
            ToastUtil.showMyLongToast( "失败：" + ((str!=null&&str.length>2)?str[2]:message));
        }

        /**
         * @descrption 分享取消的回调
         * @param platform 平台类型
         */
        @Override
        public void onCancel(SHARE_MEDIA platform) {
            SocializeUtils.safeCloseDialog(dialog);
            ToastUtil.showMyLongToast("取消了");

        }
    };
    UMAuthListener authListener = new UMAuthListener() {
        /**
         * @desc 授权开始的回调
         * @param platform 平台名称
         */
        @Override
        public void onStart(SHARE_MEDIA platform) {
            SocializeUtils.safeShowDialog(dialog);
        }

        /**
         * @desc 授权成功的回调
         * @param platform 平台名称
         * @param action 行为序号，开发者用不上
         * @param data 用户资料返回
         */
        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
            SocializeUtils.safeCloseDialog(dialog);
            Toast.makeText(context, "成功了", Toast.LENGTH_LONG).show();
        }

        /**
         * @desc 授权失败的回调
         * @param platform 平台名称
         * @param action 行为序号，开发者用不上
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            SocializeUtils.safeCloseDialog(dialog);
            Toast.makeText(context, "失败：" + t.getMessage(), Toast.LENGTH_LONG).show();
        }

        /**
         * @desc 授权取消的回调
         * @param platform 平台名称
         * @param action 行为序号，开发者用不上
         */
        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            SocializeUtils.safeCloseDialog(dialog);
            Toast.makeText(context, "取消了", Toast.LENGTH_LONG).show();
        }
    };
}
