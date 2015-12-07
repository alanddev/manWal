package com.alanddev.manwal.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.alanddev.manwal.R;
import com.alanddev.manwal.model.Budget;
import com.alanddev.manwal.ui.ReportActivity;
import com.alanddev.manwal.util.Utils;

import java.util.List;

/**
 * Created by ANLD on 01/12/2015.
 */
public class BudgetAdapter extends BaseAdapter {
    private List<Budget> budgets;
    private Context mContext;

    public BudgetAdapter(Context context,List<Budget> budgets){
        this.budgets = budgets;
        this.mContext = context;
    }
    @Override
    public int getCount() {
        if(budgets!=null){
            return budgets.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if(budgets!=null){
            return budgets.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        if(budgets!=null){
            return budgets.get(position).getId();
        }
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Budget budget = (Budget)this.getItem(position);
        Viewholder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_budget_list,null);
            viewHolder = new Viewholder();
            viewHolder.cateimg = ((ImageView) convertView
                    .findViewById(R.id.img_icon));
            viewHolder.catename = ((TextView) convertView
                    .findViewById(R.id.txt_budget_category));
            viewHolder.budgetamt = ((TextView) convertView
                    .findViewById(R.id.txt_budget_amount));
            viewHolder.budgetdate = ((TextView) convertView
                    .findViewById(R.id.txt_budget_date));
            viewHolder.budgetremain = ((TextView) convertView
                    .findViewById(R.id.txt_budget_amount1));
            viewHolder.progressBar = (ProgressBar)convertView
                    .findViewById(R.id.progress);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (Viewholder) convertView.getTag();
        }
        viewHolder.cateimg.setImageResource(mContext.getResources().getIdentifier("ic_category_" + budget.getCate_img(), "mipmap", mContext.getPackageName()));
        viewHolder.catename.setText(budget.getCate_name());
        viewHolder.budgetamt.setText(budget.getAmount() + "");
        viewHolder.budgetdate.setText(Utils.changeDateStr2Str2(budget.getStartdate())+" - "+Utils.changeDateStr2Str2(budget.getEnddate()));
        if(budget.getAmount()>budget.getRealamt()){
            viewHolder.budgetremain.setTextColor(mContext.getResources().getColor(R.color.colorInflow));
        }else{
            viewHolder.budgetremain.setTextColor(mContext.getResources().getColor(R.color.colorOutFlow));
        }
        viewHolder.budgetremain.setText(budget.getAmount()-budget.getRealamt()+"");
        int progress =(int) (budget.getRealamt()*100/budget.getAmount());
        if(progress<25){
            viewHolder.progressBar.setProgressDrawable(mContext.getResources().getDrawable(R.drawable.progess_drawable_25));
        }else if(progress<50){
            viewHolder.progressBar.setProgressDrawable(mContext.getResources().getDrawable(R.drawable.progess_drawable_50));
        }else if(progress<70){
            viewHolder.progressBar.setProgressDrawable(mContext.getResources().getDrawable(R.drawable.progess_drawable_70));
        }else if(progress<90){
            viewHolder.progressBar.setProgressDrawable(mContext.getResources().getDrawable(R.drawable.progess_drawable_90));
        }else{
            viewHolder.progressBar.setProgressDrawable(mContext.getResources().getDrawable(R.drawable.progess_drawable_100));
        }
        viewHolder.progressBar.setProgress(progress);
        //viewHolder.progressBar.setProgress((int)(budget.getRealamt()/budget.getAmount())*100);
        return convertView;
    }

    class Viewholder {
        public ImageView cateimg;
        public TextView catename;
        public TextView budgetamt;
        public TextView budgetremain;
        public TextView budgetdate;
        public ProgressBar progressBar;

        Viewholder() {
        }
    }
}
