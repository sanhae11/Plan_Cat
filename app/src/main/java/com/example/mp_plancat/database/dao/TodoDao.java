package com.example.mp_plancat.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.mp_plancat.database.entity.Todo;

import java.util.List;
import java.sql.Date;

@Dao
public interface TodoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Todo... todo);

    //특정 날짜의 to do list 중 완료된 것들의 타이틀 List 반환
    @Query("SELECT todo_title FROM Todo WHERE :date = deadline AND is_finished = 1")
    List<String> getAllTitlesOfFinishedByDate(Date date);

    //특정 날짜의 to do list 중 완료된 것들의 포인트 합산하여 반환
    @Query("SELECT COUNT(allocated_point) FROM Todo WHERE :date = deadline AND is_finished = 1")
    float getSumOfPointByDate(Date date);

    //특정 날짜, 특정 카테고리의 to do list 중 완료된 것들의 포인트 합산하여 반환
    @Query("SELECT COUNT(allocated_point) FROM Todo WHERE :category = todo_category AND :date = deadline AND is_finished = 1")
    float getSumOfPointFinishedByCategoryAndDate(String category, Date date);

    //특정 카테고리의 to do list 반환
    @Query("SELECT * FROM Todo WHERE todo_category = :category")
    List<Todo> getAllByCategory(String category);

    //특정 카테고리의 to do list 중 미완료된 것들만 반환
    @Query("SELECT * FROM Todo WHERE todo_category = :category AND is_finished = 0")
    List<Todo> getAllOfNotFinishedByCategory(String category);

    //특정 카테고리, 특정 날짜의 to do list 반환
    @Query("SELECT * FROM Todo WHERE todo_category = :category AND :date = deadline")
    List<Todo> getAllByCategoryAndDate(String category, Date date);

    //특정 카테고리, 특정 날짜의 to do list 중 미완료된 것들만 반환
    @Query("SELECT * FROM Todo WHERE todo_category = :category AND :date = deadline AND is_finished = 0")
    List<Todo> getAllOfNotFinishedByCategoryAndDate(String category, Date date);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(Todo... todo);

    @Delete
    void delete(Todo... todo);
}