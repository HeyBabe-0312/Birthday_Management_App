package com.example.ghichu;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class GhiChuHelper extends SQLiteOpenHelper {
    public GhiChuHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    //Truy van khong tra ve kq
    public void queryData(String sql){
        SQLiteDatabase database = getWritableDatabase();  //ghi
        database.execSQL(sql);
    }
    //Truy van tra ve kq
    public Cursor getData(String sql){
        SQLiteDatabase database = getReadableDatabase();  //doc
        return database.rawQuery(sql,null);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
