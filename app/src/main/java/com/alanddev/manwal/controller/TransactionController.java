package com.alanddev.manwal.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.alanddev.manwal.helper.IDataSource;
import com.alanddev.manwal.helper.MwSQLiteHelper;
import com.alanddev.manwal.model.Category;
import com.alanddev.manwal.model.Currency;
import com.alanddev.manwal.model.Model;
import com.alanddev.manwal.model.TransactionDetail;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ANLD on 24/11/2015.
 */
public class TransactionController implements IDataSource {
    private SQLiteDatabase database;
    private MwSQLiteHelper dbHelper;

    public TransactionController(Context context){
        dbHelper = new MwSQLiteHelper(context);
    }
    @Override
    public void open() {
        database = dbHelper.getWritableDatabase();
    }

    @Override
    public void close() {
        dbHelper.close();
    }

    private String [] allColumns = {
            MwSQLiteHelper.COLUMN_TRANS_ID,
            MwSQLiteHelper.COLUMN_TRANS_AMOUNT,
            MwSQLiteHelper.COLUMN_TRANS_CREATED_DATE,
            MwSQLiteHelper.COLUMN_TRANS_DISPLAY_DATE,
            MwSQLiteHelper.COLUMN_TRANS_CATE_ID,
            MwSQLiteHelper.COLUMN_TRANS_NOTE,
            MwSQLiteHelper.COLUMN_TRANS_LONGITUDE,
            MwSQLiteHelper.COLUMN_TRANS_LATTITUDE,
            MwSQLiteHelper.COLUMN_TRANS_ADDRESS,
            MwSQLiteHelper.COLUMN_TRANS_WALLET_ID,
            MwSQLiteHelper.COLUMN_TRANS_REMIND_ID,
            MwSQLiteHelper.COLUMN_TRANS_SEARCH_NOTE,
            MwSQLiteHelper.COLUMN_TRANS_BILL_ID
    };

    @Override
    public Model create(Model data) {
        ContentValues values = new ContentValues();
        TransactionDetail trans  = (TransactionDetail)data;
        values.put(MwSQLiteHelper.COLUMN_TRANS_AMOUNT, trans.getAmountt());
        values.put(MwSQLiteHelper.COLUMN_TRANS_DISPLAY_DATE, trans.getDisplay_date());
        values.put(MwSQLiteHelper.COLUMN_TRANS_CATE_ID, trans.getCat_id());
        values.put(MwSQLiteHelper.COLUMN_TRANS_NOTE, trans.getNote());
        values.put(MwSQLiteHelper.COLUMN_TRANS_LONGITUDE, trans.getLongitude());
        values.put(MwSQLiteHelper.COLUMN_TRANS_LATTITUDE, trans.getLattitude());
        values.put(MwSQLiteHelper.COLUMN_TRANS_ADDRESS, trans.getAddress());
        values.put(MwSQLiteHelper.COLUMN_TRANS_WALLET_ID, trans.getWallet_id());
        values.put(MwSQLiteHelper.COLUMN_TRANS_REMIND_ID, trans.getRemind_id());
        values.put(MwSQLiteHelper.COLUMN_TRANS_SEARCH_NOTE, trans.getSearch_note());
        values.put(MwSQLiteHelper.COLUMN_TRANS_BILL_ID, trans.getBill_id());
        database.insert(MwSQLiteHelper.TABLE_TRANSACTION, null,
                values);
        return trans;
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
        List<Model> trans = new ArrayList<Model>();
        Cursor cursor = database.query(MwSQLiteHelper.TABLE_TRANSACTION,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            TransactionDetail tran = (TransactionDetail)cursorTo(cursor);
            trans.add(tran);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return trans;
    }

    @Override
    public Model get(String query) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        Cursor cursor = database.query(MwSQLiteHelper.TABLE_TRANSACTION,
                allColumns, query, null,
                null, null, null);
        cursor.moveToFirst();
        TransactionDetail transactionDetail = (TransactionDetail)cursorTo(cursor);
        cursor.close();
        return transactionDetail;
    }

    @Override
    public Model cursorTo(Cursor cursor) {
        TransactionDetail trans = new TransactionDetail();
        trans.setId(cursor.getInt(0));
        trans.setAmountt(cursor.getFloat(1));
        trans.setCreated_date(cursor.getString(2));
        trans.setDisplay_date(cursor.getString(3));
        trans.setCat_id(cursor.getInt(4));
        trans.setNote(cursor.getString(5));
        trans.setLongitude(cursor.getString(6));
        trans.setLattitude(cursor.getString(7));
        trans.setAddress(cursor.getString(8));
        trans.setWallet_id(cursor.getInt(9));
        trans.setRemind_id(cursor.getInt(10));
        trans.setSearch_note(cursor.getString(11));
        trans.setBill_id(cursor.getInt(12));

        return trans;
    }
}
