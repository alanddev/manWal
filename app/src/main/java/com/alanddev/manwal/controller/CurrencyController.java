package com.alanddev.manwal.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.alanddev.manwal.helper.IDataSource;
import com.alanddev.manwal.helper.MwSQLiteHelper;
import com.alanddev.manwal.model.Currency;
import com.alanddev.manwal.model.Model;
import com.alanddev.manwal.model.Wallet;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by td.long on 11/19/2015.
 */
public class CurrencyController implements IDataSource {

    private SQLiteDatabase database;
    private MwSQLiteHelper dbHelper;

    private String [] allColumns = {
            MwSQLiteHelper.COLUMN_CUR_ID,
            MwSQLiteHelper.COLUMN_CUR_CODE,
            MwSQLiteHelper.COLUMN_CUR_NAME,
            MwSQLiteHelper.COLUMN_CUR_SYMBOL,
            MwSQLiteHelper.COLUMN_CUR_DISPLAY,
    };

    public CurrencyController(Context context){
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

    @Override
    public Currency create(Model data) {
        ContentValues values = new ContentValues();
        //values.put(MwSQLiteHelper.COLUMN_ID, id);
        Currency currency  = (Currency)data;
        //values.put(MwSQLiteHelper.COLUMN_CUR_ID, currency.getId());
        values.put(MwSQLiteHelper.COLUMN_CUR_CODE, currency.getCode());
        values.put(MwSQLiteHelper.COLUMN_CUR_NAME, currency.getName());
        values.put(MwSQLiteHelper.COLUMN_CUR_SYMBOL, currency.getSymbol());
        values.put(MwSQLiteHelper.COLUMN_CUR_DISPLAY, currency.getDisplay());

        //String selectQuery = MwSQLiteHelper.COLUMN_CUR_CODE + " = \"" + currency.getCode() + "\"";
        database.insert(MwSQLiteHelper.TABLE_CUR, null,
                values);
        //Currency newCurrency = (Currency)get(selectQuery);
        //dbHelper.close();
        return currency;
    }

    @Override
    public void update(Model data) {
        Currency currency  = (Currency)data;
        ContentValues values = new ContentValues();
        values.put(MwSQLiteHelper.COLUMN_CUR_CODE, currency.getCode());
        values.put(MwSQLiteHelper.COLUMN_CUR_NAME, currency.getName());
        values.put(MwSQLiteHelper.COLUMN_CUR_SYMBOL, currency.getSymbol());
        values.put(MwSQLiteHelper.COLUMN_CUR_DISPLAY, currency.getDisplay());
        // updating row
        database.update(MwSQLiteHelper.TABLE_WALLET, values, MwSQLiteHelper.COLUMN_CUR_CODE + " = ?",
                new String[]{String.valueOf(currency.getCode())});
    }

    @Override
    public int getCount() {
        String countQuery = "SELECT  * FROM " + MwSQLiteHelper.TABLE_CUR;
        Cursor cursor = database.rawQuery(countQuery, null);
        cursor.close();
        // return count
        return cursor.getCount();
    }

    @Override
    public List<Model> getAll() {
        List<Model> currencies = new ArrayList<Model>();
        Cursor cursor = database.query(MwSQLiteHelper.TABLE_CUR,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Currency currency = (Currency)cursorTo(cursor);
            currencies.add(currency);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return currencies;
    }

    @Override
    public Model get(String query) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        Cursor cursor = database.query(MwSQLiteHelper.TABLE_CUR,
                allColumns, query, null,
                null, null, null);
        cursor.moveToFirst();
        Currency currency = (Currency)cursorTo(cursor);
        cursor.close();
        return currency;
    }

    public void getChecked(){

    }


    @Override
    public Model cursorTo(Cursor cursor) {
        Currency currency = new Currency();
        currency.setId(cursor.getInt(0));
        currency.setCode(cursor.getString(1));
        currency.setName(cursor.getString(2));
        currency.setSymbol(cursor.getString(3));
        currency.setDisplay(cursor.getInt(4));
        return currency;
    }

    public void init(){
        Currency currency_vnd  = new Currency("VND","Viet Nam Dong","đ",1);
        Currency currency_usd  = new Currency("USD","Dollar","$",0);
        create(currency_vnd);
        create(currency_usd);
    }
}
