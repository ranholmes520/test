package com.ranholmes.adapter;

import java.util.HashMap;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ranholmes.activity.RecordActivity;
import com.ranholmes.aemanager.R;
import com.ranholmes.dto.AccountSearch;
import com.ranholmes.http.Storage;
import com.ranholmes.server.MainServer;
import com.ranholmes.util.Task;
import com.ranholmes.view.EditTextId;
import com.ranholmes.view.MyListViewPullDownAndUp;
import com.ranholmes.view.MyProgressDialog;
import com.ranholmes.view.RoundImageView;

@SuppressLint("NewApi")
public class AccountListAdapter extends BaseAdapter {
	private Context context;
	public List<AccountSearch> listItems;
	private LayoutInflater listContainer;
	private MyListViewPullDownAndUp listView;
	private EditTextId mEditTextId;
	
	private MyProgressDialog myDialog;
	
	private String items[] = { "复制段子", "分享到新浪微博", "分享到QQ", "收藏", "举报" };

	static class ListItemView {
		public RoundImageView typeImage;
		public TextView name;
		public TextView id;
		public LinearLayout mLinearLayout;
	}

	public AccountListAdapter(Context context,
			List<AccountSearch> listItems,
			MyListViewPullDownAndUp mbListView,EditTextId mEditTextId) {
		this.context = context;
		this.listContainer = LayoutInflater.from(context);
		this.listItems = listItems;
		this.listView = mbListView;
		this.mEditTextId = mEditTextId;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return listItems.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return listItems.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		// 图片
		Drawable cachedImage = null;
		ListItemView listItemView = null;
		if (convertView == null) {
			convertView = listContainer.inflate(R.layout.item_account, null);
			listItemView = new ListItemView();

			listItemView.typeImage = (RoundImageView) convertView
					.findViewById(R.id.item_account_gender_photo);

			listItemView.name = (TextView) convertView
					.findViewById(R.id.item_account_name);
			
			listItemView.id = (TextView) convertView
					.findViewById(R.id.item_account_id);
			
			listItemView.mLinearLayout = (LinearLayout) convertView
					.findViewById(R.id.item_account_layout);

			convertView.setTag(listItemView);
		} else {
			listItemView = (ListItemView) convertView.getTag();
		}
		final AccountSearch mb = listItems.get(position);
		listItemView.name.setText("用户名:"+mb.getAccount_name());
		listItemView.id.setText("\t账号:"+mb.getAccount_id());
		if(mb.getAccount_gender().equals("MALE")){
			listItemView.typeImage.setImageResource(R.drawable.image_male);
		}else if(mb.getAccount_gender().equals("FEMALE")){
			listItemView.typeImage.setImageResource(R.drawable.image_female);
		}
		
		listItemView.mLinearLayout.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
//				Toast.makeText(context, mEditTextId.getText()+"", 1).show();
				AlertDialog.Builder builder = new AlertDialog.Builder(context);
				   LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				   LinearLayout layout = (LinearLayout)inflater.inflate(R.layout.dialog_view, null);
				   builder.setView(layout);
				   builder.setTitle("请再次输入密码");
				   final EditText et_search = (EditText)layout.findViewById(R.id.searchC);
				   builder.setPositiveButton("转账", new DialogInterface.OnClickListener() {
				    public void onClick(DialogInterface dialog, int which) {
				    	try {
				    		int money = Integer.parseInt(mEditTextId.getText()+"");
				    		if((et_search.getText()+"").equals(Storage.getString(context, "account_pwd"))){
					    		
					    		HashMap<String,Object> mHashMap = new HashMap<String, Object>();
					    		
					    		mHashMap.put("account_o_id", mb.getAccount_id());
					    		mHashMap.put("transfer_money", mEditTextId.getText());
					    		
					    		Task t = new Task(Task.TRANS_MONEY, mHashMap);
					    		
					    		MainServer.newTask(t);
					    		
					    		myDialog = new MyProgressDialog(context);
					    		myDialog.setCancelable(false);
					    		myDialog.show();
					    	}
						} catch (Exception e) {
							// TODO: handle exception
							Toast.makeText(context, "确保转账金额为整数", 1).show();
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
		});
		
		return convertView;
	}

	public void refreshData(List<AccountSearch> listItems) {
		this.listItems = listItems;
		notifyDataSetChanged();
	}
	

}
