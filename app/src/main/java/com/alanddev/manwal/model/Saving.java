package com.alanddev.manwal.model;

/**
 * Created by ANLD on 01/12/2015.
 */
public class Saving extends Model{
    private int id;
    private String title;
    private String startdate;
    private String enddate;
    private Float amount;
    private Float amount_real;
    private int wallet_id;
    //private int isTrans;

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

    public Float getAmount_real() {
        return amount_real;
    }

    public void setAmount_real(Float amount_real) {
        this.amount_real = amount_real;
    }


    public int getWallet_id() {
        return wallet_id;
    }

    public void setWallet_id(int wallet_id) {
        this.wallet_id = wallet_id;
    }
   /* public int getIsTrans() {
        return isTrans;
    }

    public void setIsTrans(int isTrans) {
        this.isTrans = isTrans;
    }
*/
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
