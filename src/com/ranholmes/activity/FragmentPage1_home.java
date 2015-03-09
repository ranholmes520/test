package com.ranholmes.activity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ImageView.ScaleType;

import com.ranholmes.aemanager.R;
import com.ranholmes.iactivity.BMPCAFragment;
import com.ranholmes.util.InternetUtil;
import com.ranholmes.view.MyProgressDialog2;

public class FragmentPage1_home extends Fragment implements BMPCAFragment {
	private View v;
	private ImageButton codeBtn;
	private RelativeLayout mSweptCode;
	private RelativeLayout mGetCode;
	
	private RelativeLayout mTrans;
	private RelativeLayout mRecord;
	private RelativeLayout mDonate;
	private RelativeLayout mBalance;
	
	private List<ImageView> imageViews; // 滑动的图片集合
	private String[] titles; // 图片标题
	private int[] imageResId; // 图片ID
	private TextView tv_title;
	private int currentItem = 0; // 当前图片的索引号
	// android-support-v4中的滑动组件
	private ViewPager viewPager;
	
	private MyProgressDialog2 mInternet;
	
	private ScheduledExecutorService scheduledExecutorService;
	
	private List<View> dots; // 图片标题正文的那些点
	
	// 切换当前显示的图片
		private Handler handler = new Handler() {
			public void handleMessage(android.os.Message msg) {
				viewPager.setCurrentItem(currentItem);// 切换当前显示的图片
			};
		};
	
