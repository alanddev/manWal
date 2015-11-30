package com.alanddev.manwal.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.alanddev.manwal.R;
import com.alanddev.manwal.model.Currency;
import com.alanddev.manwal.model.TransactionSum;
import com.alanddev.manwal.util.Constant;

import java.util.List;

/**
 * Created by td.long on 11/23/2015.
 */
public class TransactionSumAdapter extends ArrayAdapter<TransactionSum> {
    public TransactionSumAdapter(Context context, List<TransactionSum> transactions) {
        super(context, 0, transactions);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        TransactionSum transaction = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_trans_sum, parent, false);
        }
        // Lookup view for data population
        TextView tvTitle = (TextView) convertView.findViewById(R.id.txtTitle);
        TextView tvAmount = (TextView) convertView.findViewById(R.id.txtAmount);
        ImageView imgIcon = (ImageView)convertView.findViewById(R.id.imgIcon);
        // Populate the data into the template view using the data object
        tvTitle.setText(transaction.getCatName());
        tvAmount.setText(Float.toString(transaction.getAmount()));
        if (transaction.getType() == Constant.CAT_TYPE_EXPENSE){
            tvAmount.setTextColor(convertView.getResources().getColor(R.color.colorOutFlow));
        }else{
            tvAmount.setTextColor(convertView.getResources().getColor(R.color.colorInflow));
        }


        Resources res = convertView.getResources();
        String srcImg = "ic_category_" + transaction.getCatImage().toLowerCase();
        int id = res.getIdentifier(srcImg, "mipmap", getContext().getPackageName());
        Drawable image = convertView.getResources().getDrawable(id);
        imgIcon.setImageDrawable(image);

        // Return the completed view to render on screen
        return convertView;
    }
}