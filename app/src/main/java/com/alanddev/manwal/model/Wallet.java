package com.alanddev.manwal.model;


import com.alanddev.manwal.model.Model;

public class Wallet extends Model {
	
	private String name;
	private double amount;
	private String currency;
	private int id;
	private int choose;


	public int getChoose() {
		return choose;
	}

	public void setChoose(int choose) {
		this.choose = choose;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	private String image;

	public Wallet(){

	}
	
	public Wallet(String name, double amount,String currency,String imagePath){
		this.name = name;
		this. amount = amount;
		this.currency = currency;
		this.image = imagePath;
	}
	

	public int getId(){
		return id;
	}
	
	public void setId(int ID){
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