package com.alanddev.manwal.ui;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.alanddev.manwal.R;
import com.alanddev.manwal.adapter.CurrencyTextWatcher;
import com.alanddev.manwal.controller.SavingController;
import com.alanddev.manwal.controller.SavingTController;
import com.alanddev.manwal.helper.MwSQLiteHelper;
import com.alanddev.manwal.model.Budget;
import com.alanddev.manwal.model.Saving;
import com.alanddev.manwal.model.SavingT;
import com.alanddev.manwal.util.Constant;
import com.alanddev.manwal.util.Utils;
import com.google.android.gms.ads.AdView;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;
import java.util.Date;

public class SavingDetailActivity extends AppCompatActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener{
    private TextView txtStartDate;
    private TextView txtEndDate;
    private EditText edtAmout;
    private EditText edtStartAmout;
    private int choice = 0;
    private SavingController savingController;
    private Saving saving;
    private AdView mAdView;
    private Button btndeposit;
    private Button btnwithdrawn;
    private Button btnshowT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.onActivityCreateSetTheme(this);
        Utils.setLanguage(this);
        setContentView(R.layout.activity_saving_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getResources().getString(R.string.title_activity_saving_detail));

        txtEndDate = (TextView) findViewById(R.id.txt_enddt);
        txtEndDate.setOnClickListener(this);
        txtStartDate = (TextView) findViewById(R.id.txt_startdt);
        txtStartDate.setOnClickListener(this);

        edtAmout = (EditText) findViewById(R.id.edt_amt);
        edtAmout.addTextChangedListener(new CurrencyTextWatcher(edtAmout));
        edtStartAmout = (EditText)findViewById(R.id.edtstartamt);
        edtStartAmout.addTextChangedListener(new CurrencyTextWatcher(edtStartAmout));

        Bundle bundle = getIntent().getExtras();
        int savingId=0;
        if(bundle!=null){
            savingId = bundle.getInt(MwSQLiteHelper.COLUMN_SAVING_ID, 0);
        }

