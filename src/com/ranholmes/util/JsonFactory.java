package com.ranholmes.util;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.ranholmes.dto.AccountLite;
import com.ranholmes.dto.AccountSearch;
import com.ranholmes.dto.Gender;
import com.ranholmes.dto.RecordDetail;
import com.ranholmes.http.Storage;

public class JsonFactory {
	public static ArrayList<RecordDetail> getJSONlistRecordDetailInfo(String json) throws JSONException {
		if (json == null) {
			return null;
		}
		JSONObject root = new JSONObject(json.toString());
		JSONArray items = root.getJSONArray("recorddetail");
		ArrayList<RecordDetail> mRecordDetails = new ArrayList<RecordDetail>();
		for (int i = 0; i < items.length(); i++) {
			JSONObject item = items.getJSONObject(i);
			
			
			String record_time = item.getString("record_time");
			float record_income = Float.valueOf(item.get("record_income").toString());
			float record_outcome= Float.valueOf(item.get("record_outcome").toString());
			float record_balance= Float.valueOf(item.get("record_balance").toString());
			String record_summary= item.getString("record_summary");
			int record_o_account_id = item.getInt("record_o_account_id");
			
			mRecordDetails.add(new RecordDetail(record_time, record_income, record_outcome, record_balance, record_summary, record_o_account_id));
			
		}

		return mRecordDetails;
	}
	
	public static ArrayList<AccountSearch> getJSONlistAccountSearch(String json) throws JSONException {
		if (json == null) {
			return null;
		}
		JSONObject root = new JSONObject(json.toString());
		JSONArray items = root.getJSONArray("accountsearchs");
		ArrayList<AccountSearch> mAccountSearchs = new ArrayList<AccountSearch>();
		for (int i = 0; i < items.length(); i++) {
			JSONObject item = items.getJSONObject(i);
			
			
			String account_name= item.getString("account_name");
			int account_id = item.getInt("account_id");
			String account_gender = item.getString("account_gender");
			
			mAccountSearchs.add(new AccountSearch(account_id, account_name, account_gender));
			
		}

		return mAccountSearchs;
	}
	
	public static ArrayList<AccountLite> getJSONlistAccountLite(String json) throws JSONException {
		if (json == null) {
			return null;
		}
		JSONObject root = new JSONObject(json.toString());
		JSONArray items = root.getJSONArray("detail");
		ArrayList<AccountLite> mAccountLites = new ArrayList<AccountLite>();
		for (int i = 0; i < items.length(); i++) {
			JSONObject item = items.getJSONObject(i);
			
			
			String account_create_time= item.getString("account_create_time");
			String account_name= item.getString("account_name");
			String status = item.getString("status");
			String account_mail = item.getString("account_mail");
			String account_balance = item.getString("account_balance");
			
			mAccountLites.add(new AccountLite(account_name, null, status, account_create_time, account_mail, account_balance));
			
		}

		return mAccountLites;
	}
		
}
