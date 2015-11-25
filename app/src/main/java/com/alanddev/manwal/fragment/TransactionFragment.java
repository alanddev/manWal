package com.alanddev.manwal.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alanddev.manwal.R;
import com.alanddev.manwal.adapter.TransactionAdapter;
import com.alanddev.manwal.model.Transactions;
import com.alanddev.manwal.model.TransactionDetail;
import com.alanddev.manwal.util.Utils;
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
    private static  List<Transactions> transactionses;

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static TransactionFragment newInstance(int sectionNumber,List<Transactions> datas) {
        TransactionFragment fragment = new TransactionFragment();
        transactionses = datas;
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
        Integer sectionNumber = getArguments().getInt(ARG_SECTION_NUMBER);
        AmazingListView lsComposer = (AmazingListView) rootView.findViewById(R.id.lsttransaction);
        View header = inflater.inflate(R.layout.trans_header_list, null, false);

        TextView txtheader = (TextView)rootView.findViewById(R.id.txtheadtitle);
        txtheader.setText(Utils.changeDate2Str(transactionses.get(sectionNumber - 1).getDisplay_date()));
        List<Transactions> datas = new ArrayList<Transactions>();
        datas.add(transactionses.get(sectionNumber-1));
        lsComposer.setAdapter(adapter = new TransactionAdapter(getActivity().getApplicationContext(), inflater,datas));
        lsComposer.addHeaderView(header);


        return rootView;
    }
    /*private List<Transactions> getData() {
        List<Transactions> lstItemDt = new ArrayList<Transactions>();
        Transactions itemDt = new Transactions();
        itemDt.setDay("Today");
        itemDt.setDate("16");
        itemDt.setMonth("November");
        itemDt.setYear("2015");
        itemDt.setAmount("100000");
        itemDt.setItems(getItems());
        lstItemDt.add(itemDt);

        itemDt = new Transactions();
        itemDt.setDay("Yesterday");
        itemDt.setDate("15");
        itemDt.setMonth("November");
        itemDt.setYear("2015");
        itemDt.setAmount("100000");
        itemDt.setItems(getItems());
        lstItemDt.add(itemDt);

        itemDt = new Transactions();
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
*/

}




