package com.alanddev.manwal.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.alanddev.manwal.R;
import com.alanddev.manwal.helper.IDataSource;
import com.alanddev.manwal.helper.MwSQLiteHelper;
import com.alanddev.manwal.model.Category;
import com.alanddev.manwal.model.Currency;
import com.alanddev.manwal.model.Model;
import com.alanddev.manwal.util.Constant;
import com.alanddev.manwal.util.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ANLD on 23/11/2015.
 */
public class CategoryController  implements IDataSource {
    private SQLiteDatabase database;
    private MwSQLiteHelper dbHelper;

    private String [] allColumns = {
            MwSQLiteHelper.COLUMN_CATE_ID,
            MwSQLiteHelper.COLUMN_CATE_NAME,
            MwSQLiteHelper.COLUMN_CATE_IMG,
            MwSQLiteHelper.COLUMN_CATE_TYPE
    };

    public CategoryController(Context context){
        dbHelper = new MwSQLiteHelper(context);
    }

    @Override
    public void open() {
        database = dbHelper.getWritableDatabase();
    }

    @Override
    public void close() {
        dbHelper.close();
    }

    @Override
    public Model create(Model data) {
        ContentValues values = new ContentValues();
        Category category  = (Category)data;
        values.put(MwSQLiteHelper.COLUMN_CATE_NAME, category.getName());
        values.put(MwSQLiteHelper.COLUMN_CATE_IMG, category.getImage());
        values.put(MwSQLiteHelper.COLUMN_CATE_TYPE, category.getType());
        database.insert(MwSQLiteHelper.TABLE_CATEGORY, null,
                values);
        return category;
    }

    @Override
    public void update(Model data) {

    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
     public List<Model> getAll() {
        List<Model> categories = new ArrayList<Model>();
        Cursor cursor = database.query(MwSQLiteHelper.TABLE_CATEGORY,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Category category = (Category)cursorTo(cursor);
            categories.add(category);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return categories;
    }

    @Override
    public Model get(String query) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        Cursor cursor = database.query(MwSQLiteHelper.TABLE_CATEGORY,
                allColumns, query, null,
                null, null, null);
        cursor.moveToFirst();
        Category category = (Category)cursorTo(cursor);
        cursor.close();
        return category;
    }

    @Override
    public List<Model> getAll(String query) {
        return null;
    }

    public Category getByName(String catename){
        StringBuffer sql = new StringBuffer("SELECT * FROM ").
                append(MwSQLiteHelper.TABLE_CATEGORY).append(" WHERE ").append(MwSQLiteHelper.COLUMN_CATE_NAME)
                .append("= ?");
        String[] atts = new String[]{catename};
        Cursor cursor = database.rawQuery(sql.toString(),atts);
        cursor.moveToFirst();
        Category category = (Category)cursorTo(cursor);
        cursor.close();
        return category;
    }

    @Override
    public Model cursorTo(Cursor cursor) {
        Category category = new Category();
        try {
            category.setId(cursor.getInt(0));
            category.setName(cursor.getString(1));
            category.setImage(cursor.getString(2));
            category.setType(cursor.getInt(3));
        }catch (Exception ex){
            //don't do anything
        }
        return category;
    }

    public void init(Context context){
        String[] arrayExCateName = context.getResources().getStringArray(R.array.exp_category_names);
        String[] arrayExCateImg = context.getResources().getStringArray(R.array.exp_category_image);
        int i = Math.min(arrayExCateName.length, arrayExCateImg.length);
        List<Category> lstCategories = new ArrayList<Category>();
        for(int j=0;j<i;j++){
            Category category = new Category();
            category.setName(arrayExCateName[j]);
            category.setImage(arrayExCateImg[j]);
            category.setType(Constant.EXPENSE_TYPE);
            lstCategories.add(category);
        }
        String[] arrayInCateName = context.getResources().getStringArray(R.array.inc_category_names);
        String[] arrayInCateImg = context.getResources().getStringArray(R.array.inc_category_image);
        i = Math.min(arrayInCateName.length,arrayInCateImg.length);
        for(int j=0;j<i;j++){
            Category category = new Category();
            category.setName(arrayInCateName[j]);
            category.setImage(arrayInCateImg[j]);
            category.setType(Constant.INCOME_TYPE);
            lstCategories.add(category);
        }

        //add category all
        Category category = new Category();
        category.setType(Constant.ALL_CATEGORY_TYPE);
        category.setName(context.getString(R.string.all_category_name));
        category.setImage(context.getString(R.string.all_category_image));
        lstCategories.add(category);
        //add data
        for(int j=0;j<lstCategories.size();j++){
            create(lstCategories.get(j));
        }
    }
}
