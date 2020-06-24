package com.example.mp_plancat.database.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Calendar;

@Entity
public class Message implements Serializable, Comparable<Message>{

    @PrimaryKey(autoGenerate = true)
    public int silverMessageID;

    @ColumnInfo(name = "point_type")
    public String pointType; //S(ilver), G(old)

    @ColumnInfo
    public int day;

    @ColumnInfo
    public int month;

    @ColumnInfo
    public int year;

    public Message(int day, int month, int year, String pointType){
        this.day = day;
        this.month = month;
        this.year = year;
        this.pointType = pointType;
    }

    public Calendar getDate(){
        Calendar cal = Calendar.getInstance();
        cal.set(year, month - 1, day);
        return cal;
    }

    public int compareTo(Message message){
        return this.getDate().compareTo(message.getDate());
    }
}
