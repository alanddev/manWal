package com.alanddev.manwal.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.renderscript.Sampler;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import com.alanddev.manwal.R;
import com.alanddev.manwal.helper.IDataSource;
import com.alanddev.manwal.helper.MwSQLiteHelper;
import com.alanddev.manwal.model.Budget;
import com.alanddev.manwal.model.Category;
import com.alanddev.manwal.model.Model;
import com.alanddev.manwal.model.TransactionDay;
import com.alanddev.manwal.model.TransactionDetail;
import com.alanddev.manwal.model.TransactionSum;
import com.alanddev.manwal.model.Transactions;
import com.alanddev.manwal.model.Trend;
import com.alanddev.manwal.util.Constant;
import com.alanddev.manwal.util.Utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by ANLD on 24/11/2015.
 */
public class TransactionController implements IDataSource {
    private SQLiteDatabase database;
    private MwSQLiteHelper dbHelper;
    private Context mContext;

    public TransactionController(Context context) {
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

    private String[] allColumns = {
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
        TransactionDetail trans = (TransactionDetail) data;
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
        ContentValues values = new ContentValues();
        TransactionDetail trans = (TransactionDetail) data;
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
        database.update(MwSQLiteHelper.TABLE_TRANSACTION, values, MwSQLiteHelper.COLUMN_TRANS_ID + " = ?", new String[]{String.valueOf(trans.getId())});
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
            TransactionDetail tran = (TransactionDetail) cursorTo(cursor);
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
            TransactionDetail tran = (TransactionDetail) cursorTo(cursor);
            trans.add(tran);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return trans;
    }

    @Override
    public Model get(String query) {
        Cursor cursor = database.query(MwSQLiteHelper.TABLE_TRANSACTION,
                allColumns, query, null,
                null, null, null);
        cursor.moveToFirst();
        TransactionDetail transactionDetail = (TransactionDetail) cursorTo(cursor);
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

    public List<Transactions> getAll(int viewtype) {
        List<Transactions> transactionses = new ArrayList<Transactions>();
        switch (viewtype) {
            case Constant.VIEW_TYPE_DAY:
                transactionses = getTransbyDay();
                break;
            case Constant.VIEW_TYPE_WEEK:
                transactionses = getTransbyWeek();
                break;
            case Constant.VIEW_TYPE_MONTH:
                transactionses = getTransbyMonth();
                break;
            case Constant.VIEW_TYPE_YEAR:
                transactionses = getTransbyYear();
                break;

        }
        return transactionses;
    }

    public List<Transactions> getTransbyDay() {
        List<Transactions> transactionses = new ArrayList<Transactions>();
        for (int i = 6; i >= 0; i--) {
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
                    .append(" AND (s.").append(MwSQLiteHelper.COLUMN_TRANS_DISPLAY_DATE).append(" BETWEEN ").append("Datetime(?) AND Datetime(?))");
            String[] atts = new String[]{Utils.getWallet_id() + "", strDate2, strDate1};
            Cursor cursor = database.rawQuery(sql.toString(), atts);
            cursor.moveToFirst();
            List<TransactionDetail> trans = new ArrayList<TransactionDetail>();
            while (!cursor.isAfterLast()) {
                TransactionDetail tran = (TransactionDetail) cursorTo(cursor);
                trans.add(tran);
                cursor.moveToNext();
            }
            cursor.close();
            transactionDay.setItems(trans);

            for (int j = 0; j < trans.size(); j++) {
                TransactionDetail transactionDetail = trans.get(j);
                if (transactionDetail.getCate_type() == Constant.EXPENSE_TYPE) {
                    examount = examount - transactionDetail.getAmountt();
                } else {
                    inamount = inamount + transactionDetail.getAmountt();
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
            transactions.setNetamount(inamount + examount);
            transactions.setItems(transactionDays);
            transactions.setTitle(Utils.getDayView(mContext, Utils.changeDate2Date(cal.getTime(), Constant.DATE_FORMAT_PICKER)));
            transactionses.add(transactions);
        }
        transactionses.add(getFutureTrans(getDisplayDateFuture()));
        return transactionses;
    }

    public List<Transactions> getTransbyWeek() {
        List<Transactions> transactionses = new ArrayList<Transactions>();
        for (int i = 4; i >= 0; i--) {
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.WEEK_OF_YEAR, -i);
            int dayofweek = calendar.get(Calendar.DAY_OF_WEEK);
            String startWeek = "";
            String endWeek = "";
            if (i == 0) {
                endWeek = Utils.changeDate2Str(calendar.getTime());
                if (dayofweek == 1) {
                    calendar.add(Calendar.DATE, -7);
                } else {
                    calendar.add(Calendar.DATE, -dayofweek + 1);
                }
                startWeek = Utils.changeDate2Str(calendar.getTime());
            } else {
                if (dayofweek == 1) {
                    endWeek = Utils.changeDate2Str(calendar.getTime());
                } else {
                    calendar.add(Calendar.DATE, -dayofweek + 8);
                    endWeek = Utils.changeDate2Str(calendar.getTime());
                }
                calendar.add(Calendar.DATE, -7);
                startWeek = Utils.changeDate2Str(calendar.getTime());
            }

            List<String> listDt = getDateTrans(startWeek, endWeek);
            Transactions transactions;
            if (listDt.size() >= 0) {
                transactions = getFutureTrans(listDt);
            } else {
                transactions = new Transactions();
            }

            if (i == 0) {
                transactions.setTitle(mContext.getString(R.string.onweek));
            } else if (i == 1) {
                transactions.setTitle(mContext.getString(R.string.preweek));
            } else {
                transactions.setTitle(Utils.changeDateStr2Str2(startWeek) + " - " + Utils.changeDateStr2Str2(endWeek));
            }

            transactionses.add(transactions);
        }
        transactionses.add(getFutureTrans(getDisplayDateFuture()));
        return transactionses;
    }

    public List<Transactions> getTransbyMonth() {
        List<Transactions> transactionses = new ArrayList<Transactions>();
        for(int i=11;i>=0;i--){
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.MONTH, -i);
            int dayofmonth = calendar.get(Calendar.DAY_OF_MONTH);
            String startMonth = "";
            String endMonth = "";
            if(i==0){
                endMonth=Utils.changeDate2Str(calendar.getTime());
                calendar.add(Calendar.DATE,-dayofmonth);
                startMonth=Utils.changeDate2Str(calendar.getTime());
            }else{
                calendar.add(Calendar.DATE,-dayofmonth);
                startMonth=Utils.changeDate2Str(calendar.getTime());
                calendar.add(Calendar.MONTH,1);
                endMonth=Utils.changeDate2Str(calendar.getTime());
            }
            List<String> listDt = getDateTrans(startMonth, endMonth);
            Transactions transactions;
            if (listDt.size() >= 0) {
                transactions = getFutureTrans(listDt);
            } else {
                transactions = new Transactions();
            }
            if (i == 0) {
                transactions.setTitle(mContext.getString(R.string.onmonth));
            } else if (i == 1) {
                transactions.setTitle(mContext.getString(R.string.premonth));
            } else {
                transactions.setTitle(calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Utils.getLocale())+" "+calendar.get(Calendar.YEAR));
            }
            transactionses.add(transactions);
        }
        transactionses.add(getFutureTrans(getDisplayDateFuture()));
        return transactionses;
    }

    public List<Transactions> getTransbyYear() {
        List<Transactions> transactionses = new ArrayList<Transactions>();
        for(int i=3;i>=0;i--){
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.YEAR,-i);
            int year = calendar.get(Calendar.YEAR);
            List<Integer> lstMonthTrans = getMonthTrans(year);
            Transactions transactions = getTransactionaYear(lstMonthTrans, year);
            transactionses.add(transactions);
        }
        transactionses.add(getFutureTrans(getDisplayDateFuture()));
        return transactionses;
    }

