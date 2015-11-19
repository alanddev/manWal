package com.alanddev.manwal.helper;

import android.database.Cursor;

import com.alanddev.manwal.model.Model;

import java.util.List;

/**
 * Created by td.long on 11/19/2015.
 */
public interface IDataSource {
    public Model create(MwSQLiteHelper dbHelper,Model data);
    public void update (MwSQLiteHelper dbHelper,Model data);
    public int getCount(MwSQLiteHelper dbHelper);
    public List<Model> getAll(MwSQLiteHelper dbHelper);
    public Model get(MwSQLiteHelper dbHelper,String query);
    public Model cursorTo(Cursor cursor);

}
