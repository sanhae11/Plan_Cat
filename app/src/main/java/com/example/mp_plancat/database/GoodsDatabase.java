package com.example.mp_plancat.database;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.mp_plancat.database.dao.CatDao;
import com.example.mp_plancat.database.dao.GoodsDao;
import com.example.mp_plancat.database.entity.Cat;
import com.example.mp_plancat.database.entity.Goods;

@Database(entities = {Goods.class}, version = 1)
public abstract class GoodsDatabase extends RoomDatabase {
    private static GoodsDatabase instance;

    // DAO 인터페이스
    public abstract GoodsDao goodsDao();

    // 인스턴스를 생성하여 반환
    public static GoodsDatabase getInstance(Context context) {
        if (instance == null) {
            synchronized (GoodsDatabase.class) {
                if (instance == null) {
                    // 데이터베이스 접근 인스턴스 생성
                    instance = Room.databaseBuilder(context.getApplicationContext(),
                            GoodsDatabase.class, "goods.db")
                            .build();
                }
            }
        }
        return instance;
    }

    private static class  PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private GoodsDao goodsDao;

        private PopulateDbAsyncTask(GoodsDatabase db){
            goodsDao = db.goodsDao();
        }

        @Override
        protected Void doInBackground(Void... voids){
            //Todo : 여기다가 고양이물건 넣어놓기!!!!!!!!!

            goodsDao.insert(new Goods("고양이숨숨집",
                    "귀여운 머리카락을 가진 고양이를 볼 수 있어요!",
                    "Furn",
                    300,
                    "@drawable/furniture_1_b"));

            return null;
        }
    }
    public void test(){

    }

}
