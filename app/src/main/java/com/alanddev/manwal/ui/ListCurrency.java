package com.alanddev.manwal.ui;

import android.app.ListActivity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.alanddev.manwal.R;
import com.alanddev.manwal.adapter.CurrencyAdapter;
import com.alanddev.manwal.controller.CurrencyController;
import com.alanddev.manwal.helper.Constants;
import com.alanddev.manwal.model.Currency;

import java.util.List;

public class ListCurrency extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_currency);
        ListView listCurrency = (ListView)findViewById(R.id.list_currency);
        CurrencyController currencyController = new CurrencyController(this);

        List<Currency> currencyList = (List<Currency>)(List<?>)currencyController.getAll();

        CurrencyAdapter currencyAdapter = new CurrencyAdapter(this,currencyList);
        listCurrency.setAdapter(currencyAdapter);
    }

}
