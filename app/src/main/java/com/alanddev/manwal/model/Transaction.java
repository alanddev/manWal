package com.alanddev.manwal.model;

import java.util.List;

public class Transaction {
	private String date;
	private String day;
	private String month;
	private String year;
	private String amount;
	private List<TransactionDetail> items;
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getDay() {
		return day;
	}
	public void setDay(String day) {
		this.day = day;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public List<TransactionDetail> getItems() {
		return items;
	}
	public void setItems(List<TransactionDetail> items) {
		this.items = items;
	}
	
}
