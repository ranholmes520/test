package com.ranholmes.iactivity;

public interface BMPCActivity {
	/**
	 * 刷新UI
	 */
	void refresh(Object... params);
	/**
	 * 初始化控件
	 */
	void initView();
}