    private List<String> getDisplayDateFuture() {
        Calendar cal = Calendar.getInstance();
        String strDate1 = Utils.changeDate2Str(cal.getTime());
        StringBuffer sql = new StringBuffer("SELECT distinct ").append(MwSQLiteHelper.COLUMN_TRANS_DISPLAY_DATE).append(" FROM ")
                .append(MwSQLiteHelper.TABLE_TRANSACTION)
                .append(" WHERE ").append(MwSQLiteHelper.COLUMN_TRANS_WALLET_ID).append(" = ").append(Utils.getWallet_id())
                .append(" AND ").append(MwSQLiteHelper.COLUMN_TRANS_DISPLAY_DATE).append(" >= ").append("Datetime(?)");
        String[] atts = new String[]{strDate1};
        Cursor cursor = database.rawQuery(sql.toString(), atts);
        cursor.moveToFirst();
        List<String> lstDisplayDate = new ArrayList<String>();
        while (!cursor.isAfterLast()) {
            String strDisplayDate = cursor.getString(0);
            lstDisplayDate.add(strDisplayDate);
            cursor.moveToNext();
        }
        cursor.close();
        return lstDisplayDate;
    }

    private List<String> getDateTrans(String startDt, String endDate) {
        StringBuffer sql = new StringBuffer("SELECT distinct ").append(MwSQLiteHelper.COLUMN_TRANS_DISPLAY_DATE).append(" FROM ")
                .append(MwSQLiteHelper.TABLE_TRANSACTION)
                .append(" WHERE ").append(MwSQLiteHelper.COLUMN_TRANS_WALLET_ID).append(" = ").append(Utils.getWallet_id())
                .append(" AND (").append(MwSQLiteHelper.COLUMN_TRANS_DISPLAY_DATE).append(" BETWEEN ").append("Datetime(?) AND Datetime(?))")
                .append(" ORDER BY ").append(MwSQLiteHelper.COLUMN_TRANS_DISPLAY_DATE).append(" DESC");
        String[] atts = new String[]{startDt, endDate};
        Cursor cursor = database.rawQuery(sql.toString(), atts);
        cursor.moveToFirst();
        List<String> lstDisplayDate = new ArrayList<String>();
        while (!cursor.isAfterLast()) {
            String strDisplayDate = cursor.getString(0);
            lstDisplayDate.add(strDisplayDate);
            cursor.moveToNext();
        }
        cursor.close();
        return lstDisplayDate;
    }


