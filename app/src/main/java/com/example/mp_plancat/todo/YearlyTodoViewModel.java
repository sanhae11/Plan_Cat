package com.example.mp_plancat.todo;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.mp_plancat.database.entity.Todo;
import com.example.mp_plancat.todo.monthly.MonthlyTodoRepository;

import java.util.List;

public class YearlyTodoViewModel extends AndroidViewModel {
    private YearlyTodoRepository repository;
    private LiveData<List<Todo>> allTodos;

    public YearlyTodoViewModel(@NonNull Application application){
        super(application);
        repository = new YearlyTodoRepository(application);
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
    public LiveData<List<Todo>> getAllTodos() {
        return allTodos;
    }
}
