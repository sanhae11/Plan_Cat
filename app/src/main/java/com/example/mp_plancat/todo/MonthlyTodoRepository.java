package com.example.mp_plancat.todo;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.mp_plancat.database.TodoDatabase;
import com.example.mp_plancat.database.dao.TodoDao;
import com.example.mp_plancat.database.entity.Todo;
import com.example.mp_plancat.todo.weekly.WeeklyTodoRepository;

import java.util.List;

public class MonthlyTodoRepository {
    private TodoDao todoDao;
    private LiveData<List<Todo>> allTodos;

    public MonthlyTodoRepository(Application application){
        TodoDatabase database = TodoDatabase.getInstance(application);
        todoDao = database.todoDao();
        allTodos = todoDao.getAllByCategory("M");
    }
    public void insert(Todo todo){
        new MonthlyTodoRepository.InsertTodoAsyncTask(todoDao).execute(todo);
    }
    public void update(Todo todo){
        new MonthlyTodoRepository.UpdateTodoAsyncTask(todoDao).execute(todo);
    }
    public void delete(Todo todo){
        new MonthlyTodoRepository.DeleteTodoAsyncTask(todoDao).execute(todo);
    }
    public void deleteAllTodos(){
        new MonthlyTodoRepository.DeleteAllTodosAsyncTask(todoDao).execute();
    }
    public LiveData<List<Todo>> getAllTodos() {
        return allTodos;
    }
    private static class InsertTodoAsyncTask extends AsyncTask<Todo, Void, Void> {
        private TodoDao todoDao;

        private InsertTodoAsyncTask(TodoDao todoDao){
            this.todoDao = todoDao;
        }
        @Override
        protected  Void doInBackground(Todo... todos){
            todoDao.insert(todos[0]);
            return null;
        }
    }

    private static class UpdateTodoAsyncTask extends AsyncTask<Todo, Void, Void>{
        private TodoDao todoDao;

        private UpdateTodoAsyncTask(TodoDao todoDao){
            this.todoDao = todoDao;
        }
        @Override
        protected  Void doInBackground(Todo... todos){
            todoDao.update(todos[0]);
            return null;
        }
    }

    private static class DeleteTodoAsyncTask extends AsyncTask<Todo, Void, Void>{
        private TodoDao todoDao;

        private DeleteTodoAsyncTask(TodoDao todoDao){
            this.todoDao = todoDao;
        }
        @Override
        protected  Void doInBackground(Todo... todos){
            todoDao.delete(todos[0]);
            return null;
        }
    }

    private static class DeleteAllTodosAsyncTask extends AsyncTask<Void, Void, Void>{
        private TodoDao todoDao;

        private DeleteAllTodosAsyncTask(TodoDao todoDao){
            this.todoDao = todoDao;
        }
        @Override
        protected  Void doInBackground(Void... voids){
            todoDao.deleteAllTodos();
            return null;
        }
    }
}
