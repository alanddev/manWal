package com.alanddev.manwal.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.alanddev.manwal.fragment.TransactionFragment;
import com.alanddev.manwal.model.Transactions;

import java.util.List;

/**
 * Created by ANLD on 18/11/2015.
 */
public class TransSectionPagerAdapter extends FragmentPagerAdapter {
    private List<Transactions> datas;
    public TransSectionPagerAdapter(FragmentManager fm,List<Transactions> datas) {
        super(fm);
        this.datas = datas;
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        return TransactionFragment.newInstance(position + 1);
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
        if(datas!=null){
            return datas.get(position).getDate();
        }else{
            return null;
        }
    }
}
