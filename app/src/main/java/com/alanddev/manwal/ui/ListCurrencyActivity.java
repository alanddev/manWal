package com.alanddev.manwal.ui;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alanddev.manwal.R;
import com.alanddev.manwal.adapter.CurrencyAdapter;
import com.alanddev.manwal.controller.CurrencyController;
//import com.alanddev.manwal.helper.Constants;
import com.alanddev.manwal.model.Currency;
import com.alanddev.manwal.util.Constant;
import com.alanddev.manwal.util.Utils;

import java.util.List;

public class ListCurrencyActivity extends AppCompatActivity {
    CurrencyController currencyController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.onActivityCreateSetTheme(this);
        Utils.setLanguage(this);
        setContentView(R.layout.activity_list_currency);

        final ListView listCurrency = (ListView)findViewById(R.id.list_currency);
        currencyController = new CurrencyController(this);
        currencyController.open();
        List<Currency> currencyList = (List<Currency>)(List<?>)currencyController.getAll();

        CurrencyAdapter currencyAdapter = new CurrencyAdapter(this,currencyList);
        listCurrency.setAdapter(currencyAdapter);

        listCurrency.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long arg3) {
                //Toast.makeText(view.getContext(), "" + position, Toast.LENGTH_SHORT).show();
                Intent intentAddWallet = new Intent(view.getContext(),WalletAddActivity.class);
                Currency currency = (Currency)parent.getAdapter().getItem(position);
                intentAddWallet.putExtra("cur_name", currency.getName());
                intentAddWallet.putExtra("cur_code", currency.getCode());
                //intentAddWallet.putExtra("cur_symbol", currency.getSymbol());
                //startActivityForResult(addWallet, Constant.CUR_WALLET_REQUEST);
                setResult(Constant.CUR_WALLET_REQUEST, intentAddWallet);
                //startActivity(intentAddWallet);
                finish();
                //startActivityForResult(Constant.CUR_WALLET_REQUEST);

            }
        });


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
            Intent intent = new Intent(this, WalletAddActivity.class);
            startActivity(intent);
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
