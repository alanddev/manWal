package com.alanddev.manwal.model;

import java.util.Date;
import java.util.List;

/**
 * Created by ANLD on 26/11/2015.
 */
public class TransactionDay {
    private Date display_date;
    private float examount;
    private float inamount;
    private float netamount;
    private List<TransactionDetail> items;

    public Date getDisplay_date() {
        return display_date;
    }

    public void setDisplay_date(Date display_date) {
        this.display_date = display_date;
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

    public List<TransactionDetail> getItems() {
        return items;
    }

    public void setItems(List<TransactionDetail> items) {
        this.items = items;
    }
}
