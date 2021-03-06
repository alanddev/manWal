package com.alanddev.manwal.ui;

//import android.support.v4.app.DialogFragment;

import android.app.NotificationManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

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
                    sleep(Constant.SPLASH_DISPLAY_SHORT);
                } else {
                    initfor1st();
                    sleep(Constant.SPLASH_DISPLAY_LONG);
                }
                if (checkWallet()) {
                    Intent i = new Intent(getApplicationContext(), TransactionActivity.class);
                    startActivity(i);
                } else if(Utils.getCurrentLanguage(getApplicationContext()).equals("")){
                    Intent intent = new Intent(MainActivity.this, SelectThemeActivity.class);
                    intent.putExtra("SETTING_EXTRA",Constant.CHANGE_LANGUAGE_ID);
                    intent.putExtra("SETTING_FIRST",Constant.CHANGE_LANGUAGE_ID);
                    startActivity(intent);
                }else{
                    Intent intent = new Intent(getApplicationContext(), WalletAddActivity.class);
                    startActivity(intent);
                }
                finish();
            } catch (Exception e) {
            }
        }
    };

    private Boolean checkDB() {
        File dbtest = new File(getResources().getString(R.string.db_path) + MwSQLiteHelper.DATABASE_NAME);
        return dbtest.exists();
    }

    private Boolean checkWallet() {
        WalletController controller = new WalletController(this);
        controller.open();
        Boolean isWallet = controller.getCount()>0;
        controller.close();
        return isWallet;
    }

    private void initfor1st() {
        CurrencyController currController = new CurrencyController(getApplicationContext());
        currController.open();
        currController.init(this);
        currController.close();
        //start notification services
       /* Intent intent = new Intent();
        intent.setAction("com.alanddev.manwal.CUSTOM_INTENT");
        sendBroadcast(intent);*/
    }

    private void init() {
        String theme = Utils.getCurrentTheme(this);
        if (!theme.equals("")) {
            Utils.changeToTheme(theme);
        } else {
            Utils.setSharedPreferencesValue(getApplicationContext(), Constant.THEME_CURRENT, "DodgerBlue_Theme");
            Utils.changeToTheme("DodgerBlue_Theme");
        }

        String naviheader = Utils.getCurrentNavHeader(this);
        if (naviheader.equals("")) {
            Utils.setSharedPreferencesValue(getApplicationContext(), Constant.NAV_HEADER_CURRENT, getString(R.string.navi_header_default));
        }

        String background = Utils.getCurrentBackGround(this);
        if (background.equals("")) {
            Utils.setSharedPreferencesValue(getApplicationContext(), Constant.NAV_BACKGROUND_CURRENT, getString(R.string.navi_header_default));
        }

       /* if (getIntent().getExtras() != null && getIntent().getExtras().get("NOTIFICATION")!=null && getIntent().getExtras().get("NOTIFICATION").toString().equals("1")) {
            NotificationManager mNotifyMgr =
                    (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            mNotifyMgr.cancel(NotifyService.GREETNG_ID);
        }*/
        Utils.createFolder(Constant.PATH_IMG);
    }

}