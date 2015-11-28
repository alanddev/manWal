package com.alanddev.manwal.ui;

//import android.support.v4.app.DialogFragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.alanddev.manwal.R;
import com.alanddev.manwal.controller.CategoryController;
import com.alanddev.manwal.controller.CurrencyController;
import com.alanddev.manwal.helper.MwSQLiteHelper;
import com.alanddev.manwal.util.Constant;
import com.alanddev.manwal.util.Utils;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    CurrencyController currController;
    CategoryController categoryController;
    Utils utils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_wal);
        Calendar calendar = Calendar.getInstance();
        calendar.set(2015,10,0);
        int dayofweek = calendar.get(Calendar.YEAR);
        //calendar.add(Calendar.WEEK_OF_YEAR, -dayofweek);
        String strDate1 = Utils.changeDate2Str(calendar.getTime());

        Log.d("AAAAA",dayofweek+"");
        checkDb();
    }

    public void checkDb() {
        utils = new Utils();
        File dbtest = new File("/data/data/com.alanddev.manwal/databases/" + MwSQLiteHelper.DATABASE_NAME);
        if (dbtest.exists()) {
            Toast.makeText(this, "OK", Toast.LENGTH_LONG).show();
            Utils.setWallet_id(utils.getSharedPreferencesValue(this, Constant.WALLET_ID));
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(MainActivity.this, TransactionActivity.class);
                    startActivity(intent);
                    finish();
                }
            }, Constant.SPLASH_DISPLAY_LENGTH);
        }
        else
        {
             //init currency
             currController = new CurrencyController(getApplicationContext());
             currController.open();
             currController.init();
             currController.close();
             //init category
             categoryController = new CategoryController(getApplicationContext());
             categoryController.open();
             categoryController.init(getApplicationContext());
             categoryController.close();
             utils.createFolder(Constant.PATH_IMG);
             new Handler().postDelayed(new Runnable() {
                 @Override
                 public void run() {
                     Intent intent = new Intent(MainActivity.this, WalletAddActivity.class);
                     startActivity(intent);
                     finish();
                 }
             }, Constant.SPLASH_DISPLAY_LENGTH);
        }

    }


            @Override
    protected void onResume() {
        if(currController!=null) {
            currController.open();
        }
        if(categoryController!=null){
            categoryController.open();
        }
        super.onResume();
    }

    @Override
    protected void onPause() {
        if(currController!=null) {
            currController.close();
        }
        if(categoryController!=null) {
            categoryController.close();
        }
        super.onPause();
    }


}