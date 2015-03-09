package com.ranholmes.activity;

import java.util.HashMap;

import com.ranholmes.aemanager.R;
import com.ranholmes.iactivity.BMPCActivity;
import com.ranholmes.server.MainServer;
import com.ranholmes.util.Task;
import com.ranholmes.view.MyProgressDialog;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EnterIDActivity extends Activity implements BMPCActivity{
	private EditText mEditText;
	private Button mButton;
	private MyProgressDialog myDialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_enterid);
		initView();
	}

	@Override
	public void refresh(Object... params) {
				
		myDialog.dismiss();
		
				if(params[0].toString().equals("null")){
					Toast.makeText(getApplicationContext(), "该用户没有开通账户", 1).show();
					finish();
				}else{
					Intent intent = new Intent(EnterIDActivity.this,AccountListActivity.class);
					intent.putExtra("accountjson", params[0].toString());
					startActivity(intent);
					finish();
				}
		
	}

	@Override
	public void initView() {
		
		MainServer.addActivity(this);
		
		mEditText = (EditText)this.findViewById(R.id.activity_enterid_edittext);
		mButton = (Button)this.findViewById(R.id.activity_enterid_btn);
		mButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				try {
					String id = mEditText.getText()+"";
					int a_id = Integer.parseInt(id);
					HashMap<String, Object> mHashMap = new HashMap<String, Object>();
					mHashMap.put("account_o_id", a_id);
					Task t = new Task(Task.SEARCH_ACCOUNT_BY_ID, mHashMap);
					MainServer.newTask(t);
					myDialog = new MyProgressDialog(EnterIDActivity.this);
		    		myDialog.setCancelable(false);
		    		myDialog.show();
				} catch (Exception e) {
					Toast.makeText(getApplicationContext(), "请输入正确的格式", 1).show();
				}
				
			}
		});
	}
}
