package com.alanddev.manwal.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MwSQLiteHelper extends SQLiteOpenHelper {

    public static final String TABLE_WALLET = "wallet";
    public static final String TABLE_CUR = "currency";
    public static final String TABLE_CATEGORY = "category";
    public static final String TABLE_TRANSACTION = "transaction";

    public static final String COLUMN_WALLET_NAME = "name";
    public static final String COLUMN_WALLET_AMOUNT = "amount";
    public static final String COLUMN_WALLET_CURRENCY = "currency";


    public static final String COLUMN_CUR_ID = "id";
    public static final String COLUMN_CUR_CODE = "code";
    public static final String COLUMN_CUR_NAME = "name";
    public static final String COLUMN_CUR_SYMBOL = "symbol";
    public static final String COLUMN_CUR_DISPLAY = "display";

    //cate column
    public static final String COLUMN_CATE_ID = "id";
    public static final String COLUMN_CATE_NAME = "name";
    public static final String COLUMN_CATE_IMG = "image";
    public static final String COLUMN_CATE_TYPE = "type";

    //transaction column
    private String remind_id;
    private String search_note;
    private String bill_id;
    public static final String COLUMN_TRANS_ID = "id";
    public static final String COLUMN_TRANS_AMOUNT = "amount";
    public static final String COLUMN_TRANS_CREATED_DATE = "created_date";
    public static final String COLUMN_TRANS_DISPLAY_DATE = "display_date";
    public static final String COLUMN_TRANS_CATE_ID = "cat_id";
    public static final String COLUMN_TRANS_NOTE = "note";
    public static final String COLUMN_TRANS_LONGITUDE = "longitude";
    public static final String COLUMN_TRANS_LATTITUDE = "lattitude";
    public static final String COLUMN_TRANS_ADDRESS = "address";
    public static final String COLUMN_TRANS_WALLET_ID = "wallet_id";
    public static final String COLUMN_TRANS_REMIND_ID = "remind_id";
    public static final String COLUMN_TRANS_SEARCH_NOTE = "search_note";
    public static final String COLUMN_TRANS_BILL_ID = "bill_id";

    public static final String DATABASE_NAME = "manwal.db";
    private static final int DATABASE_VERSION = 1;

    public SQLiteDatabase sqLiteDatabase;
    // 20 fields
    // Database creation sql statement
    private static final String WALLET_CREATE = "CREATE TABLE "
            + TABLE_WALLET + "("
            + COLUMN_WALLET_NAME + " text not null, "
            + COLUMN_WALLET_AMOUNT + " real not null, "
            + COLUMN_WALLET_CURRENCY + " text not null, "
            + "PRIMARY KEY (" + COLUMN_WALLET_NAME + ") "
            + ");";

    private static final String CUR_CREATE = "CREATE TABLE "
            + TABLE_CUR + "("
            + COLUMN_CUR_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_CUR_CODE + " text not null, "
            + COLUMN_CUR_NAME + " text not null, "
            + COLUMN_CUR_SYMBOL + " text not null, "
            + COLUMN_CUR_DISPLAY + " int not null "
            + ");";

    private static final String CATE_CREATE = "CREATE TABLE "
            + TABLE_CATEGORY + "("
            + COLUMN_CATE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_CATE_NAME + " text not null, "
            + COLUMN_CATE_IMG + " text not null, "
            + COLUMN_CATE_TYPE + " integer not null"
            + ");";

    private static final String TRANS_CREATE = "CREATE TABLE "
            + TABLE_TRANSACTION + "("
            + COLUMN_TRANS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_TRANS_AMOUNT + " float not null, "
            + COLUMN_TRANS_CREATED_DATE + " DATETIME DEFAULT CURRENT_TIMESTAMP, "
            + COLUMN_TRANS_DISPLAY_DATE + " DATETIME not null,"
            + COLUMN_TRANS_CATE_ID + " integer not null,"
            + COLUMN_TRANS_NOTE + " text,"
            + COLUMN_TRANS_LONGITUDE + " float,"
            + COLUMN_TRANS_LATTITUDE + " float,"
            + COLUMN_TRANS_ADDRESS + " text,"
            + COLUMN_TRANS_WALLET_ID + " integer not null,"
            + COLUMN_TRANS_REMIND_ID + " integer,"
            + COLUMN_TRANS_SEARCH_NOTE + " text,"
            + COLUMN_TRANS_BILL_ID + " integer"
            + ");";


    public MwSQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        sqLiteDatabase = database;
        database.execSQL(WALLET_CREATE);
        database.execSQL(CUR_CREATE);
        database.execSQL(CATE_CREATE);
        database.execSQL(TRANS_CREATE);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            /*Log.w(MySQLiteHelper.class.getName(),
		        "Upgrading database from version " + oldVersion + " to "
		            + newVersion + ", which will destroy all old data");*/
        //sqLiteDatabase = db;
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_WALLET);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CUR);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TRANSACTION);
        onCreate(db);
    }

}

