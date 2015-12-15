package com.alanddev.manwal.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

import java.util.Calendar;

public class TransactionAddActivity extends AppCompatActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener {

    private TextView edtDate;
    private TextView edtCate;
    private EditText edtAmout;
    private EditText edtDes;
    private ImageView imgCate;

    private Category category;
    private TransactionController transController;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    //private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.onActivityCreateSetTheme(this);
        setContentView(R.layout.activity_transaction_add);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        edtDate = (TextView) findViewById(R.id.edtdate);
        edtDate.setOnClickListener(this);
        edtCate = (TextView)findViewById(R.id.edtcate);
        edtCate.setOnClickListener(this);
        edtAmout = (EditText)findViewById(R.id.edtamount);
        edtAmout.addTextChangedListener(new CurrencyTextWatcher(edtAmout));

        edtDes = (EditText)findViewById(R.id.edtdes);
        imgCate = (ImageView)findViewById(R.id.imgcate);
        transController = new TransactionController(getApplicationContext());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        // check if the request code is same as what is passed  here it is 2
        if(requestCode==Constant.PICK_CATEGORY)
        {
            if(data!=null) {
                category = new Category();
                String cateName = data.getStringExtra(MwSQLiteHelper.COLUMN_CATE_NAME);
                category.setName(cateName);
                category.setId(data.getIntExtra(MwSQLiteHelper.COLUMN_CATE_ID, 0));
                edtCate.setText(cateName);
                String cateimg = data.getStringExtra(MwSQLiteHelper.COLUMN_CATE_IMG);
                imgCate.setImageResource(getResources().getIdentifier("ic_category_"+cateimg,"mipmap",getPackageName()));
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add, menu);
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
            saveTransaction();
        }
        return super.onOptionsItemSelected(item);
    }

    private void saveTransaction() {
        String amount = edtAmout.getText().toString();
        if(edtAmout.getText().toString().equals("")){
            Toast.makeText(this, getResources().getText(R.string.check_amout_exist), Toast.LENGTH_LONG).show();
        }else if(edtCate.getText().toString().equals("")||category==null){
            Toast.makeText(this, getResources().getText(R.string.check_category_exist), Toast.LENGTH_LONG).show();
        }else {
            transController.open();
            TransactionDetail transaction = new TransactionDetail();

            String sAmount = edtAmout.getText().toString();
            if (!sAmount.equals("")|| !sAmount.equals("0")) {
                sAmount = sAmount.replaceAll(",", "");
            }
            transaction.setAmountt(Float.valueOf(sAmount));
            transaction.setNote(edtDes.getText().toString());
            transaction.setCat_id(category.getId());
            transaction.setDisplay_date(Utils.getDatefromDayView(this, edtDate.getText().toString()));
            transaction.setWallet_id(Utils.getWallet_id());
            transController.create(transaction);
            transController.close();
            setResult(Constant.ADD_TRANSACTION_SUCCESS, new Intent());
            finish();
        }
    }

    public void onDateSet(DatePickerDialog view, int year, int month, int day) {
        String dateStr = day + "/" + (month + 1) + "/" + year;
        edtDate.setText(Utils.getDayView(this,Utils.changeStr2Date(dateStr,Constant.DATE_FORMAT_PICKER)));
    }

    public void showDatePickerDialog(View v) {
        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                TransactionAddActivity.this,
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
}
