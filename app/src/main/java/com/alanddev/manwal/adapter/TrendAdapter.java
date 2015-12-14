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
import com.alanddev.manwal.model.TransactionSum;
import com.alanddev.manwal.model.Trend;
import com.alanddev.manwal.util.Constant;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

/**
 * Created by td.long on 11/23/2015.
 */
public class TrendAdapter extends ArrayAdapter<Trend> {
    public TrendAdapter(Context context, List<Trend> transactions) {
        super(context, 0, transactions);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Trend trend = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_trend, parent, false);
        }
        // Lookup view for data population
        TextView tvMonth = (TextView) convertView.findViewById(R.id.txtMonth);
        TextView tvYear = (TextView) convertView.findViewById(R.id.txtYear);
        TextView tvIncome = (TextView) convertView.findViewById(R.id.txtIncome);
        TextView tvExpense = (TextView) convertView.findViewById(R.id.txtExpense);
        TextView tvAmount = (TextView) convertView.findViewById(R.id.txtAmount);

        ImageView imgIcon = (ImageView)convertView.findViewById(R.id.imgIcon);
        // Populate the data into the template view using the data object
        tvMonth.setText(Constant.TREND_MONTH_TITLE + trend.getMonth());
        tvYear.setText(trend.getYear());
        NumberFormat formatter = new DecimalFormat("###,###,###,###.##");
        String sAmount =  formatter.format(trend.getAmount());
        tvAmount.setText(sAmount);

        if (trend.getType() == Constant.TREND_TYPE_SUB){
            tvIncome.setVisibility(View.INVISIBLE);
            tvExpense.setVisibility(View.INVISIBLE);
            tvAmount.setTextColor(convertView.getResources().getColor(R.color.colorBlack));
        }else{
            tvIncome.setText(Float.toString(trend.getAmountIn()));
            tvExpense.setText(Float.toString(trend.getAmountOut()));
            if(trend.getType() == Constant.TREND_TYPE_INCOME){
                tvAmount.setTextColor(convertView.getResources().getColor(R.color.colorInflow));
            }else if(trend.getType() == Constant.TREND_TYPE_EXPENSE)  {
                tvAmount.setTextColor(convertView.getResources().getColor(R.color.colorOutFlow));
            }else{
                if (trend.getAmount()<0){
                    tvAmount.setTextColor(convertView.getResources().getColor(R.color.colorOutFlow));
                }else{
                    tvAmount.setTextColor(convertView.getResources().getColor(R.color.colorInflow));
                }
            }
        }

        // Return the completed view to render on screen
        return convertView;
    }
}