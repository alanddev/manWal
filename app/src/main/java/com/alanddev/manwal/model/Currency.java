package com.alanddev.manwal.model;


public class Currency extends Model {

	private int id;
	private String code;
	private String name;
	private String symbol;
	private int display;

	public Currency(){
		this.display = 0;
	}

	public Currency(String code, String name, String symbol, int display){
		this.name = name;
		this.code = code;
		this.symbol = symbol;
		this.display = display;
	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public int getDisplay() {
		return display;
	}

	public void setDisplay(int display) {
		this.display = display;
	}
}