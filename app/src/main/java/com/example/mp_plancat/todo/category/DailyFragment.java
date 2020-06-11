package com.example.mp_plancat.todo.category;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mp_plancat.R;
import com.example.mp_plancat.database.entity.Todo;
import com.example.mp_plancat.todo.CreateTodoActivity;
import com.example.mp_plancat.todo.TodoRecyclerAdapter;

import java.sql.Date;

public class DailyFragment extends Fragment {
    private TodoRecyclerAdapter adapter;
    int isAdded = 0;
    Todo todo;
    String titleStr = "title", category = "D";
    int priority;
    Date date;
    double point;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_daily, container, false);

        ////////////////logtest
        Log.e("Test", "dailyfrag : oncreateview() 실행");

        /*RecyclerView todoRecyclerView = rootView.findViewById(R.id.todoRecyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        todoRecyclerView.setLayoutManager(linearLayoutManager);

        adapter = new TodoRecyclerAdapter();
        todoRecyclerView.setAdapter(adapter);*/


        Bundle bundle = getArguments();
        if(bundle != null){
            Log.e("test", "dailyFrag : 번들 if문 실행");
            TextView title = rootView.findViewById(R.id.title);

            todo = (Todo) bundle.get("todoData");
            title.setText(todo.todoTitle + " " + todo.todoCategory + " " + todo.endDay + " " + todo.allocatedPoint + " " + todo.priority);
            isAdded = 1;

        }

        return rootView;
    }

    @Override
    public void onResume(){
        super.onResume();
        Log.e("Test", "dailyFrag : onResume() 실행");
    }

    @Override
    public void onPause(){
        super.onPause();
        Log.e("Test", "dailyFrag : onPause() 실행");
    }
    @Override
    public void onStart(){
        super.onStart();
        Log.e("Test", "dailyfrag : onStart() 실행");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("test", "dailyFrag : oncreate() 실행");
    }
}
