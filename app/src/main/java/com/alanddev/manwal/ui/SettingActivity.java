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
import com.alanddev.manwal.adapter.SettingAdapter;
import com.alanddev.manwal.model.Setting;
import com.alanddev.manwal.util.Constant;
import com.alanddev.manwal.util.Utils;

import java.util.ArrayList;
import java.util.List;

public class SettingActivity extends AppCompatActivity {

    private final int REQUEST_CHANGE_THEME = 1;
    private final int REQUEST_CHANGE_NAV = 2;
    private final int REQUEST_CHANGE_LANGUAGE = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.onActivityCreateSetTheme(this);
        setContentView(R.layout.activity_setting);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ListView lvSetting = (ListView)findViewById(R.id.lstSetting);
        final SettingAdapter adapter = new SettingAdapter(this,createData());
        lvSetting.setAdapter(adapter);
        lvSetting.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int itemId = (int)adapter.getItemId(position);
                Intent intent;
                switch (itemId){
                    case Constant.CHANGE_THEME_ID:
                        intent = new Intent(getApplicationContext(),SelectThemeActivity.class);
                        intent.putExtra("SETTING_EXTRA",Constant.CHANGE_THEME_ID);
                        startActivityForResult(intent, REQUEST_CHANGE_THEME);
                        break;
                    case Constant.CHANGE_NAV_ID:
                        intent = new Intent(getApplicationContext(),SelectThemeActivity.class);
                        intent.putExtra("SETTING_EXTRA",Constant.CHANGE_NAV_ID);
                        startActivityForResult(intent, REQUEST_CHANGE_NAV);
                        break;
                    case Constant.CHANGE_LANGUAGE_ID:
                        intent = new Intent(getApplicationContext(),SelectThemeActivity.class);
                        intent.putExtra("SETTING_EXTRA",Constant.CHANGE_LANGUAGE_ID);
                        startActivityForResult(intent, REQUEST_CHANGE_LANGUAGE);
                        break;
                }
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

    private List<Setting> createData(){
        List<Setting> lstSetting = new ArrayList<Setting>();
        String[] arrSettings = getResources().getStringArray(R.array.settings);
        if(arrSettings!=null&&arrSettings.length>0) {
            for (int i = 0; i < arrSettings.length;i++) {
                Setting setting = new Setting();
                setting.setId(i);
                setting.setTitle(arrSettings[i]);
                lstSetting.add(setting);
            }
        }
        return lstSetting;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_CHANGE_THEME){
            Utils.onActivityCreateSetTheme(this);
            Utils.refresh(this);
        }
    }
}
