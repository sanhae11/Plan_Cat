package com.example.mp_plancat.database;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.mp_plancat.R;
import com.example.mp_plancat.database.dao.CatDao;
import com.example.mp_plancat.database.dao.TodoDao;
import com.example.mp_plancat.database.entity.Cat;
import com.example.mp_plancat.database.entity.Todo;

import java.io.InputStream;
import java.util.Calendar;

@Database(entities = {Cat.class}, version = 1)
public abstract class CatDatabase extends RoomDatabase {
    private static CatDatabase instance;

    public abstract CatDao catDao();

    public static synchronized CatDatabase getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(), CatDatabase.class, "cat_database").fallbackToDestructiveMigration().addCallback(roomCallback).build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new CatDatabase.PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class  PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private CatDao catDao;

        private PopulateDbAsyncTask(CatDatabase db){
            catDao = db.catDao();
        }

        @Override
        protected Void doInBackground(Void... voids){
            //Todo : 여기다가 물음표고양이들 넣어놓기!!!!!!!!!
            /*Calendar cal1 = Calendar.getInstance();
            cal1.set(2020, 7, 10);
            Calendar cal2 = Calendar.getInstance();
            cal2.set(2020, 5, 1);
            Calendar cal3 = Calendar.getInstance();
            cal2.set(2020, 5, 23);
            todoDao.insert(new Todo("소공 레포트 제출", "D", cal1, cal1, 60.0, 2));
            todoDao.insert(new Todo("졸작 딥러닝", "D", cal2, cal2, 100.0, 1));
            todoDao.insert(new Todo("모프 팀프로젝트", "D", cal3, cal3, 100.0, 1));*/

            //Bitmap bm = BitmapFactory.decodeResource(R.drawable.cat_unknown);
            catDao.insert(new Cat(1, "길냥1", "나는 길냥이예요"));
            catDao.insert(new Cat(2, "길냥2", "나는 길냥이예요"));
            catDao.insert(new Cat(3, "길냥3", "나는 길냥이예요"));
            catDao.insert(new Cat(4, "길냥4", "나는 길냥이예요"));
            catDao.insert(new Cat(5, "길냥5", "나는 길냥이예요"));
            catDao.insert(new Cat(6, "길냥6", "나는 길냥이예요"));
            catDao.insert(new Cat(7, "길냥7", "나는 길냥이예요"));
            catDao.insert(new Cat(8, "길냥8", "나는 길냥이예요"));

            return null;
        }
    }
    public void test(){

    }
}
