package com.alanddev.manwal.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.alanddev.manwal.R;
import com.alanddev.manwal.adapter.CurrencyTextWatcher;
import com.alanddev.manwal.controller.SavingController;
import com.alanddev.manwal.model.Saving;
import com.alanddev.manwal.util.Constant;
import com.alanddev.manwal.util.Utils;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;

public class SavingAddActivity extends AppCompatActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener {
    private TextView txtStartDate;
    private TextView txtEndDate;
    private EditText edtAmout;
    private EditText edtStartAmout;
    private EditText edtDes;
    private int choice = 0;
    private SavingController savingController;
    private Saving saving;
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.onActivityCreateSetTheme(this);
        Utils.setLanguage(this);
        setContentView(R.layout.activity_saving_add);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        txtEndDate = (TextView) findViewById(R.id.txt_enddt);
        txtEndDate.setOnClickListener(this);
        txtStartDate = (TextView) findViewById(R.id.txt_startdt);
        txtStartDate.setOnClickListener(this);

        edtAmout = (EditText) findViewById(R.id.edt_amt);
        edtAmout.addTextChangedListener(new CurrencyTextWatcher(edtAmout));
        edtStartAmout = (EditText)findViewById(R.id.edtstartamt);
        edtStartAmout.addTextChangedListener(new CurrencyTextWatcher(edtStartAmout));

        savingController = new SavingController(this);
        getSupportActionBar().setTitle(getResources().getString(R.string.title_activity_saving_add));
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        txtStartDate.setText(Utils.changeDate2Str(calendar.getTime(), Constant.DATE_FORMAT_PICKER));
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        txtEndDate.setText(Utils.changeDate2Str(calendar.getTime(), Constant.DATE_FORMAT_PICKER));

        edtDes = (EditText)findViewById(R.id.edtdes);
        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        if (saving == null) {
            getMenuInflater().inflate(R.menu.menu_add, menu);
        } else {
            getMenuInflater().inflate(R.menu.menu_trans_detail, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            finish();
        }

        if (id == R.id.save) {
            saveSaving();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        if(v==txtStartDate||v==txtEndDate){
            choice=v.getId();
            showDatePickerDialog(v);
        }
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int month, int day) {
        String dateStr = day + "/" + (month + 1) + "/" + year;
        if(choice==R.id.txt_startdt){
            txtStartDate.setText(dateStr);
        }else if(choice==R.id.txt_enddt){
            txtEndDate.setText(dateStr);
        }

    }

    public void showDatePickerDialog(View v) {
        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                SavingAddActivity.this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        dpd.show(getFragmentManager(), "Datepickerdialog");

    }


    private void saveSaving() {
        String amount = edtAmout.getText().toString();
        String startamount = edtStartAmout.getText().toString();
        if (!amount.equals("") || !amount.equals("0")) {
            amount = Utils.getFormatCurrency(amount);
        }
        if(!startamount.equals("")|| !startamount.equals("0")){
            startamount=Utils.getFormatCurrency(startamount);
        }else{
            startamount="0";
        }
        if (amount.equals("")) {
            Toast.makeText(this, getResources().getText(R.string.check_amout_exist), Toast.LENGTH_LONG).show();
        } else if (Float.valueOf(amount) <= 0) {
            Toast.makeText(this, getResources().getText(R.string.check_amount_zero), Toast.LENGTH_LONG).show();
        }{
            /*Date endDt = Utils.changeStr2Date(txtEndDate.getText().toString(), Constant.DATE_FORMAT_PICKER);*/
           /* CheckBox chkistrans = (CheckBox)findViewById(R.id.chkistrans);
            Integer isTrans = 0;
            if(chkistrans.isChecked()){
                isTrans=1;
            }*/
            savingController.open();
            Saving newSaving = new Saving();
            newSaving.setTitle(edtDes.getText().toString());
            newSaving.setAmount(Float.valueOf(amount));
            newSaving.setAmount_real(Float.valueOf(startamount));
            newSaving.setEnddate(Utils.changeDateStr2Str(txtEndDate.getText().toString()));
            newSaving.setStartdate(Utils.changeDateStr2Str(txtStartDate.getText().toString()));
            newSaving.setWallet_id(Utils.getWallet_id());
            //newSaving.setIsTrans(isTrans);
            savingController.create(newSaving);
            savingController.close();
        }
        setResult(Constant.BUDGET_ADD_RESULT, new Intent());
        finish();
    }

    @Override
    protected void onPause() {
        mAdView.pause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mAdView.resume();
    }

    @Override
    protected void onDestroy() {
        mAdView.destroy();
        super.onDestroy();
    }
}
