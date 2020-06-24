package com.example.mp_plancat;

import com.example.mp_plancat.database.entity.Message;
import com.example.mp_plancat.database.entity.Todo;

import java.util.Calendar;
import java.util.Comparator;

public class AscendingMessage implements Comparator<Message> {
    public int compare(Message message1, Message message2){
        Calendar cal1 = message1.getDate();
        Calendar cal2 = message2.getDate();
        return message1.getDate().compareTo(message2.getDate());
    }
}
