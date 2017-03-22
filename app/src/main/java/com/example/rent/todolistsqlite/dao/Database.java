package com.example.rent.todolistsqlite.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by RENT on 2017-03-21.
 */

public class Database {

    public static TaskDao taskDao;
    TaskHelper taskHelper;

    public Database(Context context){
        taskHelper = new TaskHelper(context);
        SQLiteDatabase db = taskHelper.getWritableDatabase();
        taskDao = new TaskDao(db);
    }


    class TaskHelper extends SQLiteOpenHelper{

        public TaskHelper(Context context) {
            super(context, TaskContract.DB_NAME, null, TaskContract.DB_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            String database = "CREATE TABLE "+ TaskContract.TaskEntry.TABLE
                    + "("+ TaskContract.TaskEntry.COL_TASK_TITLE+")";

            db.execSQL(database);

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ TaskContract.TaskEntry.TABLE);

        }
    }



}
