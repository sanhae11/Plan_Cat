package com.example.mp_plancat;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.mp_plancat.database.entity.Cat;

import java.util.List;

public class CatViewModel extends AndroidViewModel {
    private CatRepository repository;
    private LiveData<List<Cat>> allCats;

    public CatViewModel(@NonNull Application application){
        super(application);
        repository = new CatRepository(application);
        allCats = repository.getAllCats();
    }

    public void insert(Cat cat){
        repository.insert(cat);
    }

    public void update(Cat cat){
        repository.update(cat);
    }
    public void delete(Cat cat){
        repository.delete(cat);
    }
    public void deleteAllCats(){
        repository.deleteAllCats();
    }
    public LiveData<List<Cat>> getAllCats(){
        return allCats;
    }
}
