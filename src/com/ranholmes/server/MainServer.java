package com.ranholmes.server;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;

import com.ranholmes.http.HttpDownload;
import com.ranholmes.http.Storage;
import com.ranholmes.iactivity.BMPCActivity;
import com.ranholmes.util.Task;

public class MainServer extends Service implements Runnable {
	// 任务队列
	private static Queue<Task> tasks = new LinkedList<Task>();
	private static ArrayList<BMPCActivity> appActivities = new ArrayList<BMPCActivity>();
	
	// 是否运行线程
	private static boolean isRun;

	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			BMPCActivity activity;
			switch (msg.what) {
			case Task.ACCOUNT_LOGIN:// 用户登录
				
				//更新UI
				 activity = (BMPCActivity) getActivityByName("LoginActivity");
				 activity.refresh(msg.obj);
				break;
			case Task.GET_RECORD_FIRST:
				//更新UI
				 activity = (BMPCActivity) getActivityByName("RecordActivity");
				 activity.refresh(msg.obj);
				break;
			case Task.REFRESH_RECORD:
				//更新UI
				 activity = (BMPCActivity) getActivityByName("RecordActivity");
				 activity.refresh(msg.obj);
				break;
			case Task.LOADMORE_RECORD:
				//更新UI
				 activity = (BMPCActivity) getActivityByName("RecordActivity");
				 activity.refresh(msg.obj);
				break;
			case Task.SEARCH_ACCOUNT_BY_PHONE:
				//更新UI
				 activity = (BMPCActivity) getActivityByName("TelephoneActivity");
				 activity.refresh(msg.obj);
				break;
			case Task.SEARCH_ACCOUNT_BY_ID:
				//更新UI
				 activity = (BMPCActivity) getActivityByName("EnterIDActivity");
				 activity.refresh(msg.obj);
				break;
			case Task.SEARCH_ACCOUNT_BY_ID2:
				//更新UI
				 activity = (BMPCActivity) getActivityByName("CaptureActivity");
				 activity.refresh(msg.obj);
				break;
			case Task.TRANS_MONEY:
				//更新UI
				 activity = (BMPCActivity) getActivityByName("AccountListActivity");
				 activity.refresh(msg.obj);
				break;
			case Task.LOTTERY_BUY:
				//更新UI
				 activity = (BMPCActivity) getActivityByName("DonateActivity");
				 activity.refresh(msg.obj);
				break;
			case Task.LOTTERY_WARD:
				//更新UI
				 activity = (BMPCActivity) getActivityByName("LotteryActivity");
				 activity.refresh(msg.obj);
				break;
			case Task.DONATE_HONG:
				//更新UI
				 activity = (BMPCActivity) getActivityByName("DonateActivity");
				 activity.refresh(msg.obj);
				break;
			case Task.DONATE_YI:
				//更新UI
				 activity = (BMPCActivity) getActivityByName("DonateActivity");
				 activity.refresh(msg.obj);
				break;
			case Task.GET_BALANCE:
				//更新UI
				 activity = (BMPCActivity) getActivityByName("BalanceActivity");
				 activity.refresh(msg.obj);
				break;
			case Task.GET_BALANCE_TRANSFER:
				//更新UI
				 activity = (BMPCActivity) getActivityByName("AccountListActivity");
				 activity.refresh(msg.obj);
				break;
			default:
				break;
			}
		};
	};
	
	/**
	 * 添加一个Activity对象
	 * 
	 * @param activity
	 */
	public static void addActivity(BMPCActivity activity) {

		appActivities.add(activity);
	}

	private BMPCActivity getActivityByName(String name) {
		ArrayList<BMPCActivity> list = new ArrayList<BMPCActivity>();
		if (!appActivities.isEmpty()) {
			for (BMPCActivity activity : appActivities) {
				if (null != activity) {
					if (activity.getClass().getName().indexOf(name) > 0) {
						list.add(activity);
					}
				}
			}
		}

		if (list.size() == 0) {
			return null;
		}
		return list.get(list.size() - 1);
	}

	/**
	 * 添加任务到任务队列中
	 * 
	 * @param t
	 */
	public static void newTask(Task t) {
		System.out.println("能否执行" + isRun);
		tasks.add(t);
	}
	@Override
	public void onCreate()
	{
		isRun = true;
		System.out.println("onCreate");
		Thread thread = new Thread(this);
		thread.start();
		
		super.onCreate();
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (isRun) {
			Task task = null;
			if (!tasks.isEmpty()) {
				task = tasks.poll();// 执行完任务后把改任务从任务队列中移除
				if (null != task) {
					doTask(task);
				}
			}
			try {
				Thread.sleep(1000);
			} catch (Exception e) {
			}

		}
	}

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	//
	// 处理任务
	private void doTask(Task t) {
		Message msg = handler.obtainMessage();
		msg.what = t.getTaskId();
		String url;
		List<NameValuePair> params;
		String result;
		switch (t.getTaskId()) {
		case Task.ACCOUNT_LOGIN:
			System.out.println("正在登录");
			url = "/client/BMSAction!login.action"; 
			params = new ArrayList<NameValuePair>();
			// 表单参数
			params.add(new BasicNameValuePair("account_id", t.getTaskParams().get("account_id")+""));
			params.add(new BasicNameValuePair("account_pwd", t.getTaskParams().get("account_pwd")+""));
			result = HttpDownload.sendPostHttpRequest(url, params);
			msg.obj= result;
			break;
		case Task.GET_RECORD_FIRST:
			System.out.println("正在登录");
			url = "/client/BMSAction!getRecordsFirst.action"; 
			params = new ArrayList<NameValuePair>();
			// 表单参数
			params.add(new BasicNameValuePair("account_id", Storage.getString(getApplicationContext(), "account_id")));
			result = HttpDownload.sendPostHttpRequest(url, params);
			msg.obj= result;
			break;
		case Task.REFRESH_RECORD:
			url = "/client/BMSAction!refreshRecords.action"; 
			params = new ArrayList<NameValuePair>();
			// 表单参数
			params.add(new BasicNameValuePair("account_id", Storage.getString(getApplicationContext(), "account_id")));
			params.add(new BasicNameValuePair("top_time", t.getTaskParams().get("top_time")+""));
			result = HttpDownload.sendPostHttpRequest(url, params);
			msg.obj= result;
			break;
		case Task.LOADMORE_RECORD:
			url = "/client/BMSAction!loadMoreRecord.action"; 
			params = new ArrayList<NameValuePair>();
			// 表单参数
			params.add(new BasicNameValuePair("account_id", Storage.getString(getApplicationContext(), "account_id")));
			params.add(new BasicNameValuePair("buttom_time", t.getTaskParams().get("buttom_time")+""));
			result = HttpDownload.sendPostHttpRequest(url, params);
			msg.obj= result;
			break;
		case Task.SEARCH_ACCOUNT_BY_PHONE:
			url = "/client/BMSAction!searchAccountByPhone.action"; 
			params = new ArrayList<NameValuePair>();
			// 表单参数
			params.add(new BasicNameValuePair("account_id", Storage.getString(getApplicationContext(), "account_id")));
			params.add(new BasicNameValuePair("account_tel", t.getTaskParams().get("account_tel")+""));
			result = HttpDownload.sendPostHttpRequest(url, params);
			msg.obj= result;
			break;
		case Task.SEARCH_ACCOUNT_BY_ID:
			url = "/client/BMSAction!searchAccountById.action"; 
			params = new ArrayList<NameValuePair>();
			// 表单参数
			params.add(new BasicNameValuePair("account_id", Storage.getString(getApplicationContext(), "account_id")));
			params.add(new BasicNameValuePair("account_o_id", t.getTaskParams().get("account_o_id")+""));
			result = HttpDownload.sendPostHttpRequest(url, params);
			msg.obj= result;
			break;
		case Task.SEARCH_ACCOUNT_BY_ID2:
			url = "/client/BMSAction!searchAccountById.action"; 
			params = new ArrayList<NameValuePair>();
			// 表单参数
			params.add(new BasicNameValuePair("account_id", Storage.getString(getApplicationContext(), "account_id")));
			params.add(new BasicNameValuePair("account_o_id", t.getTaskParams().get("account_o_id")+""));
			result = HttpDownload.sendPostHttpRequest(url, params);
			msg.obj= result;
			break;
		case Task.TRANS_MONEY:
			url = "/client/BMSAction!transferAccounts.action"; 
			params = new ArrayList<NameValuePair>();
			// 表单参数
			params.add(new BasicNameValuePair("account_id", Storage.getString(getApplicationContext(), "account_id")));
			params.add(new BasicNameValuePair("account_o_id", t.getTaskParams().get("account_o_id")+""));
			params.add(new BasicNameValuePair("transfer_money", t.getTaskParams().get("transfer_money")+""));
			result = HttpDownload.sendPostHttpRequest(url, params);
			if(!(result+"").equals("error")){
				float f = Float.parseFloat(t.getTaskParams().get("transfer_money")+"");
				float balance = Float.parseFloat(Storage.getString(getApplicationContext(), "account_balance"));
				
				Storage.saveString(getApplicationContext(), "account_balance", (balance-f)+"");
			}
			msg.obj= result;
			break;
		case Task.LOTTERY_BUY:
			url = "/client/BMSAction!transferAccounts.action"; 
			params = new ArrayList<NameValuePair>();
			// 表单参数
			params.add(new BasicNameValuePair("account_id", Storage.getString(getApplicationContext(), "account_id")));
			params.add(new BasicNameValuePair("account_o_id", "10000009"));
			params.add(new BasicNameValuePair("transfer_money", 2+""));
			result = HttpDownload.sendPostHttpRequest(url, params);
			msg.obj= result;
			break;
		case Task.LOTTERY_WARD:
			url = "/client/BMSAction!transferAccounts.action"; 
			params = new ArrayList<NameValuePair>();
			// 表单参数
			params.add(new BasicNameValuePair("account_id", "10000009"));
			params.add(new BasicNameValuePair("account_o_id", Storage.getString(getApplicationContext(), "account_id")));
			params.add(new BasicNameValuePair("transfer_money", t.getTaskParams().get("transfer_money")+""));
			result = HttpDownload.sendPostHttpRequest(url, params);
			msg.obj= result;
			break;
		case Task.DONATE_HONG:
			url = "/client/BMSAction!transferAccounts.action"; 
			params = new ArrayList<NameValuePair>();
			// 表单参数
			params.add(new BasicNameValuePair("account_id", Storage.getString(getApplicationContext(), "account_id")));
			params.add(new BasicNameValuePair("account_o_id", "10000010"));
			params.add(new BasicNameValuePair("transfer_money", t.getTaskParams().get("transfer_money")+""));
			result = HttpDownload.sendPostHttpRequest(url, params);
			msg.obj= result;
			break;
		case Task.DONATE_YI:
			url = "/client/BMSAction!transferAccounts.action"; 
			params = new ArrayList<NameValuePair>();
			// 表单参数
			params.add(new BasicNameValuePair("account_id", Storage.getString(getApplicationContext(), "account_id")));
			params.add(new BasicNameValuePair("account_o_id", "10000011"));
			params.add(new BasicNameValuePair("transfer_money", 1+""));
			result = HttpDownload.sendPostHttpRequest(url, params);
			msg.obj= result;
			break;
		case Task.GET_BALANCE:
			url = "/client/BMSAction!getBalance.action"; 
			params = new ArrayList<NameValuePair>();
			// 表单参数
			params.add(new BasicNameValuePair("account_id", Storage.getString(getApplicationContext(), "account_id")));
			result = HttpDownload.sendPostHttpRequest(url, params);
			msg.obj= result;
			break;
		case Task.GET_BALANCE_TRANSFER:
			url = "/client/BMSAction!getBalance.action"; 
			params = new ArrayList<NameValuePair>();
			// 表单参数
			params.add(new BasicNameValuePair("account_id", Storage.getString(getApplicationContext(), "account_id")));
			result = HttpDownload.sendPostHttpRequest(url, params);
			msg.obj= result;
			break;
		default:
			break;
		}

		handler.sendMessage(msg);

	}
}
