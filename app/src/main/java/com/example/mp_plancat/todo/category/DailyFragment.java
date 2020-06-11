package com.example.mp_plancat.todo.category;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mp_plancat.R;
import com.example.mp_plancat.database.entity.Todo;
import com.example.mp_plancat.todo.CreateTodoActivity;
import com.example.mp_plancat.todo.TodoRecyclerAdapter;
import com.example.mp_plancat.todo.TodoViewModel;

import java.sql.Date;
import java.util.List;

public class DailyFragment extends Fragment {
    public static final int ADD_NOTE_REQUEST = 1;
    private TodoViewModel todoViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_daily, container, false);

        ////////////////logtest
        Log.e("Test", "dailyfrag : oncreateview() 실행");




        RecyclerView recyclerView = rootView.findViewById(R.id.todoRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext())); //parameter 원래는 this였음. 오류 나면 얘가 문제일수도
        recyclerView.setHasFixedSize(true);

        final TodoRecyclerAdapter adapter = new TodoRecyclerAdapter();
        recyclerView.setAdapter(adapter);

        todoViewModel = ViewModelProviders.of(this).get(TodoViewModel.class); //parameter 원래 this 였는데 오류 안나서 안 바꿈
        todoViewModel.getAllTodos().observe(getViewLifecycleOwner(), new Observer<List<Todo>>() { //parameter 원래 this 였는데 오류나서 바꿈
            @Override
            public void onChanged(@Nullable List<Todo> todos) {
                //update Recyclerview
                adapter.setTodos(todos);
            }
        });




        Bundle bundle = getArguments();
        if(bundle != null){
            Log.e("test", "dailyFrag : 번들 if문 실행");

            Todo todo = (Todo) bundle.get("todoData");

            todoViewModel.insert(todo);

            //Toast.makeText(this, "Note Saved", Toast.LENGTH_SHORT).show();

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
