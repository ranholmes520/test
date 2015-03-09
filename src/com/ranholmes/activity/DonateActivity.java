package com.ranholmes.activity;

import java.util.HashMap;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.ranholmes.aemanager.R;
import com.ranholmes.http.Storage;
import com.ranholmes.iactivity.BMPCActivity;
import com.ranholmes.server.MainServer;
import com.ranholmes.util.Task;
import com.ranholmes.view.MyProgressDialog;

public class DonateActivity extends Activity implements BMPCActivity{
	private RelativeLayout mLottery;
	private RelativeLayout mHong;
	private RelativeLayout mYi;
	private float account_balance;
	private MyProgressDialog myDialog;
	
	public static final int BUY_LOTTERY = 0;
	public static final int DONATE_HONG = 1;
	public static final int DONATE_YI = 2;
	
	private Button mBack;
	
	private int task = -1;
	private float donate_money_num;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_donate);
		initView();
		
	}
	
	@Override
	public void refresh(Object... params) {
		switch(task){
			case BUY_LOTTERY:
				myDialog.dismiss();
				if(!params[0].toString().equals("error")){
					account_balance = account_balance-2;
					Storage.saveString(getApplicationContext(), "account_balance", account_balance+"");
					Toast.makeText(getApplicationContext(), "购买成功,祝君好运！", 1).show();
					Intent intent = new Intent(DonateActivity.this,LotteryActivity.class);
					startActivity(intent);
				}else{
					Toast.makeText(getApplicationContext(), "购买失败！", 1).show();
				}
				break;
			case DONATE_HONG:
				myDialog.dismiss();
				if(!params[0].toString().equals("error")){
					account_balance = account_balance-donate_money_num;
					Storage.saveString(getApplicationContext(), "account_balance", account_balance+"");
					Toast.makeText(getApplicationContext(), "捐款成功！", 1).show();
				}else{
					Toast.makeText(getApplicationContext(), "捐款失败！", 1).show();
				}
				break;
			case DONATE_YI:
				myDialog.dismiss();
				if(!params[0].toString().equals("error")){
					account_balance = account_balance-1;
					Storage.saveString(getApplicationContext(), "account_balance", account_balance+"");
					Toast.makeText(getApplicationContext(), "捐款成功！", 1).show();
				}else{
					Toast.makeText(getApplicationContext(), "捐款失败！", 1).show();
				}
				break;
		}
	}

	@Override
	public void initView() {
		
		MainServer.addActivity(this);
		
		mLottery = (RelativeLayout)this.findViewById(R.id.activity_donate_welfare_lotteries);
		
		mHong = (RelativeLayout)this.findViewById(R.id.activity_donate_hong);
		
		mYi = (RelativeLayout)this.findViewById(R.id.activity_donate_welfare_yi);
		
		mBack = (Button)this.findViewById(R.id.back2);
		
		mBack.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		
		mLottery.setOnClickListener(new LotterListener());
		
		mHong.setOnClickListener(new HongListener());
		
		mYi.setOnClickListener(new YirListener());
	}
	
	class LotterListener implements View.OnClickListener{
		@Override
		public void onClick(View arg0) {
			account_balance = Float.parseFloat(Storage.getString(getApplicationContext(), "account_balance"));
			if(account_balance>=2){
				
				
				AlertDialog.Builder builder = new AlertDialog.Builder(DonateActivity.this);
				   LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				   LinearLayout layout = (LinearLayout)inflater.inflate(R.layout.dialog_view, null);
				   builder.setView(layout);
				   builder.setTitle("请再次输入密码");
				   final EditText et_search = (EditText)layout.findViewById(R.id.searchC);
				   builder.setPositiveButton("购买", new DialogInterface.OnClickListener() {
				    public void onClick(DialogInterface dialog, int which) {
				    	try {
				    		if((et_search.getText()+"").equals(Storage.getString(DonateActivity.this, "account_pwd"))){
					    		
					    		
					    		Task t = new Task(Task.LOTTERY_BUY, null);
					    		
					    		MainServer.newTask(t);
					    		
					    		myDialog = new MyProgressDialog(DonateActivity.this);
					    		myDialog.setCancelable(false);
					    		myDialog.show();
					    		task = BUY_LOTTERY;
					    	}
						} catch (Exception e) {
							// TODO: handle exception
							Toast.makeText(DonateActivity.this, "失败", 1).show();
						}
				    	
				    }
				   });
				  
				   builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
				    public void onClick(DialogInterface dialog, int which) {
				    
				    }
//				   
				   });
				   Dialog dialog = builder.create();
				   dialog.show();
				
				
			}else{
				Toast.makeText(getApplicationContext(), "您的余额不足", 1).show();
			}
		}
	}
	
	class HongListener implements View.OnClickListener{
		@Override
		public void onClick(View arg0) {
			account_balance = Float.parseFloat(Storage.getString(getApplicationContext(), "account_balance"));
				
				AlertDialog.Builder builder = new AlertDialog.Builder(DonateActivity.this);
				   LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				   LinearLayout layout = (LinearLayout)inflater.inflate(R.layout.dialog_view2, null);
				   builder.setView(layout);
				   builder.setTitle("请再次输入密码");
				   final EditText et_search = (EditText)layout.findViewById(R.id.searchC);
				   final EditText et_search2 = (EditText)layout.findViewById(R.id.searchD);
				   builder.setPositiveButton("捐款", new DialogInterface.OnClickListener() {
				    public void onClick(DialogInterface dialog, int which) {
				    	try {
				    		if((et_search2.getText()+"").equals(Storage.getString(DonateActivity.this, "account_pwd"))){
					    		
				    			String donate_money = et_search.getText()+"";
				    			
				    			float d_money = Float.parseFloat(donate_money);
				    			donate_money_num = d_money;
				    			if(account_balance<d_money){
				    				Toast.makeText(getApplicationContext(), "余额不足", 1).show();return;
				    			}
				    			
				    			HashMap<String, Object> mHashMap = new HashMap<String, Object>();
				    			mHashMap.put("transfer_money", d_money);
				    			
					    		Task t = new Task(Task.DONATE_HONG, mHashMap);
					    		
					    		MainServer.newTask(t);
					    		
					    		myDialog = new MyProgressDialog(DonateActivity.this);
					    		myDialog.setCancelable(false);
					    		myDialog.show();
					    		task = DONATE_HONG;
					    	}
						} catch (Exception e) {
							// TODO: handle exception
							Toast.makeText(DonateActivity.this, "金额格式错误", 1).show();
						}
				    	
				    }
				   });
				  
				   builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
				    public void onClick(DialogInterface dialog, int which) {
				    
				    }
//				   
				   });
				   Dialog dialog = builder.create();
				   dialog.show();
				
		}
	}
	
	
	class YirListener implements View.OnClickListener{
		@Override
		public void onClick(View arg0) {
			account_balance = Float.parseFloat(Storage.getString(getApplicationContext(), "account_balance"));
			if(account_balance>=1){
				
				
				AlertDialog.Builder builder = new AlertDialog.Builder(DonateActivity.this);
				   LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				   LinearLayout layout = (LinearLayout)inflater.inflate(R.layout.dialog_view, null);
				   builder.setView(layout);
				   builder.setTitle("请再次输入密码");
				   final EditText et_search = (EditText)layout.findViewById(R.id.searchC);
				   builder.setPositiveButton("捐款1元", new DialogInterface.OnClickListener() {
				    public void onClick(DialogInterface dialog, int which) {
				    	try {
				    		if((et_search.getText()+"").equals(Storage.getString(DonateActivity.this, "account_pwd"))){
					    		
					    		
					    		Task t = new Task(Task.DONATE_YI, null);
					    		
					    		MainServer.newTask(t);
					    		
					    		myDialog = new MyProgressDialog(DonateActivity.this);
					    		myDialog.setCancelable(false);
					    		myDialog.show();
					    		task = DONATE_YI;
					    	}
						} catch (Exception e) {
							// TODO: handle exception
							Toast.makeText(DonateActivity.this, "失败", 1).show();
						}
				    	
				    }
				   });
				  
				   builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
				    public void onClick(DialogInterface dialog, int which) {
				    
				    }
//				   
				   });
				   Dialog dialog = builder.create();
				   dialog.show();
				
				
			}else{
				Toast.makeText(getApplicationContext(), "您的余额不足", 1).show();
			}
		}
	}
}
