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




	
}
