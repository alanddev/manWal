package com.alanddev.manwal.ui;

//import android.support.v4.app.DialogFragment;

import android.content.Intent;
import android.content.SharedPreferences;
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

public class MainActivity extends AppCompatActivity {

    CurrencyController currController;
    CategoryController categoryController;
    Utils utils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String theme = Utils.getCurrentTheme(this);
        if(!theme.equals("")){
            Utils.changeToTheme(theme);
        }else{
            Utils.changeToTheme("Default_Theme");
        }
        setContentView(R.layout.activity_man_wal);
        checkDb();
    }

    public void checkDb() {
        utils = new Utils();
        File dbtest = new File("/data/data/com.alanddev.manwal/databases/" + MwSQLiteHelper.DATABASE_NAME);
        utils.createFolder(Constant.PATH_IMG);
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
             currController.init(this);
             currController.close();
             //init category
             categoryController = new CategoryController(getApplicationContext());
             categoryController.open();
             categoryController.init(getApplicationContext());
             categoryController.close();
             //utils.createFolder(Constant.PATH_IMG);
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