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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by td.long on 11/23/2015.
 */
public class CurrencyAdapter extends ArrayAdapter<Currency> {
    public CurrencyAdapter(Context context, List<Currency> currencies) {
        super(context, 0, currencies);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Currency currency = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_currency, parent, false);
        }
        // Lookup view for data population
        TextView tvName = (TextView) convertView.findViewById(R.id.code);
        TextView tvHome = (TextView) convertView.findViewById(R.id.symbol);
        ImageView imgIcon = (ImageView)convertView.findViewById(R.id.icon);
        // Populate the data into the template view using the data object
        tvName.setText(currency.getCode());
        tvHome.setText(currency.getSymbol());
        Resources res = convertView.getResources();
        String srcImg = "ic_currency_" + currency.getCode().toLowerCase();
        int id = res.getIdentifier(srcImg, "mipmap", getContext().getPackageName());
        Drawable image = convertView.getResources().getDrawable(id);
        imgIcon.setImageDrawable(image);
        // Return the completed view to render on screen
        return convertView;
    }
}