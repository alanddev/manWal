package com.alanddev.manwal.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.alanddev.manwal.R;
import com.alanddev.manwal.adapter.SellectThemeAdapter;
import com.alanddev.manwal.adapter.SettingAdapter;
import com.alanddev.manwal.model.Setting;
import com.alanddev.manwal.model.Theme;
import com.alanddev.manwal.util.Constant;
import com.alanddev.manwal.util.Utils;

import java.util.ArrayList;
import java.util.List;

public class SelectThemeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.onActivityCreateSetTheme(this);
        setContentView(R.layout.activity_select_theme);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ListView lvTheme = (ListView)findViewById(R.id.lstthemes);
        final SellectThemeAdapter adapter = new SellectThemeAdapter(this,createData());
        lvTheme.setAdapter(adapter);
        lvTheme.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Theme theme = (Theme)parent.getAdapter().getItem(position);
                Utils.setSharedPreferencesValue(getApplicationContext(),Constant.THEME_CURRENT,theme.getTheme());
                Utils.changeToTheme(theme.getTheme());
                finish();
            }
        });

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

    private List<Theme> createData(){
        List<Theme> lstTheme = new ArrayList<Theme>();
        String[] arrThemes = getResources().getStringArray(R.array.theme_array);
        String[] arrColors = getResources().getStringArray(R.array.color_array);
        if(arrThemes!=null&&arrThemes.length>0) {
            for (int i = 0; i < arrThemes.length;i++) {
                Theme theme = new Theme();
                theme.setTheme(arrThemes[i]);
                theme.setColor(arrColors[i]);
                lstTheme.add(theme);
            }
        }
        return lstTheme;
    }

}
