package com.example.mp_plancat.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.mp_plancat.database.entity.GameInfo;

import java.util.List;

@Dao
public interface GameInfoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(GameInfo gameInfo);

    //Game Info를 list 형식으로 반환
    @Query("SELECT * FROM GameInfo")
    List<GameInfo> getAll();

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(GameInfo gameInfo);

    @Delete
    void delete(GameInfo gameInfo);
}
