package com.ranholmes.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TabHost.TabSpec;
import android.widget.Toast;

import com.ranholmes.aemanager.R;
import com.ranholmes.view.MyProgressDialog2;

public class MainUIActivity extends FragmentActivity{	
	private boolean flag;
	
	//定义FragmentTabHost对象
	private FragmentTabHost mTabHost;
	
	//定义一个布局
	private LayoutInflater layoutInflater;
		
	//定义数组来存放Fragment界面
	private Class fragmentArray[] = {FragmentPage1_home.class,FragmentPage2_search.class};
	
	//定义数组来存放按钮图片
	private int mImageViewArray[] = {R.drawable.tab_home_btn,R.drawable.tab_square_btn};
	
	
	
	//Tab选项卡的文字
	private String mTextviewArray[] = {"动态", "搜索", "我"};
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        
        setContentView(R.layout.main_tab_layout);
        
        initView();
    }
	 
	/**
	 * 初始化组件
	 */
	private void initView(){
		//实例化布局对象
		layoutInflater = LayoutInflater.from(this);
				
		//实例化TabHost对象，得到TabHost
		mTabHost = (FragmentTabHost)findViewById(android.R.id.tabhost);
		mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);	
		
		//得到fragment的个数
		int count = fragmentArray.length;
				
		for(int i = 0; i < count; i++){	
			//为每一个Tab按钮设置图标、文字和内容
			TabSpec tabSpec = mTabHost.newTabSpec(mTextviewArray[i]).setIndicator(getTabItemView(i));
			//将Tab按钮添加进Tab选项卡中
			mTabHost.addTab(tabSpec, fragmentArray[i], null);
			//设置Tab按钮的背景
		//	mTabHost.getTabWidget().getChildAt(i).setBackgroundResource(R.drawable.selector_tab_background);//选中时Tab的颜色
			
			mTabHost.getTabWidget().setDividerDrawable(null);//去除Tab之间的竖线
		}
	}
				
	/**
	 * 给Tab按钮设置图标和文字
	 */
	private View getTabItemView(int index){
		View view = layoutInflater.inflate(R.layout.tab_item_view, null);
		
		ImageView imageView = (ImageView) view.findViewById(R.id.imageview);
		imageView.setImageResource(mImageViewArray[index]);
		
//		TextView textView = (TextView) view.findViewById(R.id.textview);		
//		textView.setText(mTextviewArray[index]);
	
		return view;
	}
//	//退出时彻底关闭
//	@Override
//	public boolean onKeyDown(int keyCode, KeyEvent event) {
//		
//		//按下键盘上返回按钮
//		if(keyCode == KeyEvent.KEYCODE_BACK){
//						finish();
//			return true;
//		}else{		
//			return super.onKeyDown(keyCode, event);
//		}
//	}
// 
// 
//	@Override
//	protected void onDestroy() {
//		super.onDestroy();
//		
//		System.exit(0);
//		//或者下面这种方式
//		//android.os.Process.killProcess(android.os.Process.myPid()); 
//	}
	
	@Override
	protected void onStop() {
		super.onStop();
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if(flag){
			Intent intent = new Intent(getApplicationContext(),LoginActivity2.class);
			startActivity(intent);
			flag = false;
			finish();
		}
	}
	
	@Override
	public void onBackPressed() { 
    //实现Home键效果 
    //super.onBackPressed();这句话一定要注掉,不然又去调用默认的back处理方式了
	flag = true;
    Intent i= new Intent(Intent.ACTION_MAIN); 
    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 
    i.addCategory(Intent.CATEGORY_HOME); 
    startActivity(i);  
}
}
