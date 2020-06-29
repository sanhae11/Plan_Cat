package com.example.mp_plancat.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.mp_plancat.database.entity.Goods;

import java.util.List;

@Dao
public interface GoodsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Goods... goods);

    //goods list 반환
    @Query("SELECT * FROM Goods")
    LiveData<List<Goods>> getAllGoods();

    @Query("SELECT * FROM Goods WHERE is_assigned == 1")
    List<Goods> getAllAssigned();

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(Goods... goods);

    @Delete
    void delete(Goods... goods);

    @Query("DELETE FROM Goods")
    void deleteAllGoods();
}

