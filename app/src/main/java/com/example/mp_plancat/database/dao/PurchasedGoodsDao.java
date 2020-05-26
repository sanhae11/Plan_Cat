package com.example.mp_plancat.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.mp_plancat.database.entity.Goods;
import com.example.mp_plancat.database.entity.PurchasedGoods;

import java.util.List;

@Dao
public interface PurchasedGoodsDao {

    @Insert
    void insert(PurchasedGoods... purchasedGoods);

    //보유 물품 list 반환
    @Query("SELECT * FROM Goods WHERE goodsID IN (SELECT purchased_goodsID FROM PurchasedGoods)")
    List<Goods> getAll();

    //특정 카테고리의 보유 물품 list 반환
    @Query("SELECT * FROM Goods  WHERE goodsID IN (SELECT purchased_goodsID FROM PurchasedGoods) AND :category = goods_category")
    List<Goods> getAllByCategory(String category);

    @Update
    void update(PurchasedGoods... purchasedGoods);

    @Delete
    void delete(PurchasedGoods... purchasedGoods);
}
