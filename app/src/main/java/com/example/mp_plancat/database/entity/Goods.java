package com.example.mp_plancat.database.entity;

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

    @ColumnInfo(name = "img_src")
    public String goodsImgSrc; // image source

    @ColumnInfo(name = "is_purchased")
    public int isPurchased; // (1 = 구매함, 0 = 구매 안 함)

    @ColumnInfo(name = "is_assigned")
    public int isAssigned; //(1 = 배치됨, 0 = 배치 안 됨)


    public Goods(){}

    public Goods(String goodsName, String goodsDescription, String goodsCategory, int purchasePoint, String goodsImgSrc){
        this.goodsName = goodsName;
        this.goodsDescription = goodsDescription;
        this.goodsCategory = goodsCategory;
        this.purchasePoint = purchasePoint;
        this.goodsImgSrc = goodsImgSrc;
        this.isPurchased = 0;
        this.isAssigned = 0;
    }
}
