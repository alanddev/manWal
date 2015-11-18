package com.alanddev.manwal.data;


public class Wallet {
	
	private String name;
	private long id;
	private double amount;
	private String currency;
	
	public Wallet(){

	}
	
	public Wallet(long id, String name, double amount,String currency){
		this.id = id;
		this.name = name;
		this. amount = amount;
		this.currency = currency;
	}
	

	public long getId(){
		return id;
	}
	
	public void setId(long ID){
		this.id = ID;
	}
	
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}
}