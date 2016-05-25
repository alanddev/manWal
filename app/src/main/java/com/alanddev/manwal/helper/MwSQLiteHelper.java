package com.alanddev.manwal.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MwSQLiteHelper extends SQLiteOpenHelper {

    public static final String TABLE_WALLET = "wallet";
    public static final String TABLE_CUR = "currency";
    public static final String TABLE_CATEGORY = "category";
    public static final String TABLE_TRANSACTION = "transactions";
    public static final String TABLE_BUDGET = "budget";
    public static final String TABLE_SAVING = "saving";
    public static final String TABLE_SAVINGT = "saving_trans";

    // table Wallet
    public static final String COLUMN_WALLET_ID = "id";
    public static final String COLUMN_WALLET_NAME = "name";
    public static final String COLUMN_WALLET_AMOUNT = "amount";
    public static final String COLUMN_WALLET_CURRENCY = "currency";
    // only save image Name file.
    public static final String COLUMN_WALLET_IMG="image";


    // table Currency
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

    //budget column
    public static final String COLUMN_BUDGET_ID = "budget_id";
    public static final String COLUMN_BUDGET_START_DATE = "start_date";
    public static final String COLUMN_BUDGET_END_DATE = "end_date";
    public static final String COLUMN_BUDGET_AMOUNT = "amount";
    public static final String COLUMN_BUDGET_WALLET_ID = "wallet_id";
    public static final String COLUMN_BUDGET_CATE_ID = "cat_id";
    public static final String COLUMN_BUDGET_RECURRING_NOTIFY = "recurring_notify";
    public static final String COLUMN_BUDGET_IS_REAPEAT = "isrepeat";

    public static final String COLUMN_SAVING_ID = "id";
    public static final String COLUMN_SAVING_TITLE = "title";
    public static final String COLUMN_SAVING_START_DATE = "start_date";
    public static final String COLUMN_SAVING_END_DATE = "end_date";
    public static final String COLUMN_SAVING_AMOUNT = "amount";
    public static final String COLUMN_SAVING_AMOUNT_REAL = "amount_real";
    public static final String COLUMN_SAVING_WALLET_ID = "wallet_id";
    //public static final String COLUMN_SAVING_ISTRANS = "istrans";

    public static final String COLUMN_SAVINGT_ID = "id";
    public static final String COLUMN_SAVINGT_TYPE = "type";
    public static final String COLUMN_SAVINGT_CREATE_DATE = "start_date";
    public static final String COLUMN_SAVINGT_AMOUNT = "amount";
    public static final String COLUMN_SAVINGT_SAVING_ID = "saving_id";

    public static final String DATABASE_NAME = "manwal.db";
    private static final int DATABASE_VERSION = 2;

    public SQLiteDatabase sqLiteDatabase;
    // 20 fields
    // Database creation sql statement
    private static final String WALLET_CREATE = "CREATE TABLE "
            + TABLE_WALLET + "("
            + COLUMN_WALLET_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_WALLET_NAME + " text not null, "
            + COLUMN_WALLET_AMOUNT + " real not null, "
            + COLUMN_WALLET_CURRENCY + " text not null, "
            + COLUMN_WALLET_IMG + " text not null "
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
            + COLUMN_CATE_ID + " INTEGER PRIMARY KEY, "
            + COLUMN_CATE_NAME + " text not null, "
            + COLUMN_CATE_IMG + " text not null, "
            + COLUMN_CATE_TYPE + " integer not null"
            + ");";

    private static final String TRANS_CREATE = "CREATE TABLE "
            + TABLE_TRANSACTION + "("
            + COLUMN_TRANS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_TRANS_AMOUNT + " float not null,"
            + COLUMN_TRANS_CREATED_DATE + " DATETIME DEFAULT CURRENT_TIMESTAMP,"
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

    private static final String BUDGET_CREATE = "CREATE TABLE "
            + TABLE_BUDGET + "("
            + COLUMN_BUDGET_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_BUDGET_START_DATE + " DATETIME not null,"
            + COLUMN_BUDGET_END_DATE + " DATETIME not null,"
            + COLUMN_BUDGET_AMOUNT + " Float not null,"
            + COLUMN_BUDGET_CATE_ID + " integer not null,"
            + COLUMN_BUDGET_WALLET_ID + " integer not null,"
            + COLUMN_BUDGET_RECURRING_NOTIFY + " INTEGER DEFAULT 0,"
            + COLUMN_BUDGET_IS_REAPEAT + " INTEGER DEFAULT 0"
            + ");";

    private static final String SAVING_CREATE = "CREATE TABLE "
            + TABLE_SAVING + "("
            + COLUMN_SAVING_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_SAVING_TITLE + " text not null,"
            + COLUMN_SAVING_START_DATE + " DATETIME not null,"
            + COLUMN_SAVING_END_DATE + " DATETIME not null,"
            + COLUMN_SAVING_AMOUNT + " Float not null,"
            + COLUMN_SAVING_AMOUNT_REAL + " integer not null,"
            + COLUMN_SAVING_WALLET_ID + " integer not null"
            + ");";

    private static final String SAVINGT_CREATE = "CREATE TABLE "
            + TABLE_SAVINGT + "("
            + COLUMN_SAVINGT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_SAVINGT_TYPE + " integer not null,"
            + COLUMN_SAVINGT_CREATE_DATE + "  DATETIME DEFAULT CURRENT_TIMESTAMP,"
            + COLUMN_SAVINGT_AMOUNT + " Float not null,"
            + COLUMN_SAVINGT_SAVING_ID + " integer not null"
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
        database.execSQL(BUDGET_CREATE);
        database.execSQL(SAVING_CREATE);
        database.execSQL(SAVINGT_CREATE);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        switch (oldVersion){
            case 1:
                db.execSQL(SAVING_CREATE);
                db.execSQL(SAVINGT_CREATE);
                break;
            case 2:
                onCreate(db);
                break;
        }

    }

}

