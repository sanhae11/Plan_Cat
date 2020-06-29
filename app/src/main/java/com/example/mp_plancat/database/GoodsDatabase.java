package com.example.mp_plancat.database;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.mp_plancat.database.dao.GoodsDao;
import com.example.mp_plancat.database.entity.Goods;

@Database(entities = {Goods.class}, version = 1)
public abstract class GoodsDatabase extends RoomDatabase {
    private static GoodsDatabase instance;

    public abstract GoodsDao goodsDao();

    public static synchronized GoodsDatabase getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(), GoodsDatabase.class, "goods_database").fallbackToDestructiveMigration().addCallback(roomCallback).build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new GoodsDatabase.PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class  PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private GoodsDao goodsDao;

        private PopulateDbAsyncTask(GoodsDatabase db){
            goodsDao = db.goodsDao();
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
            goodsDao.deleteAllGoods();

            goodsDao.insert(new Goods("고양이귀숨숨집", "고양이가 숨을 수 있다!", 1000));
            goodsDao.insert(new Goods("고양이귀숨숨집2", "고양이가 숨을 수 있다22!", 1500));

            return null;
        }
    }
    public void test(){

    }
}

