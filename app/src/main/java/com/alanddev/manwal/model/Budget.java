package com.alanddev.manwal.model;

import java.util.Date;

/**
 * Created by ANLD on 01/12/2015.
 */
public class Budget extends Model{
    private int id;
    private String startdate;
    private String enddate;
    private Float amount;
    private Float amount_avl;
    private int cate_id;
    private int wallet_id;
    private String cate_img;
    private String cate_name;
    private int recurring_notify;
    private int is_repeat;
    private Float realamt;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStartdate() {
        return startdate;
    }

    public void setStartdate(String startdate) {
        this.startdate = startdate;
    }

    public String getEnddate() {
        return enddate;
    }

    public void setEnddate(String enddate) {
        this.enddate = enddate;
    }

    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    public Float getAmount_avl() {
        return amount_avl;
    }

    public void setAmount_avl(Float amount_avl) {
        this.amount_avl = amount_avl;
    }

    public int getCate_id() {
        return cate_id;
    }

    public void setCate_id(int cate_id) {
        this.cate_id = cate_id;
    }

    public int getWallet_id() {
        return wallet_id;
    }

    public void setWallet_id(int wallet_id) {
        this.wallet_id = wallet_id;
    }

    public String getCate_img() {
        return cate_img;
    }

    public void setCate_img(String cate_img) {
        this.cate_img = cate_img;
    }

    public String getCate_name() {
        return cate_name;
    }

    public void setCate_name(String cate_name) {
        this.cate_name = cate_name;
    }

    public int getRecurring_notify() {
        return recurring_notify;
    }

    public void setRecurring_notify(int recurring_notify) {
        this.recurring_notify = recurring_notify;
    }

    public int getIs_repeat() {
        return is_repeat;
    }

    public void setIs_repeat(int is_repeat) {
        this.is_repeat = is_repeat;
    }
    public Float getRealamt() {
        return realamt;
    }

    public void setRealamt(Float realamt) {
        this.realamt = realamt;
    }
}
