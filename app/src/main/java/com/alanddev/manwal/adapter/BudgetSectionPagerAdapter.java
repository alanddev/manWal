package com.alanddev.manwal.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import com.alanddev.manwal.R;
import com.alanddev.manwal.fragment.BudgetFragment;
import com.alanddev.manwal.fragment.TransactionFragment;
import com.alanddev.manwal.model.Budget;
import com.alanddev.manwal.model.Budgets;
import com.alanddev.manwal.model.Transactions;

import java.util.Comparator;
import java.util.List;

/**
 * Created by ANLD on 18/11/2015.
 */
public class BudgetSectionPagerAdapter extends FragmentStatePagerAdapter {
    private List<Budgets> datas;
    private Context mContext;
    public BudgetSectionPagerAdapter(FragmentManager fm, List<Budgets> datas,Context context) {
        super(fm);
        this.datas = datas;
        mContext=context;
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        Fragment fragment = BudgetFragment.newInstance(position + 1, datas);
        return fragment;
    }

    @Override
    public int getItemPosition(Object object){
        return POSITION_NONE;
    }




    @Override
    public int getCount() {
        // Show 3 total pages.
        if(datas!=null&&datas.size()>0) {
            return datas.size();
        }else{
            return 0;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return mContext.getString(R.string.budget_applied);
            case 1:
                return mContext.getString(R.string.budget_finished);
        }
        return null;
    }

    public void setData(List<Budgets> datas){
        this.datas = datas;
    }
}
