package com.alanddev.manwal.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.alanddev.manwal.R;
import com.alanddev.manwal.helper.IDataSource;
import com.alanddev.manwal.helper.MwSQLiteHelper;
import com.alanddev.manwal.model.Budget;
import com.alanddev.manwal.model.Category;
import com.alanddev.manwal.model.Model;
import com.alanddev.manwal.model.TransactionDetail;
import com.alanddev.manwal.model.Transactions;
import com.alanddev.manwal.util.Constant;
import com.alanddev.manwal.util.Utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by ANLD on 23/11/2015.
 */
public class BudgetController implements IDataSource {
    private SQLiteDatabase database;
    private MwSQLiteHelper dbHelper;
    private Context mContext;

    private String [] allColumns = {
            MwSQLiteHelper.COLUMN_BUDGET_ID,
            MwSQLiteHelper.COLUMN_BUDGET_START_DATE,
            MwSQLiteHelper.COLUMN_BUDGET_END_DATE,
            MwSQLiteHelper.COLUMN_BUDGET_AMOUNT,
            MwSQLiteHelper.COLUMN_BUDGET_CATE_ID,
            MwSQLiteHelper.COLUMN_BUDGET_WALLET_ID,
            MwSQLiteHelper.COLUMN_BUDGET_RECURRING_NOTIFY,
            MwSQLiteHelper.COLUMN_BUDGET_IS_REAPEAT
    };

    public BudgetController(Context context){
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
        Budget budget  = (Budget)data;
        values.put(MwSQLiteHelper.COLUMN_BUDGET_START_DATE, budget.getStartdate());
        values.put(MwSQLiteHelper.COLUMN_BUDGET_END_DATE, budget.getEnddate());
        values.put(MwSQLiteHelper.COLUMN_BUDGET_AMOUNT, budget.getAmount());
        values.put(MwSQLiteHelper.COLUMN_BUDGET_CATE_ID, budget.getCate_id());
        values.put(MwSQLiteHelper.COLUMN_BUDGET_WALLET_ID, budget.getWallet_id());
        values.put(MwSQLiteHelper.COLUMN_BUDGET_RECURRING_NOTIFY, budget.getRecurring_notify());
        values.put(MwSQLiteHelper.COLUMN_BUDGET_IS_REAPEAT, budget.getIs_repeat());
        database.insert(MwSQLiteHelper.TABLE_BUDGET, null,
                values);
        return budget;
    }

    @Override
    public void update(Model data) {
        ContentValues values = new ContentValues();
        Budget budget = (Budget) data;
        values.put(MwSQLiteHelper.COLUMN_BUDGET_START_DATE, budget.getStartdate());
        values.put(MwSQLiteHelper.COLUMN_BUDGET_END_DATE, budget.getEnddate());
        values.put(MwSQLiteHelper.COLUMN_BUDGET_AMOUNT, budget.getAmount());
        values.put(MwSQLiteHelper.COLUMN_BUDGET_CATE_ID, budget.getCate_id());
        //values.put(MwSQLiteHelper.COLUMN_BUDGET_WALLET_ID, budget.getWallet_id());
        values.put(MwSQLiteHelper.COLUMN_BUDGET_RECURRING_NOTIFY, budget.getRecurring_notify());
        values.put(MwSQLiteHelper.COLUMN_BUDGET_IS_REAPEAT, budget.getIs_repeat());
        database.update(MwSQLiteHelper.TABLE_BUDGET, values, MwSQLiteHelper.COLUMN_BUDGET_ID + " = ?", new String[]{budget.getId() + ""});
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

    public Boolean delete(long budgetId){
        return database.delete(MwSQLiteHelper.TABLE_BUDGET, MwSQLiteHelper.COLUMN_BUDGET_ID + "=" + budgetId, null) > 0;
    }

    public Budget getBudgetById(long id){
        StringBuffer sql = new StringBuffer("SELECT * FROM ").append(MwSQLiteHelper.TABLE_BUDGET).append(" s inner join ")
                .append(MwSQLiteHelper.TABLE_CATEGORY).append(" c ON s.").append(MwSQLiteHelper.COLUMN_BUDGET_CATE_ID).append(" = c.")
                .append(MwSQLiteHelper.COLUMN_CATE_ID)
                .append(" WHERE s.").append(MwSQLiteHelper.COLUMN_BUDGET_WALLET_ID).append(" = ").append(Utils.getWallet_id())
                .append(" AND s.").append(MwSQLiteHelper.COLUMN_BUDGET_ID).append(" = ?");
        String[] atts = new String[]{id+""};
        Cursor cursor = database.rawQuery(sql.toString(), atts);
        cursor.moveToFirst();
        Budget budget = (Budget) cursorTo(cursor);
        cursor.close();
        return budget;
    }

    public List<Budget> getAll(int type){
        List<Budget> budgets = new ArrayList<Budget>();
        StringBuffer sql;
        if(type==0) {
            sql = new StringBuffer("SELECT * FROM ").append(MwSQLiteHelper.TABLE_BUDGET).append(" s inner join ")
                    .append(MwSQLiteHelper.TABLE_CATEGORY).append(" c ON s.").append(MwSQLiteHelper.COLUMN_BUDGET_CATE_ID).append(" = c.")
                    .append(MwSQLiteHelper.COLUMN_CATE_ID)
                    .append(" WHERE s.").append(MwSQLiteHelper.COLUMN_BUDGET_WALLET_ID).append(" = ").append(Utils.getWallet_id())
                    .append(" AND s.").append(MwSQLiteHelper.COLUMN_BUDGET_END_DATE).append(" >= ?");
        }else{
            sql = new StringBuffer("SELECT * FROM ").append(MwSQLiteHelper.TABLE_BUDGET).append(" s inner join ")
                    .append(MwSQLiteHelper.TABLE_CATEGORY).append(" c ON s.").append(MwSQLiteHelper.COLUMN_BUDGET_CATE_ID).append(" = c.")
                    .append(MwSQLiteHelper.COLUMN_CATE_ID)
                    .append(" WHERE s.").append(MwSQLiteHelper.COLUMN_BUDGET_WALLET_ID).append(" = ").append(Utils.getWallet_id())
                    .append(" AND s.").append(MwSQLiteHelper.COLUMN_BUDGET_END_DATE).append(" < ?");
        }
        String strDate = Utils.changeDate2Str(new Date(), Constant.DATE_FORMAT_DB);
        String[] atts = new String[]{strDate};
        Cursor cursor = database.rawQuery(sql.toString(), atts);
        cursor.moveToFirst();
        TransactionController transController = new TransactionController(mContext);
        transController.open();
        while (!cursor.isAfterLast()) {
            Budget budget = (Budget)cursorTo(cursor);
            budget.setRealamt(transController.getRealAmount(budget));
            budgets.add(budget);
            cursor.moveToNext();
        }
        transController.close();
        return budgets;
    }

    @Override
    public Model cursorTo(Cursor cursor) {
        Budget budget = new Budget();
        budget.setId(cursor.getInt(0));
        budget.setStartdate(cursor.getString(1));
        budget.setEnddate(cursor.getString(2));
        budget.setAmount(cursor.getFloat(3));
        budget.setCate_id(cursor.getInt(4));
        budget.setWallet_id(cursor.getInt(5));
        budget.setRecurring_notify(cursor.getInt(6));
        budget.setIs_repeat(cursor.getInt(7));
        budget.setCate_name(cursor.getString(9));
        budget.setCate_img(cursor.getString(10));
        return budget;
    }


}
