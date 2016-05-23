package com.alanddev.manwal.model;

/**
 * Created by ANLD on 13/04/2016.
 */
public class SavingT extends Model{
    private int id;
    private int type;
    private String createdDt;
    private Float amount;
    private int saving_id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getCreatedDt() {
        return createdDt;
    }

    public void setCreatedDt(String createdDt) {
        this.createdDt = createdDt;
    }

    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    public int getSaving_id() {
        return saving_id;
    }

    public void setSaving_id(int saving_id) {
        this.saving_id = saving_id;
    }
}
