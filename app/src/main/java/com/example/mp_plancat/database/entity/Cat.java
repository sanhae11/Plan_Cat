package com.example.mp_plancat.database.entity;

import android.graphics.Bitmap;

import androidx.annotation.DrawableRes;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Cat implements Serializable {

    @PrimaryKey
    public int catID;

    @ColumnInfo
    public String catName; //고양이 이름

    @ColumnInfo(name = "cat_description")
    public String catDescription; //고양이에 대한 설명

    @ColumnInfo
    @Ignore
    public Bitmap catImgBitmap; //image source

    @ColumnInfo
    public int isCollected; //(1 = collected, 0 = not collected)

    public Cat(){}

    public Cat(int catID, String catName, String catDescription){
        this.catID = catID;
        this.catName = catName;
        this.catDescription = catDescription;
        this.isCollected = 0;
    }

    public String getCatName() {
        return catName;
    }

    public String getCatDescription() {
        return catDescription;
    }

    public Bitmap getCatImgBitmap() {
        return catImgBitmap;
    }

    public int getIsCollected() {
        return isCollected;
    }

    public int getCatID() {
        return catID;
    }

    public void setCatID(int catID) {
        this.catID = catID;
    }
}
