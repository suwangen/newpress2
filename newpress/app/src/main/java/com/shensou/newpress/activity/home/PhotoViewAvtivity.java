package com.shensou.newpress.activity.home;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.bm.library.Info;
import com.bm.library.PhotoView;
import com.shensou.newpress.R;
import com.shensou.newpress.activity.BaseActivity;
import com.shensou.newpress.utils.ImageLoadProxy;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 *
 */
public class PhotoViewAvtivity extends BaseActivity {

	@Bind(R.id.toolbar_title)
	TextView tvTitle;
	@Bind(R.id.photoview)
	PhotoView photoView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_photoview);
		ButterKnife.bind(this);
		
		initData();
	}

	private void initData() {
		// TODO Auto-generated method stub
		tvTitle.setText("");
		ImageLoadProxy.displayImageWithLoadingPicture(mUserInfoXML.getUser_avatar(),
				photoView, R.drawable.default_head);
		// 启用图片缩放功能
		photoView.enable();
		// 获取图片信息
		Info info = photoView.getInfo();
		// 从普通的ImageView中获取Info
//		Info info2 = PhotoView.getImageViewInfo(image);
		// 从一张图片信息变化到现在的图片，用于图片点击后放大浏览，具体使用可以参照demo的使用
		photoView.animaFrom(info);
		// 从现在的图片变化到所给定的图片信息，用于图片放大后点击缩小到原来的位置，具体使用可以参照demo的使用
		photoView.animaTo(info,new Runnable() {
			@Override
			public void run() {
				//动画完成监听
			}
		});
//		// 获取/设置 动画持续时间
		photoView.setAnimaDuring(500);
//		int d = photoView.getAnimaDuring();
//		// 获取/设置 最大缩放倍数
		photoView.setMaxScale(5);
//		float maxScale = photoView.getMaxScale();
//		// 设置动画的插入器
//		photoView.setInterpolator(Interpolator interpolator);
	}

	@OnClick({ R.id.back})
	public void buttonClick(View view) {

		switch (view.getId()) {
		case R.id.back:
			finish();
			break;


		default:
			break;
		}
	}
}
