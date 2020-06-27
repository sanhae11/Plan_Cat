package com.example.mp_plancat;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;


import com.example.mp_plancat.database.CatDatabase;
import com.example.mp_plancat.database.dao.CatDao;
import com.example.mp_plancat.database.entity.Cat;

import java.util.List;

public class CatRepository {
    private CatDao catDao;
    private LiveData<List<Cat>> allCats;

    public CatRepository(Application application){
        CatDatabase database = CatDatabase.getInstance(application);
        catDao = database.catDao();
        allCats = catDao.getAllCats();
    }
    public void insert(Cat cat){
        new CatRepository.InsertCatAsyncTask(catDao).execute(cat);
    }
    public void update(Cat cat){
        new CatRepository.UpdateCatAsyncTask(catDao).execute(cat);
    }
    public void delete(Cat cat){
        new CatRepository.DeleteCatAsyncTask(catDao).execute(cat);
    }
    public void deleteAllCats(){
        new CatRepository.DeleteAllCatsAsyncTask(catDao).execute();
    }
    public LiveData<List<Cat>> getAllCats() {
        return allCats;
    }
    private static class InsertCatAsyncTask extends AsyncTask<Cat, Void, Void> {
        private CatDao catDao;

        private InsertCatAsyncTask(CatDao catDao){
            this.catDao = catDao;
        }
        @Override
        protected  Void doInBackground(Cat... cats){
            catDao.insert(cats[0]);
            return null;
        }
    }

    private static class UpdateCatAsyncTask extends AsyncTask<Cat, Void, Void>{
        private CatDao catDao;

        private UpdateCatAsyncTask(CatDao catDao){
            this.catDao = catDao;
        }
        @Override
        protected  Void doInBackground(Cat... cats){
            catDao.update(cats[0]);
            return null;
        }
    }

    private static class DeleteCatAsyncTask extends AsyncTask<Cat, Void, Void>{
        private CatDao catDao;

        private DeleteCatAsyncTask(CatDao catDao){
            this.catDao = catDao;
        }
        @Override
        protected  Void doInBackground(Cat... cats){
            catDao.delete(cats[0]);
            return null;
        }
    }

    private static class DeleteAllCatsAsyncTask extends AsyncTask<Void, Void, Void>{
        private CatDao catDao;

        private DeleteAllCatsAsyncTask(CatDao catDao){
            this.catDao = catDao;
        }
        @Override
        protected  Void doInBackground(Void... voids){
            catDao.deleteAllCats();
            return null;
        }
    }
}
