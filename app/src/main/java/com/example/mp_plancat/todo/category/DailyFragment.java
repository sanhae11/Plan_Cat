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

import com.example.mp_plancat.R;
import com.example.mp_plancat.database.entity.Todo;
import com.example.mp_plancat.todo.CreateEditTodoActivity;
import com.example.mp_plancat.todo.TodoRecyclerAdapter;
import com.example.mp_plancat.todo.TodoViewModel;

import java.util.List;

import static android.app.Activity.RESULT_OK;

public class DailyFragment extends Fragment {
    public static final int EDIT_TODO_REQUEST = 2;

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

        adapter.setOnItemClickListener(new TodoRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Todo todo) {
                Intent intent = new Intent(getActivity(), CreateEditTodoActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("todoData", todo);
                intent.putExtra("todoData", bundle);
                intent.putExtra("todoData_ID", todo.getTodoID());

                Log.e("checkID", "dailyfrag" + todo.getTodoID() + "");

                startActivityForResult(intent, EDIT_TODO_REQUEST);
            }
        });


        Bundle bundle = getArguments();
        if(bundle != null){
            Log.e("test", "dailyFrag : 번들 if문 실행");

            Todo todo = (Todo) bundle.get("todoData");

            todoViewModel.insert(todo);
        }



        return rootView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        ////////////log test
        Log.e("test", "dailyfrag : onActivityResult 실행됨");

        if (requestCode == EDIT_TODO_REQUEST && resultCode == RESULT_OK) {
            Bundle bundle = intent.getBundleExtra("todoData");

            Todo todo = (Todo) bundle.get("todoData");
            Log.e("test", "dailyFrag onactivityresult 실행 : " + todo.todoID + " " + todo.todoTitle + " " + todo.todoCategory + " " + todo.endDay + " " + todo.allocatedPoint + " " + todo.priority);

            todoViewModel.update(todo);
        }
    }
}
