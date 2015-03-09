package com.ranholmes.activity;

import java.util.HashMap;
import java.util.Random;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ranholmes.aemanager.R;
import com.ranholmes.http.Storage;
import com.ranholmes.iactivity.BMPCActivity;
import com.ranholmes.server.MainServer;
import com.ranholmes.util.Task;
import com.ranholmes.view.MyProgressDialog;

public class LotteryActivity extends Activity implements BMPCActivity {
	private Button mSubmie;
	private TextView mTxt;
	private int ward;
	private MyProgressDialog myDialog;
	private Button mBack;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lottery);
		initView();
	}

	@Override
	public void refresh(Object... params) {
		// TODO Auto-generated method stub
		myDialog.dismiss();
		if(!params[0].toString().equals("error")){
			float account_balance = Float.parseFloat(Storage.getString(getApplicationContext(), "account_balance"));
			account_balance = account_balance+ward*4;
			Storage.saveString(getApplicationContext(), "account_balance", account_balance+"");
			Toast.makeText(getApplicationContext(), "兑奖成功！", 1).show();
			finish();
		}else{
			Toast.makeText(getApplicationContext(), "兑奖失败！请重试", 1).show();
		}
	}

	@Override
	public void initView() {
		MainServer.addActivity(this);
		// TODO Auto-generated method stub
		mSubmie = (Button)this.findViewById(R.id.activity_lottery_submit);
		mTxt = (TextView)this.findViewById(R.id.activity_lotter_txt);
		mBack = (Button)this.findViewById(R.id.back2);
		
		mBack.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		ward = num(new Random());
		if(ward>=10&&ward<=30){
			
			mTxt.setText(ward*4+"元");
			
			mSubmie.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					HashMap<String,Object> mHashMap = new HashMap<String, Object>();
					mHashMap.put("transfer_money", ward*4+"");
					Task t = new Task(Task.LOTTERY_WARD, mHashMap);
					MainServer.newTask(t);
					myDialog = new MyProgressDialog(LotteryActivity.this);
		    		myDialog.setCancelable(false);
		    		myDialog.show();
				}
			});
		}else{
			mSubmie.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					Toast.makeText(getApplicationContext(), "很遗憾，您没中奖", 1).show();
					
				}
			});
		}
	}

	public int num(Random r) {
		int chance = r.nextInt(10);
		if (chance < 6)
			return r.nextInt(10) + 1;
		else if (chance < 9)
			return r.nextInt(10) + 11;
		else
			return r.nextInt(10) + 21;
	}
}
