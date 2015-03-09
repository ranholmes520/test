package com.ranholmes.dto;


public class RecordDetail {
	
	private String record_time;
	private float record_income;
	private float record_outcome;
	private float record_balance;
	private String record_summary;
	private int record_o_account_id;
	
	public RecordDetail(String record_time, float record_income,
			float record_outcome, float record_balance, String record_summary,
			int record_o_account_id) {
		super();
		this.record_time = record_time;
		this.record_income = record_income;
		this.record_outcome = record_outcome;
		this.record_balance = record_balance;
		this.record_summary = record_summary;
		this.record_o_account_id = record_o_account_id;
	}
	
	
	public String getRecord_time() {
		return record_time;
	}
	public void setRecord_time(String record_time) {
		this.record_time = record_time;
	}
	public float getRecord_income() {
		return record_income;
	}
	public void setRecord_income(float record_income) {
		this.record_income = record_income;
	}
	public float getRecord_outcome() {
		return record_outcome;
	}
	public void setRecord_outcome(float record_outcome) {
		this.record_outcome = record_outcome;
	}
	public float getRecord_balance() {
		return record_balance;
	}
	public void setRecord_balance(float record_balance) {
		this.record_balance = record_balance;
	}
	public String getRecord_summary() {
		return record_summary;
	}
	public void setRecord_summary(String record_summary) {
		this.record_summary = record_summary;
	}
	public int getRecord_o_account_id() {
		return record_o_account_id;
	}
	public void setRecord_o_account_id(int record_o_account_id) {
		this.record_o_account_id = record_o_account_id;
	}
	
	
}
