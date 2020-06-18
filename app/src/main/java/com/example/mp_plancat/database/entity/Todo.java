package com.example.mp_plancat.database.entity;

import android.widget.CheckBox;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.sql.Date;
import java.util.Calendar;

@Entity(indices = @Index(value = {"todo_title", "todo_category", "priority"}, unique = true))
public class Todo implements Serializable, Comparable<Todo>{

    @PrimaryKey(autoGenerate = true)
    public int todoID;

    @ColumnInfo(name = "todo_title")
    public String todoTitle; //타이틀

    @ColumnInfo(name = "todo_category")
    public String todoCategory; //카테고리; D(=Daily), W(=Weekly), M(=Monthly), Y(=Yearly)

    /*
    @ColumnInfo(name = "start_date")
    public Date startDate; // 포맷 "yyyy-mm-dd"; e.g. 2020-11-14

    @ColumnInfo(name = "end_date")
    public Date endDate; // 포맷 "yyyy-mm-dd"; e.g. 2020-11-14
    */

    @ColumnInfo(name = "start_day")
    public int startDay;

    @ColumnInfo(name = "start_month")
    public int startMonth;

    @ColumnInfo(name = "start_year")
    public int startYear;

    @ColumnInfo(name = "end_day")
    public int endDay;

    @ColumnInfo(name = "end_month")
    public int endMonth;

    @ColumnInfo(name = "end_year")
    public int endYear;


    @ColumnInfo(name = "is_finished")
    public int isFinished; //to do 완료 여부; 1=true, 0=false

    @ColumnInfo(name = "allocated_point")
    public double allocatedPoint; //각 to do에 할당된 포인트

    public int priority; //우선순위; 1(=상), 2(=중), 3(=하)

    public Todo(){}

    public Todo(String todoTitle, String todoCategory, Calendar startDate, Calendar endDate, double allocatedPoint, int priority){
        this.todoTitle = todoTitle;
        this.todoCategory = todoCategory;
        this.startDay = startDate.get(Calendar.DAY_OF_MONTH);
        this.startMonth = startDate.get(Calendar.MONTH) + 1;
        this.startYear = startDate.get(Calendar.YEAR);
        this.endDay = endDate.get(Calendar.DAY_OF_MONTH);
        this.endMonth = endDate.get(Calendar.MONTH) + 1;
        this.endYear = endDate.get(Calendar.YEAR);
        this.isFinished = 0;
        this.allocatedPoint = allocatedPoint;
        this.priority = priority;
    }

    public int getTodoID() {
        return todoID;
    }

    public String getTodoTitle() {
        return todoTitle;
    }

    public String getTodoCategory() {
        return todoCategory;
    }

    public int getIsFinished() {
        return isFinished;
    }

    public double getAllocatedPoint() {
        return allocatedPoint;
    }

    public int getPriority() {
        return priority;
    }

    public Calendar getStartDate(){
        Calendar cal = Calendar.getInstance();
        cal.set(startYear, startMonth - 1, startDay);
        return cal;
    }

    public Calendar getEndDate(){
        Calendar cal = Calendar.getInstance();
        cal.set(endYear, endMonth - 1, endDay);
        return cal;
    }

    public void setId(int id) {
        this.todoID = id;
    }

    public boolean isChecked(){
        if(isFinished == 1)
            return true;
        else
            return false;
    }
    public void setIsFinished(boolean isFinished){
        if(isFinished){
            this.isFinished = 1;
        }
        else{
            this.isFinished = 0;
        }
    }

    public int compareTo(Todo todo){
        return this.getEndDate().compareTo(todo.getEndDate());
    }
}
