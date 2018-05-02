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
 * 钱包
 */
public class WalletActivity extends BaseActivity {

    @Bind(R.id.toolbar_title)
    TextView toolbar_title;
    @Bind(R.id.lin_bottom)
    LinearLayout lin_bottom;
    private View parentView;

    private int p = 0;
    private String uid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);
        ButterKnife.bind(this);
        Tools.setStatusBarTranslucent((Activity) context);
//        lin_bottom.setPadding(0, 0, 0, Tools.getNavigationBarHeight(context));//虚拟底部按键高度
        initView();
    }


    private void initView() {

        toolbar_title.setText("钱包");
        uid = mUserInfoXML.getUid();

    }

    @OnClick({R.id.back,R.id.tv_wallet_recharge})
    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.back:
                onBackPressed();
                break;
            case R.id.tv_wallet_recharge:
                intent = new Intent(context, RechargeActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}
