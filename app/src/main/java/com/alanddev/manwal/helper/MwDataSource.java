package com.alanddev.manwal.helper;

import java.util.ArrayList;
import java.util.List;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.alanddev.manwal.model.Wallet;


public class MwDataSource {
	private SQLiteDatabase database;
	private MwSQLiteHelper dbHelper;
	private String [] allColumns = {
			//MwSQLiteHelper.COLUMN_ID,
			MwSQLiteHelper.COLUMN_WALLET_NAME,
			MwSQLiteHelper.COLUMN_WALLET_AMOUNT,
			MwSQLiteHelper.COLUMN_WALLET_CURRENCY
	};
	
	public MwDataSource(Context context) {
		dbHelper = new MwSQLiteHelper(context);
		open();
	}

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}
	
	public void close(){
		dbHelper.close();
	}
	
	// createNetwork
	public Wallet createWallet(String name, double amount, String currency) {
		ContentValues values = new ContentValues();
		//values.put(MwSQLiteHelper.COLUMN_ID, id);
		values.put(MwSQLiteHelper.COLUMN_WALLET_NAME, name);
		values.put(MwSQLiteHelper.COLUMN_WALLET_AMOUNT, amount);
		values.put(MwSQLiteHelper.COLUMN_WALLET_CURRENCY, currency);

	    String selectQuery = MwSQLiteHelper.COLUMN_WALLET_NAME + " = \"" + name + "\"";
	    Cursor cursor = database.query(MwSQLiteHelper.TABLE_WALLET,
	        allColumns, selectQuery, null,
	        null, null, null);
	    //if (cursor.getCount() <= 0){
	    	// Can assign insertId = ...
	    	database.insert(MwSQLiteHelper.TABLE_WALLET, null,
		        values);
	    	cursor = database.query(MwSQLiteHelper.TABLE_WALLET,
	    	        allColumns, selectQuery, null,
	    	        null, null, null);
	    	cursor.moveToFirst();
	    //}
	    //cursor.moveToFirst();
	    Wallet newWallet = cursorToWallet(cursor);
	    cursor.close();
	    return newWallet;
	  }

	public Wallet createWallet(Wallet wallet){
		Wallet newWallet= createWallet(wallet.getName(),wallet.getAmount(),wallet.getCurrency());
		return newWallet;
	}

	public int updateWallet(Wallet wallet) {
		//SQLiteDatabase db = dbHelper.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(MwSQLiteHelper.COLUMN_WALLET_CURRENCY, wallet.getCurrency());
		values.put(MwSQLiteHelper.COLUMN_WALLET_AMOUNT, wallet.getAmount());

		// updating row
		return database.update(MwSQLiteHelper.TABLE_WALLET, values, MwSQLiteHelper.COLUMN_WALLET_NAME + " = ?",
				new String[] { String.valueOf(wallet.getName()) });
	}



	// don't do it
	public void deleteWallet(Wallet wallet) {
		//long id = wallet.getId();
		//System.out.println("Wallet deleted with id: " + id);
		//	    database.delete(NwSQLiteHelper.TABLE_NETWORK, NwSQLiteHelper.COLUMN_ID
		//	        + " = " + id, null);
	}

	
	public List<Wallet> getAllWallets() {
		List<Wallet> wallets = new ArrayList<Wallet>();

		Cursor cursor = database.query(MwSQLiteHelper.TABLE_WALLET,
				allColumns, null, null, null, null, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Wallet wallet = cursorToWallet(cursor);
			wallets.add(wallet);
			cursor.moveToNext();
		}
		// make sure to close the cursor
		cursor.close();
		return wallets;
	}


	public int getWalletCount() {
		String countQuery = "SELECT  * FROM " + MwSQLiteHelper.TABLE_WALLET;
		Cursor cursor = database.rawQuery(countQuery, null);
		cursor.close();
		// return count
		return cursor.getCount();


	}


	private Wallet cursorToWallet(Cursor cursor) {
		Wallet wallet = new Wallet();
		//wallet.setId(cursor.getLong(0));
		wallet.setName(cursor.getString(0));
		wallet.setAmount(cursor.getDouble(1));
		wallet.setCurrency(cursor.getString(2));
		return wallet;
	}



	
}
