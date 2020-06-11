package com.example.mp_plancat.todo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mp_plancat.R;
import com.example.mp_plancat.database.entity.Todo;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class TodoRecyclerAdapter extends RecyclerView.Adapter<TodoRecyclerAdapter.TodoHolder> {

    private List<Todo> todos = new ArrayList<>();

    @NonNull
    @Override
    public TodoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_todo_item, parent, false);
        return new TodoHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TodoHolder holder, int position) {
        final Todo currentTodo = todos.get(position);

        Calendar calendar = currentTodo.getEndDate();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH) + 1;
        int year = calendar.get(Calendar.YEAR);

        holder.textViewTitle.setText(currentTodo.getTodoTitle());
        holder.textViewEndDate.setText(String.valueOf(month) + "." + String.valueOf(day));
        holder.textViewPoint.setText(String.valueOf(currentTodo.getAllocatedPoint()));


        //Todo : 체크박스 상태 유지 하는 거 에러; 다시 짜기
        holder.checkBox.setOnCheckedChangeListener(null);

        holder.checkBox.setChecked(currentTodo.isChecked());////////////////////////////////////
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked){
                currentTodo.setIsFinished(isChecked);
            }
        });
        ///////////////////////////////
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
        }
    }
}
