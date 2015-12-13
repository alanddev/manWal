package com.alanddev.manwal.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.alanddev.manwal.R;
import com.alanddev.manwal.model.Setting;
import com.alanddev.manwal.model.Theme;

import java.util.List;

/**
 * Created by ANLD on 08/12/2015.
 */
public class SellectThemeAdapter extends BaseAdapter {
    private List<Theme> themes;
    private Context mContext;

    public SellectThemeAdapter(Context context, List<Theme> datas){
        this.themes = datas;
        this.mContext = context;
    }
    @Override
    public int getCount() {
        if(themes!=null){
            return themes.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if(themes!=null){
            return themes.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Theme theme = (Theme)this.getItem(position);
        Viewholder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_themes,null);
            viewHolder = new Viewholder();
            viewHolder.imgthemes = ((ImageView) convertView
                    .findViewById(R.id.img_theme));
            viewHolder.imgcheck = ((ImageView) convertView
                    .findViewById(R.id.img_check));
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (Viewholder) convertView.getTag();
        }
        viewHolder.imgthemes.setImageResource(mContext.getResources().getIdentifier(theme.getColor(),"color",mContext.getPackageName()));

        return convertView;
    }

    class Viewholder {
        public ImageView imgthemes;
        public ImageView imgcheck;

        Viewholder() {
        }
    }
}
