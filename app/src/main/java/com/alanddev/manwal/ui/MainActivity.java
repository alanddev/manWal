package com.alanddev.manwal.ui;

//import android.support.v4.app.DialogFragment;

import android.app.NotificationManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.alanddev.manwal.R;
import com.alanddev.manwal.controller.CategoryController;
import com.alanddev.manwal.controller.CurrencyController;
import com.alanddev.manwal.controller.WalletController;
import com.alanddev.manwal.helper.MwSQLiteHelper;
import com.alanddev.manwal.service.NotifyService;
import com.alanddev.manwal.util.Constant;
import com.alanddev.manwal.util.Utils;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_wal);
        background.start();
    }
    Thread background = new Thread() {
        public void run() {
            try {
                init();
                if (checkDB()) {
                    Utils.setWallet_id(Utils.getSharedPreferencesValue(getApplicationContext(), Constant.WALLET_ID));
                    sleep(Constant.SPLASH_DISPLAY_LONG);
                } else {
                    initfor1st();
                    sleep(Constant.SPLASH_DISPLAY_SHORT);
                }
                if (checkWallet()) {
                    Intent i = new Intent(getBaseContext(), TransactionActivity.class);
                    startActivity(i);
                } else {
                    Intent intent = new Intent(MainActivity.this, WalletAddActivity.class);
                    startActivity(intent);
                }
                finish();
            } catch (Exception e) {
            }
        }
    };

    private Boolean checkDB() {
        File dbtest = new File("/data/data/com.alanddev.manwal/databases/" + MwSQLiteHelper.DATABASE_NAME);
        return dbtest.exists();
    }

    private Boolean checkWallet() {
        WalletController controller = new WalletController(this);
        controller.open();
        return (controller.getCount() > 0);
    }

    private void initfor1st() {
        CurrencyController currController = new CurrencyController(getApplicationContext());
        currController.open();
        currController.init(this);
        currController.close();
        //init category
        CategoryController categoryController = new CategoryController(getApplicationContext());
        categoryController.open();
        categoryController.init(getApplicationContext());
        categoryController.close();
        //start notification services
        Intent intent = new Intent();
        intent.setAction("com.alanddev.manwal.CUSTOM_INTENT");
        sendBroadcast(intent);
    }

    private void init() {
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

        if (getIntent().getExtras() != null && getIntent().getExtras().get("NOTIFICATION") == 1) {
            NotificationManager mNotifyMgr =
                    (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            mNotifyMgr.cancel(NotifyService.GREETNG_ID);
        }
        Utils.createFolder(Constant.PATH_IMG);
    }

}