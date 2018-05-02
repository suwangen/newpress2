package com.shensou.newpress.widget.dialog;

import android.content.Context;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.shensou.newpress.R;


/**
 * 选择头像ialog
 *
 * @author kymjs
 */
public class SelectHeadDialog extends CommonDialog implements
        View.OnClickListener {

    public interface OnSelectHeadClick {
        void onSelectHeadClick(int id);
    }

    private OnSelectHeadClick mListener;

    private SelectHeadDialog(Context context, int defStyle) {
        super(context, defStyle);
        init();
    }

    public SelectHeadDialog(Context context) {
        //this(context, R.style.dialog_share);
        super(context);
        init();
    }

    private void init() {
        View shareView = getLayoutInflater().inflate(
                R.layout.dialog_select_head, null);
        shareView.findViewById(R.id.slect_head_take_photo).setOnClickListener(this);
        shareView.findViewById(R.id.slect_head_album).setOnClickListener(
                this);
        setContent(shareView, 0);
    }

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getWindow().setGravity(Gravity.CENTER);

        WindowManager m = getWindow().getWindowManager();
        Display d = m.getDefaultDisplay();
        WindowManager.LayoutParams p = getWindow().getAttributes();
//        p.width = d.getWidth()-2*((int) getContext().getResources().getDimension(
//                R.dimen.global_dialog_padding));
        p.width= LinearLayout.LayoutParams.WRAP_CONTENT;
        getWindow().setAttributes(p);
    }

    public void setOnSelectHeadClickListener(OnSelectHeadClick lis) {
        mListener = lis;
    }

    @Override
    public void onClick(View v) {
        final int id = v.getId();
        if (mListener != null) {
            mListener.onSelectHeadClick(id);
        }
    }
}
