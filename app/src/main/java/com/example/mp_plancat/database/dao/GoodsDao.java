package com.example.mp_plancat.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.mp_plancat.database.entity.Goods;

import java.util.List;

@Dao
public interface GoodsDao {

    @Insert
    void insert(Goods... goods);

    //특정 카테고리의 goods list 반환
    @Query("SELECT * FROM Goods WHERE :category = goods_category")
    List<Goods> getAllByCategory(String category);

    @Query("SELECT * FROM Goods WHERE ")

    @Update
    void update(Goods... goods);

    @Delete
    void delete(Goods... goods);
}
