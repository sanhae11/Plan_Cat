package com.example.mp_plancat.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;


import com.example.mp_plancat.database.entity.Message;
import com.example.mp_plancat.database.entity.Todo;

import java.util.List;

@Dao
public interface MessageDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Message... messages);

    //모든 message 리스트 반환
    @Query("SELECT * FROM Goods")
    LiveData<List<Message>> getAllMessages();

    //특정 point type의 to do list 반환
    @Query("SELECT * FROM Message WHERE point_type = :pointType")
    LiveData<List<Message>> getAllByPointType(String pointType);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(Message... messages);

    @Delete
    void delete(Message... messages);

    @Query("DELETE FROM Message")
    void deleteAllMessages();
}
