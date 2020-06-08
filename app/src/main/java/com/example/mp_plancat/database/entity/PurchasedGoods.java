package com.example.mp_plancat.database.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = {@ForeignKey(entity = Goods.class, parentColumns = "goodsID", childColumns = "purchased_goodsID")})
public class PurchasedGoods {

    @PrimaryKey
    @ColumnInfo(name = "purchased_goodsID")
    public int purchasedGoodsID;

    public int quantity; //보유 중인 수량

    public PurchasedGoods(){}

    public PurchasedGoods(int goodsID, int quantity){
        this.purchasedGoodsID = goodsID;
        this.quantity = quantity;
    }
}
