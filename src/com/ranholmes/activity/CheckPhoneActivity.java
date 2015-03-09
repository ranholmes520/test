package com.ranholmes.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import com.ranholmes.aemanager.R;
import com.ranholmes.iactivity.BMPCActivity;

public class CheckPhoneActivity extends Activity implements BMPCActivity{
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_checkphone);
		
		TelephonyManager phoneMgr=(TelephonyManager)this.getSystemService(Context.TELEPHONY_SERVICE);
		Toast.makeText(getApplicationContext(), "本机号码"+phoneMgr.getLine1Number(), 1).show();
	}
	
	@Override
	public void refresh(Object... params) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void initView() {
		// TODO Auto-generated method stub
		
	}

}
