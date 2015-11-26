package com.alanddev.manwal.model;

import java.util.Date;
import java.util.List;

public class Transactions {
	private String title;
	private float examount;
	private float inamount;
	private float netamount;
	private List<TransactionDay> items;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public float getExamount() {
		return examount;
	}

	public void setExamount(float examount) {
		this.examount = examount;
	}

	public float getInamount() {
		return inamount;
	}

	public void setInamount(float inamount) {
		this.inamount = inamount;
	}

	public float getNetamount() {
		return netamount;
	}

	public void setNetamount(float netamount) {
		this.netamount = netamount;
	}

	public List<TransactionDay> getItems() {
		return items;
	}

	public void setItems(List<TransactionDay> items) {
		this.items = items;
	}
}