    private Transactions getFutureTrans(List<String> lstDisplayDt) {
        Transactions transactions = new Transactions();
        List<TransactionDay> transactionDays = new ArrayList<TransactionDay>();
        Float ttexamount = Float.valueOf(0);
        Float ttinamount = Float.valueOf(0);
        for (int i = 0; i < lstDisplayDt.size(); i++) {
            TransactionDay transactionDay = new TransactionDay();
            Float examount = Float.valueOf(0);
            Float inamount = Float.valueOf(0);
            String strDisplayDt = lstDisplayDt.get(i);
            StringBuffer sql = new StringBuffer("SELECT * FROM ").append(MwSQLiteHelper.TABLE_TRANSACTION).append(" s inner join ")
                    .append(MwSQLiteHelper.TABLE_CATEGORY).append(" c ON s.").append(MwSQLiteHelper.COLUMN_TRANS_CATE_ID).append(" = c.")
                    .append(MwSQLiteHelper.COLUMN_CATE_ID)
                    .append(" WHERE s.").append(MwSQLiteHelper.COLUMN_TRANS_WALLET_ID).append(" = ?")
                    .append(" AND (s.").append(MwSQLiteHelper.COLUMN_TRANS_DISPLAY_DATE).append(" BETWEEN ").append("Datetime(?) AND (?))");
            Date dipslayDt = Utils.changeStr2Date(strDisplayDt, Constant.DATE_FORMAT_DB);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(dipslayDt);
            calendar.add(Calendar.DATE, -1);
            String strDate2 = Utils.changeDate2Str(calendar.getTime());
            String[] atts = new String[]{Utils.getWallet_id() + "", strDate2, strDisplayDt};
            Cursor cursor = database.rawQuery(sql.toString(), atts);
            cursor.moveToFirst();
            List<TransactionDetail> trans = new ArrayList<TransactionDetail>();
            while (!cursor.isAfterLast()) {
                TransactionDetail tran = (TransactionDetail) cursorTo(cursor);
                trans.add(tran);
                cursor.moveToNext();
            }
            cursor.close();
            transactionDay.setItems(trans);

            for (int j = 0; j < trans.size(); j++) {
                TransactionDetail transactionDetail = trans.get(j);
                if (transactionDetail.getCate_type() == Constant.EXPENSE_TYPE) {
                    examount = examount - transactionDetail.getAmountt();
                } else {
                    inamount = inamount + transactionDetail.getAmountt();
                }
            }
            transactionDay.setExamount(examount);
            transactionDay.setInamount(inamount);
            transactionDay.setNetamount(examount + inamount);
            transactionDay.setDisplay_date(dipslayDt);
            transactionDays.add(transactionDay);
            ttexamount = ttexamount - examount;
            ttinamount = ttinamount + inamount;
        }
        transactions.setItems(transactionDays);
        transactions.setNetamount(ttinamount - ttexamount);
        transactions.setExamount(ttexamount);
        transactions.setInamount(ttinamount);
        transactions.setTitle(mContext.getResources().getString(R.string.future));
        return transactions;
    }

