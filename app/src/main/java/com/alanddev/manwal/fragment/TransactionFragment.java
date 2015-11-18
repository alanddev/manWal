package com.alanddev.manwal.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alanddev.manwal.R;
import com.alanddev.manwal.adapter.TransactionAdapter;
import com.alanddev.manwal.model.Transaction;
import com.alanddev.manwal.model.TransactionDetail;
import com.foound.widget.AmazingListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ANLD on 18/11/2015.
 */
public class TransactionFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static TransactionFragment newInstance(int sectionNumber) {
        TransactionFragment fragment = new TransactionFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public TransactionFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.trans_fragment_tabbed, container, false);
        TransactionAdapter adapter;
        AmazingListView lsComposer = (AmazingListView) rootView.findViewById(R.id.lsttransaction);
        // lsComposer.setPinnedHeaderView(LayoutInflater.from(this).inflate(R.layout.item_composer_header,
        // lsComposer, false));
        List<Transaction> datas = getData();
        TextView txtheader = (TextView)rootView.findViewById(R.id.txtheadtitle);
        txtheader.setText("Today");
        lsComposer.setAdapter(adapter = new TransactionAdapter(inflater, datas));

        return rootView;
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




