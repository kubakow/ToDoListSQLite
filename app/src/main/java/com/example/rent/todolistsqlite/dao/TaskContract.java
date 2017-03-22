package com.example.rent.todolistsqlite.dao;

import android.os.Build;
import android.provider.BaseColumns;

import static android.provider.BaseColumns._ID;
import static com.example.rent.todolistsqlite.dao.TaskContract.TaskEntry.TABLE;

/**
 * Created by RENT on 2017-03-18.
 */

public class TaskContract{

    public static final String DB_NAME= "tasksDB";
    public static final int DB_VERSION = 1;

    public class TaskEntry implements BaseColumns{
        public static final String TABLE = "tasks";
        public static final String COL_TASK_TITLE = "title";


    }
    public static String[] COLUMN_NAMES ={_ID, TaskEntry.COL_TASK_TITLE};

}
