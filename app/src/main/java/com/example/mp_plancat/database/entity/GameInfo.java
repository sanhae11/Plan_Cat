package com.example.mp_plancat.database.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Calendar;

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

    @ColumnInfo(name = "last_message_updated_day")
    public int lastMessageUpdatedDay;

    @ColumnInfo(name = "last_message_updated_month")
    public int lastMessageUpdatedMonth;

    @ColumnInfo(name = "last_message_updated_year")
    public int lastMessageUpdatedYear;

    public GameInfo(){
        this.userName = "";
        this.normalPoint = 1000;
        this.specialPoint = 50;
    }

    public GameInfo(String userName){
        this.userName = userName;
        this.normalPoint = 0; //어플 처음 깔았을 때 기본으로 제공할 일반 포인트 양
        this.specialPoint = 0; //어플 처음 깔았을 때 기본으로 제공할 특별 포인트 양
    }

    public Calendar getLastMessageUpdatedDate(){
        Calendar cal = Calendar.getInstance();
        cal.set(lastMessageUpdatedYear, lastMessageUpdatedMonth - 1, lastMessageUpdatedDay);
        return cal;
    }

    public void setLastMessageUpdatedDay(int lastMessageUpdatedDay) {
        this.lastMessageUpdatedDay = lastMessageUpdatedDay;
    }

    public void setLastMessageUpdatedMonth(int lastMessageUpdatedMonth) {
        this.lastMessageUpdatedMonth = lastMessageUpdatedMonth;
    }

    public void setLastMessageUpdatedYear(int lastMessageUpdatedYear) {
        this.lastMessageUpdatedYear = lastMessageUpdatedYear;
    }

    public void setNormalPoint(int normalPoint) {
        this.normalPoint = normalPoint;
    }

    public void setSpecialPoint(int specialPoint) {
        this.specialPoint = specialPoint;
    }
}
