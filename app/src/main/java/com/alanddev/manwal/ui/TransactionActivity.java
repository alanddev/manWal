package com.alanddev.manwal.ui;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.alanddev.manwal.R;
import com.alanddev.manwal.adapter.TransSectionPagerAdapter;
import com.alanddev.manwal.model.Transaction;
import com.alanddev.manwal.model.TransactionDetail;

import java.util.ArrayList;
import java.util.List;

public class TransactionActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private TransSectionPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        // Set up the ViewPager with the sections adapter.
        mSectionsPagerAdapter = new TransSectionPagerAdapter(getSupportFragmentManager(),getData());
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camara) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private List<Transaction> getData() {
        List<Transaction> lstItemDt = new ArrayList<Transaction>();
        Transaction itemDt = new Transaction();
        itemDt.setDay("Today");
        itemDt.setDate("16");
        itemDt.setMonth("November");
        itemDt.setYear("2015");
        itemDt.setAmount("100000");
        itemDt.setItems(getItems());
        lstItemDt.add(itemDt);

        itemDt = new Transaction();
        itemDt.setDay("Yesterday");
        itemDt.setDate("15");
        itemDt.setMonth("November");
        itemDt.setYear("2015");
        itemDt.setAmount("100000");
        itemDt.setItems(getItems());
        lstItemDt.add(itemDt);

        itemDt = new Transaction();
        itemDt.setDay("Saturday");
        itemDt.setDate("14");
        itemDt.setMonth("November");
        itemDt.setYear("2015");
        itemDt.setAmount("100000");
        itemDt.setItems(getItems());
        lstItemDt.add(itemDt);

        return lstItemDt;
    }

    private List<TransactionDetail> getItems() {
        List<TransactionDetail> lstItems = new ArrayList<TransactionDetail>();
        for (int i = 0; i < 5; i++) {
            TransactionDetail item = new TransactionDetail();
            item.setAmount("20000");
            item.setDes("Buy clothers in the market");
            item.setType("Shopping");
            lstItems.add(item);
        }
        return lstItems;
    }
}
