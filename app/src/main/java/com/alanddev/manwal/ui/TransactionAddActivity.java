package com.alanddev.manwal.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.alanddev.manwal.R;
import com.alanddev.manwal.helper.MwSQLiteHelper;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;

public class TransactionAddActivity extends AppCompatActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener {

    private TextView edtDate;
    private TextView edtCate;
    private static final int PICK_CATEGORY = 1;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    //private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_add);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        edtDate = (TextView) findViewById(R.id.edtdate);
        edtDate.setOnClickListener(this);

        edtCate = (TextView)findViewById(R.id.edtcate);
        edtCate.setOnClickListener(this);

        //client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        // check if the request code is same as what is passed  here it is 2
        if(requestCode==PICK_CATEGORY)
        {
            String message=data.getStringExtra(MwSQLiteHelper.COLUMN_CATE_NAME);
            edtCate.setText(message);
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
        finish();
    }

    public void onDateSet(DatePickerDialog view, int year, int month, int day) {
        String date = day + "/" + (month + 1) + "/" + year;
        edtDate.setText(date);
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
            startActivityForResult(intent, PICK_CATEGORY);
        }
        if(v.getId()==R.id.edtdate){
            showDatePickerDialog(v);
        }
    }
}
