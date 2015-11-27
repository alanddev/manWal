package com.alanddev.manwal.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.alanddev.manwal.R;
import com.alanddev.manwal.adapter.CurrencyAdapter;
import com.alanddev.manwal.adapter.WalletAdapter;
import com.alanddev.manwal.controller.CurrencyController;
import com.alanddev.manwal.controller.WalletController;
import com.alanddev.manwal.model.Currency;
import com.alanddev.manwal.model.Wallet;
import com.alanddev.manwal.util.Constant;

import java.util.List;

//import com.alanddev.manwal.helper.Constants;

public class WalletsActivity extends AppCompatActivity {
    WalletController walletController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_currency);

        final ListView listWallet = (ListView)findViewById(R.id.list_currency);
        walletController = new WalletController(this);
        walletController.open();
        prepareData();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_wallets, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        switch(id){
            case android.R.id.home:
                //Intent intent = new Intent(this, WalletEditActivity.class);
                //startActivity(intent);
                finish();
                break;
            case R.id.add:
                Intent intent = new Intent(this, WalletAddActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        walletController.open();
        prepareData();
        super.onResume();
    }

    @Override
    protected void onPause() {
        walletController.close();
        super.onPause();
    }


    public void prepareData(){
        final ListView listWallet = (ListView)findViewById(R.id.list_currency);
        walletController = new WalletController(this);
        walletController.open();
        List<Wallet> walletList = (List<Wallet>)(List<?>)walletController.getAll();

        WalletAdapter walletAdapter = new WalletAdapter(this,walletList);
        listWallet.setAdapter(walletAdapter);

        listWallet.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long arg3) {
                //Toast.makeText(view.getContext(), "" + position, Toast.LENGTH_SHORT).show();
                Intent intentEditWallet = new Intent(view.getContext(),WalletEditActivity.class);
                Wallet wallet = (Wallet)parent.getAdapter().getItem(position);
                intentEditWallet.putExtra("wallet_id", wallet.getId());
                intentEditWallet.putExtra("wallet_name", wallet.getName());
                intentEditWallet.putExtra("wallet_amount", wallet.getAmount());
                intentEditWallet.putExtra("wallet_currency", wallet.getCurrency());
                intentEditWallet.putExtra("wallet_image", wallet.getImage());
                intentEditWallet.putExtra("wallet_choose", wallet.getChoose());
                //Bundle b = new Bundle();
                //intentEditWallet.putExtra(b);
                //setResult(Constant.CUR_WALLET_REQUEST, intentEditWallet);
                startActivity(intentEditWallet);
                //finish();
            }
        });



    }

}