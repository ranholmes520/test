package com.ranholmes.dto;


public class AccountLite {
	private String account_name;
	private Gender account_gender;
	private String status;
	private String account_create_time;
	private String account_mail;
	private String account_balance;
	
	
	public AccountLite(String account_name, Gender account_gender,
			String status, String account_create_time, String account_mail,
			String account_balance) {
		super();
		this.account_name = account_name;
		this.account_gender = account_gender;
		this.status = status;
		this.account_create_time = account_create_time;
		this.account_mail = account_mail;
		this.account_balance = account_balance;
	}
	public String getAccount_balance() {
		return account_balance;
	}
	public void setAccount_balance(String account_balance) {
		this.account_balance = account_balance;
	}
	public String getAccount_name() {
		return account_name;
	}
	public void setAccount_name(String account_name) {
		this.account_name = account_name;
	}
	public Gender getAccount_gender() {
		return account_gender;
	}
	public void setAccount_gender(Gender account_gender) {
		this.account_gender = account_gender;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getAccount_create_time() {
		return account_create_time;
	}
	public void setAccount_create_time(String account_create_time) {
		this.account_create_time = account_create_time;
	}
	public String getAccount_mail() {
		return account_mail;
	}
	public void setAccount_mail(String account_mail) {
		this.account_mail = account_mail;
	}
	
	
}
