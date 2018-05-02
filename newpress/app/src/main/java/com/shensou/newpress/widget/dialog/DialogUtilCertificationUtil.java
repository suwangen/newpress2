package com.shensou.newpress.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.shensou.newpress.R;
import com.shensou.newpress.activity.home.ActivityDetailActivity;
import com.shensou.newpress.gobal.URL;


/**
 * swg
 * @author Administrator
 *
 */

public class DialogUtilCertificationUtil {
	private Dialog dialog;

    public void dialogDismiss(){
		if(dialog!=null&&dialog.isShowing())
		   dialog.dismiss();
	}

	private TextView tv_certification_renzheng_no;
	private TextView tv_certification_renzheng_yes;
	public DialogUtilCertificationUtil(final Context context, final CallBackConfirm2 callBackConfirm2){
		dialog = new Dialog(context, R.style.dialog);
		dialog.setContentView(R.layout.dialog_certification_confirm);
		tv_certification_renzheng_no = (TextView)dialog.findViewById(R.id.tv_certification_renzheng_no);
		tv_certification_renzheng_yes = (TextView)dialog.findViewById(R.id.tv_certification_renzheng_yes);

		dialog.setCanceledOnTouchOutside(true);
		dialog.show();
		tv_certification_renzheng_no.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
		tv_certification_renzheng_yes.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dialog.dismiss();
				Intent intent = new Intent(context,ActivityDetailActivity.class);
				intent.putExtra("title","实名认证");
				intent.putExtra("url", URL.USER_CERTIFICATION);
				context.startActivity(intent);
			}
		});
	}
	public  interface CallBackConfirm2{
		void confirm(String num);
	}
}
