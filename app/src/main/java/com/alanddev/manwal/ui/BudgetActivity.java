package com.alanddev.manwal.ui;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ListView;
import android.widget.TextView;

import com.alanddev.manwal.R;
import com.alanddev.manwal.adapter.BudgetAdapter;
import com.alanddev.manwal.adapter.BudgetSectionPagerAdapter;
import com.alanddev.manwal.adapter.CategoryAdapter;
import com.alanddev.manwal.controller.BudgetController;
import com.alanddev.manwal.model.Budget;
import com.alanddev.manwal.model.Budgets;
import com.alanddev.manwal.util.Constant;

import java.util.ArrayList;
import java.util.List;

public class BudgetActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private BudgetSectionPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new BudgetSectionPagerAdapter(getSupportFragmentManager(),getData(),this);

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),BudgetAddActivity.class);
                startActivityForResult(intent,Constant.BUDGET_ADD_REQUEST);
            }
        });

        mViewPager.setCurrentItem(0);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_budget, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        // check if the request code is same as what is passed  here it is 2
        if(requestCode==Constant.BUDGET_ADD_REQUEST&&resultCode==Constant.BUDGET_ADD_RESULT) {
            notifyDataSetChanged();
        }
    }

    private void notifyDataSetChanged(){
        mSectionsPagerAdapter.setData(getData());
        mSectionsPagerAdapter.notifyDataSetChanged();
    }

    private List<Budgets> getData(){
        List<Budgets> budgetsList = new ArrayList<Budgets>();
        BudgetController controller = new BudgetController(getApplicationContext());
        controller.open();
        Budgets budgets = new Budgets();
        List<Budget> budgetList = controller.getAll(Constant.BUDGET_AVAL_TYPE);
        budgets.setItems(budgetList);
        budgetsList.add(budgets);

        budgets = new Budgets();
        budgetList = controller.getAll(Constant.BUDGET_EX_TYPE);
        budgets.setItems(budgetList);
        budgetsList.add(budgets);

        controller.close();
        return budgetsList;
    }
}
