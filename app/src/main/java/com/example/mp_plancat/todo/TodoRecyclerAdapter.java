package com.example.mp_plancat.todo;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.mp_plancat.R;
import com.example.mp_plancat.database.entity.Todo;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class TodoRecyclerAdapter extends RecyclerView.Adapter<TodoRecyclerAdapter.TodoHolder> {

    private List<Todo> todos = new ArrayList<>();
    private OnItemClickListener listener;
    private OnItemLongClickListener longClickListener;
    private OnStatusCheckBoxChangeListener onStatusCheckBoxChangeListener;

    @NonNull
    @Override
    public TodoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_todo_item, parent, false);
        return new TodoHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TodoHolder holder, final int position) { //recyclerview 바인딩
        final Todo currentTodo = todos.get(position);

        Calendar calendar = currentTodo.getEndDate();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH) + 1;
        int year = calendar.get(Calendar.YEAR);

        holder.textViewTitle.setText(currentTodo.getTodoTitle());
        holder.textViewEndDate.setText(String.valueOf(month) + "." + String.valueOf(day));
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
    }

    @Override
    public int getItemCount() {
        return todos.size();
    }

    public void setTodos(List<Todo> todos){
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
    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

    public interface OnItemLongClickListener {
        void onItemLongClick(Todo todo, View view);
    }
    public void setOnItemLongClickListener(OnItemLongClickListener listener){
        this.longClickListener = listener;
    }

    public interface OnStatusCheckBoxChangeListener{
        void onStatusCheckBoxChanged(Todo todo, boolean isChecked);
    }
    public void setOnStatusCheckBoxChanged(OnStatusCheckBoxChangeListener listener){
        this.onStatusCheckBoxChangeListener = listener;
    }
}
