package com.example.rent.todolistsqlite;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.rent.todolistsqlite.dao.Database;
import com.example.rent.todolistsqlite.dao.Task;
import com.example.rent.todolistsqlite.dao.TaskContract;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Database dbAccess;
    ListView toDoList;
    private List<String> resultList;
    private CursorAdapter adapter;
    private SimpleCursorAdapter cursorAdapter;
    private SQLiteDatabase db;
    private Cursor cursor;
    AsyncTask task = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbAccess = new Database(this);

        resultList = new ArrayList<String>();
        toDoList = (ListView) findViewById(R.id.list_todo);
        cursorAdapter = new SimpleCursorAdapter(this,
                R.layout.item_todo, cursor,
                new String[] {
                        TaskContract.TaskEntry._ID,
                        TaskContract.TaskEntry.COL_TASK_TITLE
                }, new int[] {
                R.id.task_id, R.id.task_title
        },0);
        toDoList.setAdapter(cursorAdapter);
        updateUI();
    }


    abstract private class BaseTask<T> extends AsyncTask<T,Void, List<Task>>{

        @Override
        protected void onPostExecute(List<Task> result) {
            for (Task task: result) {
                Log.d("oPE", "Task: " + task.title);
            }
        }

        public List<Task> doQuery(){
           return dbAccess.taskDao.fetchAllTasks();

        }
    }

    class LoadCursorTask extends BaseTask<Void>{

        @Override
        protected List<Task> doInBackground(Void... params) {
            return doQuery();
        }
    }


    class InsertTask extends BaseTask<Task>{

        @Override
        protected List<Task> doInBackground(Task... params) {
            dbAccess.taskDao.addTask(params[0]);
            return doQuery();
        }
    }

    class DeleteTask extends BaseTask<String>{

        @Override
        protected List<Task> doInBackground(String... params) {
            Task task = new Task();
            task.title = params[0];
            dbAccess.taskDao.deleteTask(task);


            return doQuery();
        }
    }

    public void updateUI(){
        new LoadCursorTask().execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.add_action_button:
            {
                launchAddNoteWindow();
                return true;
            }
            default: return super.onOptionsItemSelected(item);
        }


    }

    private void launchAddNoteWindow() {
        final EditText editText = new EditText(this);
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Add new note")
                .setMessage("Dodaj zadanie do zrobienia: ")
                .setView(editText)
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        ContentValues values = new ContentValues();
                        values.put (TaskContract.TaskEntry.COL_TASK_TITLE, editText.getText().toString());
                        Task task = new Task();
                        task.title = editText.getText().toString();
                        new InsertTask().execute(task);
                        updateUI();

                    }
                })
                .setNegativeButton("Cancel", null)
                .create();
        dialog.show();
    }

    public void deleteTask(View view) {
        View relativeView = (View) view.getParent();
        TextView textView = (TextView)
                relativeView.findViewById(R.id.task_title);
        String taskToRemove = String.valueOf(textView.getText());

        new DeleteTask().execute(taskToRemove);

        updateUI();

    }



}
