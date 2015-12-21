package com.alanddev.manwal.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alanddev.manwal.R;
import com.alanddev.manwal.adapter.CurrencyTextWatcher;
import com.alanddev.manwal.controller.TransactionController;
import com.alanddev.manwal.helper.MwSQLiteHelper;
import com.alanddev.manwal.model.Category;
import com.alanddev.manwal.model.TransactionDetail;
import com.alanddev.manwal.util.Constant;
import com.alanddev.manwal.util.Utils;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Calendar;

public class TransactionDetailActivity extends AppCompatActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener{

    private TransactionController controller;
    private TransactionDetail transactionDetail;
    private TextView edtDate;
    private TextView edtCate;
    private EditText edtAmout;
    private EditText edtDes;
    private ImageView imgCate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.onActivityCreateSetTheme(this);
        Utils.setLanguage(this);
        setContentView(R.layout.activity_transaction_add);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getResources().getString(R.string.title_activity_transaction_detail));
        long transId = getIntent().getExtras().getLong(MwSQLiteHelper.COLUMN_TRANS_ID, 0);
        controller = new TransactionController(this);
        transactionDetail = getTransaction(transId);
        edtDate = (TextView) findViewById(R.id.edtdate);
        edtDate.setOnClickListener(this);
        edtDate.setText(Utils.changeDateStr2Str2(transactionDetail.getDisplay_date()));

        edtCate = (TextView)findViewById(R.id.edtcate);
        edtCate.setOnClickListener(this);
        edtCate.setText(transactionDetail.getCate_name());

        edtAmout = (EditText)findViewById(R.id.edtamount);
        NumberFormat formatter = new DecimalFormat("###,###,###,###.##");
        String sAmount =  formatter.format(transactionDetail.getAmountt());
        edtAmout.setText(sAmount);
        edtAmout.addTextChangedListener(new CurrencyTextWatcher(edtAmout));


        edtDes = (EditText)findViewById(R.id.edtdes);
        edtDes.setText(transactionDetail.getNote());

        imgCate = (ImageView)findViewById(R.id.imgcate);
        imgCate.setImageResource(getResources().getIdentifier("ic_category_"+transactionDetail.getCate_img(),"mipmap",getPackageName()));

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
            finish();
        }

        if (id == R.id.action_delete) {
            openConfirmDialog();
        }

        if (id == R.id.action_edit) {
            if(changeData()) {
                saveTransaction();
                setResult(Constant.TRANS_DETAIL_UPDATE);
                finish();
            }else{
                Toast.makeText(this, getResources().getText(R.string.check_change_data), Toast.LENGTH_LONG).show();
            }
        }

        return super.onOptionsItemSelected(item);
    }

    public TransactionDetail getTransaction(long transId){
        controller.open();
        TransactionDetail transactionDetail = controller.getTransbyId(transId);
        controller.close();
        return transactionDetail;
    }


    public void onDateSet(DatePickerDialog view, int year, int month, int day) {
        String dateStr = day + "/" + (month + 1) + "/" + year;
        edtDate.setText(Utils.getDayView(this, Utils.changeStr2Date(dateStr, Constant.DATE_FORMAT_PICKER)));
    }

    public void showDatePickerDialog(View v) {
        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                TransactionDetailActivity.this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        dpd.show(getFragmentManager(), "Datepickerdialog");

    }


    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.edtcate){
            Intent intent = new Intent(getApplicationContext(),CategoryActivity.class);
            startActivityForResult(intent, Constant.PICK_CATEGORY);
        }
        if(v.getId()==R.id.edtdate){
            showDatePickerDialog(v);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        // check if the request code is same as what is passed  here it is 2
        if(requestCode==Constant.PICK_CATEGORY)
        {
            if(data!=null) {
                String cateName = data.getStringExtra(MwSQLiteHelper.COLUMN_CATE_NAME);
                transactionDetail.setCate_name(cateName);
                transactionDetail.setCat_id(data.getIntExtra(MwSQLiteHelper.COLUMN_CATE_ID, 0));
                edtCate.setText(cateName);
                String cateimg = data.getStringExtra(MwSQLiteHelper.COLUMN_CATE_IMG);
                imgCate.setImageResource(getResources().getIdentifier("ic_category_"+cateimg,"mipmap",getPackageName()));
            }
        }
    }

    private void saveTransaction() {
        String amount = edtAmout.getText().toString();
        if(edtAmout.getText().toString().equals("")){
            Toast.makeText(this, getResources().getText(R.string.check_amout_exist), Toast.LENGTH_LONG).show();
        }else if(edtCate.getText().toString().equals("")){
            Toast.makeText(this, getResources().getText(R.string.check_category_exist), Toast.LENGTH_LONG).show();
        }else {
            controller.open();
            TransactionDetail transaction = new TransactionDetail();

            if (!amount.equals("")|| !amount.equals("0")) {
//                amount = amount.replaceAll(",", "");
                amount = Utils.getFormatCurrency(amount);
            }
            transaction.setAmountt(Float.valueOf(amount));
            transaction.setNote(edtDes.getText().toString());
            transaction.setCat_id(transactionDetail.getCat_id());
            transaction.setDisplay_date(Utils.getDatefromDayView(this, edtDate.getText().toString()));
            transaction.setWallet_id(Utils.getWallet_id());
            transaction.setId(transactionDetail.getId());
            controller.update(transaction);
            controller.close();
        }
    }

    private void delete(long transId){
        controller.open();
        controller.delete(transId);
        controller.close();
    }

    @Override
    protected void onPause() {
        if(controller!=null) {
            controller.close();
        }
        super.onPause();
    }

    public void openConfirmDialog(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage(getString(R.string.dialog_confirm_delete_trans));

        alertDialogBuilder.setPositiveButton(getString(R.string.dialog_yes_button), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                delete(transactionDetail.getId());
                setResult(Constant.TRANS_DETAIL_UPDATE);
                finish();
            }
        });

        alertDialogBuilder.setNegativeButton(getString(R.string.dialog_no_button),new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //don't do anything
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private Boolean changeData(){
        String sAmount = edtAmout.getText().toString();
        if (!sAmount.equals("")|| !sAmount.equals("0")) {
            sAmount = sAmount.replaceAll(",", "");
        }
        Float amount = Float.valueOf(sAmount);
        String catename = edtCate.getText().toString();
        String note = edtDes.getText().toString();
        String strdate = Utils.getDatefromDayView(this, edtDate.getText().toString());
        if(amount==transactionDetail.getAmountt()&&catename.equals(transactionDetail.getCate_name())
        &&note.equals(transactionDetail.getNote())&&strdate.equals(transactionDetail.getDisplay_date())){
            return false;
        }
        return true;
    }
}
