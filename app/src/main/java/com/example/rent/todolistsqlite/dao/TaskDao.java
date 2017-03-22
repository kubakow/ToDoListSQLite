package com.example.rent.todolistsqlite.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by RENT on 2017-03-21.
 */

public class TaskDao extends DbContentProvider implements ITaskDao {

    public TaskDao(SQLiteDatabase db) {
        super(db);
    }

    @Override
    public List<Task> fetchAllTasks() {
        Cursor cursor = query(TaskContract.TaskEntry.TABLE, TaskContract.COLUMN_NAMES);
        List<Task> taskList = new ArrayList<>();

        while(cursor.moveToNext()){
            int index = cursor.getColumnIndex(TaskContract.TaskEntry._ID);
            int id = cursor.getInt(index);
            int indexTitle = cursor.getColumnIndex(TaskContract.TaskEntry.COL_TASK_TITLE);
            String title = cursor.getString(indexTitle);

            Task newTask = new Task();
            newTask.id = id;
            newTask.title = title;

            taskList.add(newTask);
        }
        return taskList;
    }

    @Override
    public boolean addTask(Task task) {
        ContentValues values = new ContentValues();
        String title = task.title;
        values.put(TaskContract.TaskEntry.COL_TASK_TITLE, title);
       return insert(TaskContract.TaskEntry.TABLE, values);

    }

    @Override
    public boolean deleteTask(Task task) {
        String selectionString = TaskContract.TaskEntry.COL_TASK_TITLE+" = ?";
        String[] selectionArgs = {task.title};
        return delete(TaskContract.TaskEntry.TABLE,
                selectionString,
                selectionArgs);

    }
}
