package com.alanddev.manwal.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.alanddev.manwal.R;
import com.alanddev.manwal.model.TransactionDetail;
import com.alanddev.manwal.util.Constant;

public class TransactionDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_detail);
        TransactionDetail trans = (TransactionDetail)getIntent().getParcelableExtra(Constant.TRANSACTION_OBJECT);
        if(trans==null) {
            Log.d("AAAAAAA","BBBBBBBBBB");
        }else{
            Log.d("AAAAAAAA",trans.getId()+"");
        }
    }
}
