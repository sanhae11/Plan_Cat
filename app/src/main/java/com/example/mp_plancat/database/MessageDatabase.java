package com.example.mp_plancat.database;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.mp_plancat.database.dao.MessageDao;
import com.example.mp_plancat.database.entity.Message;

public abstract class MessageDatabase extends RoomDatabase {
    private static MessageDatabase instance;

    public abstract MessageDao messageDao();

    public static synchronized MessageDatabase getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(), MessageDatabase.class, "silver_message_database").fallbackToDestructiveMigration().addCallback(roomCallback).build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new MessageDatabase.PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class  PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private MessageDao messageDao;

        private PopulateDbAsyncTask(MessageDatabase db){
            messageDao = db.messageDao();
        }

        @Override
        protected Void doInBackground(Void... voids){
            //Todo : 현재 임시 코드; 바꾸기!!!!!!!!
            messageDao.insert(new Message(20, 6, 2020, "S"));
            messageDao.insert(new Message(20, 6, 2020, "G"));

            return null;
        }
    }
}
