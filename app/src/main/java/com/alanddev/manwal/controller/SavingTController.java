package com.alanddev.manwal.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.alanddev.manwal.helper.IDataSource;
import com.alanddev.manwal.helper.MwSQLiteHelper;
import com.alanddev.manwal.model.Model;
import com.alanddev.manwal.model.Saving;
import com.alanddev.manwal.model.SavingT;
import com.alanddev.manwal.util.Constant;
import com.alanddev.manwal.util.Utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by ANLD on 13/04/2016.
 */
public class SavingTController implements IDataSource {
    private SQLiteDatabase database;
    private MwSQLiteHelper dbHelper;
    private Context mContext;

    private String [] allColumns = {
            MwSQLiteHelper.COLUMN_SAVINGT_ID,
            MwSQLiteHelper.COLUMN_SAVINGT_TYPE,
            MwSQLiteHelper.COLUMN_SAVINGT_CREATE_DATE,
            MwSQLiteHelper.COLUMN_SAVINGT_AMOUNT,
            MwSQLiteHelper.COLUMN_SAVINGT_SAVING_ID,
    };

    public SavingTController(Context context){
        dbHelper = new MwSQLiteHelper(context);
        this.mContext = context;
    }

    @Override
    public void open() {
        database = dbHelper.getWritableDatabase();
    }

    @Override
    public void close() {
        dbHelper.close();
    }

    @Override
    public Model create(Model data) {
        ContentValues values = new ContentValues();
        SavingT saving  = (SavingT)data;
        values.put(MwSQLiteHelper.COLUMN_SAVINGT_TYPE, saving.getType());
        values.put(MwSQLiteHelper.COLUMN_SAVINGT_AMOUNT, saving.getAmount());
        values.put(MwSQLiteHelper.COLUMN_SAVINGT_SAVING_ID, saving.getSaving_id());
        database.insert(MwSQLiteHelper.TABLE_SAVINGT, null,
                values);
        return saving;

    }

    @Override
    public void update(Model data) {

    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public List<Model> getAll() {
        return null;
    }

    @Override
    public Model get(String query) {
        return null;
    }

    @Override
    public List<Model> getAll(String query) {
        return null;
    }

    public List<SavingT> getAllbySaving(int savingId){
        List<SavingT> savings = new ArrayList<SavingT>();
        StringBuffer sql = new StringBuffer("SELECT * FROM ").append(MwSQLiteHelper.TABLE_SAVINGT)
                    .append(" WHERE ").append(MwSQLiteHelper.COLUMN_SAVINGT_SAVING_ID).append(" = ?");
        String[] atts = new String[]{savingId+""};
        Cursor cursor = database.rawQuery(sql.toString(), atts);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            SavingT saving = (SavingT)cursorTo(cursor);
            savings.add(saving);
            cursor.moveToNext();
        }
        return savings;
    }

    public Boolean delete(int savingTId){
        return database.delete(MwSQLiteHelper.TABLE_SAVINGT, MwSQLiteHelper.COLUMN_SAVINGT_ID + "=" + savingTId, null) > 0;
    }

    @Override
    public Model cursorTo(Cursor cursor) {
        SavingT saving = new SavingT();
        saving.setId(cursor.getInt(0));
        saving.setType(cursor.getInt(1));
        saving.setCreatedDt(cursor.getString(2));
        saving.setAmount(cursor.getFloat(3));
        saving.setSaving_id(cursor.getInt(4));
        return saving;
    }
}
