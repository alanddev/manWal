package com.alanddev.manwal.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.alanddev.manwal.R;
import com.alanddev.manwal.model.SavingT;
import com.alanddev.manwal.util.Constant;
import com.alanddev.manwal.util.Utils;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

/**
 * Created by ANLD on 08/12/2015.
 */
public class SavingTAdapter extends BaseAdapter {
    private List<SavingT> savingTs;
    private Context mContext;

    public SavingTAdapter(Context context, List<SavingT> datas){
        this.savingTs = datas;
        this.mContext = context;
    }
    @Override
    public int getCount() {
        if(savingTs!=null){
            return savingTs.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if(savingTs!=null){
            return savingTs.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        if(savingTs!=null){
            return savingTs.get(position).getId();
        }
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SavingT savingT = (SavingT)this.getItem(position);
        Viewholder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_savingt,null);
            viewHolder = new Viewholder();
            viewHolder.createDt = ((TextView) convertView
                    .findViewById(R.id.txtSavTDate));
            viewHolder.title = ((TextView) convertView
                    .findViewById(R.id.txtSavTType));
            viewHolder.amount = ((TextView) convertView
                    .findViewById(R.id.txtSavTAmt));
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (Viewholder) convertView.getTag();
        }
        if(savingT.getType()== Constant.SAVING_DEPOSIT){
            viewHolder.title.setText(mContext.getString(R.string.saving_deposit));
            viewHolder.amount.setTextColor(mContext.getResources().getColor(R.color.colorInflow));
        }else{
            viewHolder.title.setText(mContext.getString(R.string.saving_withdrawal));
            viewHolder.amount.setTextColor(mContext.getResources().getColor(R.color.colorOutFlow));
        }
        viewHolder.createDt.setText(Utils.changeDateStr2Str2(savingT.getCreatedDt()));
        viewHolder.createDt.setText(Utils.changeDateStr2Str2(savingT.getCreatedDt()));
        Float fAmount = savingT.getAmount();
        NumberFormat formatter = new DecimalFormat("###,###,###,###.##");
        String sAmount =  formatter.format(fAmount);
        viewHolder.amount.setText(sAmount);
        return convertView;
    }

    class Viewholder {
        public TextView title;
        public TextView amount;
        public TextView createDt;

        Viewholder() {
        }
    }
}
