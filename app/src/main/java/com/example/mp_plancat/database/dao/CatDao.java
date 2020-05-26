package com.example.mp_plancat.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.mp_plancat.database.entity.Cat;

import java.util.List;

@Dao
public interface CatDao {

    @Insert
    void insert(Cat... cats);

    //모든 고양이 list 반환
    @Query("SELECT * FROM Cat")
    List<Cat> getAll();

    @Update
    void update(Cat... cats);

    @Delete
    void delete(Cat... cats);
}
