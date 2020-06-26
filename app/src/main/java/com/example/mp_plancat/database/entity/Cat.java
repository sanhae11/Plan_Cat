package com.example.mp_plancat.database.entity;

import android.graphics.Bitmap;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(indices = {@Index(value = {"cat_description"}, unique = true)})
public class Cat {

    @PrimaryKey(autoGenerate = true)
    public int catID;

    @ColumnInfo
    public String catName; //고양이 이름

    @ColumnInfo(name = "cat_description")
    public String catDescription; //고양이에 대한 설명

    @ColumnInfo
    public String cat_imgSrc; //image source

    @ColumnInfo
    public int isCollected; //(1 = collected, 0 = not collected)

    public Cat(){}

    public Cat(String catName, String catDescription, String cat_imgSrc){
        this.catName = catName;
        this.catDescription = catDescription;
        this.cat_imgSrc = cat_imgSrc;
        this.isCollected = 0;
    }
}
