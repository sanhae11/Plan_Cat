package com.example.mp_plancat.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;

@Entity(indices = @Index(value = {"todo_title", "todo_category", "deadline"}, unique = true))
public class Todo {

    @PrimaryKey(autoGenerate = true)
    public int todoID;

    @ColumnInfo(name = "todo_title")
    public String todoTitle; //타이틀

    @ColumnInfo(name = "todo_category")
    public String todoCategory; //카테고리; D(=Daily), W(=Weekly), M(=Monthly), Y(=Yearly)

    public LocalDate deadline; // e.g. 2020-11-14

    @ColumnInfo(name = "is_finished")
    public boolean isFinished; //to do 완료 여부; true, false

    @ColumnInfo(name = "allocated_point")
    public double allocatedPoint; //각 to do에 할당된 포인트

    public int priority; //우선순위; 1(=상), 2(=중), 3(=하)

    public Todo(){}

    public Todo(String todoTitle, String todoCategory, LocalDate deadline, double allocatedPoint, int priority){
        this.todoTitle = todoTitle;
        this.todoCategory = todoCategory;
        this.deadline = deadline;
        this.isFinished = false;
        this.allocatedPoint = allocatedPoint;
        this.priority = priority;
    }
}