        savingController = new SavingController(this);
        savingController.open();
        saving = savingController.getSavingById(savingId);
        savingController.close();
        txtStartDate.setText(Utils.changeDateStr2Str2(saving.getStartdate()));
        txtEndDate.setText(Utils.changeDateStr2Str2(saving.getEnddate()));
        edtAmout.setText(saving.getAmount()+"");
        edtStartAmout.setText(saving.getAmount_real()+"");
        btndeposit = (Button)findViewById(R.id.btndep);
        btnwithdrawn = (Button)findViewById(R.id.btnwithdrawn);
        btnshowT = (Button)findViewById(R.id.btnshowT);
        btndeposit.setOnClickListener(this);
        btnwithdrawn.setOnClickListener(this);
        btnshowT.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.btndep){
            openSavingDialog(Constant.SAVING_DEPOSIT);
        }
        if(v.getId()==R.id.btnwithdrawn){
            openSavingDialog(Constant.SAVING_WITHDRAWL);
        }

        if(v==txtStartDate||v==txtEndDate){
            choice=v.getId();
            showDatePickerDialog(v);
        }

        if(v==btnshowT){
            Intent intent = new Intent(getApplicationContext(), SavingTActivity.class);
            intent.putExtra(MwSQLiteHelper.COLUMN_SAVING_ID,saving.getId());
            startActivity(intent);
        }
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String dateStr = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
        if(choice==R.id.txt_startdt){
            txtStartDate.setText(dateStr);
        }else if(choice==R.id.txt_enddt){
            txtEndDate.setText(dateStr);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_trans_detail, menu);
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
            setResult(Constant.BUDGET_ADD_RESULT, new Intent());
            finish();
        }
        if (id == R.id.action_delete) {
            openConfirmDialog();
        }
        if (id == R.id.action_edit) {
            if(changeData()) {
                saveSaving();
            }else{
                Toast.makeText(this, getResources().getText(R.string.check_change_data), Toast.LENGTH_LONG).show();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    public void showDatePickerDialog(View v) {
        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                SavingDetailActivity.this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        dpd.show(getFragmentManager(), "Datepickerdialog");

    }

    public void openConfirmDialog(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage(getString(R.string.dialog_confirm_delete_trans));

        alertDialogBuilder.setPositiveButton(getString(R.string.dialog_yes_button), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                delete(saving.getId());
                setResult(Constant.BUDGET_ADD_RESULT, new Intent());
                finish();
            }
        });

        alertDialogBuilder.setNegativeButton(getString(R.string.dialog_no_button), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //don't do anything
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }


    public void openSavingDialog(final int type) {
        final int savingType = type;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        if(savingType==Constant.SAVING_DEPOSIT){
            builder.setMessage(getString(R.string.saving_deposit));
        }else if(savingType==Constant.SAVING_WITHDRAWL){
            builder.setMessage(getString(R.string.saving_withdrawal));
        }

        // Get the layout inflater
        LayoutInflater inflater = this.getLayoutInflater();
        View view = inflater.inflate(R.layout.savingdialog, null);
        final EditText editText = (EditText)view.findViewById(R.id.edtdamt);
        editText.addTextChangedListener(new CurrencyTextWatcher(editText));
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(view)
                // Add action buttons
                .setPositiveButton(getString(R.string.dialog_yes_button), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        String samt = editText.getText().toString();
                        if ("".equals(samt)) {
                            samt = "0";
                        } else {
                            samt = Utils.getFormatCurrency(samt);
                        }
                        Float amt = Float.valueOf(samt);
                        if (savingType == Constant.SAVING_WITHDRAWL && amt > saving.getAmount_real()) {
                            Toast.makeText(getApplicationContext(), getResources().getText(R.string.check_withdrawn), Toast.LENGTH_LONG).show();
                        } else {
                            SavingTController controller = new SavingTController(getApplicationContext());
                            controller.open();
                            SavingT savingT = new SavingT();
                            savingT.setType(savingType);
                            savingT.setAmount(amt);
                            savingT.setSaving_id(saving.getId());
                            controller.create(savingT);
                            controller.close();
                            SavingController savController = new SavingController(getApplicationContext());
                            savController.open();
                            Float amtb = saving.getAmount_real();
                            if (savingType == Constant.SAVING_DEPOSIT) {
                                amtb = amtb + amt;
                            } else {
                                amtb = amtb - amt;
                            }
                            saving.setAmount_real(amtb);
                            savController.update(saving);
                            savController.close();
                            edtStartAmout.setText(amtb+"");
                        }
                    }
                })
                .setNegativeButton(getString(R.string.dialog_no_button), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //don't do anything
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void delete(int savingId){
        savingController.open();
        savingController.delete(savingId);
        savingController.close();
    }

    private Boolean changeData(){
        String sAmount = Utils.getFormatCurrency(edtAmout.getText().toString());
        Float amount = Float.valueOf(sAmount);
        String sAmount_r = Utils.getFormatCurrency(edtAmout.getText().toString());
        Float Amount_r = Float.valueOf(sAmount);
        String startDt = Utils.changeDateStr2Str(txtStartDate.getText().toString());
        String endDt = Utils.changeDateStr2Str(txtEndDate.getText().toString());
        if(amount.compareTo(saving.getAmount())==0&&Amount_r.compareTo(saving.getAmount_real())==0
                &&startDt.equals(saving.getStartdate())&&endDt.equals(saving.getEnddate())){
            return false;
        }
        return true;
    }

    private void saveSaving() {
        String amount = edtAmout.getText().toString();
        if (!amount.equals("")&& !amount.equals("0")) {
            //amount = amount.replaceAll(",", "");
            amount = Utils.getFormatCurrency(amount);
        }
        String amount_r = edtStartAmout.getText().toString();
        if (!amount_r.equals("")&& !amount_r.equals("0")) {
            //amount = amount.replaceAll(",", "");
            amount_r = Utils.getFormatCurrency(amount_r);
        }
        if(amount.equals("")){
            Toast.makeText(this, getResources().getText(R.string.check_amout_exist), Toast.LENGTH_LONG).show();
        }else if(Float.valueOf(amount)<=0){
            Toast.makeText(this, getResources().getText(R.string.check_amount_zero), Toast.LENGTH_LONG).show();
        }else {
            Date startDt = Utils.changeStr2Date(txtStartDate.getText().toString(),Constant.DATE_FORMAT_PICKER);
            Date endDt = Utils.changeStr2Date(txtEndDate.getText().toString(),Constant.DATE_FORMAT_PICKER);
            if(endDt.compareTo(startDt)>0) {
                savingController.open();
                saving.setAmount(Float.valueOf(amount));
                saving.setAmount_real(Float.valueOf(amount_r));
                saving.setStartdate(Utils.changeDateStr2Str(txtStartDate.getText().toString()));
                saving.setEnddate(Utils.changeDateStr2Str(txtEndDate.getText().toString()));
                saving.setWallet_id(Utils.getWallet_id());
                savingController.update(saving);
                savingController.close();
            }else{
                Toast.makeText(this, "Ngày bắt đầu trước ngày kết thúc", Toast.LENGTH_LONG).show();
            }
        }
        setResult(Constant.SAVING_ADD_RESULT, new Intent());
        finish();
    }
}
