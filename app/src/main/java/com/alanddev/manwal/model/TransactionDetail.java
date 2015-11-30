package com.alanddev.manwal.model;

public class TransactionDetail extends Model{
	private long id;
	private float amount;
	private String created_date;
	private String display_date;
	private int cat_id;
	private String note;
	private String longitude;
	private String lattitude;
	private String address;
	private int wallet_id;
	private int remind_id;
	private String search_note;
	private int bill_id;
	private String cate_name;
	private String cate_img;
	private int cate_type;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public float getAmountt() {
		return amount;
	}

	public void setAmountt(float amout) {
		this.amount = amout;
	}

	public String getCreated_date() {
		return created_date;
	}

	public void setCreated_date(String created_date) {
		this.created_date = created_date;
	}

	public String getDisplay_date() {
		return display_date;
	}

	public void setDisplay_date(String display_date) {
		this.display_date = display_date;
	}

	public int getCat_id() {
		return cat_id;
	}

	public void setCat_id(int cat_id) {
		this.cat_id = cat_id;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLattitude() {
		return lattitude;
	}

	public void setLattitude(String lattitude) {
		this.lattitude = lattitude;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getWallet_id() {
		return wallet_id;
	}

	public void setWallet_id(int wallet_id) {
		this.wallet_id = wallet_id;
	}

	public int getRemind_id() {
		return remind_id;
	}

	public void setRemind_id(int remind_id) {
		this.remind_id = remind_id;
	}

	public String getSearch_note() {
		return search_note;
	}

	public void setSearch_note(String search_note) {
		this.search_note = search_note;
	}

	public int getBill_id() {
		return bill_id;
	}

	public void setBill_id(int bill_id) {
		this.bill_id = bill_id;
	}

	public String getCate_name() {
		return cate_name;
	}

	public void setCate_name(String cate_name) {
		this.cate_name = cate_name;
	}

	public String getCate_img() {
		return cate_img;
	}

	public void setCate_img(String cate_img) {
		this.cate_img = cate_img;
	}

	public int getCate_type() {
		return cate_type;
	}

	public void setCate_type(int cate_type) {
		this.cate_type = cate_type;
	}
}
