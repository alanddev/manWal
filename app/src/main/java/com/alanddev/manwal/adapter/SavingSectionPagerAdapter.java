package com.alanddev.manwal.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.alanddev.manwal.R;
import com.alanddev.manwal.fragment.BudgetFragment;
import com.alanddev.manwal.fragment.SavingFragment;
import com.alanddev.manwal.model.Budgets;
import com.alanddev.manwal.model.Savings;

import java.util.List;

/**
 * Created by ANLD on 18/11/2015.
 */
public class SavingSectionPagerAdapter extends FragmentStatePagerAdapter {
    private List<Savings> datas;
    private Context mContext;
    public SavingSectionPagerAdapter(FragmentManager fm, List<Savings> datas, Context context) {
        super(fm);
        this.datas = datas;
        mContext=context;
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        Fragment fragment = SavingFragment.newInstance(position + 1, datas);
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

    public void setData(List<Savings> datas){
        this.datas = datas;
    }
}
