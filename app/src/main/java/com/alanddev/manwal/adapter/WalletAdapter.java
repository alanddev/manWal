package com.alanddev.manwal.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.alanddev.manwal.R;
import com.alanddev.manwal.model.Currency;
import com.alanddev.manwal.model.Wallet;
import com.alanddev.manwal.util.Constant;
import com.alanddev.manwal.util.Utils;

import java.util.List;

/**
 * Created by td.long on 11/23/2015.
 */
public class WalletAdapter extends ArrayAdapter<Wallet> {
    Utils utils;
    public WalletAdapter(Context context, List<Wallet> currencies) {
        super(context, 0, currencies);
        utils = new Utils();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Wallet wallet = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_wallet, parent, false);
        }
        // Lookup view for data population
        TextView tvName = (TextView) convertView.findViewById(R.id.name);
        TextView tvAmount = (TextView) convertView.findViewById(R.id.amount);
        ImageView imgIcon = (ImageView)convertView.findViewById(R.id.icon);
        ImageView imgChecked = (ImageView)convertView.findViewById(R.id.checked);
        // Populate the data into the template view using the data object
        tvName.setText(wallet.getName());
        tvAmount.setText(wallet.getAmount() + "  " + wallet.getCurrency());

//        Resources res = convertView.getResources();
//        String srcImg = "ic_currency_" + wallet.getImage().toLowerCase();
//        int id = res.getIdentifier(srcImg, "mipmap", getContext().getPackageName());
//        Drawable image = convertView.getResources().getDrawable(id);
//        imgIcon.setImageDrawable(image);

        String imagePath = wallet.getImage();
        ImageView imgWallet = (ImageView)convertView.findViewById(R.id.icon);
        imgWallet.setImageBitmap(BitmapFactory.decodeFile(Constant.PATH_IMG + "/" + imagePath));

        if (wallet.getId() == utils.getSharedPreferencesValue(convertView.getContext(), Constant.WALLET_ID)){
            imgChecked.setImageResource(R.mipmap.ic_check_green);
        }

        // Return the completed view to render on screen
        return convertView;
    }
}