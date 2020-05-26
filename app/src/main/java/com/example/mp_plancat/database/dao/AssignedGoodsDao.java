package com.example.mp_plancat.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.mp_plancat.database.entity.AssignedGoods;
import com.example.mp_plancat.database.entity.Goods;

import java.util.List;

@Dao
public interface AssignedGoodsDao {

    @Insert
    void insert(AssignedGoods... assignedGoods);

    //배치된 물품 list 반환
    @Query("SELECT * FROM Goods WHERE goodsID IN (SELECT assigned_goodsID FROM AssignedGoods)")
    List<Goods> getAll();

    //배치된 물품 개수 반환
    @Query("SELECT COUNT(*) FROM AssignedGoods")
    int getCount();

    @Update
    void update(AssignedGoods... assignedGoods);

    @Delete
    void delete(AssignedGoods... assignedGoods);
}
