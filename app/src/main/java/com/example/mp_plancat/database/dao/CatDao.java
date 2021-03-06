package com.example.mp_plancat.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.mp_plancat.database.entity.Cat;

import java.util.List;

@Dao
public interface CatDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Cat... cats);

    //모든 고양이 list 반환
    @Query("SELECT * FROM Cat")
    LiveData<List<Cat>> getAllCats();

    @Query("SELECT COUNT(*) FROM Cat")
    int getNumOfCats();

    @Query("SELECT COUNT(*) FROM Cat WHERE isCollected = 1")
    int getNumOfCollectedCats();

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(Cat... cats);

    @Delete
    void delete(Cat... cats);

    @Query("DELETE FROM Cat")
    void deleteAllCats();
}
