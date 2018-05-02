package com.shensou.newpress.widget.dialog;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.shensou.newpress.R;


/**
 * Created by Administrator on 2016/5/3 0003.
 */
public class DownLoadDialog extends AlertDialog {
    protected View content;
    private ProgressBar progressBar;
    private TextView tvProgress;

    public DownLoadDialog(Context context) {
        super(context);
        init(context);
    }

    public DownLoadDialog(Context context, int theme) {
        super(context, theme);
        init(context);
    }

    private void init(final Context context){
//        setCanceledOnTouchOutside(false);
        setCancelable(false);
        content = LayoutInflater.from(context).inflate(
                R.layout.dialog_download, null);
        progressBar= (ProgressBar) content.findViewById(R.id.download_progress);
        tvProgress= (TextView) content.findViewById(R.id.tv_process);
        progressBar.setMax(100);
        setMessage("正在更新，请稍候...");
        super.setView(content);
    }

    public void setProgress(int progress){
        progressBar.setProgress(progress);
        tvProgress.setText(progress+"");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.dismiss();
    }
}
