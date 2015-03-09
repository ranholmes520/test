package com.ranholmes.activity;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONException;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.ranholmes.adapter.EpisodeListAdapter;
import com.ranholmes.aemanager.R;
import com.ranholmes.dto.RecordDetail;
import com.ranholmes.iactivity.BMPCActivity;
import com.ranholmes.server.MainServer;
import com.ranholmes.util.JsonFactory;
import com.ranholmes.util.Task;
import com.ranholmes.view.MyListViewPullDownAndUp;
import com.ranholmes.view.MyListViewPullDownAndUp.RefreshListener;
import com.ranholmes.view.MyProgressDialog;

public class RecordActivity extends Activity implements BMPCActivity{
	private MyListViewPullDownAndUp mListView;
	private ArrayList<RecordDetail> list = new ArrayList<RecordDetail>();
	private EpisodeListAdapter mEpisodeListAdapter;
	private static final int TASK_1 =0;
	private static final int TASK_2 = 1;
	private static final int TASK_3 = 2;
	private int task = 0;
	private MyProgressDialog myDialog;
	private Button back;
	private Handler handler=new Handler();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_record);
		initView();
	}

	@Override
	public void refresh(Object... params) {
		// TODO Auto-generated method stub
		switch(task){
		case TASK_1:
			try {
				list = JsonFactory.getJSONlistRecordDetailInfo(params[0].toString());
				mEpisodeListAdapter = new EpisodeListAdapter(getApplicationContext(), list, mListView);
				mListView.setAdapter(mEpisodeListAdapter);
				mListView.setRefreshListener(new MyRefreshListener());
				myDialog.dismiss();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				myDialog.dismiss();
				finish();
			}
			
			break;
		case TASK_2:
			try {
				if(params[0].toString().equals("empty")||params[0].toString().equals("error")){
					mListView.onPulldownRefreshComplete();
					return;
				}
				list = montageArrayList(JsonFactory.getJSONlistRecordDetailInfo(params[0].toString()),list);
				mEpisodeListAdapter.refreshData(list);
				mListView.onPulldownRefreshComplete();
			} catch (JSONException e) {
				e.printStackTrace();
			}
			break;
		case TASK_3:
			try {
				if(params[0].toString().equals("empty")||params[0].toString().equals("error")){
					mListView.onPullupRefreshComplete();
					return;
				}
				list = montageArrayList(list,JsonFactory.getJSONlistRecordDetailInfo(params[0].toString()));
				mEpisodeListAdapter.refreshData(list);
				mListView.onPullupRefreshComplete();
			} catch (JSONException e) {
				e.printStackTrace();
			}
			break;
		}
	}

	@Override
	public void initView() {
		myDialog = new MyProgressDialog(RecordActivity.this);
		myDialog.setCancelable(false);
		myDialog.show();
		mListView = (MyListViewPullDownAndUp)this.findViewById(R.id.activity_record_listview);
		back = (Button)this.findViewById(R.id.back2);
		back.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		MainServer.addActivity(this);
		Task t = new Task(Task.GET_RECORD_FIRST, null);
		MainServer.newTask(t);
		task = TASK_1;
	}
	
	class MyRefreshListener implements RefreshListener {
		// 处理下拉刷新
		@Override
		public void pullDownRefresh() {
			new Thread(new Runnable() {
				@Override
				public void run() {
					 SystemClock.sleep(500);
                     handler.post(new Runnable() {
					 
						@Override
						public void run() {
							HashMap hashMap2 = new HashMap<String, String>();
							String top_time = "";
							top_time = list.get(0).getRecord_time();
								hashMap2.put("top_time", top_time);
								Task t = new Task(Task.REFRESH_RECORD, hashMap2);
								MainServer.newTask(t);
								task = TASK_2;
							
						}
					});
				}
			}).start();
		}

		// 处理上拉刷新
		@Override
		public void pullUpRefresh() {
			new Thread(new Runnable() {
				@Override
				public void run() {
					SystemClock.sleep(500);
                    handler.post(new Runnable() {
						@Override
						public void run() {
							HashMap hashMap2 = new HashMap<String, String>();
							String buttom_time = list.get(list.size()-1).getRecord_time();
							hashMap2.put("buttom_time",buttom_time);
							Task t = new Task(Task.LOADMORE_RECORD, hashMap2);
							MainServer.newTask(t);
							
							task = TASK_3;
						}
					});
				}
			}).start();
		}
	}
	/**
	 * 拼接两个Arraylist
	 * @param list1
	 * @param list2
	 * @return
	 */
	public ArrayList<RecordDetail> montageArrayList(ArrayList<RecordDetail> list1,
			ArrayList<RecordDetail> list2) {
		ArrayList<RecordDetail> list = new ArrayList<RecordDetail>();
		for (int i = 0; i < list1.size(); i++) {
			list.add(list1.get(i));

		}
		for (int i = 0; i < list2.size(); i++) {
			list.add(list2.get(i));
		}
		return list;

	}
}
