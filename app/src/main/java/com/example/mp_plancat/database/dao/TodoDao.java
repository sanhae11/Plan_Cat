package com.example.mp_plancat.database.dao;

import androidx.lifecycle.LiveData;
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

    //Todo : livedata 붙인 것도 있고 안 붙인 것도 있음. 필요하거나 에러 나면 수정하기


    //모든 to do list 반환
    @Query("SELECT * FROM Todo")
    LiveData<List<Todo>> getAllTodos();

    //특정 날짜의 to do list 중 완료된 것들의 타이틀 List 반환
    @Query("SELECT todo_title FROM Todo WHERE :day = end_day AND :month = end_month AND :year = end_year AND is_finished = 1")
    List<String> getAllTitlesOfFinishedByDate(int day, int month, int year);

    //특정 날짜의 to do list 중 완료된 것들의 포인트 합산하여 반환
    @Query("SELECT SUM(allocated_point) FROM Todo WHERE :day = end_day AND :month = end_month AND :year = end_year AND is_finished = 1")
    float getSumOfPointByDate(int day, int month, int year);

    //특정 날짜, 특정 카테고리의 to do list 중 완료된 것들의 포인트 합산하여 반환
    @Query("SELECT SUM(allocated_point) FROM Todo WHERE :category = todo_category AND :day = end_day AND :month = end_month AND :year = end_year AND is_finished = 1")
    float getSumOfPointFinishedByCategoryAndDate(int day, int month, int year, String category);

    //특정 카테고리의 to do list 반환
    @Query("SELECT * FROM Todo WHERE todo_category = :category")
    LiveData<List<Todo>> getAllByCategory(String category);

    //특정 카테고리의 to do list 중 미완료된 것들만 반환
    @Query("SELECT * FROM Todo WHERE todo_category = :category AND is_finished = 0")
    LiveData<List<Todo>> getAllOfNotFinishedByCategory(String category);

    //특정 카테고리, 특정 날짜의 to do list 반환
    @Query("SELECT * FROM Todo WHERE todo_category = :category AND :day = end_day AND :month = end_month AND :year = end_year")
    LiveData<List<Todo>> getAllByCategoryAndDate(int day, int month, int year, String category);

    //특정 카테고리, 특정 날짜의 to do list 중 미완료된 것들만 반환
    @Query("SELECT * FROM Todo WHERE todo_category = :category AND :day = end_day AND :month = end_month AND :year = end_year AND is_finished = 0")
    LiveData<List<Todo>> getAllOfNotFinishedByCategoryAndDate(int day, int month, int year, String category);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(Todo todo);

    @Delete
    void delete(Todo... todo);

    @Query("DELETE FROM Todo")
    void deleteAllTodos();
}