package com.alanddev.manwal.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.alanddev.manwal.helper.IDataSource;
import com.alanddev.manwal.helper.MwSQLiteHelper;
import com.alanddev.manwal.model.Model;
import com.alanddev.manwal.model.Wallet;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by td.long on 11/19/2015.
 */
public class WalletController implements IDataSource {

    private SQLiteDatabase database;
    private MwSQLiteHelper dbHelper;

    private String [] allColumns = {
            MwSQLiteHelper.COLUMN_WALLET_NAME,
            MwSQLiteHelper.COLUMN_WALLET_AMOUNT,
            MwSQLiteHelper.COLUMN_WALLET_CURRENCY
    };

    public WalletController(Context context){
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
    public Model create(Model data) {
        ContentValues values = new ContentValues();
        //values.put(MwSQLiteHelper.COLUMN_ID, id);
        Wallet wallet  = (Wallet)data;
        values.put(MwSQLiteHelper.COLUMN_WALLET_NAME, wallet.getName());
        values.put(MwSQLiteHelper.COLUMN_WALLET_AMOUNT, wallet.getAmount());
        values.put(MwSQLiteHelper.COLUMN_WALLET_CURRENCY, wallet.getCurrency());

        String selectQuery = MwSQLiteHelper.COLUMN_WALLET_NAME + " = \"" + wallet.getName() + "\"";
        database.insert(MwSQLiteHelper.TABLE_WALLET, null,
                values);
        Wallet newWallet = (Wallet)get(selectQuery);
        dbHelper.close();
        return newWallet;
    }

    @Override
    public void update(Model data) {
        Wallet wallet  = (Wallet)data;
        ContentValues values = new ContentValues();
        values.put(MwSQLiteHelper.COLUMN_WALLET_CURRENCY, wallet.getCurrency());
        values.put(MwSQLiteHelper.COLUMN_WALLET_AMOUNT, wallet.getAmount());
        // updating row
        database.update(MwSQLiteHelper.TABLE_WALLET, values, MwSQLiteHelper.COLUMN_WALLET_NAME + " = ?",
                new String[]{String.valueOf(wallet.getName())});
        dbHelper.close();
    }

    @Override
    public int getCount() {
        String countQuery = "SELECT  * FROM " + MwSQLiteHelper.TABLE_WALLET;
        Cursor cursor = database.rawQuery(countQuery, null);
        cursor.close();
        // return count
        dbHelper.close();
        return cursor.getCount();

    }

    @Override
    public List<Model> getAll() {
        List<Model> wallets = new ArrayList<Model>();
        Cursor cursor = database.query(MwSQLiteHelper.TABLE_WALLET,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Wallet wallet = (Wallet)cursorTo(cursor);
            wallets.add(wallet);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        dbHelper.close();
        return wallets;
    }

    @Override
    public Model get(String query) {
        Cursor cursor = database.query(MwSQLiteHelper.TABLE_WALLET,
                allColumns, query, null,
                null, null, null);
        cursor.moveToFirst();
        Wallet wallet = (Wallet)cursorTo(cursor);
        cursor.close();
        dbHelper.close();
        return wallet;

    }

    @Override
    public Model cursorTo(Cursor cursor) {
        Wallet wallet = new Wallet();
        wallet.setName(cursor.getString(0));
        wallet.setAmount(cursor.getDouble(1));
        wallet.setCurrency(cursor.getString(2));
        return wallet;
    }

}