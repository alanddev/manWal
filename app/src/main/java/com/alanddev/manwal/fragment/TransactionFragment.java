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
import com.foound.widget.AmazingListView;

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
        Transactions transactions = transactionses.get(sectionNumber - 1);
        AmazingListView lsComposer = (AmazingListView) rootView.findViewById(R.id.lsttransaction);
        lsComposer.setAdapter(adapter = new TransactionAdapter(getActivity().getApplicationContext(), inflater, transactions.getItems()));
        TextView txtheader = (TextView)rootView.findViewById(R.id.txtheadtitle);
        txtheader.setText(transactions.getTitle());
        View header = inflater.inflate(R.layout.trans_header_list, null, false);
        if(transactions.getItems()!=null&&transactions.getItems().size()>0){
            TextView txtInflowAmt = (TextView)header.findViewById(R.id.txtInflowAmt);
            TextView txtOutflowAmt = (TextView)header.findViewById(R.id.txtOutflowAmt);
            TextView txtNetAmt = (TextView)header.findViewById(R.id.txtallAmt);
            txtInflowAmt.setText(transactions.getInamount()+"");
            txtOutflowAmt.setText(transactions.getExamount()+"");
            txtNetAmt.setText(transactions.getNetamount()+"");
            txtheader.setText((transactions.getTitle()));
            lsComposer.addHeaderView(header);
        }
        return rootView;
    }

}




