package com.example.mp_plancat.database.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(indices = @Index(value = {"goods_ID"}, unique = true))
public class Location {

    @PrimaryKey
    @ColumnInfo(name = "location_ID")
    public int locationID; //배치된 물품의 해당 location; 1,2,3,4,5...

    @ColumnInfo(name = "goods_ID")
    public int goodsID; //배치된 물품

    public Location(){}

    public Location(int locationID, int goodsID){
        this.locationID = locationID;
        this.goodsID = goodsID;
    }

    public Location(int locationID){
        this.locationID = locationID;
        this.goodsID = 0;
    }
}
