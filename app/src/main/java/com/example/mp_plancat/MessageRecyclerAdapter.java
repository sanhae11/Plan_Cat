package com.example.mp_plancat;

import android.graphics.Color;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mp_plancat.database.entity.Message;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

public class MessageRecyclerAdapter extends RecyclerView.Adapter<MessageRecyclerAdapter.MessageHolder> {
    private List<Message> messages = new ArrayList<>();
    private MessageRecyclerAdapter.OnItemClickListener listener;
    //private MessageRecyclerAdapter.OnItemLongClickListener longClickListener;
    //private MessageRecyclerAdapter.OnStatusCheckBoxChangeListener onStatusCheckBoxChangeListener;
    private String pointType;

    public MessageRecyclerAdapter(String pointType){
        this.pointType = pointType;
    }

    @NonNull
    @Override
    public MessageRecyclerAdapter.MessageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_message_item, parent, false);
        return new MessageRecyclerAdapter.MessageHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageRecyclerAdapter.MessageHolder holder, final int position) { //recyclerview 바인딩
        final Message currentMessage = messages.get(position);

        Calendar calendar = currentMessage.getDate();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH) + 1;
        int year = calendar.get(Calendar.YEAR);

        holder.textViewDate.setText(String.valueOf(year) + "년 " + String.valueOf(month) + "월 " + String.valueOf(day) + "일");
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public void setMessages(List<Message> messages){
        Collections.sort(messages, new AscendingMessage());

        List<Message> newMessages = new ArrayList<>();

        for(int i = 0; i < messages.size(); i++){
            if(messages.get(i).pointType.equals(this.pointType)){
                newMessages.add(messages.get(i));
            }
        }

        this.messages = newMessages;
        notifyDataSetChanged();
    }

    class MessageHolder extends RecyclerView.ViewHolder {
        private TextView textViewDate;
        //private TextView textViewPoint;

        public MessageHolder(View itemView){
            super(itemView);
            textViewDate = itemView.findViewById(R.id.message_title);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if(listener != null && position != RecyclerView.NO_POSITION){
                        listener.onItemClick(messages.get(position));
                    }
                }
            });

        }
    }

    public interface OnItemClickListener {
        void onItemClick(Message message);
    }
    public void setOnItemClickListener(MessageRecyclerAdapter.OnItemClickListener listener){
        this.listener = listener;
    }
}
