package com.shensou.newpress.wxapi;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.shensou.newpress.activity.mine.RechargeActivity;
import com.shensou.newpress.gobal.Constants;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;


public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {
	
	private static final String TAG = "MicroMsg.SDKSample.WXPayEntryActivity";
	
    private IWXAPI api;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.pay_result);
        
    	api = WXAPIFactory.createWXAPI(this, Constants.WEIXIN_APP_ID);
        api.handleIntent(getIntent(), this);
    }

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
        api.handleIntent(intent, this);
	}

	@Override
	public void onReq(BaseReq req) {
	}

	@Override
	public void onResp(BaseResp resp) {

		if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
			if(resp.errCode==0){
//				Intent intent=new Intent(this, PaySuccessActivity.class);
//				startActivity(intent);
			}else{
//				Intent intent=new Intent(this, PayFailActivity.class);
//				startActivity(intent);
			}
			Intent intent=new Intent();
			intent.setAction(RechargeActivity.WEIXIN_PAY_RECEIVER);
			intent.putExtra("errCode",resp.errCode);
			sendBroadcast(intent);
			finish();
		}
	}
}