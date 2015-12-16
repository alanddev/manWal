package com.alanddev.manwal.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.alanddev.manwal.R;
import com.alanddev.manwal.adapter.CurrencyTextWatcher;
import com.alanddev.manwal.adapter.WalletAdapter;
import com.alanddev.manwal.adapter.WalletSpinnerAdapter;
import com.alanddev.manwal.controller.TransactionController;
import com.alanddev.manwal.controller.WalletController;
import com.alanddev.manwal.model.Wallet;
import com.alanddev.manwal.util.Constant;
import com.alanddev.manwal.util.Utils;

import java.io.File;
import java.io.IOException;
import java.util.List;


public class WalletTransferActivity extends AppCompatActivity {

    //WalletController walletController;
    //TransactionController transactionController;
    Spinner spinnerFromWallet;
    Spinner spinnerToWallet;
    EditText amountEdit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Utils.onActivityCreateSetTheme(this);
        setContentView(R.layout.activity_wallet_transfer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        WalletController walletController = new WalletController(this);
        walletController.open();
        spinnerFromWallet = (Spinner) findViewById(R.id.from_wallet);
        spinnerToWallet = (Spinner) findViewById(R.id.to_wallet);

        List<Wallet> walletList = (List<Wallet>)(List<?>)walletController.getAll();
        WalletSpinnerAdapter walletAdapter = new WalletSpinnerAdapter(this,walletList);
        walletController.close();
        spinnerFromWallet.setAdapter(walletAdapter);
        spinnerToWallet.setAdapter(walletAdapter);
        //int selectionPosition= walletAdapter.getPosition();

        amountEdit = (EditText)findViewById(R.id.txtAmount);
        amountEdit.addTextChangedListener(new CurrencyTextWatcher(amountEdit));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_transfer_wallet, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch(id){
            case android.R.id.home:
                finish();
                break;
            case R.id.save:
                transferMoney();
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }


    public void transferMoney() {
        Wallet fromWallet = (Wallet)spinnerFromWallet.getSelectedItem();
        Wallet toWallet = (Wallet)spinnerToWallet.getSelectedItem();

        if (fromWallet.getId() != toWallet.getId()){
            String sAmount = amountEdit.getText().toString();
            float amount = 0;
            if (!sAmount.equals("")|| !sAmount.equals("0")) {
                sAmount = sAmount.replaceAll(",", "");
                amount = Float.valueOf(sAmount);
            }
            TransactionController transactionController = new TransactionController(this);
            transactionController.open();
            transactionController.transferMoney(this, fromWallet, toWallet, amount);
            transactionController.close();
            Intent intent = new Intent(this, TransactionActivity.class);
            startActivity(intent);
            finish();
        }else{
            Toast.makeText(this, getResources().getString(R.string.warning_transfer), Toast.LENGTH_LONG).show();
        }
    }

}
