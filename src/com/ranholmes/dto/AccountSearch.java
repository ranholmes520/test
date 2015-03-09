package com.ranholmes.dto;

public class AccountSearch {
	private int account_id;
	private String account_name;
	private String account_gender;
	
	
	
	public AccountSearch(int account_id, String account_name,
			String account_gender) {
		super();
		this.account_id = account_id;
		this.account_name = account_name;
		this.account_gender = account_gender;
	}
	public int getAccount_id() {
		return account_id;
	}
	public void setAccount_id(int account_id) {
		this.account_id = account_id;
	}
	public String getAccount_name() {
		return account_name;
	}
	public void setAccount_name(String account_name) {
		this.account_name = account_name;
	}
	public String getAccount_gender() {
		return account_gender;
	}
	public void setAccount_gender(String account_gender) {
		this.account_gender = account_gender;
	}
	
}
