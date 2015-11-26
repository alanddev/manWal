package com.alanddev.manwal.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.alanddev.manwal.helper.IDataSource;
import com.alanddev.manwal.helper.MwSQLiteHelper;
import com.alanddev.manwal.model.Category;
import com.alanddev.manwal.model.Model;
import com.alanddev.manwal.model.TransactionDay;
import com.alanddev.manwal.model.TransactionDetail;
import com.alanddev.manwal.model.Transactions;
import com.alanddev.manwal.util.Constant;
import com.alanddev.manwal.util.Utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by ANLD on 24/11/2015.
 */
public class TransactionController implements IDataSource {
    private SQLiteDatabase database;
    private MwSQLiteHelper dbHelper;
    private Context mContext;

    public TransactionController(Context context){
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
    public List<Model> getAll(String query) {
        List<Model> trans = new ArrayList<Model>();
        Cursor cursor = database.query(MwSQLiteHelper.TABLE_TRANSACTION,
                allColumns, query, null,
                null, null, null);
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
        trans.setCate_name(cursor.getString(14));
        trans.setCate_img(cursor.getString(15));
        trans.setCate_type(cursor.getInt(16));

        return trans;
    }

    public List<Transactions> getAll(int viewtype){
        List<Transactions> transactionses = new ArrayList<Transactions>();
        switch (viewtype){
            case Constant.VIEW_TYPE_DAY:
                transactionses = getTransbyDay();
                break;

        }
        return transactionses;
    }

    public List<Transactions> getTransbyDay(){
        List<Transactions> transactionses = new ArrayList<Transactions>();
        for(int i=0;i<7;i++){
            List<TransactionDay> transactionDays = new ArrayList<TransactionDay>();
            TransactionDay transactionDay = new TransactionDay();
            Transactions transactions = new Transactions();
            Float examount = Float.valueOf(0);
            Float inamount = Float.valueOf(0);
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DATE, -i);
            String strDate1 = Utils.changeDate2Str(cal.getTime());
            cal.add(Calendar.DATE, -1);
            String strDate2 = Utils.changeDate2Str(cal.getTime());
            StringBuffer sql = new StringBuffer("SELECT * FROM ").append(MwSQLiteHelper.TABLE_TRANSACTION).append(" s inner join ")
                    .append(MwSQLiteHelper.TABLE_CATEGORY).append(" c ON s.").append(MwSQLiteHelper.COLUMN_TRANS_CATE_ID).append(" = c.")
                    .append(MwSQLiteHelper.COLUMN_CATE_ID)
                    .append(" WHERE s.").append(MwSQLiteHelper.COLUMN_TRANS_WALLET_ID).append(" = ?")
                    .append(" AND (s.").append(MwSQLiteHelper.COLUMN_TRANS_DISPLAY_DATE).append(" BETWEEN ").append("Datetime(?) AND (?))");

            String[] atts = new String[]{Utils.getWallet_id()+"",strDate2,strDate1};
            Cursor cursor = database.rawQuery(sql.toString(), atts);
            cursor.moveToFirst();
            List<TransactionDetail> trans = new ArrayList<TransactionDetail>();
            while (!cursor.isAfterLast()) {
                TransactionDetail tran = (TransactionDetail)cursorTo(cursor);
                trans.add(tran);
                cursor.moveToNext();
            }
            transactionDay.setItems(trans);

            for(int j=0;j<trans.size();j++){
                TransactionDetail transactionDetail = trans.get(j);
                if(transactionDetail.getCate_type()== Constant.EXPENSE_TYPE){
                    examount = examount-transactionDetail.getAmountt();
                }else{
                    inamount = inamount+transactionDetail.getAmountt();
                }
            }
            transactionDay.setExamount(examount);
            transactionDay.setInamount(inamount);
            transactionDay.setNetamount(examount + inamount);
            cal.add(Calendar.DATE, 1);
            transactionDay.setDisplay_date(cal.getTime());
            transactionDays.add(transactionDay);

            //set transactions
            transactions.setInamount(inamount);
            transactions.setExamount(examount);
            transactions.setNetamount(inamount+examount);
            transactions.setItems(transactionDays);
            transactions.setTitle(Utils.getDayView(mContext, Utils.changeDate2Date(cal.getTime(),Constant.DATE_FORMAT_PICKER)));
            transactionses.add(transactions);
        }
        return transactionses;
    }

    public List<Transactions> getTransbyWeek(){
        List<Transactions> transactionses = new ArrayList<Transactions>();
        for(int i=1;i<=4;i++){
            Transactions transactions = new Transactions();
            Float examount = Float.valueOf(0);
            Float inamount = Float.valueOf(0);
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DATE, -i);
            String strDate = Utils.changeDate2Str(cal.getTime());
            StringBuffer sql = new StringBuffer("SELECT * FROM ").append(MwSQLiteHelper.TABLE_TRANSACTION)
                    .append(" WHERE ").append(MwSQLiteHelper.COLUMN_WALLET_ID).append(" = ").append(Utils.getWallet_id())
                    .append(" AND ").append(MwSQLiteHelper.COLUMN_TRANS_DISPLAY_DATE).append(" = ").append(strDate);
            List<Model> models = getAll(sql.toString());
            for(int j=0;j<models.size();j++){
                TransactionDetail transactionDetail = (TransactionDetail)models.get(j);
                if(transactionDetail.getCate_type()== Constant.EXPENSE_TYPE){
                    examount = examount-transactionDetail.getAmountt();
                }else{
                    inamount = inamount+transactionDetail.getAmountt();
                }
                //transactions.getItems().add(transactionDetail);
            }
           /* transactions.setExamount(examount);
            transactions.setInamount(inamount);
            transactions.setDisplay_date(cal.getTime());*/
            transactionses.add(transactions);
        }
        return transactionses;
    }
}
