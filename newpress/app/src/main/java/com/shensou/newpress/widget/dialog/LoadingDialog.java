package com.shensou.newpress.widget.dialog;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;

import com.shensou.newpress.R;


/**
 * Created by Administrator on 2016/4/21 0021.
 */
public class LoadingDialog extends AlertDialog {

    protected View content;

    public LoadingDialog(Context context) {
        super(context);
        init(context);
    }

    public LoadingDialog(Context context, int theme) {
        super(context, theme);
        init(context);
    }


    private void init(final Context context){
        setCanceledOnTouchOutside(false);
        content = LayoutInflater.from(context).inflate(
                R.layout.dialog_loading, null);
        super.setView(content);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.dismiss();
    }
}