    private Transactions getTransactionaYear(List<Integer> months,int year){
        List<TransactionDay> transactionDays = new ArrayList<TransactionDay>();
        Transactions transactions = new Transactions();
        Float texamount = Float.valueOf(0);
        Float tinamount = Float.valueOf(0);
        for(int i=0;i<months.size();i++){
            int month = months.get(i);
            Calendar calendar = Calendar.getInstance();
            String startDt="";
            String endDt="";
            if(month==calendar.get(Calendar.MONTH)&&year==calendar.get(Calendar.YEAR)){
                endDt=Utils.changeDate2Str(calendar.getTime());
                int date = calendar.get(Calendar.DATE);
                calendar.add(Calendar.DATE,-date);
                startDt=Utils.changeDate2Str(calendar.getTime());
            }else{
                calendar.set(year,month,0);
                startDt=Utils.changeDate2Str(calendar.getTime());
                calendar.set(year, month, 1);
                int numdayofmonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
                calendar.add(Calendar.DATE,numdayofmonth-1);
                endDt=Utils.changeDate2Str(calendar.getTime());
            }
            //Log.d("AAAAAAA",startDt+" "+endDt+" "+month+" "+calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
            StringBuffer sql = new StringBuffer("SELECT * FROM ").append(MwSQLiteHelper.TABLE_TRANSACTION).append(" s inner join ")
                    .append(MwSQLiteHelper.TABLE_CATEGORY).append(" c ON s.").append(MwSQLiteHelper.COLUMN_TRANS_CATE_ID).append(" = c.")
                    .append(MwSQLiteHelper.COLUMN_CATE_ID)
                    .append(" WHERE s.").append(MwSQLiteHelper.COLUMN_TRANS_WALLET_ID).append(" = ?")
                    .append(" AND (s.").append(MwSQLiteHelper.COLUMN_TRANS_DISPLAY_DATE).append(" BETWEEN ").append("Datetime(?) AND (?))");

            String[] atts = new String[]{Utils.getWallet_id() + "", startDt, endDt};
            Cursor cursor = database.rawQuery(sql.toString(), atts);
            cursor.moveToFirst();
            List<TransactionDetail> trans = new ArrayList<TransactionDetail>();
            Float examount = Float.valueOf(0);
            Float inamount = Float.valueOf(0);
            while (!cursor.isAfterLast()) {
                TransactionDetail tran = (TransactionDetail) cursorTo(cursor);
                if (tran.getCate_type() == Constant.EXPENSE_TYPE) {
                    examount = examount - tran.getAmountt();
                } else {
                    inamount = inamount + tran.getAmountt();
                }
                trans.add(tran);
                cursor.moveToNext();
            }
            cursor.close();
            TransactionDay transactionDay = new TransactionDay();
            transactionDay.setItems(trans);
            transactionDay.setExamount(examount);
            transactionDay.setInamount(inamount);
            transactionDay.setNetamount(examount + inamount);
            calendar=Calendar.getInstance();
            calendar.set(Calendar.MONTH, month);
            transactionDay.setDisplayStr(calendar.getDisplayName(Calendar.MONTH, Calendar.SHORT, Utils.getLocale()));
            texamount = texamount + examount;
            tinamount = tinamount + inamount;
            transactionDays.add(transactionDay);

        }
        transactions.setItems(transactionDays);
        transactions.setExamount(texamount);
        transactions.setInamount(tinamount);
        transactions.setNetamount(tinamount - texamount);
        transactions.setTitle(year + "");
        return transactions;
    }

