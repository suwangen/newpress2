package com.shensou.newpress.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.shensou.newpress.R;
import com.shensou.newpress.utils.ToastUtil;


/**
 * swg
 * 客服
 * @author Administrator
 *
 */

public class DialogUtilCustomerServicer {
	private Dialog dialog;

    public void dialogDismiss(){
		if(dialog!=null&&dialog.isShowing())
		   dialog.dismiss();
	}

	private TextView tv_dialog_customer_servicer_phone;
	public DialogUtilCustomerServicer(final Context context, final CallBackConfirm2 callBackConfirm2){
		dialog = new Dialog(context, R.style.dialog);
		dialog.setContentView(R.layout.dialog_customer_servicer);
		tv_dialog_customer_servicer_phone = (TextView)dialog.findViewById(R.id.tv_dialog_customer_servicer_phone);

		dialog.setCanceledOnTouchOutside(true);
		dialog.show();
		tv_dialog_customer_servicer_phone.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (TextUtils.isEmpty(tv_dialog_customer_servicer_phone.getText().toString().trim())) {
					ToastUtil.showMyLongToast("暂无客服");
					return;
				}
				Intent intent = new Intent(Intent.ACTION_VIEW,
						Uri.parse("tel:" + tv_dialog_customer_servicer_phone.getText().toString().trim()));
				context.startActivity(intent);
				dialog.dismiss();
			}
		});
	}
	public  interface CallBackConfirm2{
		void confirm(String num);
	}
}
