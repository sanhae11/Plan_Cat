package com.example.mp_plancat;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.mp_plancat.database.MessageDatabase;

import com.example.mp_plancat.database.dao.MessageDao;

import com.example.mp_plancat.database.entity.Message;


import java.util.List;

public class MessageRepository {
    private MessageDao messageDao;
    private LiveData<List<Message>> allMessages;

    public MessageRepository(Application application){
        MessageDatabase database = MessageDatabase.getInstance(application);
        messageDao = database.messageDao();
        allMessages = messageDao.getAllMessages();
    }
    public void insert(Message message){
        new MessageRepository.InsertMessageAsyncTask(messageDao).execute(message);
    }
    public void update(Message message){
        new MessageRepository.UpdateMessageAsyncTask(messageDao).execute(message);
    }
    public void delete(Message message){
        new MessageRepository.DeleteMessageAsyncTask(messageDao).execute(message);
    }
    public void deleteAllMessages(){
        new MessageRepository.DeleteAllMessagesAsyncTask(messageDao).execute();
    }
    public LiveData<List<Message>> getAllMessages() {
        return allMessages;
    }
    private static class InsertMessageAsyncTask extends AsyncTask<Message, Void, Void> {
        private MessageDao messageDao;

        private InsertMessageAsyncTask(MessageDao messageDao){
            this.messageDao = messageDao;
        }
        @Override
        protected  Void doInBackground(Message... messages){
            messageDao.insert(messages[0]);
            return null;
        }
    }

    private static class UpdateMessageAsyncTask extends AsyncTask<Message, Void, Void>{
        private MessageDao messageDao;

        private UpdateMessageAsyncTask(MessageDao messageDao){
            this.messageDao = messageDao;
        }
        @Override
        protected  Void doInBackground(Message... messages){
            messageDao.update(messages[0]);
            return null;
        }
    }

    private static class DeleteMessageAsyncTask extends AsyncTask<Message, Void, Void>{
        private MessageDao messageDao;

        private DeleteMessageAsyncTask(MessageDao messageDao){
            this.messageDao = messageDao;
        }
        @Override
        protected  Void doInBackground(Message... messages){
            messageDao.delete(messages[0]);
            return null;
        }
    }

    private static class DeleteAllMessagesAsyncTask extends AsyncTask<Void, Void, Void>{
        private MessageDao messageDao;

        private DeleteAllMessagesAsyncTask(MessageDao messageDao){
            this.messageDao = messageDao;
        }
        @Override
        protected  Void doInBackground(Void... voids){
            messageDao.deleteAllMessages();
            return null;
        }
    }
}
