package com.example.pavani.movieinfo;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by pavani on 29/5/18.
 */

public class MyContentProvider extends ContentProvider {

    public static final int CODE_TABLE = 100;

    public static final int CODE_COLUMN = 101;

    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private FavDBHelper mFavDBHelper;

    private static UriMatcher buildUriMatcher() {

        final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = TaskContract.AUTHORITY;

        uriMatcher.addURI(authority, TaskContract.PATH_TASKS, CODE_TABLE);

        uriMatcher.addURI(authority, TaskContract.PATH_TASKS + "/*", CODE_COLUMN);
        return uriMatcher;

    }

    @Override
    public boolean onCreate() {

        Context context = getContext();
        mFavDBHelper = new FavDBHelper(context);

        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder)
    {

        Cursor cursor;
        switch (sUriMatcher.match(uri)) {

            case CODE_COLUMN: {
                cursor = mFavDBHelper.getReadableDatabase().
                        query(TaskContract.TaskEntry.Table_Name, null,
                                selection,selectionArgs, null, null, null);
                break;

            }
            case CODE_TABLE: {

                cursor = mFavDBHelper.getReadableDatabase().query(
                        TaskContract.TaskEntry.Table_Name, null, null,
                        null, null, null, null);
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown Uri: " + uri);
        }
        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;

    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        long id = 0;

        final SQLiteDatabase db = mFavDBHelper.getWritableDatabase();
        switch (sUriMatcher.match(uri)) {
            case CODE_TABLE:
                id = db.insert(TaskContract.TaskEntry.Table_Name, null, contentValues);
                break;
            default:
                throw new UnsupportedOperationException("Unknown Uri: " + uri);
        }
        Uri uri1 = Uri.parse("" + id);
        return uri1;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        int RowsDeleted=0;
        switch (sUriMatcher.match(uri)) {
            case CODE_COLUMN:
                RowsDeleted = mFavDBHelper.getWritableDatabase().delete(TaskContract.TaskEntry.Table_Name,selection, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown Uri: " + uri);
        }
        if (RowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return RowsDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }
}
