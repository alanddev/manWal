package com.alanddev.manwal.ui;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.alanddev.manwal.R;
import com.alanddev.manwal.adapter.SavingAdapter;
import com.alanddev.manwal.adapter.SavingTAdapter;
import com.alanddev.manwal.controller.SavingTController;
import com.alanddev.manwal.helper.MwSQLiteHelper;
import com.alanddev.manwal.model.SavingT;
import com.alanddev.manwal.util.Utils;

import java.util.List;

public class SavingTActivity extends AppCompatActivity {
    private int savingId = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.onActivityCreateSetTheme(this);
        Utils.setLanguage(this);
        setContentView(R.layout.activity_saving_t);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getResources().getString(R.string.title_activity_saving_t));

        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){
            savingId = bundle.getInt(MwSQLiteHelper.COLUMN_SAVING_ID, 0);
        }
        SavingTController controller = new SavingTController(this);
        controller.open();
        List<SavingT> savingTs = controller.getAllbySaving(savingId);
        controller.close();
        ListView lstSavingT = (ListView)findViewById(R.id.lstSavingT);
        SavingTAdapter adapter = new SavingTAdapter(this,savingTs);
        lstSavingT.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_category, menu);
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
        return super.onOptionsItemSelected(item);
    }

}
