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

    @ColumnInfo(name = "cat_description")
    public String catDescription; //고양이에 대한 설명

    @Ignore
    @ColumnInfo(name = "cat_pic")
    public Bitmap catPic;

    public Cat(){}

    public Cat(String catDescription, Bitmap catPic){
        this.catDescription = catDescription;
        this.catPic = catPic;
    }
}
