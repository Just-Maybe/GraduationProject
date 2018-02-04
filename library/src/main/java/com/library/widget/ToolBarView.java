package com.library.widget;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.library.R;
import com.library.utils.AppManager;


public class ToolBarView extends RelativeLayout {

	private TextView mTitleView;// 标题
	private ImageView mLlBack;// 返回按钮
	public ImageView mIvRight;// 按钮

	public ToolBarView(Context context) {
		super(context);
		// 初始化视图
		init();
	}

	@SuppressLint("Recycle")
	public ToolBarView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// 初始化视图
		init();
		if (attrs == null) {
			return;
		}
		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ToolBar);
		String title = a.getString(R.styleable.ToolBar_titleName);
		// 设置标题
		if (!TextUtils.isEmpty(title)) {
			int i = title.indexOf("/");
			if (i >= 0) {
				String name = title.substring(i + 1);
				String value = null;
				if (title.startsWith("@string")) {
					value = getResources().getString(
							getResources().getIdentifier(name, "string",
									getContext().getPackageName()));
				} else if (title.startsWith("@android:string")) {
					value = getResources().getString(
							getResources().getIdentifier(name, "string",
									"android"));
				}
				if (!TextUtils.isEmpty(value) && mTitleView != null) {
					mTitleView.setText(value);
				}
			} else {
				if (mTitleView != null) {
					mTitleView.setText(title);
				}
			}
		}
		// 设置是否显示返回图标，默认为true
		boolean showBack = a.getBoolean(R.styleable.ToolBar_showBack, true);
		// 设置显示返回如果为true则显示，false则不显示
		mLlBack.setVisibility(showBack ? View.VISIBLE : View.GONE);
		int rightResource = a.getInteger(R.styleable.ToolBar_rightPic, 0);
		if (rightResource != 0) {
			mIvRight.setVisibility(VISIBLE);
			mIvRight.setImageResource(rightResource);
		}
	}

	/**
	 * 设置返回的点击事件监听
	 * 
	 * @param listener
	 *            事件监听
	 */
	public void setOnBackClickListener(View.OnClickListener listener) {
		if (mLlBack != null) {
			mLlBack.setOnClickListener(listener);
		}
	}

	/**
	 * 设置右按钮的点击事件监听
	 *
	 * @param listener
	 *            事件监听
	 */
	public void setOnRightClickListener(View.OnClickListener listener) {
		if (mIvRight != null) {
			mIvRight.setOnClickListener(listener);
		}
	}


	/**
	 * 设置标题
	 * 
	 * @param title
	 *            标题内容
	 */
	public void setTitle(String title) {
		if (!TextUtils.isEmpty(title) && mTitleView != null) {
			mTitleView.setText(title);
		}
	}

	/**
	 * 设置标题
	 * 
	 * @param resId
	 *            根据本地资源id
	 */
	public void setTitle(int resId) {
		if (resId <= 0) {
			return;
		}

		String title = getResources().getString(resId);
		if (!TextUtils.isEmpty(title) && mTitleView != null) {
			mTitleView.setText(title);
		}
	}

	/**
	 * 设置背景颜色
	 * @param color
	 *
	 */

	@SuppressLint("ResourceAsColor")
	public void setTitleBackground(int color) {
		RelativeLayout iv = (RelativeLayout)findViewById(R.id.toolbar_view);
		iv.setBackgroundColor(color);
	}

	public void setRightBg(int resource) {
		if (resource != 0) {
			mIvRight.setVisibility(VISIBLE);
			mIvRight.setImageResource(resource);
		}
	}

	public void setLeftImage(int resoure){
		if (resoure != 0) {
			mLlBack.setVisibility(VISIBLE);
			mLlBack.setImageResource(resoure);
		}
	}


	/**
	 * 初始化界面
	 */
	private void init() {
		LayoutParams lp = (LayoutParams) getLayoutParams();
		if (lp == null) {
			lp = new LayoutParams(
					android.view.ViewGroup.LayoutParams.MATCH_PARENT,
					android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
			setLayoutParams(lp);
		}

		LayoutInflater inflater = (LayoutInflater) getContext()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.toolbar, this);

		mLlBack = (ImageView) findViewById(R.id.iv_back);
		mTitleView = (TextView) findViewById(R.id.tv_header_title);
		mIvRight = (ImageView) findViewById(R.id.iv_right);
		mLlBack.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Activity activity = (Activity) getContext();
				if (getContext() instanceof Activity) {
					AppManager.get().finishActivity(activity.getClass());
				}
			}
		});
	}
}
