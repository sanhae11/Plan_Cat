package com.example.mp_plancat.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.mp_plancat.database.entity.Location;
import com.example.mp_plancat.database.entity.Goods;

import java.util.List;

@Dao
public interface AssignedGoodsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Location... assignedGoods);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(Location... assignedGoods);

    @Delete
    void delete(Location... assignedGoods);
}
