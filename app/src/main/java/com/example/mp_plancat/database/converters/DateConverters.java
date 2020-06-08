package com.example.mp_plancat.database.converters;

import androidx.room.TypeConverter;

import java.sql.Date;

public class DateConverters {
    @TypeConverter
    public static Date stringToDate(String str){ //str의 포맷은 "yyyy-mm-dd"여야 함
        return  str == null ? null : Date.valueOf(str);
    }

    @TypeConverter
    public static String DateToString(Date date){
        return date == null ? null : date.toString();
    }
}
