package com.alanddev.manwal.ui;

//import android.support.v4.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.DatePicker;
import android.widget.Toast;

import com.alanddev.manwal.R;
import com.alanddev.manwal.controller.CurrencyController;
import com.alanddev.manwal.controller.WalletController;
import com.alanddev.manwal.helper.Constants;
import com.alanddev.manwal.helper.MwDataSource;
import com.alanddev.manwal.helper.MwSQLiteHelper;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.io.File;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity
        {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_wal);
        Constants.dbHelper = new MwSQLiteHelper(this);
        Constants.db = new MwDataSource(this);
        checkDb();
    }



//    public void showDatePickerDialog(View v) {
//        Calendar now = Calendar.getInstance();
//        DatePickerDialog dpd = DatePickerDialog.newInstance(
//                MainActivity.this,
//                now.get(Calendar.YEAR),
//                now.get(Calendar.MONTH),
//                now.get(Calendar.DAY_OF_MONTH)
//        );
//        dpd.show(getFragmentManager(), "Datepickerdialog");
//
//    }

//    public void changeActivity(){
//        Intent intent = new Intent(this, WalletAddActivity.class);
//        startActivity(intent);
//    }

    public void checkDb(){
        File dbtest =new File("/data/data/com.alanddev.manwal/databases/" + MwSQLiteHelper.DATABASE_NAME);
        if (dbtest.exists()){
            Toast.makeText(this,"OK",Toast.LENGTH_LONG).show();
            //Constants.dbHelper.createTable();
        }else{
            //Constants.dbHelper.createTable();
            CurrencyController currController = new CurrencyController();
            currController.init(Constants.dbHelper);
            //Toast.makeText(this,"FAIL",Toast.LENGTH_LONG).show();
        }

        Intent intent = new Intent(this, TransactionActivity.class);
        startActivity(intent);
        finish();
    }

    public void onDateSet(DatePickerDialog view, int year, int month, int day) {
        // Do something with the date chosen by the user
        String date = "You picked the following date: "+day+"/"+(month+1)+"/"+year;
        //dateTextView.setText(date);
        //Snackbar.make(view.getView(), "Date is:" + date, Snackbar.LENGTH_LONG)
        //               .setAction("Action", null).show();
    }


}