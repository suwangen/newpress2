package com.shensou.newpress.activity.mine;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shensou.newpress.R;
import com.shensou.newpress.activity.BaseActivity;
import com.shensou.newpress.utils.Tools;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 账号安全
 */
public class AccountSafeActivity extends BaseActivity {

	@Bind(R.id.toolbar_title)
	TextView tvTitle;
	@Bind(R.id.lin_bottom)
	LinearLayout lin_bottom;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_account_safe);
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
		tvTitle.setText("账号安全");

	}



	@OnClick({  R.id.back
			})
	public void buttonClick(View view) {
		Intent intent = null;
		switch (view.getId()) {
			case R.id.back:
				finish();
				break;
			default:
				break;
		}
	}


}
