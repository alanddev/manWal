package com.alanddev.manwal.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.alanddev.manwal.R;
import com.alanddev.manwal.model.Category;

import java.util.List;

/**
 * Created by ANLD on 23/11/2015.
 */
public class CategoryAdapter extends BaseAdapter {

    private List<Category> categories;
    private Context mContext;

    public CategoryAdapter(Context context,List<Category> categories){
        this.categories = categories;
        this.mContext = context;
    }
    @Override
    public int getCount() {
        if(categories!=null){
            return categories.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if(categories!=null){
            return categories.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        if(categories!=null){
            return categories.get(position).getId();
        }
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Category category = (Category)this.getItem(position);
        Viewholder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_category,null);
            viewHolder = new Viewholder();
            viewHolder.avatar = ((ImageView) convertView
                    .findViewById(R.id.imgavatar));
            viewHolder.title = ((TextView) convertView
                    .findViewById(R.id.txtcategory));
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (Viewholder) convertView.getTag();
        }
        viewHolder.avatar.setImageResource(mContext.getResources().getIdentifier("ic_category_"+category.getImage(),"mipmap",mContext.getPackageName()));
        viewHolder.title.setText(category.getName());
        return convertView;
    }

    class Viewholder {
        public ImageView avatar;
        public TextView title;

        Viewholder() {
        }
    }
}
