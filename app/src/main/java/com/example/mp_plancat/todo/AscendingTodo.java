package com.example.mp_plancat.todo;

import com.example.mp_plancat.database.entity.Todo;

import java.util.Calendar;
import java.util.Comparator;

public class AscendingTodo implements Comparator<Todo> {
    public int compare(Todo todo1, Todo todo2){
        Calendar cal1 = todo1.getEndDate();
        Calendar cal2 = todo2.getEndDate();
        return todo1.getEndDate().compareTo(todo2.getEndDate());
    }
}
