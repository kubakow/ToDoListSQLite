package com.example.rent.todolistsqlite.dao;

import java.util.List;

/**
 * Created by RENT on 2017-03-21.
 */

public interface ITaskDao {

    List<Task> fetchAllTasks();
    boolean addTask(Task task);
    boolean deleteTask(Task task);


}
