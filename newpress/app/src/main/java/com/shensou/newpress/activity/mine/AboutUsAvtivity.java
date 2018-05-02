package com.shensou.newpress.activity.mine;

import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.shensou.newpress.R;
import com.shensou.newpress.activity.BaseActivity;
import com.shensou.newpress.utils.Tools;
import com.shensou.newpress.utils.VersionUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 关于我们
 */
public class AboutUsAvtivity extends BaseActivity {

	@Bind(R.id.toolbar_title)
	TextView tvTitle;
	@Bind(R.id.about_us_tv_version)
	 TextView tvVersion;//版本号
	@Bind(R.id.about_us_tv_email)
	 TextView tvEmail;//邮箱
	@Bind(R.id.about_us_tv_hotline)
	 TextView tvHotline;
	@Bind(R.id.about_us_tv_talks_phone)
	 TextView tvTalksPhone;

	private String version;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about_us);
		ButterKnife.bind(this);
		
		initData();
	}

	private void initData() {
		// TODO Auto-generated method stub
		tvTitle.setText(R.string.about_us);
		tvEmail.getPaint().setFlags(Paint. UNDERLINE_TEXT_FLAG ); //下划线
		tvEmail.getPaint().setAntiAlias(true);//抗锯齿
		version= VersionUtils.getVersion(this);
		tvVersion.setText("(home_banben："+"V"+version+")");
	}

	@OnClick({ R.id.back, R.id.about_us_tv_net_address,
			R.id.about_us_tv_hotline, R.id.about_us_tv_talks_phone })
	public void buttonClick(View view) {

		switch (view.getId()) {
		case R.id.about_us_tv_net_address:
			Intent webIntent = new Intent();        
		     webIntent.setAction("android.intent.action.VIEW");     
		     webIntent.setData(Uri.parse(getResources().getString(R.string.internet_site)));
		     startActivity(webIntent);
			break;
		case R.id.about_us_tv_hotline:
			String hotline=tvHotline.getText().toString().trim().replace("-", "");
			Tools.callTell(this, hotline);

			break;
		case R.id.about_us_tv_talks_phone:
			String talksPhone=tvTalksPhone.getText().toString().trim().replace("-", "");
			Tools.callTell(this, talksPhone);

			break;
		case R.id.back:
			finish();
			break;
		

		default:
			break;
		}
	}
}
