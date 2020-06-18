package com.example.mp_plancat.todo.daily;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.mp_plancat.database.entity.Todo;

import java.util.List;

public class DailyTodoViewModel extends AndroidViewModel {
    private DailyTodoRepository repository;
    private LiveData<List<Todo>> allTodos;

    public DailyTodoViewModel(@NonNull Application application){
        super(application);
        repository = new DailyTodoRepository(application);
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
