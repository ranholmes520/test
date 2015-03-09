package com.ranholmes.activity;

import java.util.HashMap;

import org.json.JSONException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ranholmes.aemanager.R;
import com.ranholmes.http.Storage;
import com.ranholmes.iactivity.BMPCActivity;
import com.ranholmes.server.MainServer;
import com.ranholmes.util.InternetUtil;
import com.ranholmes.util.JsonFactory;
import com.ranholmes.util.Task;
import com.ranholmes.view.IndeterminateProgressBar;
import com.ranholmes.view.MyProgressDialog2;

/**
 * 登录Activity
 * 
 * @author Lan
 * 
 */
public class LoginActivity extends Activity implements BMPCActivity{
	private EditText accountIdEditTxt;
	private EditText accountPWDEditTxt;
	private Button loginBtn;
	private IndeterminateProgressBar loginPb;
	private MyProgressDialog2 mInternet;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		initView();
//		mInternet = new MyProgressDialog2(this);
//		mInternet.show();
//		check = true;
//		while(true){
//			if(!new InternetUtil().isNetworkConnected(this)&&!check){
////				mInternet = new MyProgressDialog2(this);
////				mInternet.setCancelable(false);
////				mInternet.show();
//			}else{
//				mInternet.dismiss();
//				check = false;
//			}
//		}
	}

	@Override
	public void refresh(Object... params) {
		// TODO Auto-generated method stub
		String result = params[0].toString();
		try {
			String status = JsonFactory.getJSONlistAccountLite(result).get(0).getStatus();
			if(status.equals("error")){
				Toast.makeText(getApplicationContext(), "出现错误", 1).show();
				loginPb.setVisibility(View.GONE);
				loginBtn.setClickable(true);
				loginBtn.setText("登录");
			}else if(status.equals("admin")){
				Toast.makeText(getApplicationContext(), "管理员请在PC端登录", 1).show();
				loginPb.setVisibility(View.GONE);
				loginBtn.setClickable(true);
				loginBtn.setText("登录");
			}else if(status.equals("success")){
				Storage.saveString(getApplicationContext(), "account_id", accountIdEditTxt.getText()+"");
				Storage.saveString(getApplicationContext(), "account_pwd", accountPWDEditTxt.getText()+"");
				Storage.saveString(getApplicationContext(), "account_balance", JsonFactory.getJSONlistAccountLite(result).get(0).getAccount_balance());
				Storage.saveString(getApplicationContext(), "account_name", JsonFactory.getJSONlistAccountLite(result).get(0).getAccount_name());
				Intent intent = new Intent(LoginActivity.this,LoginActivity2.class);
				startActivity(intent);
				finish();
			}else if(status.equals("The account is not exist,maybe it has been blocked!")){
				Toast.makeText(getApplicationContext(), "密码错误，或账户不存在", 1).show();
				loginPb.setVisibility(View.GONE);
				loginBtn.setClickable(true);
				loginBtn.setText("登录");
			}else{
				Toast.makeText(getApplicationContext(), "出现网络错误", 1).show();
				loginPb.setVisibility(View.GONE);
				loginBtn.setClickable(true);
				loginBtn.setText("登录");
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void initView() {
		// 开启服务
		Intent intent = new Intent(LoginActivity.this, MainServer.class);
		startService(intent);

		// TODO Auto-generated method stub
		accountIdEditTxt = (EditText) this.findViewById(R.id.login_account_id);
		accountPWDEditTxt = (EditText) this
				.findViewById(R.id.login_account_pwd);
		loginBtn = (Button) this.findViewById(R.id.login_login_btn);
		loginPb     = (IndeterminateProgressBar)this.findViewById(R.id.login_pb);
		
		loginPb.setVisibility(View.GONE);
		
		MainServer.addActivity(this);
		
		loginBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				if(new InternetUtil().isNetworkConnected(LoginActivity.this)){
					loginBtn.setClickable(false);
					loginBtn.setText("");
					int account_id;
					String account_pwd;
					try {
						account_id = Integer.parseInt(accountIdEditTxt.getText()+"");
						account_pwd= accountPWDEditTxt.getText()+"";
					} catch (Exception e) {
						Toast.makeText(getApplicationContext(), "输入格式有误", 1).show();
						loginBtn.setClickable(true);
						loginBtn.setText("登录");
						return;
					}
					HashMap<String,Object> mHashMap = new HashMap<String, Object>();
					mHashMap.put("account_id", account_id);
					mHashMap.put("account_pwd", account_pwd);
					Task t = new Task(Task.ACCOUNT_LOGIN, mHashMap);
					MainServer.newTask(t);
					
					loginPb.setVisibility(View.VISIBLE);
				}else{
					mInternet = new MyProgressDialog2(LoginActivity.this);
					mInternet.show();
				}
			}
		});
		
	}

}
