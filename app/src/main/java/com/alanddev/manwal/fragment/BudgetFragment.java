package com.alanddev.manwal.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.alanddev.manwal.R;
import com.alanddev.manwal.adapter.BudgetAdapter;
import com.alanddev.manwal.adapter.TransactionAdapter;
import com.alanddev.manwal.helper.MwSQLiteHelper;
import com.alanddev.manwal.model.Budget;
import com.alanddev.manwal.model.Budgets;
import com.alanddev.manwal.model.TransactionDay;
import com.alanddev.manwal.model.TransactionDetail;
import com.alanddev.manwal.model.Transactions;
import com.alanddev.manwal.ui.BudgetActivity;
import com.alanddev.manwal.ui.BudgetAddActivity;
import com.alanddev.manwal.ui.ReportActivity;
import com.alanddev.manwal.ui.TransactionDetailActivity;
import com.alanddev.manwal.util.Constant;
import com.alanddev.manwal.util.Utils;
import com.foound.widget.AmazingListView;

import java.util.List;

/**
 * Created by ANLD on 18/11/2015.
 */
public class BudgetFragment extends Fragment{
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";
    private static List<Budgets> budgetses;

    public BudgetFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static BudgetFragment newInstance(int sectionNumber,List<Budgets> datas) {
        BudgetFragment fragment = new BudgetFragment();
        budgetses = datas;
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_budget, container, false);
        ListView lstbudget = (ListView)rootView.findViewById(R.id.lstbudget);
        final Integer sectionNumber = getArguments().getInt(ARG_SECTION_NUMBER);
        final List<Budget> budgets = budgetses.get(sectionNumber - 1).getItems();
        BudgetAdapter adapter = new BudgetAdapter(getActivity().getApplicationContext(),budgets);;
        lstbudget.setAdapter(adapter);

        lstbudget.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Budget budget = budgets.get(position);
                Intent intent = new Intent(getContext(), BudgetAddActivity.class);
                intent.putExtra(MwSQLiteHelper.COLUMN_BUDGET_ID,budget.getId());
                startActivityForResult(intent,Constant.BUDGET_ADD_REQUEST);
            }
        });

        return rootView;
    }
}




