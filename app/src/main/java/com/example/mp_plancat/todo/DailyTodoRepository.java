package com.example.mp_plancat.todo;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.mp_plancat.database.TodoDatabase;
import com.example.mp_plancat.database.dao.TodoDao;
import com.example.mp_plancat.database.entity.Todo;

import java.util.List;

public class DailyTodoRepository {
    private TodoDao todoDao;
    private LiveData<List<Todo>> allTodos;

    public DailyTodoRepository(Application application){
        TodoDatabase database = TodoDatabase.getInstance(application);
        todoDao = database.todoDao();
        allTodos = todoDao.getAllByCategory("D");
        //Todo 카테고리에 맞게 가져오도록 ,, 바꿔야 함 ,,, sort 한 곳에다가 해보기!!!!!!!!!!!!!!!!!!!!!!1
    }
    public void insert(Todo todo){
        new InsertTodoAsyncTask(todoDao).execute(todo);
    }
    public void update(Todo todo){
        new UpdateTodoAsyncTask(todoDao).execute(todo);
    }
    public void delete(Todo todo){
        new DeleteTodoAsyncTask(todoDao).execute(todo);
    }
    public void deleteAllTodos(){
        new DeleteAllTodosAsyncTask(todoDao).execute();
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
