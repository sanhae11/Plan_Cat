package com.example.mp_plancat.todo;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.mp_plancat.database.entity.Todo;

import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class TodoViewModel extends AndroidViewModel {
    private TodoRepository repository;
    private LiveData<List<Todo>> allTodos;

    public TodoViewModel(@NonNull Application application){
        super(application);
        repository = new TodoRepository(application);
        allTodos = repository.getAllTodos();
    }

    public void insert(Todo todo){
        repository.insert(todo);
    }

    public void update(Todo todo){
        repository.update(todo);
    }
    public void delete(Todo todo){
        repository.delete(todo);
    }
    public void deleteAllTodos(){
        repository.deleteAllTodos();
    }
    public LiveData<List<Todo>> getAllTodos(){
        return allTodos;
    }
}
