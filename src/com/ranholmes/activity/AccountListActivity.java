package com.ranholmes.activity;

import java.util.ArrayList;

import org.json.JSONException;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ranholmes.adapter.AccountListAdapter;
import com.ranholmes.aemanager.R;
import com.ranholmes.dto.AccountSearch;
import com.ranholmes.http.Storage;
import com.ranholmes.iactivity.BMPCActivity;
import com.ranholmes.server.MainServer;
import com.ranholmes.util.JsonFactory;
import com.ranholmes.util.Task;
import com.ranholmes.view.EditTextId;
import com.ranholmes.view.MyListViewPullDownAndUp;
import com.ranholmes.view.MyProgressDialog;

public class AccountListActivity extends Activity implements BMPCActivity{
	private Button mBack;
	private MyListViewPullDownAndUp mListView;
	private ArrayList<AccountSearch> list = new ArrayList<AccountSearch>();
	private AccountListAdapter mAccountListAdapter;
	public EditTextId mEditTextId;
	private MyProgressDialog myDialog;
	private TextView mBalance;
	
	public static final int TA1 = 0;
	public static final int TA2 = 1;
	
	private int task = 0;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_accountlist);
		
		MainServer.addActivity(this);
		
		
		mListView = (MyListViewPullDownAndUp)this.findViewById(R.id.activity_accountlist_listview);
		mEditTextId = (EditTextId)this.findViewById(R.id.activity_accountlist_edit);
		mBalance = (TextView)this.findViewById(R.id.activity_accountlist_balance);
		
		
		mBack = (Button)this.findViewById(R.id.back2);
		
		mBack.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		
		
		Task t = new Task(Task.GET_BALANCE_TRANSFER, null);
		MainServer.newTask(t);
		
		myDialog = new MyProgressDialog(this);
		myDialog.setCancelable(false);
		myDialog.show();
	}
	@Override
	public void refresh(Object... params) {
		
		switch(task){
		case TA2:
			
			if(!params[0].toString().equals("error")){
				Toast.makeText(getApplicationContext(), "转账成功！", 1).show();
				finish();
			}
			
			break;
		case TA1:
			
			myDialog.dismiss();
			
			if(!params[0].toString().equals("error")){
				String result = params[0].toString();
				try {
					float money = Float.parseFloat(result);
					
					Storage.saveString(AccountListActivity.this, "account_balance", money+"");
					
					
					mBalance.setText("您的余额为:"+money+"元");
					
				} catch (Exception e) {
					// TODO: handle exception
					Toast.makeText(getApplicationContext(), "出现错误", 1).show();
					finish();
				}
			}else{
				Toast.makeText(getApplicationContext(), "出现错误", 1).show();
				finish();
			}
			
			
			try {
				list = JsonFactory.getJSONlistAccountSearch(getIntent().getStringExtra("accountjson"));
				mAccountListAdapter = new AccountListAdapter(AccountListActivity.this, list, mListView,mEditTextId);
				mListView.setAdapter(mAccountListAdapter);
				
				task = TA2;
				
			} catch (JSONException e) {
				Toast.makeText(getApplicationContext(), "内部错误", 1).show();
				e.printStackTrace();
				finish();
			}
			
			break;
		}
		
	}
	@Override
	public void initView() {
		
	}
	
	
}
