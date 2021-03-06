package com.example.mp_plancat.database.entity;

import android.graphics.Bitmap;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Goods implements Serializable {

    @PrimaryKey
    public int goodsID;

    @ColumnInfo(name = "goods_name") //물품 이름
    public String goodsName;

    @ColumnInfo(name = "goods_description") //물품에 대한 설명
    public String goodsDescription;

    @ColumnInfo(name = "purchase_point") //구매 시 필요한 포인트
    public int purchasePoint;

    @ColumnInfo(name = "img_src")
    public String goodsImgSrc; // image source

    @ColumnInfo(name = "is_purchased")
    public int isPurchased; // (1 = 구매함, 0 = 구매 안 함)

    @ColumnInfo(name = "is_assigned")
    public int isAssigned; //(1 = 배치됨, 0 = 배치 안 됨)


    public Goods(){}

    public Goods(int goodsID, String goodsName, String goodsDescription, int purchasePoint){
        this.goodsID = goodsID;
        this.goodsName = goodsName;
        this.goodsDescription = goodsDescription;
        this.purchasePoint = purchasePoint;
        this.goodsImgSrc = "";
        this.isPurchased = 0;
        this.isAssigned = 0;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsDescription() {
        return goodsDescription;
    }

    public void setGoodsDescription(String goodsDescription) {
        this.goodsDescription = goodsDescription;
    }

    public int getPurchasePoint() {
        return purchasePoint;
    }

    public void setPurchasePoint(int purchasePoint) {
        this.purchasePoint = purchasePoint;
    }

    public String getGoodsImgSrc() {
        return goodsImgSrc;
    }

    public void setGoodsImgSrc(String goodsImgSrc) {
        this.goodsImgSrc = goodsImgSrc;
    }

    public int getIsPurchased() {
        return isPurchased;
    }

    public void setIsPurchased(int isPurchased) {
        this.isPurchased = isPurchased;
    }

    public int getIsAssigned() {
        return isAssigned;
    }

    public void setIsAssigned(int isAssigned) {
        this.isAssigned = isAssigned;
    }

    public int getGoodsID() {
        return goodsID;
    }

    public void setGoodsID(int goodsID) {
        this.goodsID = goodsID;
    }
}
