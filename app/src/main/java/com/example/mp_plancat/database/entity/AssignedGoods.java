package com.example.mp_plancat.database.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = {@ForeignKey(entity = PurchasedGoods.class, parentColumns = "purchased_goodsID", childColumns = "assigned_goodsID")})
public class AssignedGoods {

    @PrimaryKey
    @ColumnInfo(name = "assigned_goodsID")
    public int assignedGoodsID; //배치된 물품

    public int positionID; //배치된 물품의 해당 position; 1,2,3,4,5...

    public AssignedGoods(){}

    public AssignedGoods(int purchasedGoodsID, int positionID){
        this.assignedGoodsID = purchasedGoodsID;
        this.positionID = positionID;
    }
}
