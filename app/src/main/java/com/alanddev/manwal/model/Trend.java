package com.alanddev.manwal.model;

/**
 * Created by longty on 01/12/2015.
 */
public class Trend {
    private String month;
    private String year;
    private float amountIn;
    private float amountOut;
    private int type;
    private float amount;

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }





    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
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

    public float getAmountIn() {
        return amountIn;
    }

    public void setAmountIn(float amountIn) {
        this.amountIn = amountIn;
    }

    public float getAmountOut() {
        return amountOut;
    }

    public void setAmountOut(float amountOut) {
        this.amountOut = amountOut;
    }

}
