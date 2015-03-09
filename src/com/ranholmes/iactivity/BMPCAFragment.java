package com.ranholmes.iactivity;

import android.view.View;

public interface BMPCAFragment {
	/**
	 * 刷新UI
	 */
	void refresh(Object... params);
	/**
	 * 初始化控件
	 */
	void initView(View v);
}
