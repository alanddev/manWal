package com.alanddev.manwal.ui;

import android.app.ListActivity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.alanddev.manwal.R;
import com.alanddev.manwal.adapter.CurrencyAdapter;
import com.alanddev.manwal.controller.CurrencyController;
//import com.alanddev.manwal.helper.Constants;
import com.alanddev.manwal.model.Currency;

import java.util.List;

public class ListCurrencyActivity extends AppCompatActivity {
    CurrencyController currencyController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_currency);
        ListView listCurrency = (ListView)findViewById(R.id.list_currency);
        currencyController = new CurrencyController(this);
        currencyController.open();
        List<Currency> currencyList = (List<Currency>)(List<?>)currencyController.getAll();

        CurrencyAdapter currencyAdapter = new CurrencyAdapter(this,currencyList);
        listCurrency.setAdapter(currencyAdapter);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



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
        if(id==android.R.id.home){
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        currencyController.open();
        super.onResume();
    }

    @Override
    protected void onPause() {
        currencyController.close();
        super.onPause();
    }

}
