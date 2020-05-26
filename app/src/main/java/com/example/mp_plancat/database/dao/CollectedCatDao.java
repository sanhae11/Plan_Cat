package com.example.mp_plancat.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.mp_plancat.database.entity.Cat;
import com.example.mp_plancat.database.entity.CollectedCat;

import java.util.List;

@Dao
public interface CollectedCatDao {

    @Insert
    void insert(CollectedCat... collectedCats);

    //수집된 고양이 list 반환
    @Query("SELECT * FROM Cat WHERE catID IN (SELECT collected_catID FROM CollectedCat)")
    List<Cat> getAll();

    //수집된 고양이 마리수 반환
    @Query("SELECT COUNT(*) FROM CollectedCat")
    int getCount();

    @Update
    void update(CollectedCat... collectedCats);

    @Delete
    void delete(CollectedCat... collectedCats);
}
