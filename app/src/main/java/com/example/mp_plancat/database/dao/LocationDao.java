package com.example.mp_plancat.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.mp_plancat.database.entity.Location;

import java.util.List;

@Dao
public interface LocationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Location... locations);

    @Query("SELECT * FROM Location")
    List<Location> getAllLocations();

    @Query("SELECT * FROM Location WHERE :id = goods_ID")
    Location findLoation(int id);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(Location... locations);

    @Delete
    void delete(Location... locations);

    @Query("DELETE FROM Location")
    void deleteAllLocstions();
}