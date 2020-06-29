package com.example.mp_plancat.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.mp_plancat.database.entity.Goods;
import com.example.mp_plancat.database.entity.PurchasedGoods;

import java.util.List;

@Dao
public interface PurchasedGoodsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(PurchasedGoods... purchasedGoods);

    //보유 물품 list 반환
    @Query("SELECT * FROM Goods WHERE goodsID IN (SELECT purchased_goodsID FROM PurchasedGoods)")
    List<Goods> getAll();

    //보유 물품 list 반환
    @Query("SELECT * FROM Goods  WHERE goodsID IN (SELECT purchased_goodsID FROM PurchasedGoods)")
    List<Goods> getAllPurchased();

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(PurchasedGoods... purchasedGoods);

    @Delete
    void delete(PurchasedGoods... purchasedGoods);
}

