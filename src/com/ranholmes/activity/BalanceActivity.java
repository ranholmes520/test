package com.ranholmes.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ranholmes.aemanager.R;
import com.ranholmes.http.Storage;
import com.ranholmes.iactivity.BMPCActivity;
import com.ranholmes.server.MainServer;
import com.ranholmes.util.Task;
import com.ranholmes.view.IndeterminateProgressBar;

public class BalanceActivity extends Activity implements BMPCActivity{
	
	private TextView mTxt;
	
	private Button mBack;
	
	private IndeterminateProgressBar mBar;
	
	private RelativeLayout mRefresh;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_balance);
		MainServer.addActivity(this);
		initView();
	}
	
	@Override
	public void refresh(Object... params) {
		mRefresh.setClickable(true);
		mBar.setVisibility(View.GONE);
		mTxt.setVisibility(View.VISIBLE);
		
		
		if(!params[0].toString().equals("error")){
			String result = params[0].toString();
			try {
				float money = Float.parseFloat(result);
				
				Storage.saveString(BalanceActivity.this, "account_balance", money+"");
				
				
				mTxt.setText(money+"元");
				
			} catch (Exception e) {
				// TODO: handle exception
				Toast.makeText(getApplicationContext(), "出现错误", 1).show();
				
			}
		}else{
			Toast.makeText(getApplicationContext(), "查询失败", 1).show();
		}
		
	}

	@Override
	public void initView() {
		mTxt = (TextView)this.findViewById(R.id.activity_balance_txt);
		
		mBar = (IndeterminateProgressBar)this.findViewById(R.id.login_pb);
		
		mBar.setVisibility(View.GONE);
		
		mRefresh = (RelativeLayout)this.findViewById(R.id.activity_balance_refresh);
		
		mBack = (Button)this.findViewById(R.id.back2);
		
		mBack.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
		mRefresh.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Task t = new Task(Task.GET_BALANCE, null);
				MainServer.newTask(t);
				mRefresh.setClickable(false);
				mBar.setVisibility(View.VISIBLE);
				mTxt.setVisibility(View.GONE);
			}
		});
		
		mTxt.setText(Storage.getString(getApplicationContext(), "account_balance")+"元");
	}
	
}
