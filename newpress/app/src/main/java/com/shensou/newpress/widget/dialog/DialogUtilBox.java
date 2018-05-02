package com.shensou.newpress.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

import com.shensou.newpress.R;
import com.shensou.newpress.utils.ToastUtil;


/**
 * swg
 *  宝箱
 * @author Administrator
 *
 */

public class DialogUtilBox {
	private Dialog dialog;

    public void dialogDismiss(){
		if(dialog!=null&&dialog.isShowing())
		   dialog.dismiss();
	}
	public interface CallBackConfirm {
		void share();
		void calculate();
		void collect();
		void next();
	}

	private TextView dialog_default_address_cancele;
	private TextView dialog_xiantqao_num;
	private TextView dialog_default_address_confirm;
	private TextView dialog_default_address_prompt;
	private EditText et_dialog_default_address_pay_pwd;
	public DialogUtilBox(Context context, final CallBackConfirm2 callBackConfirm2){
		dialog = new Dialog(context, R.style.dialog);
		dialog.setContentView(R.layout.dialog_default_address2);
		dialog_default_address_cancele = (TextView)dialog.findViewById(R.id.dialog_default_address_cancele);
		dialog_xiantqao_num = (TextView)dialog.findViewById(R.id.dialog_xiantqao_num);
		dialog_default_address_confirm = (TextView)dialog.findViewById(R.id.dialog_default_address_confirm);
		dialog_default_address_prompt = (TextView)dialog.findViewById(R.id.dialog_default_address_prompt);
		et_dialog_default_address_pay_pwd = (EditText)dialog.findViewById(R.id.et_dialog_default_address_pay_pwd);
//		dialog_default_address_prompt.setText(context.getResources().getString(R.string.set_pay_pwd));

		dialog.setCanceledOnTouchOutside(true);
		dialog.show();
		dialog_default_address_cancele.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
		dialog_default_address_confirm.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if(TextUtils.isEmpty(et_dialog_default_address_pay_pwd.getText().toString().trim())){
					ToastUtil.showMyLongToast("请输入卷轴数");
					return;
				}
				if(Integer.parseInt(et_dialog_default_address_pay_pwd.getText().toString().trim())<1){
					ToastUtil.showMyLongToast("卷轴数必须大于0");
					return;
				}
				dialog.dismiss();
				callBackConfirm2.confirm(et_dialog_default_address_pay_pwd.getText().toString().trim());
			}
		});
	}
	/**
	 * 0 visible 1 invisible 2 gone
	 * @param visible
	 */
	public void setEditTextVisible(int visible){
		switch (visible) {
			case 0:
				et_dialog_default_address_pay_pwd.setVisibility(View.VISIBLE);
				break;
			case 1:
				et_dialog_default_address_pay_pwd.setVisibility(View.INVISIBLE);
				break;
			case 2:
				et_dialog_default_address_pay_pwd.setVisibility(View.GONE);
				break;
			default:
				break;
		}
	}
	/**
	 * @promt 提示
	 */
	public void setPromt(String promt){
		dialog_default_address_prompt.setText(promt);
	}
	public void setEditText(String edittext){
		et_dialog_default_address_pay_pwd.setText(edittext);
		et_dialog_default_address_pay_pwd.setEnabled(false);
		et_dialog_default_address_pay_pwd.setClickable(false);
		et_dialog_default_address_pay_pwd.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
	}
	public void setXiantaoNum(String num){
		dialog_xiantqao_num.setText(num);
	}
	public  interface CallBackConfirm2{
		void confirm(String num);
	}
}
