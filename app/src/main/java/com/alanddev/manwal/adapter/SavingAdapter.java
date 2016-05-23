package com.alanddev.manwal.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.alanddev.manwal.R;
import com.alanddev.manwal.model.Budget;
import com.alanddev.manwal.model.Saving;
import com.alanddev.manwal.util.Constant;
import com.alanddev.manwal.util.Utils;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.List;

/**
 * Created by ANLD on 01/12/2015.
 */
public class SavingAdapter extends BaseAdapter {
    private List<Saving> savings;
    private Context mContext;
    public SavingAdapter(Context context, List<Saving> savings){
        this.savings = savings;
        this.mContext = context;
    }
    @Override
    public int getCount() {
        if(savings!=null){
            return savings.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if(savings!=null){
            return savings.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        if(savings!=null){
            return savings.get(position).getId();
        }
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Saving saving = (Saving)this.getItem(position);
        Viewholder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_saving_list,null);
            viewHolder = new Viewholder();
            viewHolder.savingamt = ((TextView) convertView
                    .findViewById(R.id.txt_saving_amount));
            viewHolder.savingdate = ((TextView) convertView
                    .findViewById(R.id.txt_saving_date));
            viewHolder.savingamt0 = ((TextView) convertView
                    .findViewById(R.id.txt_saving_amount0));
            viewHolder.savingremain = ((TextView) convertView
                    .findViewById(R.id.txt_left_date));
            viewHolder.progressBar = (ProgressBar)convertView
                    .findViewById(R.id.progress);
            viewHolder.savingTitle = (TextView)convertView
                    .findViewById(R.id.txt_saving_desc);
            viewHolder.savingAllIncome = (TextView)convertView.findViewById(R.id.txtAllIncome);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (Viewholder) convertView.getTag();
        }

        Float fAmount = saving.getAmount();
        NumberFormat formatter = new DecimalFormat("###,###,###,###.##");
        String sAmount =  formatter.format(fAmount);
        viewHolder.savingamt.setText(sAmount);
        int progress =(int) (saving.getAmount_real()*100/saving.getAmount());
        viewHolder.savingdate.setText(Utils.changeDateStr2Str2(saving.getStartdate()) + " - " + Utils.changeDateStr2Str2(saving.getEnddate()));
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(Utils.changeStr2Date(saving.getStartdate(), Constant.DATE_FORMAT_DB));

        viewHolder.savingTitle.setText(saving.getTitle());
        viewHolder.savingamt0.setText(saving.getAmount_real() + "");
        if(saving.getAmount_real()<=saving.getAmount()) {
            viewHolder.savingAllIncome.setText(mContext.getResources().getString(R.string.budget_letf_title));
            viewHolder.savingremain.setText(saving.getAmount()-saving.getAmount_real()+"");
        }else{
            viewHolder.savingAllIncome.setText(mContext.getResources().getString(R.string.saving_over));
            viewHolder.savingremain.setText(saving.getAmount_real()-saving.getAmount()+"");
        }
        viewHolder.progressBar.setProgress(progress);

        return convertView;
    }

    class Viewholder {
        public TextView savingamt;
        public TextView savingremain;
        public TextView savingamt0;
        public TextView savingdate;
        public TextView savingAllIncome;
        public ProgressBar progressBar;
        public TextView savingTitle;

        Viewholder() {
        }
    }
}