    private List<Integer> getMonthTrans(int year){
        List<Integer> lstMonth = new ArrayList<Integer>();
        Calendar calendar = Calendar.getInstance();
        String startDt="";
        String endDt="";
        int nyear = calendar.get(Calendar.YEAR);
        if(year==nyear){
            endDt = Utils.changeDate2Str(calendar.getTime());
        }else{
            endDt = year+"-12-31";
        }
        startDt = year-1 + "-12-31";

        StringBuffer sql = new StringBuffer("SELECT distinct ").append(MwSQLiteHelper.COLUMN_TRANS_DISPLAY_DATE).append(" FROM ")
                .append(MwSQLiteHelper.TABLE_TRANSACTION)
                .append(" WHERE ").append(MwSQLiteHelper.COLUMN_TRANS_WALLET_ID).append(" = ").append(Utils.getWallet_id())
                .append(" AND (").append(MwSQLiteHelper.COLUMN_TRANS_DISPLAY_DATE).append(" BETWEEN ").append("Datetime(?) AND Datetime(?))")
                .append(" ORDER BY ").append(MwSQLiteHelper.COLUMN_TRANS_DISPLAY_DATE).append(" DESC");
        String[] atts = new String[]{startDt, endDt};
        Cursor cursor = database.rawQuery(sql.toString(), atts);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            String strDisplayDate = cursor.getString(0);
            Date date = Utils.changeStr2Date(strDisplayDate, Constant.DATE_FORMAT_DB);
            calendar.setTime(date);
            int month = calendar.get(Calendar.MONTH);
            if(!lstMonth.contains(month)){
                lstMonth.add(month);
            }
            cursor.moveToNext();
        }
        cursor.close();
        return lstMonth;
    }



    public void createTransactionDefault(Context context, float amount,int catId, String note){
        TransactionDetail transaction = new TransactionDetail();
        transaction.setAmountt(amount);
        transaction.setNote(note);
        transaction.setCat_id(catId);
        String date =  new SimpleDateFormat("dd-MM-yyyy").format(new Date());
        transaction.setDisplay_date(Utils.getDatefromDayView(context, date));
        transaction.setWallet_id(Utils.getWallet_id());
        create(transaction);
        //close();
    }





    public float getAmountByWalletCategory(int id,int type){
        //StringBuffer sql = new StringBuffer("SELECT SUM (")
        String date =  new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        StringBuffer sql = new StringBuffer("SELECT SUM( t." +dbHelper.COLUMN_TRANS_AMOUNT +") FROM " + dbHelper.TABLE_TRANSACTION +" as t JOIN " +
                dbHelper.TABLE_CATEGORY + " as c ON t."+dbHelper.COLUMN_TRANS_CATE_ID +"=c."+dbHelper.COLUMN_CATE_ID + " where t."
                + dbHelper.COLUMN_TRANS_WALLET_ID +" = "  + id + " and t." + dbHelper.COLUMN_TRANS_DISPLAY_DATE +"<='" + date +
                "' and c." + dbHelper.COLUMN_CATE_TYPE +"=" + type );

        Cursor cursor = database.rawQuery(sql.toString(), null);
        float returnAmount =0;
        if(cursor.moveToFirst()) {
            returnAmount =  cursor.getInt(0);
        }
        cursor.close();
        return returnAmount;

    }


    public float getAmountByWallet(int id){
        float amountExpense = getAmountByWalletCategory(id, Constant.EXPENSE_TYPE);
        float amountIncome = getAmountByWalletCategory(id, Constant.INCOME_TYPE);
        float amount = amountIncome - amountExpense;
        return amount;
    }


    public ArrayList<TransactionSum> getAmountCategoryTypeByDate(int type, int walletId, Date dateReport){
        String date =  new SimpleDateFormat("yyyy-MM-dd").format(dateReport);
        StringBuffer sql = new StringBuffer("SELECT SUM( t." +dbHelper.COLUMN_TRANS_AMOUNT +")" + ",c."+ dbHelper.COLUMN_CATE_ID +
                ",c."+ dbHelper.COLUMN_CATE_NAME + ",c."+ dbHelper.COLUMN_CATE_IMG +
                " FROM " + dbHelper.TABLE_TRANSACTION +" as t JOIN " +
                dbHelper.TABLE_CATEGORY + " as c ON t."+dbHelper.COLUMN_TRANS_CATE_ID +"=c."+dbHelper.COLUMN_CATE_ID + " where t."
                + dbHelper.COLUMN_TRANS_WALLET_ID +" = "  + walletId  +
                " and t." + dbHelper.COLUMN_TRANS_DISPLAY_DATE + "=='"+ date +"'"+
                " and c." + dbHelper.COLUMN_CATE_TYPE +"=" + type + " group by (c." + dbHelper.COLUMN_CATE_ID + ")" );

        Cursor cursor = database.rawQuery(sql.toString(), null);

        cursor.moveToFirst();
        ArrayList<TransactionSum> trans = new ArrayList<TransactionSum>();
        while (!cursor.isAfterLast()) {
            TransactionSum tran = new TransactionSum();
            tran.setAmount(cursor.getFloat(0));
            tran.setCatId(cursor.getInt(1));
            tran.setCatName(cursor.getString(2));
            tran.setCatImage(cursor.getString(3));
            tran.setType(type);
            trans.add(tran);
            cursor.moveToNext();
        }


        return trans;

    }


    public ArrayList<TransactionSum> getAmountCategoryTypeByDate(int type, int walletId, ArrayList<Date> dates){
        Date dateStart = dates.get(0);
        Date dateEnd = dates.get(1);
        String sDateStart =  new SimpleDateFormat("yyyy-MM-dd").format(dateStart);
        String sDateEnd =  new SimpleDateFormat("yyyy-MM-dd").format(dateEnd);

        StringBuffer sql = new StringBuffer("SELECT SUM( t." +dbHelper.COLUMN_TRANS_AMOUNT +")" + ",c."+ dbHelper.COLUMN_CATE_ID +
                ",c."+ dbHelper.COLUMN_CATE_NAME +  ",c."+ dbHelper.COLUMN_CATE_IMG +
                " FROM " + dbHelper.TABLE_TRANSACTION +" as t JOIN " +
                dbHelper.TABLE_CATEGORY + " as c ON t."+dbHelper.COLUMN_TRANS_CATE_ID +"=c."+dbHelper.COLUMN_CATE_ID + " where t."
                + dbHelper.COLUMN_TRANS_WALLET_ID +" = "  + walletId  +
                " and t." + dbHelper.COLUMN_TRANS_DISPLAY_DATE + " >= '"+ sDateStart +"'"+
                " and t." + dbHelper.COLUMN_TRANS_DISPLAY_DATE + " <= '"+ sDateEnd +"'"+
                " and c." + dbHelper.COLUMN_CATE_TYPE +"=" + type + " group by (c." + dbHelper.COLUMN_CATE_ID + ")" );

        Cursor cursor = database.rawQuery(sql.toString(), null);

        cursor.moveToFirst();
        ArrayList<TransactionSum> trans = new ArrayList<TransactionSum>();
        while (!cursor.isAfterLast()) {
            TransactionSum tran = new TransactionSum();
            tran.setAmount(cursor.getFloat(0));
            tran.setCatId(cursor.getInt(1));
            tran.setCatName(cursor.getString(2));
            tran.setCatImage(cursor.getString(3));
            tran.setType(type);
            trans.add(tran);
            cursor.moveToNext();
        }


        return trans;

    }


    public ArrayList<TransactionSum> getAmountCategoryTypeByWeek(int type, int walletId,Date date){
        ArrayList<TransactionSum> trans = new ArrayList<TransactionSum>();
        ArrayList<Date> dates = getDateOfWeek(date);
        trans = getAmountCategoryTypeByDate(type,walletId,dates);
        return trans;
    }


    public ArrayList<TransactionSum> getAmountCategoryTypeByMonth(int type, int walletId,Date date){
        ArrayList<TransactionSum> trans = new ArrayList<TransactionSum>();
        ArrayList<Date> dates = getDateOfMonth(date);
        trans = getAmountCategoryTypeByDate(type,walletId,dates);
        return trans;
    }


    public ArrayList<TransactionSum>getAmountCategoryTypeByMonths(int type,int walletId, int fromMonth,int toMonth,String year){
        ArrayList<TransactionSum> trans = new ArrayList<TransactionSum>();
        ArrayList<Date> dates = getDateOfMonths(fromMonth, toMonth, year);
        trans = getAmountCategoryTypeByDate(type,walletId,dates);
        return trans;
    }


    private ArrayList<Date> getDateOfWeek(Date date){
        //Date date = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK) - c.getFirstDayOfWeek();
        c.add(Calendar.DAY_OF_MONTH, -dayOfWeek);

        Date weekStart = c.getTime();
        // we do not need the same day a week after, that's why use 6, not 7
        c.add(Calendar.DAY_OF_MONTH, 6);
        Date weekEnd = c.getTime();
        ArrayList<Date>dates = new ArrayList<Date>();
        dates.add(weekStart);
        dates.add(weekEnd);
        return dates;
    }


    private ArrayList<Date>getDateOfMonth(Date date){
        Calendar c = Calendar.getInstance();   // this takes current date
        c.setTime(date);
        c.set(Calendar.DAY_OF_MONTH, 1);
        Date monthStart = c.getTime();

        //c.set(Calendar.DATE, 1);
        //c.add(Calendar.DATE, -1);
        c.set(Calendar.DATE, c.getActualMaximum(Calendar.DATE));
        Date monthEnd = c.getTime();
        ArrayList<Date>dates = new ArrayList<Date>();
        dates.add(monthStart);
        dates.add(monthEnd);
        //System.out.println(c.getTime());
        return dates;

    }


    private ArrayList<Date>getDateOfMonths(int fromMonth, int toMonth, String year){
        String beginDateOfMonth = year + "-" + fromMonth + "-01";
        Date dateStart = Utils.changeStr2Date(beginDateOfMonth, Constant.DATE_FORMAT_DB);

        String endDateOfMonth = year + "-" + toMonth + "-01";
        Date dateTo = Utils.changeStr2Date(endDateOfMonth, Constant.DATE_FORMAT_DB);
        Calendar c = Calendar.getInstance();   // this takes current date
        c.setTime(dateTo);
        c.set(Calendar.DAY_OF_MONTH, 1);
        c.set(Calendar.DATE, c.getActualMaximum(Calendar.DATE));
        Date dateEnd = c.getTime();

        ArrayList<Date>dates = new ArrayList<Date>();
        dates.add(dateStart);
        dates.add(dateEnd);
        //System.out.println(c.getTime());
        return dates;

    }


    public TransactionDetail getTransbyId(long id){
        StringBuffer sql = new StringBuffer("SELECT * FROM ").append(MwSQLiteHelper.TABLE_TRANSACTION).append(" s inner join ")
                .append(MwSQLiteHelper.TABLE_CATEGORY).append(" c ON s.").append(MwSQLiteHelper.COLUMN_TRANS_CATE_ID).append(" = c.")
                .append(MwSQLiteHelper.COLUMN_CATE_ID)
                .append(" WHERE s.").append(MwSQLiteHelper.COLUMN_TRANS_WALLET_ID).append(" = ").append(Utils.getWallet_id())
                .append(" AND s.").append(MwSQLiteHelper.COLUMN_TRANS_ID).append(" = ?");
        String[] atts = new String[]{id+""};
        Cursor cursor = database.rawQuery(sql.toString(), atts);
        cursor.moveToFirst();
        TransactionDetail tran = (TransactionDetail) cursorTo(cursor);
        cursor.close();
        return tran;
    }


    public float getAmountByMonth(int type, int walletId, int month, String year){
        String beginDateOfMonth = year + "-" + month + "-01";
        Date dateBeginOfMoth = Utils.changeStr2Date(beginDateOfMonth, Constant.DATE_FORMAT_DB);
        ArrayList<Date> dates =  getDateOfMonth(dateBeginOfMoth);
        Date dateStart = dates.get(0);
        Date dateEnd = dates.get(1);
        String sDateStart =  new SimpleDateFormat("yyyy-MM-dd").format(dateStart);
        String sDateEnd =  new SimpleDateFormat("yyyy-MM-dd").format(dateEnd);


        StringBuffer sql = new StringBuffer("SELECT SUM( t." +dbHelper.COLUMN_TRANS_AMOUNT +")" +
                " FROM " + dbHelper.TABLE_TRANSACTION +" as t JOIN " +
                dbHelper.TABLE_CATEGORY + " as c ON t."+dbHelper.COLUMN_TRANS_CATE_ID +"=c."+dbHelper.COLUMN_CATE_ID + " where t."
                + dbHelper.COLUMN_TRANS_WALLET_ID +" = "  + walletId  +
                " and t." + dbHelper.COLUMN_TRANS_DISPLAY_DATE + " >= '"+ sDateStart +"'"+
                " and t." + dbHelper.COLUMN_TRANS_DISPLAY_DATE + " <= '"+ sDateEnd +"'"+
                " and c." + dbHelper.COLUMN_CATE_TYPE +"=" + type + " group by (t." + dbHelper.COLUMN_WALLET_ID + ")" );

        Cursor cursor = database.rawQuery(sql.toString(), null);

        cursor.moveToFirst();
        float amount = 0;
        ArrayList<Trend> trans = new ArrayList<Trend>();
        while (!cursor.isAfterLast()) {
            amount = cursor.getFloat(0);
            cursor.moveToNext();
        }

        return amount;

    }


    public Trend getAmountTrendByMonth(int option, int walletId, int month, String year){
        Trend trend = new Trend();
        float amount = 0;
        float income = 0;
        float expense = 0;
        trend.setMonth(Integer.toString(month));
        trend.setYear(year);
        switch(option) {
            case Constant.TREND_TYPE_INCOME:
                amount = getAmountByMonth(Constant.INCOME_TYPE, walletId, month, year);
                trend.setAmount(amount);
                break;
            case Constant.TREND_TYPE_EXPENSE:
                amount = getAmountByMonth(Constant.EXPENSE_TYPE, walletId, month, year);
                trend.setAmount(amount);
                break;
            case Constant.TREND_TYPE_SUB:
                income = getAmountByMonth(Constant.INCOME_TYPE, walletId, month, year);
                expense = getAmountByMonth(Constant.EXPENSE_TYPE, walletId, month, year);
                trend.setAmountIn(income);
                trend.setAmountOut(expense);
                trend.setAmount(income - expense);
                break;
            case Constant.TREND_TYPE_BALANCE:
                income = getAmountByMonth(Constant.INCOME_TYPE, walletId, month, year);
                expense = getAmountByMonth(Constant.EXPENSE_TYPE, walletId, month, year);
                trend.setAmount(income - expense);
            default:
                break;
        }

        return trend;
    }


    public ArrayList<Trend> getAmountTrendByMonths(int option, int walletId, int monthBegin, int monthEnd, String year) {
        ArrayList<Trend>trends = new ArrayList<Trend>();
        for (int i = monthBegin; i<=monthEnd ; i++){
            Trend trend = getAmountTrendByMonth(option,walletId,i,year);
            trends.add(trend);
        }
        return trends;
    }


    public Boolean delete(long tranId){
        return database.delete(MwSQLiteHelper.TABLE_TRANSACTION, MwSQLiteHelper.COLUMN_TRANS_ID + "=" + tranId, null) > 0;
    }

    public Float getRealAmount(Budget budget){
        String startDt = budget.getStartdate();
        String endDt = budget.getEnddate();
        int cate_id = budget.getCate_id();
        CategoryController cateController = new CategoryController(mContext);
        cateController.open();
        Category category = cateController.getById(cate_id);
        cateController.close();
        StringBuffer sql;
        if(category.getType()!=Constant.ALL_CATEGORY_TYPE) {
            sql = new StringBuffer("SELECT SUM( " + dbHelper.COLUMN_TRANS_AMOUNT + ")" +
                    " FROM " + dbHelper.TABLE_TRANSACTION + " where "
                    + dbHelper.COLUMN_TRANS_WALLET_ID + " = " + budget.getWallet_id() +
                    " and " + dbHelper.COLUMN_TRANS_DISPLAY_DATE + " >= '" + startDt + "'" +
                    " and " + dbHelper.COLUMN_TRANS_DISPLAY_DATE + " <= '" + endDt + "'" +
                    " and " + dbHelper.COLUMN_TRANS_CATE_ID + "=" + cate_id + " group by (" + dbHelper.COLUMN_TRANS_CATE_ID + ")");
        }else{
            sql = new StringBuffer("SELECT SUM(s." + dbHelper.COLUMN_TRANS_AMOUNT + ")" +
                    " FROM " + dbHelper.TABLE_TRANSACTION +" s inner join "+MwSQLiteHelper.TABLE_CATEGORY +" c ON s."
                    + MwSQLiteHelper.COLUMN_TRANS_CATE_ID + " =c."+MwSQLiteHelper.COLUMN_CATE_ID + " where "
                    + dbHelper.COLUMN_TRANS_WALLET_ID + " = " + budget.getWallet_id()
                    + " and (c." + dbHelper.COLUMN_CATE_TYPE + " = " + Constant.EXPENSE_TYPE + " OR c." + dbHelper.COLUMN_CATE_TYPE
                    + " = " + Constant.ALL_CATEGORY_TYPE + ")"
                    + " and " + dbHelper.COLUMN_TRANS_DISPLAY_DATE + " >= '" + startDt + "'" +
                    " and " + dbHelper.COLUMN_TRANS_DISPLAY_DATE + " <= '" + endDt + "'");
        }

        new StringBuffer("SELECT * FROM ").append(MwSQLiteHelper.TABLE_TRANSACTION).append(" s inner join ")
                .append(MwSQLiteHelper.TABLE_CATEGORY).append(" c ON s.").append(MwSQLiteHelper.COLUMN_TRANS_CATE_ID).append(" = c.")
                .append(MwSQLiteHelper.COLUMN_CATE_ID)
                .append(" WHERE s.").append(MwSQLiteHelper.COLUMN_TRANS_WALLET_ID).append(" = ").append(Utils.getWallet_id())
                .append(" AND s.").append(MwSQLiteHelper.COLUMN_TRANS_ID).append(" = ?");

        Cursor cursor = database.rawQuery(sql.toString(), null);

        cursor.moveToFirst();
        Float amount = Float.valueOf(0);
        while (!cursor.isAfterLast()) {
            TransactionSum tran = new TransactionSum();
            amount = amount + cursor.getFloat(0);
            cursor.moveToNext();
        }
        return amount;
    }




}
