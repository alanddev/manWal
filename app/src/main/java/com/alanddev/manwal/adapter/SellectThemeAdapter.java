package com.alanddev.manwal.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.alanddev.manwal.R;
import com.alanddev.manwal.model.Setting;
import com.alanddev.manwal.model.Theme;
import com.alanddev.manwal.util.Utils;

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
        if(null!=theme.getColor()&&!"".equals(theme.getColor())) {
            viewHolder.imgthemes.setImageResource(mContext.getResources().getIdentifier(theme.getColor(), "color", mContext.getPackageName()));
            String currentTheme = Utils.getCurrentTheme(mContext);
            if (!currentTheme.equals(theme.getTheme())) {
                viewHolder.imgcheck.setVisibility(View.INVISIBLE);
            } else {
                viewHolder.imgcheck.setVisibility(View.VISIBLE);
            }
        }else if(null!=theme.getHeader()&&!"".equals(theme.getHeader())){
            viewHolder.imgthemes.setImageResource(mContext.getResources().getIdentifier(theme.getHeader()+"_s", "mipmap", mContext.getPackageName()));
            String currentNavHeader = Utils.getCurrentNavHeader(mContext);
            if (!currentNavHeader.equals(theme.getHeader())) {
                viewHolder.imgcheck.setVisibility(View.INVISIBLE);
            } else {
                viewHolder.imgcheck.setVisibility(View.VISIBLE);
            }
        }

        return convertView;
    }

    class Viewholder {
        public ImageView imgthemes;
        public ImageView imgcheck;

        Viewholder() {
        }
    }
}
