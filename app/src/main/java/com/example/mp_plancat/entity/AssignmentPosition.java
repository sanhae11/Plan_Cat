package com.example.mp_plancat.entity;

import android.icu.text.Transliterator;
import android.widget.Button;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = {@ForeignKey(entity = PurchasedGoods.class, parentColumns = "purchased_goodsID", childColumns = "assigned_goodsID")}, indices = {@Index(value = {"assigned_goodsID"}, unique = true)})
public class AssignmentPosition {

    @PrimaryKey
    public int positionID; //1,2,3,4,5...

    @ColumnInfo(name = "assigned_goodsID")
    public int assignedGoodsID; //해당 position에 배치된 물품

    public AssignmentPosition(){}

    public AssignmentPosition(int positionID, int purchasedGoodsID){
        this.positionID = positionID;
        this.assignedGoodsID = purchasedGoodsID;
    }
}
