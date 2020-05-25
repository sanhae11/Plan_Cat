package com.example.mp_plancat.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = {@ForeignKey(entity = Cat.class, parentColumns = "catID", childColumns = "collected_catID")}, indices = {@Index(value = {"cat_name"}, unique = true)})
public class CollectedCat {

    @PrimaryKey
    @ColumnInfo(name = "collected_catID")
    public int collectedCatID;

    @ColumnInfo(name = "cat_name")
    public String catName; //고양이 이름

    public int intimacy; //친밀도; 1~5(5가 제일 높은 것)

    public CollectedCat(){}

    public CollectedCat(int catID, String catName){
        this.collectedCatID = catID;
        this.catName = catName;
        this.intimacy = 1;
    }
}
