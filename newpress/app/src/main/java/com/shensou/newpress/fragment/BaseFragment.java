package com.shensou.newpress.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shensou.newpress.permission.PermissionUtils;
import com.shensou.newpress.widget.dialog.LoadingDialog;

import java.util.List;




public class BaseFragment extends Fragment implements PermissionUtils.PermissionCallbacks {

	private static final String TAG = BaseFragment.class.getCanonicalName();
	
	private AlertDialog progressDialog;
	/** Fragment当前状态是否可见 */
	protected boolean isVisible;

	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		return super.onCreateView(inflater, container, savedInstanceState);
	}


	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		// TODO Auto-generated method stub
		super.setUserVisibleHint(isVisibleToUser);
		if(getUserVisibleHint()) {
			isVisible = true;
			onVisible();
		} else {
			isVisible = false;
			onInvisible();
		}
	}
	/**
	 * 可见
	 */
	protected void onVisible() {
		lazyLoad();
	}


	/**
	 * 不可见
	 */
	protected void onInvisible() {


	}


	/**
	 * 延迟加载
	 * 子类必须重写此方法
	 */
	protected void lazyLoad() {
	}


	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	/**
	 * 显示dialog
	 *
	 * param msg 显示内容
	 */
	public void showProgressDialog() {
		try {

			if (progressDialog == null) {
				progressDialog = new LoadingDialog(getActivity());
				progressDialog.setCanceledOnTouchOutside(false);
			}
			progressDialog.show();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 隐藏dialog
	 */
	public void dismissProgressDialog() {
		try {

			if (progressDialog != null && progressDialog.isShowing()) {
				progressDialog.dismiss();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		PermissionUtils.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
	}

	@Override
	public void onPermissionGranted(int requestCode, List<String> perms) {
		Log.d(TAG, perms.size() + " permissions granted.");
	}

	@Override
	public void onPermissionDenied(int requestCode, List<String> perms) {
		Log.e(TAG, perms.size() + " permissions denied.");

		//此处不处理"不在询问"的状态，如果处理了会导致弹出两个Dialog
		//统一在BaseActivity中做处理
	}
}
