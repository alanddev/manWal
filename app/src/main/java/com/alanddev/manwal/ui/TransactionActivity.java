package com.alanddev.manwal.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


import com.alanddev.manwal.R;
import com.alanddev.manwal.adapter.TransSectionPagerAdapter;
import com.alanddev.manwal.controller.TransactionController;
import com.alanddev.manwal.model.Model;
import com.alanddev.manwal.model.Transactions;
import com.alanddev.manwal.model.TransactionDetail;
import com.alanddev.manwal.util.Constant;
import com.alanddev.manwal.util.Utils;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;
import java.util.List;

public class TransactionActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private TransSectionPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    private TransactionController transController;
    //private int viewType=0;
    private SharedPreferences mShaPref;

    List<Transactions> transactionses;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mShaPref = Utils.getSharedPreferences(this);
        int viewType = mShaPref.getInt(Constant.VIEW_TYPE, 0);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), TransactionAddActivity.class);
                startActivityForResult(intent, Constant.ADD_TRANSACTION_SUCCESS);
            }
        });
        transactionses = getData(viewType);
        // Set up the ViewPager with the sections adapter.
        mSectionsPagerAdapter = new TransSectionPagerAdapter(getSupportFragmentManager(),transactionses);
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        if(transactionses.size()>0) {
            mViewPager.setCurrentItem(transactionses.size() - 2);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        /*AdView mAdView = (AdView)findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);*/
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.transaction, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if(mShaPref!=null){
            mShaPref = Utils.getSharedPreferences(this);
        }
        int viewtype=mShaPref.getInt(Constant.VIEW_TYPE, 0);
        Boolean isState = true;
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_view_day) {
            if(viewtype!=Constant.VIEW_TYPE_DAY) {
                viewtype = Constant.VIEW_TYPE_DAY;
                isState=false;
            }
        }else if (id == R.id.action_view_week) {
            if(viewtype!=Constant.VIEW_TYPE_WEEK) {
                viewtype = Constant.VIEW_TYPE_WEEK;
                isState=false;
            }
        }else if(id == R.id.action_view_month) {
            if(viewtype!=Constant.VIEW_TYPE_MONTH) {
                viewtype = Constant.VIEW_TYPE_MONTH;
                isState=false;
            }
        }else if (id == R.id.action_view_year) {
            if(viewtype!=Constant.VIEW_TYPE_YEAR) {
                viewtype = Constant.VIEW_TYPE_YEAR;
                isState=false;
            }
        }else if (id == R.id.action_view_trans) {
            if(viewtype!=Constant.VIEW_TYPE_CATE) {
                viewtype = Constant.VIEW_TYPE_CATE;
                isState=false;
            }
        }
        Utils.setSharedPreferencesValue(this,Constant.VIEW_TYPE,viewtype);
        if(!isState){
           notifyDataSetChanged();
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_wallet) {
            Intent intent = new Intent(this, WalletsActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_gallery) {
            //item.getSubMenu().add("list_submenu");
        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public void changeActivity(){
        Intent intent = new Intent(this, WalletAddActivity.class);
        startActivity(intent);
    }

    private List<Transactions> getData(int viewType){
        TransactionController controller = new TransactionController(this);
        controller.open();
        List<Transactions> lstTrans = controller.getAll(viewType);
        controller.close();
        return lstTrans;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        // check if the request code is same as what is passed  here it is 2
        if(requestCode==Constant.ADD_TRANSACTION_SUCCESS) {
            notifyDataSetChanged();
        }
    }

    private void notifyDataSetChanged(){
        transactionses = getData(mShaPref.getInt(Constant.VIEW_TYPE, 0));
        mSectionsPagerAdapter.setData(transactionses);
        mSectionsPagerAdapter.notifyDataSetChanged();
        if(transactionses.size()>0) {
            mViewPager.setCurrentItem(transactionses.size() - 2);
        }
    }
}
