package com.shensou.newpress.widget.dialog;

import android.content.Context;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;

import com.shensou.newpress.R;


/**
 * 分享界面dialog
 *
 * @author kymjs
 */
public class ShareDialog extends CommonDialog implements
        View.OnClickListener {

    public interface OnSharePlatformClick {
        void onPlatformClick(int id);
    }

    private OnSharePlatformClick mListener;

    private ShareDialog(Context context, int defStyle) {
        super(context, defStyle);
        init();
    }

    public ShareDialog(Context context) {
        //this(context, R.style.dialog_share);
        super(context);
        init();
    }

    private void init() {
        View shareView = getLayoutInflater().inflate(
                R.layout.dialog_cotent_share, null);
        shareView.findViewById(R.id.ly_share_qq).setOnClickListener(this);
        shareView.findViewById(R.id.ly_share_sina_weibo).setOnClickListener(
                this);
        shareView.findViewById(R.id.ly_share_weichat).setOnClickListener(this);
        shareView.findViewById(R.id.ly_share_weichat_circle)
                .setOnClickListener(this);
        setContent(shareView, 0);
    }

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getWindow().setGravity(Gravity.CENTER);

        WindowManager m = getWindow().getWindowManager();
        Display d = m.getDefaultDisplay();
        WindowManager.LayoutParams p = getWindow().getAttributes();
        p.width = d.getWidth()-2*((int) getContext().getResources().getDimension(
                R.dimen.global_dialog_padding));
//        p.width= LinearLayout.LayoutParams.WRAP_CONTENT;
        getWindow().setAttributes(p);
    }

    public void setOnPlatformClickListener(OnSharePlatformClick lis) {
        mListener = lis;
    }

    @Override
    public void onClick(View v) {
        final int id = v.getId();
        if (mListener != null) {
            mListener.onPlatformClick(id);
        }
    }
}
