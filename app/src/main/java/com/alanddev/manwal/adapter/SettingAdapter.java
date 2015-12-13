package com.alanddev.manwal.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.alanddev.manwal.R;
import com.alanddev.manwal.model.Category;
import com.alanddev.manwal.model.Setting;

import java.util.List;

/**
 * Created by ANLD on 08/12/2015.
 */
public class SettingAdapter extends BaseAdapter {
    private List<Setting> settings;
    private Context mContext;

    public SettingAdapter(Context context,List<Setting> datas){
        this.settings = datas;
        this.mContext = context;
    }
    @Override
    public int getCount() {
        if(settings!=null){
            return settings.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if(settings!=null){
            return settings.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        if(settings!=null){
            return settings.get(position).getId();
        }
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Setting setting = (Setting)this.getItem(position);
        Viewholder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_setting,null);
            viewHolder = new Viewholder();
            viewHolder.title = ((TextView) convertView
                    .findViewById(R.id.txt_title));
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (Viewholder) convertView.getTag();
        }
        viewHolder.title.setText(setting.getTitle());
        return convertView;
    }

    class Viewholder {
        public TextView title;

        Viewholder() {
        }
    }
}
