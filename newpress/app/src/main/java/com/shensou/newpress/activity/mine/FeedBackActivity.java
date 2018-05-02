package com.shensou.newpress.activity.mine;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.shensou.newpress.R;
import com.shensou.newpress.activity.BaseActivity;
import com.shensou.newpress.dokhttp.DOkHttp;
import com.shensou.newpress.gobal.URL;
import com.shensou.newpress.gobal.UserInfoXML;
import com.shensou.newpress.bean.CommonGson;
import com.shensou.newpress.utils.JsonUtils;
import com.shensou.newpress.utils.ToastUtil;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;

import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class FeedBackActivity extends BaseActivity {

	@Bind(R.id.toolbar_title)
	TextView tvTitle;
	@Bind(R.id.right)
	TextView tvRight;
	@Bind(R.id.feedback_edt_content)
	 EditText edtContent;
	
	private UserInfoXML mUserInfoXML;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_feedback);
		ButterKnife.bind(this);
		
		initData();
	}
	
	
	private void initData() {
		// TODO Auto-generated method stub
		tvTitle.setText(R.string.feedback);
		tvRight.setText(R.string.commit);
		mUserInfoXML=UserInfoXML.getInstance(this);
	}


	@OnClick({ R.id.right, R.id.back })
	public void buttonClick(View view) {

		switch (view.getId()) {
		case R.id.right:
			
			String token=mUserInfoXML.getuserid();
			String content=edtContent.getText().toString().trim();
			if(!TextUtils.isEmpty(content)){
				submit(token, content);
			}else{
				showToast(R.string.input_feedback);
			}

			break;

		case R.id.back:

			finish();
			break;

		default:
			break;
		}
	}
	
	private void submit(String token, String feedback){
		showProgressDialog();
		RequestBody requestBody=new FormEncodingBuilder().add("feedback", feedback).add("token",token)
				.build();

		Request request=new Request.Builder().post(requestBody).tag(this)
				.url(URL.FEEDBACK)
				.build();

		DOkHttp.getInstance().getData4Server(request, new DOkHttp.MyCallBack() {
			@Override
			public void onFailure(Request request, IOException e) {
				ToastUtil.showMyShortToast(R.string.network_anomaly);
				dismissProgressDialog();

			}

			@Override
			public void onResponse(String response) {
				dismissProgressDialog();
				Log.e("json", response);
				try {
					CommonGson result= JsonUtils.deserialize(response, CommonGson.class);
					showToast(result.getMsg());
					if(result.getCode().equals("200")){

						finish();
					}else if(result.getCode().equals("-1")){
						//token失效
//						Intent intent=new Intent(FeedBackActivity.this, LoginActivity.class);
//						startActivity(intent);
//						showToast(result.getMsg());
					}
				} catch (Exception e) {

				}
			}
		});

	}
}
