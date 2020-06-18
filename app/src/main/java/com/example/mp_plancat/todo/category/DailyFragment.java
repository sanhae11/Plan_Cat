package com.example.mp_plancat.todo.category;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
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
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.example.mp_plancat.MainActivity;
import com.example.mp_plancat.R;
import com.example.mp_plancat.database.entity.Todo;
import com.example.mp_plancat.todo.CreateEditTodoActivity;
import com.example.mp_plancat.todo.DeleteTodoDialog;
import com.example.mp_plancat.todo.TodoRecyclerAdapter;
import com.example.mp_plancat.todo.TodoViewModel;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class DailyFragment extends Fragment {
    public static final int EDIT_TODO_REQUEST = 2;

    private TodoViewModel todoViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_daily, container, false);

        RecyclerView recyclerView = rootView.findViewById(R.id.todoRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext())); //parameter 원래는 this였음
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

        adapter.setOnItemClickListener(new TodoRecyclerAdapter.OnItemClickListener() { //할 일 클릭 시 edit to do 화면으로 이동
            @Override
            public void onItemClick(Todo todo) {
                //할 일 클릭 시
            }
        });

        adapter.setOnItemLongClickListener(new TodoRecyclerAdapter.OnItemLongClickListener() { //할 일 꾹 누르면 delete to do 다이얼로그 창 뜸
            @Override
            public void onItemLongClick(final Todo todo, View view) {
                PopupMenu popupMenu = new PopupMenu(getContext(), view);
                getActivity().getMenuInflater().inflate(R.menu.popup_menu_listview, popupMenu.getMenu());

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.item_edit:
                                Intent intent = new Intent(getActivity(), CreateEditTodoActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putSerializable("todoData", todo);
                                intent.putExtra("todoData", bundle);
                                intent.putExtra("todoData_ID", todo.getTodoID());
                                intent.putExtra("todoData_checkState", todo.getIsFinished());

                                startActivityForResult(intent, EDIT_TODO_REQUEST);
                                break;
                            case R.id.item_delete:
                                DeleteTodoDialog deleteTodoDialog = new DeleteTodoDialog();
                                deleteTodoDialog.setDialogListener(new DeleteTodoDialog.DeleteTodoDialogListener() {
                                    @Override
                                    public void onPositiveClicked() {
                                        todoViewModel.delete(todo);
                                    }
                                    @Override
                                    public void onNegativeClicked() {

                                    }
                                });
                                deleteTodoDialog.show(getActivity().getSupportFragmentManager(), "Delete Todo Dialog");
                                break;
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });

        adapter.setOnStatusCheckBoxChanged(new TodoRecyclerAdapter.OnStatusCheckBoxChangeListener() { //checkbox 클릭할 때마다 db 업데이트
            @Override
            public void onStatusCheckBoxChanged(Todo todo, boolean isChecked) {
                todo.setIsFinished(isChecked);
                todoViewModel.update(todo);
            }
        });

        Bundle bundle = getArguments();
        if(bundle != null){
            Todo todo = (Todo) bundle.get("todoData");

            todoViewModel.insert(todo);
        }

        return rootView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == EDIT_TODO_REQUEST && resultCode == RESULT_OK) {
            Bundle bundle = intent.getBundleExtra("todoData");

            Todo todo = (Todo) bundle.get("todoData");

            todoViewModel.update(todo);
        }
    }
}
