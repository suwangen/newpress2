package com.shensou.newpress.activity.mine;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shensou.newpress.R;
import com.shensou.newpress.activity.BaseActivity;
import com.shensou.newpress.activity.LoginActivity;
import com.shensou.newpress.bean.BaseGson;
import com.shensou.newpress.bean.UpdateGson;
import com.shensou.newpress.dokhttp.DOkHttp;
import com.shensou.newpress.gobal.PushInfoXML;
import com.shensou.newpress.gobal.URL;
import com.shensou.newpress.gobal.UserInfoXML;
import com.shensou.newpress.utils.DataCleanManager;
import com.shensou.newpress.utils.JsonUtils;
import com.shensou.newpress.utils.ToastUtil;
import com.shensou.newpress.utils.Tools;
import com.shensou.newpress.utils.VersionUtils;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;

import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingActivity extends BaseActivity {

	@Bind(R.id.toolbar_title)
	TextView tvTitle;
	@Bind(R.id.set_tv_clear_cache)
	TextView tvClearCache;
	@Bind(R.id.set_tv_version_update)
	TextView tvUpdate;
	@Bind(R.id.toggle_send)
	ImageView toggleButton;
	@Bind(R.id.info_btn_login_out)
	Button info_btn_login_out;
	@Bind(R.id.lin_bottom)
	LinearLayout lin_bottom;
	private PushInfoXML pushInfoXML;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);
		ButterKnife.bind(this);
		Tools.setStatusBarTranslucent((Activity) context);
//		lin_bottom.setPadding(0, 0, 0, Tools.getNavigationBarHeight(context));//虚拟底部按键高度
		initData();
//		if(Tools.checkUserLogin(SettingActivity.this)){
//			info_btn_login_out.setVisibility(View.VISIBLE);
//		}else{
//			info_btn_login_out.setVisibility(View.GONE);
//		}
	}

	private void initData() {
		// TODO Auto-generated method stub
		tvTitle.setText("系统设置");
		pushInfoXML= PushInfoXML.getInstance(this);
		try {
			tvClearCache.setText(DataCleanManager.getTotalCacheSize(this));

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		tvUpdate.setText("V" + VersionUtils.getVersion(this));

		toggleButton.setSelected(pushInfoXML.isReceive());

	}



	@OnClick({ R.id.set_rl_clear_cache,
			R.id.set_tv_feedback, R.id.set_rl_version_update,
			R.id.set_tv_about_us, R.id.set_tv_a_key_praise, R.id.back
			,R.id.info_btn_login_out,R.id.toggle_send
	        ,R.id.set_rl_account_safe,R.id.set_rl_secret
	})
	public void buttonClick(View view) {
		Intent intent = null;
		switch (view.getId()) {
			case R.id.info_btn_login_out:
				UserInfoXML.getSharedEditor(SettingActivity.this)
						.clear().commit();
				finish();
				mUserInfoXML.setis_change_statue("2");
				intent = new Intent(SettingActivity.this, LoginActivity.class);
				startActivity(intent);
				//退出登录
//				logout(mUserInfoXML.getuserid());
				break;
			case R.id.toggle_send:
				toggleButton.setSelected(!toggleButton.isSelected());
				pushInfoXML.setReceive(toggleButton.isSelected());
//				if(pushInfoXML.isReceive()){
//					//接收推送
//					PushManager.startWork(getApplicationContext(), PushConstants.LOGIN_TYPE_API_KEY,
//							PushUtils.getMetaValue(SettingActivity.this, "api_key"));
//				}else{
//					PushManager.stopWork(this);
//				}
				break;
			case R.id.set_rl_clear_cache:
				// 清除缓存
				DataCleanManager.clearAllCache(this);
				try {
					tvClearCache.setText(DataCleanManager.getTotalCacheSize(this));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				break;

			case R.id.set_tv_feedback:
				// 意见反馈
				Intent feedbackIntent = new Intent(this, FeedBackActivity.class);
				startActivity(feedbackIntent);

				break;

			case R.id.set_rl_version_update:
				// 版本更新
				checkUpdate();

				break;

			case R.id.set_tv_about_us:
				// 关于我们
				Intent aboutUsIntent = new Intent(this, AboutUsAvtivity.class);
				startActivity(aboutUsIntent);

				break;

			case R.id.set_tv_a_key_praise:
				Uri uri = Uri.parse("market://details?id="+getPackageName());
				intent = new Intent(Intent.ACTION_VIEW,uri);
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(intent);

				break;

			case R.id.set_rl_account_safe://账号安全
				intent = new Intent(this, AccountSafeActivity.class);
				startActivity(intent);
				break;
			case R.id.set_rl_secret://隐私设置
				intent = new Intent(this, SecretSettingActivity.class);
				startActivity(intent);
				break;
			case R.id.back:
				SettingActivity.this.finish();
				break;

			default:
				break;
		}
	}

	/**
	 * 退出登录
	 *
	 * @param token
	 *            手机号
	 * @param
	 */
	private void logout(String token) {
		showProgressDialog();
		RequestBody requestBody=new FormEncodingBuilder()
				.add("token", token)
				.build();
		Request request=new Request.Builder()
				.post(requestBody)
				.tag(this)
				.url(URL.LOGOUT)
				.build();

		DOkHttp.getInstance().getData4Server(request, new DOkHttp.MyCallBack() {
			@Override
			public void onFailure(Request request, IOException e) {
				dismissProgressDialog();
				ToastUtil.showMyShortToast(R.string.network_anomaly);
			}

			@Override
			public void onResponse(String response) {
				Log.e("退出登录",response);
				try {
					dismissProgressDialog();
					BaseGson result = JsonUtils.deserialize(new String(response),
							BaseGson.class);
					ToastUtil.showMyLongToast(result.getMsg());
					if (result.getCode().equals("200")) {
						UserInfoXML.getSharedEditor(SettingActivity.this)
								.clear().commit();
						Intent intent = new Intent(SettingActivity.this, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						startActivity(intent);
					}

				} catch (Exception e) {

				}
			}
		});

	}
	public  void  checkUpdate(){
		showProgressDialog();
		Request request=new Request.Builder().get().tag(this)
				.url(URL.CHECK_UPDATE)
				.build();
		DOkHttp.getInstance().getData4Server(request, new DOkHttp.MyCallBack() {
			@Override
			public void onFailure(Request request, IOException e) {
				ToastUtil.showMyShortToast(R.string.network_anomaly);
				dismissProgressDialog();

			}

			@Override
			public void onResponse(String response) {
				Log.e("response", response);
				dismissProgressDialog();
				try {
					UpdateGson result= JsonUtils.deserialize(response, UpdateGson.class);
					if (result.getCode().equals("200")){
						Log.e("version", VersionUtils.getVersion(SettingActivity.this));
						if (!VersionUtils.getVersion(SettingActivity.this).equals(result.getData().getName())){
							//版本号相同则不弹框提示
							VersionUtils.showUpdateDialog(SettingActivity.this,result.getData().getName(), result.getData().getContent(), result.getData().getPath());
						}else{
							ToastUtil.showMyLongToast("已是最新版");
						}

					}else
						ToastUtil.showMyLongToast(result.getMsg());
				} catch (Exception e) {

				}
			}
		});
	}


}
