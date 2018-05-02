package com.shensou.newpress.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.shensou.newpress.dokhttp.DOkHttp;
import com.shensou.newpress.dokhttp.FileUtils;
import com.shensou.newpress.gobal.URL;
import com.shensou.newpress.widget.dialog.DownLoadDialog;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class VersionUtils {
	private static class VersionUtilsHolder{
		public static VersionUtils mInstance=new VersionUtils();

	}
	public static VersionUtils getInstance() {
		return VersionUtilsHolder.mInstance;
	}
	public static final String filePath = Environment.getExternalStorageDirectory() + "/download/";
	static File files = new File(filePath);

    private String filePath2 ;
	private File files2 ;
	private File filestemp ;
    private String pathtemp;
    private String nametemp;
	public VersionUtils(){
//		filePath2 += path;
		filePath2 = Environment.getExternalStorageDirectory() +"/download/yinluimages/";
		files2 = new File(filePath2);
	}
	public File getFile(){
		return files2;
	}
	public static String getVersion(Context context) {
		PackageManager pm = context.getPackageManager();
		try {
			// 代表的就是清单文件的信息。
			PackageInfo packageInfo = pm.getPackageInfo(context.getPackageName(), 0);
			return packageInfo.versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
			// 肯定不会发生。
			// can't reach
			return "";
		}
	}


	public  static void showUpdateDialog(final Context context,String versionNum,String msg, final String url){
		AlertDialog.Builder dialog=new AlertDialog.Builder(context);
		dialog.setTitle("发现新版本:V"+versionNum);
		dialog.setMessage(msg);
		dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				downloadAPK(context,url);
			}
		}).setNegativeButton("取消", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		}).show();
	}


	public static void downloadAPK(final Context context,String url){
		if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			ToastUtil.showMyLongToast( "sd卡不可用，更新失败");
			return;
		}

		if (files.exists())
			files.delete();
		final DownLoadDialog dialog=new DownLoadDialog(context);
		dialog.show();
		Request request=new Request.Builder().tag(context).get().url(url).build();
		DOkHttp.getInstance().download4ServerListener(request, new DOkHttp.MyCallBack_Progress() {
			@Override
			public void onFailure(Request request, IOException e) {
				dialog.dismiss();
				handler.sendEmptyMessage(2);
			}

			@Override
			public void onResponse(Response response) {
				files = FileUtils.saveFile2Local(response, filePath, "LuGan.apk");
				handler.sendEmptyMessage(1);
				installApk(context, files);
				dialog.dismiss();

			}
		}, new DOkHttp.UIchangeListener() {
			@Override
			public void progressUpdate(long bytesWrite, long contentLength, boolean done) {
				int progress = (int) (bytesWrite * 100 / contentLength);
				dialog.setProgress(progress);

			}
		});
	}

	/**
	 * 安装一个apk文件
	 *
	 * @param
	 */
	private static void installApk(Context context,File file) {
		Intent startInent = new Intent();
		startInent.setAction(Intent.ACTION_VIEW);
		startInent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startInent.setDataAndType(Uri.fromFile(file),
				"application/vnd.android.package-archive");
		context.startActivity(startInent);// 安装APK
	}

	private static Handler handler=new Handler(){
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what){
				case 1:
					//成功
//					ToastUtil.Short(R.string.network_anomaly);
					break;
				case 2:
					//失败
//					ToastUtil.Long("下载成功");
					break;
			}
		}
	};
	public static int i = 0;
	public  void downloadImage(final Context context,String url,final String name){
//		if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
//			ToastUtil.Long( "sd卡不可用，更新失败");
//			return;
//		}
//
//		if (files2.exists())
//			files2.delete();
		RequestBody requestBody = new FormEncodingBuilder()
				.build();
		final Request request = new Request.Builder()
				.post(requestBody)
				.tag(context)
				.url(url)
				.build();
		Log.e("swg","url::"+url+"---name::"+name);
//		Request request=new Request.Builder().tag(context).post().url(url).build();
		DOkHttp.getInstance().download4ServerListener(request, new DOkHttp.MyCallBack_Progress() {
			@Override
			public void onFailure(Request request, IOException e) {
//				handler.sendEmptyMessage(2);
			}

			@Override
			public void onResponse(Response response) {
//				files = FileUtils.saveFile2Local(response, filePath, "LuGan.apk");
				files2 = FileUtils.saveFile2Local(response, filePath2, name);
//				handler.sendEmptyMessage(1);

			}
		}, new DOkHttp.UIchangeListener() {
			@Override
			public void progressUpdate(long bytesWrite, long contentLength, boolean done) {
				int progress = (int) (bytesWrite * 100 / contentLength);

			}
		});
	}
	private int length;
    public  void downloadImage2(final Context context, String url, final String name_, final List<String> imgUrl, final boolean isInhome){
//		if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
//			ToastUtil.Long( "sd卡不可用，更新失败");
//			return;
//		}
//
//		if (files2.exists())
//			files2.delete();
        RequestBody requestBody = new FormEncodingBuilder()
                .build();
        final Request request = new Request.Builder()
                .post(requestBody)
                .tag(context)
                .url(url)
                .build();
//		Request request=new Request.Builder().tag(context).post().url(url).build();
        DOkHttp.getInstance().download4ServerListener(request, new DOkHttp.MyCallBack_Progress() {
            @Override
            public void onFailure(Request request, IOException e) {
				Log.e("swg","failure++"+i);
//                handler.sendEmptyMessage(2);
				if(isInhome){
					length = 4;
				}else{
					length = imgUrl.size();
				}
				if(imgUrl!=null&&i<length){
					pathtemp = imgUrl.get(i).replace(URL.HOST,"");
					nametemp = pathtemp.split("/")[pathtemp.split("/").length-1];
					filestemp = new File(filePath +"yinluimages/"+nametemp);
					if (!filestemp.exists()){
						downloadImage2(context,imgUrl.get(i),nametemp,imgUrl,isInhome);
					}
				}
            }

            @Override
            public void onResponse(Response response) {
				Log.e("swg","success++"+i);
                i++;
                FileUtils.saveFile2Local(response, filePath2, name_);
				if(isInhome){
					length = 4;
				}else{
					length = imgUrl.size();
				}
                if(imgUrl!=null&&i<length){
                    pathtemp = imgUrl.get(i).replace(URL.HOST,"");
                    nametemp = pathtemp.split("/")[pathtemp.split("/").length-1];
                    filestemp = new File(filePath +"yinluimages/"+nametemp);
                    if (!filestemp.exists()){
                        downloadImage2(context,imgUrl.get(i),nametemp,imgUrl,isInhome);
                    }
                }
//				files = FileUtils.saveFile2Local(response, filePath, "LuGan.apk");

//				handler.sendEmptyMessage(1);

            }
        }, new DOkHttp.UIchangeListener() {
            @Override
            public void progressUpdate(long bytesWrite, long contentLength, boolean done) {
                int progress = (int) (bytesWrite * 100 / contentLength);

            }
        });
    }
}
