package com.alanddev.manwal.ui;

//import android.support.v4.app.DialogFragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.alanddev.manwal.R;
import com.alanddev.manwal.controller.CategoryController;
import com.alanddev.manwal.controller.CurrencyController;
import com.alanddev.manwal.helper.MwSQLiteHelper;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    CurrencyController currController;
    CategoryController categoryController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_wal);
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

    public void checkDb() {
        File dbtest = new File("/data/data/com.alanddev.manwal/databases/" + MwSQLiteHelper.DATABASE_NAME);
        if (dbtest.exists()) {
            Toast.makeText(this, "OK", Toast.LENGTH_LONG).show();
        } else {
            //init currency
            currController = new CurrencyController(getApplicationContext());
            currController.open();
            currController.init();
            //init category
            categoryController = new CategoryController(getApplicationContext());
            categoryController.open();
            categoryController.init();
        }

        Intent intent = new Intent(this, TransactionActivity.class);
        startActivity(intent);
        finish();
    }

    public void onDateSet(DatePickerDialog view, int year, int month, int day) {
        // Do something with the date chosen by the user
        String date = "You picked the following date: " + day + "/" + (month + 1) + "/" + year;
        //dateTextView.setText(date);
        //Snackbar.make(view.getView(), "Date is:" + date, Snackbar.LENGTH_LONG)
        //               .setAction("Action", null).show();
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