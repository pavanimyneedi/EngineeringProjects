package com.example.pavani.movieinfo;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by pavani on 29/5/18.
 */

public class FavDBHelper extends SQLiteOpenHelper {
    public static final String DataBase_Name = "FavouriteDataBase";
    public static final int version = 7;
    Context context;


    public FavDBHelper(Context context) {
        super(context, DataBase_Name, null, version);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String q="CREATE TABLE "+ TaskContract.TaskEntry.Table_Name+"("+ TaskContract.TaskEntry._ID+" INTEGER AUTO INCREMENT,"
                + TaskContract.TaskEntry.id_mov+" TEXT PRIMARY KEY,"
                + TaskContract.TaskEntry.title+" TEXT NOT NULL,"
                + TaskContract.TaskEntry.release_date+" TEXT NOT NULL,"
                + TaskContract.TaskEntry.rating+" TEXT NOT NULL,"
                + TaskContract.TaskEntry.description+" TEXT NOT NULL,"
                + TaskContract.TaskEntry.image_path+" TEXT NOT NULL,"
                + TaskContract.TaskEntry.backdrop_path+" TEXT NOT NULL)"+";";
        sqLiteDatabase.execSQL(q);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(" Drop table " + TaskContract.TaskEntry.Table_Name);
        onCreate(sqLiteDatabase);


    }
    public boolean isFavorite(int fav){
        SQLiteDatabase database=getWritableDatabase();
        String s="SELECT * FROM "+ TaskContract.TaskEntry.Table_Name + " WHERE " + TaskContract.TaskEntry.id_mov + " =?";
        Cursor cursor=database.rawQuery(s,new String[]{String.valueOf(fav)});
        boolean isFavorite =false;
        if (cursor.moveToFirst()){
            isFavorite=true;
            int count=0;
            while (cursor.moveToNext()){
                count++;
            }
        }
        cursor.close();
        database.close();
        return isFavorite;
    }
}
