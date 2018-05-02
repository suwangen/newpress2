package com.shensou.newpress.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.shensou.newpress.R;
import com.shensou.newpress.gobal.MyApplication;


/**
 * Created by Administrator on 2016/2/23 0023.
 */
public class ToastUtil {

//    public static void Short(@NonNull CharSequence sequence) {
//        Toast.makeText(MyApplication.getInstance(), sequence, Toast.LENGTH_SHORT).show();
//    }
//
//    public static void Long(@NonNull CharSequence sequence) {
//        Toast.makeText(MyApplication.getInstance(), sequence, Toast.LENGTH_SHORT).show();
//    }
//    public static void Short(@NonNull int id) {
//        String message= MyApplication.getInstance().getResources().getString(id);
//        Toast.makeText(MyApplication.getInstance(), message, Toast.LENGTH_SHORT).show();
//    }
//
//    public static void Long(@NonNull int id) {
//        String message= MyApplication.getInstance().getResources().getString(id);
//        Toast.makeText(MyApplication.getInstance(), message, Toast.LENGTH_SHORT).show();
//    }
    public static void showMyLongToast(@NonNull int id){
        Toast result = new Toast(MyApplication.getInstance());
        String toast= MyApplication.getInstance().getResources().getString(id);
        LayoutInflater inflate = (LayoutInflater) MyApplication.getInstance()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflate.inflate(R.layout.my_toast, null);
        TextView tv_toast_promt = (TextView) v.findViewById(R.id.tv_toast_promt);
        tv_toast_promt.setText(toast);
        result.setDuration(Toast.LENGTH_LONG);
        result.setGravity(Gravity.CENTER, 0, 0);
        result.setView(v);
        result.show();
    }
    public static void showMyLongToast(String toast){
        Toast result = new Toast(MyApplication.getInstance());

        LayoutInflater inflate = (LayoutInflater) MyApplication.getInstance()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflate.inflate(R.layout.my_toast, null);
        TextView tv_toast_promt = (TextView) v.findViewById(R.id.tv_toast_promt);
        tv_toast_promt.setText(toast);
        result.setDuration(Toast.LENGTH_LONG);
        result.setGravity(Gravity.CENTER, 0, 0);
        result.setView(v);
        result.show();
    }
    public static void showMyShortToast(String toast){
        Toast result = new Toast(MyApplication.getInstance());

        LayoutInflater inflate = (LayoutInflater) MyApplication.getInstance()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflate.inflate(R.layout.my_toast, null);
        TextView tv_toast_promt = (TextView) v.findViewById(R.id.tv_toast_promt);
        tv_toast_promt.setText(toast);
        result.setDuration(Toast.LENGTH_SHORT);
        result.setGravity(Gravity.CENTER, 0, 0);
        result.setView(v);
        result.show();
    }
    public static void showMyShortToast(@NonNull int id){
        Toast result = new Toast(MyApplication.getInstance());
        String toast= MyApplication.getInstance().getResources().getString(id);
        LayoutInflater inflate = (LayoutInflater) MyApplication.getInstance()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflate.inflate(R.layout.my_toast, null);
        TextView tv_toast_promt = (TextView) v.findViewById(R.id.tv_toast_promt);
        tv_toast_promt.setText(toast);
        result.setDuration(Toast.LENGTH_SHORT);
        result.setGravity(Gravity.CENTER, 0, 0);
        result.setView(v);
        result.show();
    }
}
