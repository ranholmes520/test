package com.ranholmes.util;

import java.util.Map;

public class Task
{

	//任务ID
	private int taskId;
	
	//参数
	private Map<String, Object> taskParams;
	
	private int id;
	
	//用户登录
	public static final int ACCOUNT_LOGIN=1;
	//用户注册
	public static final int USER_REGISTER = 2;
	
	//第一次刷新纪录
	public static final int GET_RECORD_FIRST = 100;
	
	//第一次刷新纪录
		public static final int REFRESH_RECORD = 101;
		
		//第一次刷新纪录
		public static final int LOADMORE_RECORD = 102;
		
		public static final int SEARCH_ACCOUNT_BY_PHONE = 200;
		
		public static final int SEARCH_ACCOUNT_BY_ID = 201;
		public static final int SEARCH_ACCOUNT_BY_ID2 = 202;
		
		public static final int TRANS_MONEY = 300;
		public static final int GET_BALANCE_TRANSFER = 301;
		
		public static final int LOTTERY_BUY = 501;
		public static final int LOTTERY_WARD = 502;
		
		public static final int DONATE_HONG = 503;
		public static final int DONATE_YI = 504;
		
		public static final int GET_BALANCE = 600;
		
	
	public Task(int taskId, Map<String, Object> taskParams)
	{
		this.taskId = taskId;
		this.taskParams = taskParams;
	}



	public int getTaskId()
	{
		return taskId;
	}



	public void setTaskId(int taskId)
	{
		this.taskId = taskId;
	}



	public Map<String, Object> getTaskParams()
	{
		return taskParams;
	}



	public void setTaskParams(Map<String, Object> taskParams)
	{
		this.taskParams = taskParams;
	}



	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}
	
	
}
