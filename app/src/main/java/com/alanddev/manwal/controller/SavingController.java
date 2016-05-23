package com.alanddev.manwal.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.alanddev.manwal.helper.IDataSource;
import com.alanddev.manwal.helper.MwSQLiteHelper;
import com.alanddev.manwal.model.Budget;
import com.alanddev.manwal.model.Model;
import com.alanddev.manwal.model.Saving;
import com.alanddev.manwal.util.Constant;
import com.alanddev.manwal.util.Utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by ANLD on 23/11/2015.
 */
public class SavingController implements IDataSource {
    private SQLiteDatabase database;
    private MwSQLiteHelper dbHelper;
    private Context mContext;

    private String [] allColumns = {
            MwSQLiteHelper.COLUMN_SAVING_ID,
            MwSQLiteHelper.COLUMN_SAVING_START_DATE,
            MwSQLiteHelper.COLUMN_SAVING_END_DATE,
            MwSQLiteHelper.COLUMN_SAVING_AMOUNT,
            MwSQLiteHelper.COLUMN_SAVING_AMOUNT_REAL,
            MwSQLiteHelper.COLUMN_SAVING_WALLET_ID,
            //MwSQLiteHelper.COLUMN_SAVING_ISTRANS
    };

    public SavingController(Context context){
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
        Saving saving  = (Saving)data;
        values.put(MwSQLiteHelper.COLUMN_SAVING_TITLE, saving.getTitle());
        values.put(MwSQLiteHelper.COLUMN_SAVING_START_DATE, saving.getStartdate());
        values.put(MwSQLiteHelper.COLUMN_SAVING_END_DATE, saving.getEnddate());
        values.put(MwSQLiteHelper.COLUMN_SAVING_AMOUNT, saving.getAmount());
        values.put(MwSQLiteHelper.COLUMN_SAVING_AMOUNT_REAL, saving.getAmount_real());
        values.put(MwSQLiteHelper.COLUMN_SAVING_WALLET_ID, saving.getWallet_id());
        //values.put(MwSQLiteHelper.COLUMN_SAVING_ISTRANS, saving.getIsTrans());
        database.insert(MwSQLiteHelper.TABLE_SAVING, null,
                values);
        return saving;
    }

    @Override
    public void update(Model data) {
        ContentValues values = new ContentValues();
        Saving saving = (Saving) data;
        values.put(MwSQLiteHelper.COLUMN_SAVING_TITLE, saving.getTitle());
        values.put(MwSQLiteHelper.COLUMN_SAVING_START_DATE, saving.getStartdate());
        values.put(MwSQLiteHelper.COLUMN_SAVING_END_DATE, saving.getEnddate());
        values.put(MwSQLiteHelper.COLUMN_SAVING_AMOUNT, saving.getAmount());
        values.put(MwSQLiteHelper.COLUMN_SAVING_AMOUNT_REAL, saving.getAmount_real());
        //values.put(MwSQLiteHelper.COLUMN_SAVING_ISTRANS, saving.getIsTrans());
        database.update(MwSQLiteHelper.TABLE_SAVING, values, MwSQLiteHelper.COLUMN_SAVING_ID + " = ?", new String[]{saving.getId() + ""});
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

    public Boolean delete(long savingId){
        return database.delete(MwSQLiteHelper.TABLE_SAVING, MwSQLiteHelper.COLUMN_SAVING_ID + "=" + savingId, null) > 0;
    }

    public Saving getSavingById(long id){
        StringBuffer sql = new StringBuffer("SELECT * FROM ").append(MwSQLiteHelper.TABLE_SAVING)
                .append(" WHERE ").append(MwSQLiteHelper.COLUMN_SAVING_WALLET_ID).append(" = ").append(Utils.getWallet_id())
                .append(" AND ").append(MwSQLiteHelper.COLUMN_SAVING_ID).append(" = ?");
        String[] atts = new String[]{id+""};
        Cursor cursor = database.rawQuery(sql.toString(), atts);
        cursor.moveToFirst();
        Saving saving = (Saving) cursorTo(cursor);
        cursor.close();
        return saving;
    }

    public List<Saving> getAll(int type){
        List<Saving> savings = new ArrayList<Saving>();
        StringBuffer sql;
        if(type==0) {
            sql = new StringBuffer("SELECT * FROM ").append(MwSQLiteHelper.TABLE_SAVING)
                    .append(" WHERE ").append(MwSQLiteHelper.COLUMN_SAVING_WALLET_ID).append(" = ").append(Utils.getWallet_id())
                    .append(" AND ").append(MwSQLiteHelper.COLUMN_SAVING_END_DATE).append(" >= ?");
        }else{
            sql = new StringBuffer("SELECT * FROM ").append(MwSQLiteHelper.TABLE_SAVING)
                    .append(" WHERE ").append(MwSQLiteHelper.COLUMN_SAVING_WALLET_ID).append(" = ").append(Utils.getWallet_id())
                    .append(" AND ").append(MwSQLiteHelper.COLUMN_SAVING_END_DATE).append(" < ?");
        }
        String strDate = Utils.changeDate2Str(new Date(), Constant.DATE_FORMAT_DB);
        String[] atts = new String[]{strDate};
        Cursor cursor = database.rawQuery(sql.toString(), atts);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Saving saving = (Saving)cursorTo(cursor);
            savings.add(saving);
            cursor.moveToNext();
        }
        return savings;
    }

    @Override
    public Model cursorTo(Cursor cursor) {
        Saving saving = new Saving();
        saving.setId(cursor.getInt(0));
        saving.setTitle(cursor.getString(1));
        saving.setStartdate(cursor.getString(2));
        saving.setEnddate(cursor.getString(3));
        saving.setAmount(cursor.getFloat(4));
        saving.setAmount_real(cursor.getFloat(5));
        saving.setWallet_id(cursor.getInt(6));
        //saving.setIsTrans(cursor.getInt(7));
        return saving;
    }


}
