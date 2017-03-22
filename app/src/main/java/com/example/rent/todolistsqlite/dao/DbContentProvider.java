package com.example.rent.todolistsqlite.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by RENT on 2017-03-21.
 */

public class DbContentProvider {

    SQLiteDatabase db;

    public DbContentProvider(SQLiteDatabase db) {
        this.db = db;
    }

    protected Cursor query(String tableName, String[] columnNames){
        return db
                .query(tableName,
                columnNames,
                null, null, null, null, null);
    }

    protected boolean insert(String tableName, ContentValues values){
        return db.insertWithOnConflict(
                tableName,
                null,
                values,
                SQLiteDatabase.CONFLICT_REPLACE)>-1;
    }

    protected boolean delete(String tableName, String selectionString, String[] selectionArgs){
        return db.delete(tableName,
                selectionString,
                selectionArgs)>0;
    }

}
