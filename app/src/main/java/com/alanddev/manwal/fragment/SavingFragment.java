package com.alanddev.manwal.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.alanddev.manwal.R;
import com.alanddev.manwal.adapter.BudgetAdapter;
import com.alanddev.manwal.adapter.SavingAdapter;
import com.alanddev.manwal.helper.MwSQLiteHelper;
import com.alanddev.manwal.model.Budget;
import com.alanddev.manwal.model.Budgets;
import com.alanddev.manwal.model.Saving;
import com.alanddev.manwal.model.Savings;
import com.alanddev.manwal.ui.BudgetAddActivity;
import com.alanddev.manwal.ui.SavingAddActivity;
import com.alanddev.manwal.ui.SavingDetailActivity;
import com.alanddev.manwal.util.Constant;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ANLD on 18/11/2015.
 */
public class SavingFragment extends Fragment{
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";
    private static List<Savings> savingses;

    public SavingFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static SavingFragment newInstance(int sectionNumber,List<Savings> datas) {
        SavingFragment fragment = new SavingFragment();
        savingses = datas;
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_saving, container, false);
        ListView lstsaving = (ListView)rootView.findViewById(R.id.lstsaving);
        final Integer sectionNumber = getArguments().getInt(ARG_SECTION_NUMBER);
        final List<Saving> savings;
        if(savingses!=null&&savingses.get(sectionNumber-1)!=null) {
            savings = savingses.get(sectionNumber - 1).getItems();
        }else{
            savings = new ArrayList<Saving>();
        }
        SavingAdapter adapter = new SavingAdapter(getActivity().getApplicationContext(),savings);;
        lstsaving.setAdapter(adapter);

        lstsaving.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Saving saving = savings.get(position);
                Intent intent = new Intent(getContext(), SavingDetailActivity.class);
                intent.putExtra(MwSQLiteHelper.COLUMN_SAVING_ID,saving.getId());
                startActivityForResult(intent,Constant.SAVING_ADD_REQUEST);
            }
        });

        return rootView;
    }
}