	private String items[] = { "手机联系人转账", "账号转账"};
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		if (v == null) {
			v = inflater.inflate(R.layout.fragment_1_home, container, false);
			initView(v);
		}
		// 缓存的v需要判断是否已经被加过parent，
		// 如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
		ViewGroup parent = (ViewGroup) v.getParent();
		if (parent != null) {
			parent.removeView(v);
		}
		return v;
	}
	
	
	@Override
	public void refresh(Object... params) {
	}

	@Override
	public void initView(View v) {
		mSweptCode = (RelativeLayout)v.findViewById(R.id.fragment_1_home_swept_code);
		mGetCode   = (RelativeLayout)v.findViewById(R.id.fragment_1_home_get_code);
		mTrans     = (RelativeLayout)v.findViewById(R.id.fragment_1_home_trans);
		mRecord     = (RelativeLayout)v.findViewById(R.id.fragment_1_home_record);
		mDonate     = (RelativeLayout)v.findViewById(R.id.fragment_1_home_donate);
		mBalance     = (RelativeLayout)v.findViewById(R.id.fragment_1_home_balance);
		
		mSweptCode.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				if(new InternetUtil().isNetworkConnected(getActivity())){
					Intent intent = new Intent(getActivity(),CaptureActivity.class);
					getActivity().startActivity(intent);
				}else{
					mInternet = new MyProgressDialog2(getActivity());
					mInternet.show();
				}
				
			}
		});
		
		mGetCode.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				if(new InternetUtil().isNetworkConnected(getActivity())){
					Intent intent = new Intent(getActivity(),MakecodeActivity.class);
					getActivity().startActivity(intent);
				}else{
					mInternet = new MyProgressDialog2(getActivity());
					mInternet.show();
				}
				
			}
		});
		
		mTrans.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				if(new InternetUtil().isNetworkConnected(getActivity())){
					AlertDialog.Builder builder = new AlertDialog.Builder(
							getActivity());
					builder.setItems(items, new OnClickListener() {

						@Override
						public void onClick(DialogInterface arg0, int id) {
							Intent intent;
							switch (id) {
							case 0:
								intent = new Intent(getActivity(),TelephoneActivity.class);
								getActivity().startActivity(intent);
								break;
							case 1:
								intent = new Intent(getActivity(),EnterIDActivity.class);
								getActivity().startActivity(intent);
								break;
							}
						}
					});
					Dialog dialog = builder.create();
					dialog.show();
				}else{
					mInternet = new MyProgressDialog2(getActivity());
					mInternet.show();
				}
			}
		});
		
		mRecord.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				if(new InternetUtil().isNetworkConnected(getActivity())){
					Intent intent = new Intent(getActivity(),RecordActivity.class);
					getActivity().startActivity(intent);
				}else{
					mInternet = new MyProgressDialog2(getActivity());
					mInternet.show();
				}
				
			}
		});
		
		mDonate.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				if(new InternetUtil().isNetworkConnected(getActivity())){
					Intent intent = new Intent(getActivity(),DonateActivity.class);
					getActivity().startActivity(intent);
				}else{
					mInternet = new MyProgressDialog2(getActivity());
					mInternet.show();
				}
				
			}
		});
		
		mBalance.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				if(new InternetUtil().isNetworkConnected(getActivity())){
					Intent intent = new Intent(getActivity(),BalanceActivity.class);
					getActivity().startActivity(intent);
				}else{
					mInternet = new MyProgressDialog2(getActivity());
					mInternet.show();
				}
				
			}
		});
		
		
		
		imageResId = new int[] { 1, 2, 3 };
		titles = new String[imageResId.length];
		titles[0] = "广告位1";
		titles[1] = "广告位2";
		titles[2] = "广告位3";

		imageViews = new ArrayList<ImageView>();
		
		for (int i = 0; i < imageResId.length; i++) {
			ImageView imageView = new ImageView(getActivity());
			if(i==0){
				imageView.setImageResource(R.drawable.image_adv1);
			}else if(i==1){
				imageView.setImageResource(R.drawable.image_adv2);
			}else{
				imageView.setImageResource(R.drawable.image_adv3);
			}
			
			imageView.setScaleType(ScaleType.CENTER_CROP);
			imageViews.add(imageView);
		}
		
		
		dots = new ArrayList<View>();
		dots.add(v.findViewById(R.id.v_dot0));
		dots.add(v.findViewById(R.id.v_dot1));
		dots.add(v.findViewById(R.id.v_dot2));

		tv_title = (TextView) v.findViewById(R.id.tv_title);
		tv_title.setText(titles[0]);//

		viewPager = (ViewPager) v.findViewById(R.id.vp);
		viewPager.setAdapter(new MyAdapter());// 设置填充ViewPager页面的适配器
		// 设置一个监听器，当ViewPager中的页面改变时调用
		viewPager.setOnPageChangeListener(new MyPageChangeListener());

		scheduledExecutorService = Executors
				.newSingleThreadScheduledExecutor();
		// 当Activity显示出来后，每两秒钟切换一次图片显示
		scheduledExecutorService.scheduleAtFixedRate(new ScrollTask(), 1,
				2, TimeUnit.SECONDS);
		
		
	}
	
	
	/**
	 * 换行切换任务
	 * 
	 * @author Administrator
	 * 
	 */
	private class ScrollTask implements Runnable {

		public void run() {
			synchronized (viewPager) {
				System.out.println("currentItem: " + currentItem);
				currentItem = (currentItem + 1) % imageViews.size();
				handler.obtainMessage().sendToTarget(); // 通过Handler切换图片
			}
		}

	}

	/**
	 * 当ViewPager中页面的状态发生改变时调用
	 * 
	 * @author Administrator
	 * 
	 */
	private class MyPageChangeListener implements OnPageChangeListener {
		private int oldPosition = 0;

		/**
		 * This method will be invoked when a new page becomes selected.
		 * position: Position index of the new selected page.
		 */
		public void onPageSelected(int position) {
			currentItem = position;
			tv_title.setText(titles[position]);
			dots.get(oldPosition).setBackgroundResource(R.drawable.dot_normal);
			dots.get(position).setBackgroundResource(R.drawable.dot_focused);
			oldPosition = position;
		}

		public void onPageScrollStateChanged(int arg0) {

		}

		public void onPageScrolled(int arg0, float arg1, int arg2) {

		}
	}

	/**
	 * 填充ViewPager页面的适配器
	 * 
	 * @author Administrator
	 * 
	 */
	private class MyAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			return imageResId.length;
		}

		@Override
		public Object instantiateItem(View arg0, int arg1) {
			((ViewPager) arg0).addView(imageViews.get(arg1));
			return imageViews.get(arg1);
		}

		@Override
		public void destroyItem(View arg0, int arg1, Object arg2) {
			((ViewPager) arg0).removeView((View) arg2);
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public void restoreState(Parcelable arg0, ClassLoader arg1) {

		}

		@Override
		public Parcelable saveState() {
			return null;
		}

		@Override
		public void startUpdate(View arg0) {

		}

		@Override
		public void finishUpdate(View arg0) {

		}
	}
}
