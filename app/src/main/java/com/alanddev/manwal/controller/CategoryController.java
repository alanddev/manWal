package com.alanddev.manwal.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.alanddev.manwal.helper.IDataSource;
import com.alanddev.manwal.helper.MwSQLiteHelper;
import com.alanddev.manwal.model.Category;
import com.alanddev.manwal.model.Currency;
import com.alanddev.manwal.model.Model;

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
        dbHelper.close();
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
        dbHelper.close();
        return categories;
    }

    @Override
    public Model get(String query) {
        return null;
    }

    @Override
    public Model cursorTo(Cursor cursor) {
        Category category = new Category();
        category.setId(cursor.getInt(0));
        category.setName(cursor.getString(1));
        category.setImage(cursor.getString(2));
        category.setType(cursor.getInt(3));
        return category;
    }

    public void init(){

    }
}
