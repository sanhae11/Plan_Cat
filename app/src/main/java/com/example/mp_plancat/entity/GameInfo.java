package com.example.mp_plancat.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

@Entity
public class GameInfo {

    @PrimaryKey(autoGenerate = true)
    public int userID;

    @ColumnInfo(name = "user_name")
    public String userName; //고양이 게임에서 사용할 닉네임

    @ColumnInfo(name = "normal_point")
    public int normalPoint; //일반 포인트

    @ColumnInfo(name = "special_point")
    public int specialPoint; //특별 포인트

    public GameInfo(){}

    public GameInfo(String userName){
        this.userName = userName;
        this.normalPoint = 0; //어플 처음 깔았을 때 기본으로 제공할 일반 포인트 양
        this.specialPoint = 0; //어플 처음 깔았을 때 기본으로 제공할 특별 포인트 양
    }
}
