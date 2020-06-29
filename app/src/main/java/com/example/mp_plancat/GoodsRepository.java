package com.example.mp_plancat;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.mp_plancat.database.GoodsDatabase;
import com.example.mp_plancat.database.dao.GoodsDao;
import com.example.mp_plancat.database.entity.Goods;

import java.util.List;

public class GoodsRepository {
    private GoodsDao goodsDao;
    private LiveData<List<Goods>> allGoods;

    public GoodsRepository(Application application){
        GoodsDatabase database = GoodsDatabase.getInstance(application);
        goodsDao = database.goodsDao();
        allGoods = goodsDao.getAllGoods();
    }
    public void insert(Goods goods){
        new GoodsRepository.InsertGoodsAsyncTask(goodsDao).execute(goods);
    }
    public void update(Goods goods){
        new GoodsRepository.UpdateGoodsAsyncTask(goodsDao).execute(goods);
    }
    public void delete(Goods goods){
        new GoodsRepository.DeleteGoodsAsyncTask(goodsDao).execute(goods);
    }
    public void deleteAllGoods(){
        new GoodsRepository.DeleteAllGoodsAsyncTask(goodsDao).execute();
    }
    public LiveData<List<Goods>> getAllGoods() {
        return allGoods;
    }
    private static class InsertGoodsAsyncTask extends AsyncTask<Goods, Void, Void> {
        private GoodsDao goodsDao;

        private InsertGoodsAsyncTask(GoodsDao goodsDao){
            this.goodsDao = goodsDao;
        }
        @Override
        protected  Void doInBackground(Goods... goods){
            goodsDao.insert(goods[0]);
            return null;
        }
    }

    private static class UpdateGoodsAsyncTask extends AsyncTask<Goods, Void, Void>{
        private GoodsDao goodsDao;

        private UpdateGoodsAsyncTask(GoodsDao goodsDao){
            this.goodsDao = goodsDao;
        }
        @Override
        protected  Void doInBackground(Goods... goods){
            goodsDao.update(goods[0]);
            return null;
        }
    }

    private static class DeleteGoodsAsyncTask extends AsyncTask<Goods, Void, Void>{
        private GoodsDao goodsDao;

        private DeleteGoodsAsyncTask(GoodsDao goodsDao){
            this.goodsDao = goodsDao;
        }
        @Override
        protected  Void doInBackground(Goods... goods){
            goodsDao.delete(goods[0]);
            return null;
        }
    }

    private static class DeleteAllGoodsAsyncTask extends AsyncTask<Void, Void, Void>{
        private GoodsDao goodsDao;

        private DeleteAllGoodsAsyncTask(GoodsDao goodsDao){
            this.goodsDao = goodsDao;
        }
        @Override
        protected  Void doInBackground(Void... voids){
            goodsDao.deleteAllGoods();
            return null;
        }
    }
}

