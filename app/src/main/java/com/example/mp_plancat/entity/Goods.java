package com.example.mp_plancat.entity;

import android.graphics.Bitmap;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(indices = {@Index(value = {"goods_name"}, unique = true)})
public class Goods {

    @PrimaryKey(autoGenerate = true)
    public int goodsID;

    @ColumnInfo(name = "goods_name") //물품 이름
    public String goodsName;

    @ColumnInfo(name = "goods_description") //물품에 대한 설명
    public String goodsDescription;

    @ColumnInfo(name = "goods_category") //카테고리; Toy, Food, Furn(=Furniture)
    public String goodsCategory;

    @ColumnInfo(name = "purchase_point") //구매 시 필요한 포인트
    public int purchasePoint;

    @ColumnInfo(name = "point_type")
    public String pointType; // 포인트 타입; N(=Normal Point), S(=Special Point)

    @Ignore
    @ColumnInfo(name = "goods_pic")
    Bitmap goodsPic;

    public Goods(){}

    public Goods(String goodsName, String goodsDescription, String goodsCategory, int purchasePoint, String pointType, Bitmap goodsPic){
        this.goodsName = goodsName;
        this.goodsDescription = goodsDescription;
        this.goodsCategory = goodsCategory;
        this.purchasePoint = purchasePoint;
        this.pointType = pointType;
        this.goodsPic = goodsPic;
    }
}
