package com.example.mp_plancat.todo.monthly;

import android.graphics.Color;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mp_plancat.R;
import com.example.mp_plancat.database.entity.Todo;
import com.example.mp_plancat.todo.AscendingTodo;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

public class MonthlyTodoRecyclerAdapter extends RecyclerView.Adapter<MonthlyTodoRecyclerAdapter.TodoHolder> {
    private List<Todo> todos = new ArrayList<>();
    private MonthlyTodoRecyclerAdapter.OnItemClickListener listener;
    private MonthlyTodoRecyclerAdapter.OnItemLongClickListener longClickListener;
    private MonthlyTodoRecyclerAdapter.OnStatusCheckBoxChangeListener onStatusCheckBoxChangeListener;

    @NonNull
    @Override
    public MonthlyTodoRecyclerAdapter.TodoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_todo_item, parent, false);
        return new MonthlyTodoRecyclerAdapter.TodoHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MonthlyTodoRecyclerAdapter.TodoHolder holder, final int position) { //recyclerview 바인딩
        final Todo currentTodo = todos.get(position);

        Calendar calendar = currentTodo.getEndDate();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH) + 1;
        int year = calendar.get(Calendar.YEAR);

        holder.textViewTitle.setText(currentTodo.getTodoTitle());
        holder.textViewEndDate.setText(String.valueOf(year) + "년 " + String.valueOf(month) + "월");
        holder.textViewPoint.setText(String.valueOf(currentTodo.getAllocatedPoint()));


        //holder.checkBox.setOnCheckedChangeListener(null);
        holder.checkBox.setTag(currentTodo);

        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox cb = (CheckBox) v;
                Todo todo = (Todo) cb.getTag();
                if (onStatusCheckBoxChangeListener != null)
                    onStatusCheckBoxChangeListener.onStatusCheckBoxChanged(todo,cb.isChecked());
            }
        });

        holder.checkBox.setChecked(currentTodo.isChecked());
        if(currentTodo.isChecked()){
            holder.textViewTitle.setPaintFlags(holder.textViewTitle.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);
            holder.textViewTitle.setTextColor(Color.LTGRAY);
        }
        else{
            holder.textViewTitle.setPaintFlags(0);
            holder.textViewTitle.setTextColor(Color.BLACK);
        }
    }

    @Override
    public int getItemCount() {
        return todos.size();
    }

    public void setTodos(List<Todo> todos){
        Collections.sort(todos, new AscendingTodo());



        this.todos = todos;
        notifyDataSetChanged();
    }

    class TodoHolder extends RecyclerView.ViewHolder {
        private TextView textViewTitle;
        private TextView textViewEndDate;
        private TextView textViewPoint;
        private CheckBox checkBox;

        public TodoHolder(View itemView){
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.todo_title);
            textViewEndDate = itemView.findViewById(R.id.todo_endDate);
            textViewPoint = itemView.findViewById(R.id.todo_point);
            checkBox = itemView.findViewById(R.id.checkbox);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if(listener != null && position != RecyclerView.NO_POSITION){
                        listener.onItemClick(todos.get(position));
                    }
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int position = getAdapterPosition();
                    if(longClickListener != null && position != RecyclerView.NO_POSITION){
                        longClickListener.onItemLongClick(todos.get(position), v);
                    }
                    return true;
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Todo todo);
    }
    public void setOnItemClickListener(MonthlyTodoRecyclerAdapter.OnItemClickListener listener){
        this.listener = listener;
    }

    public interface OnItemLongClickListener {
        void onItemLongClick(Todo todo, View view);
    }
    public void setOnItemLongClickListener(MonthlyTodoRecyclerAdapter.OnItemLongClickListener listener){
        this.longClickListener = listener;
    }

    public interface OnStatusCheckBoxChangeListener{
        void onStatusCheckBoxChanged(Todo todo, boolean isChecked);
    }
    public void setOnStatusCheckBoxChanged(MonthlyTodoRecyclerAdapter.OnStatusCheckBoxChangeListener listener){
        this.onStatusCheckBoxChangeListener = listener;
    }
}
