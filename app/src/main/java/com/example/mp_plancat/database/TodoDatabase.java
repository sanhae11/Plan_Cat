package com.example.mp_plancat.database;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.mp_plancat.database.dao.TodoDao;
import com.example.mp_plancat.database.entity.Todo;

import java.util.Calendar;

@Database(entities = {Todo.class}, version = 1)
public abstract class TodoDatabase extends RoomDatabase {
    private static TodoDatabase instance;

    public abstract TodoDao todoDao();

    public static synchronized TodoDatabase getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(), TodoDatabase.class, "todo_database").fallbackToDestructiveMigration().addCallback(roomCallback).build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class  PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private TodoDao todoDao;

        private PopulateDbAsyncTask(TodoDatabase db){
            todoDao = db.todoDao();
        }

        @Override
        protected Void doInBackground(Void... voids){
            //Todo : 현재 임시 코드; 바꾸기!!!!!!!!
            Calendar cal1 = Calendar.getInstance();
            cal1.set(2020, 7, 10);
            Calendar cal2 = Calendar.getInstance();
            cal2.set(2020, 5, 1);
            Calendar cal3 = Calendar.getInstance();
            cal2.set(2020, 5, 23);
            todoDao.insert(new Todo("소공 레포트 제출", "D", cal1, cal1, 60.0, 2));
            todoDao.insert(new Todo("졸작 딥러닝", "D", cal2, cal2, 100.0, 1));
            todoDao.insert(new Todo("모프 팀프로젝트", "D", cal3, cal3, 100.0, 1));
            return null;
        }
    }
}
