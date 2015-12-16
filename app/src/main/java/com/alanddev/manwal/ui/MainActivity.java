package com.alanddev.manwal.ui;

//import android.support.v4.app.DialogFragment;

import android.app.NotificationManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.alanddev.manwal.R;
import com.alanddev.manwal.controller.CategoryController;
import com.alanddev.manwal.controller.CurrencyController;
import com.alanddev.manwal.helper.MwSQLiteHelper;
import com.alanddev.manwal.service.MyScheduleReceiver;
import com.alanddev.manwal.service.NotifyService;
import com.alanddev.manwal.util.Constant;
import com.alanddev.manwal.util.Utils;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    CurrencyController currController;
    CategoryController categoryController;
    Utils utils;
    //NotifyService s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String theme = Utils.getCurrentTheme(this);
        if (!theme.equals("")) {
            Utils.changeToTheme(theme);
        } else {
            Utils.changeToTheme("Default_Theme");
        }

        String naviheader = Utils.getCurrentNavHeader(this);
        if (naviheader.equals("")) {
            Utils.setSharedPreferencesValue(getApplicationContext(), Constant.NAV_HEADER_CURRENT, getString(R.string.navi_header_default));
        }
        setContentView(R.layout.activity_man_wal);
        checkDb();

        if(getIntent().getExtras()!=null&&getIntent().getExtras().get("NOTIFICATION")==1){
            NotificationManager mNotifyMgr =
                    (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            mNotifyMgr.cancel(NotifyService.GREETNG_ID);
        }
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
        } else {
            // the first time
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

            //start notification services
            Intent intent = new Intent();
            intent.setAction("com.alanddev.manwal.CUSTOM_INTENT");
            sendBroadcast(intent);
        }

    }


    @Override
    protected void onResume() {
        if (currController != null) {
            currController.open();
        }
        if (categoryController != null) {
            categoryController.open();
        }
        //start notification services
        Intent intent = new Intent();
        intent.setAction("com.alanddev.manwal.CUSTOM_INTENT");
        sendBroadcast(intent);
        super.onResume();
    }

    @Override
    protected void onPause() {
        if (currController != null) {
            currController.close();
        }
        if (categoryController != null) {
            categoryController.close();
        }
        super.onPause();
    }

}