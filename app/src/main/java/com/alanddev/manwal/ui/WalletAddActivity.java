package com.alanddev.manwal.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.alanddev.manwal.R;
import com.alanddev.manwal.controller.WalletController;
import com.alanddev.manwal.helper.MwSQLiteHelper;
import com.alanddev.manwal.model.Wallet;

public class WalletAddActivity extends AppCompatActivity {

    //MwDataSource db;
    MwSQLiteHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet_add);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        dbHelper = new MwSQLiteHelper(getApplicationContext());
        //db = new MwDataSource(getApplicationContext());


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
        if (id == R.id.save){
            saveWallet();
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * return Main Activity if user click back button;
     */

    public void returnMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }

    public void saveWallet() {
        EditText nameEdit   = (EditText)findViewById(R.id.txtName);
        EditText currEdit   = (EditText)findViewById(R.id.txtCurrency);
        EditText amountEdit   = (EditText)findViewById(R.id.txtAmount);
        Wallet newWallet = new Wallet(nameEdit.getText().toString(), Double.parseDouble(amountEdit.getText().toString()), currEdit.getText().toString());
        //db.createWallet();
        WalletController walletController = new WalletController();
        walletController.create(dbHelper, newWallet);
        //returnMainActivity();
        finish();
    }

}
