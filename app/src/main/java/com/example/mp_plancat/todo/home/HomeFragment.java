package com.example.mp_plancat.todo.home;
//홈 화면

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;

import com.example.mp_plancat.MainActivity;
import com.example.mp_plancat.R;
import com.example.mp_plancat.database.entity.Todo;
import com.example.mp_plancat.todo.CreateEditTodoActivity;
import com.example.mp_plancat.todo.DeleteTodoDialog;
import com.example.mp_plancat.todo.TodoViewModel;

import java.util.Calendar;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class HomeFragment extends Fragment {
    public static final int EDIT_TODO_REQUEST = 2;

    private DatePickerDialog.OnDateSetListener listener;
    Calendar cal = Calendar.getInstance();
    int currentDay = cal.get(Calendar.DATE);
    int currentWeek = cal.get(Calendar.WEEK_OF_MONTH);
    int currentMonth = cal.get(Calendar.MONTH) + 1;
    int currentYear = cal.get(Calendar.YEAR);

    private TodoViewModel dailyTodoViewModel, weeklyTodoViewModel, monthlyTodoViewModel, yearlyTodoViewModel;
    private RecyclerView dailyRecyclerView, weeklyRecyclerView, monthlyRecyclerView, yearlyRecyclerView;
    private DateCategoryTodoRecyclerAdapter dailyAdapter, weeklyAdapter, monthlyAdapter, yearlyAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_home, container, false);

        ActionBar actionBar = ((MainActivity)getActivity()).getSupportActionBar();
        actionBar.show(); //액션바 보여줌
        actionBar.setTitle("Home");

        dailyRecyclerView = rootView.findViewById(R.id.dailyTodoRecyclerView);
        dailyRecyclerView.setLayoutManager(new LinearLayoutManager(getContext())); //parameter 원래는 this였음
        dailyRecyclerView.setHasFixedSize(true);

        weeklyRecyclerView = rootView.findViewById(R.id.weeklyTodoRecyclerView);
        weeklyRecyclerView.setLayoutManager(new LinearLayoutManager(getContext())); //parameter 원래는 this였음
        weeklyRecyclerView.setHasFixedSize(true);

        monthlyRecyclerView = rootView.findViewById(R.id.monthlyTodoRecyclerView);
        monthlyRecyclerView.setLayoutManager(new LinearLayoutManager(getContext())); //parameter 원래는 this였음
        monthlyRecyclerView.setHasFixedSize(true);

        yearlyRecyclerView = rootView.findViewById(R.id.yearlyTodoRecyclerView);
        yearlyRecyclerView.setLayoutManager(new LinearLayoutManager(getContext())); //parameter 원래는 this였음
        yearlyRecyclerView.setHasFixedSize(true);

        dailyAdapter = new DateCategoryTodoRecyclerAdapter("D", currentDay, currentWeek, currentMonth, currentYear);
        dailyRecyclerView.setAdapter(dailyAdapter);
        dailyTodoViewModel = ViewModelProviders.of(this).get(TodoViewModel.class); //parameter 원래 this 였는데 오류 안나서 안 바꿈
        dailyTodoViewModel.getAllTodos().observe(getViewLifecycleOwner(), new Observer<List<Todo>>() { //parameter 원래 this 였는데 오류나서 바꿈
            @Override
            public void onChanged(@Nullable List<Todo> todos) {
                //update Recyclerview
                dailyAdapter.setTodos(todos);
            }
        });

        dailyAdapter.setOnItemClickListener(new DateCategoryTodoRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Todo todo) {

            }
        });

        dailyAdapter.setOnItemLongClickListener(new DateCategoryTodoRecyclerAdapter.OnItemLongClickListener() { //할 일 꾹 누르면 delete to do 다이얼로그 창 뜸
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
                                        dailyTodoViewModel.delete(todo);
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

        dailyAdapter.setOnStatusCheckBoxChanged(new DateCategoryTodoRecyclerAdapter.OnStatusCheckBoxChangeListener() { //checkbox 클릭할 때마다 db 업데이트
            @Override
            public void onStatusCheckBoxChanged(Todo todo, boolean isChecked) {
                todo.setIsFinished(isChecked);
                dailyTodoViewModel.update(todo);
            }
        });


        weeklyAdapter = new DateCategoryTodoRecyclerAdapter("W", currentDay, currentWeek, currentMonth, currentYear);
        weeklyRecyclerView.setAdapter(weeklyAdapter);
        weeklyTodoViewModel = ViewModelProviders.of(this).get(TodoViewModel.class); //parameter 원래 this 였는데 오류 안나서 안 바꿈
        weeklyTodoViewModel.getAllTodos().observe(getViewLifecycleOwner(), new Observer<List<Todo>>() { //parameter 원래 this 였는데 오류나서 바꿈
            @Override
            public void onChanged(@Nullable List<Todo> todos) {
                //update Recyclerview
                weeklyAdapter.setTodos(todos);
            }
        });

        weeklyAdapter.setOnItemClickListener(new DateCategoryTodoRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Todo todo) {
                //할 일 클릭 시
            }
        });

        weeklyAdapter.setOnItemLongClickListener(new DateCategoryTodoRecyclerAdapter.OnItemLongClickListener() { //할 일 꾹 누르면 delete to do 다이얼로그 창 뜸
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
                                intent.putExtra("todoData_category", todo.getTodoCategory());
                                intent.putExtra("todoData_checkState", todo.getIsFinished());

                                startActivityForResult(intent, EDIT_TODO_REQUEST);
                                break;
                            case R.id.item_delete:
                                DeleteTodoDialog deleteTodoDialog = new DeleteTodoDialog();
                                deleteTodoDialog.setDialogListener(new DeleteTodoDialog.DeleteTodoDialogListener() {
                                    @Override
                                    public void onPositiveClicked() {
                                        weeklyTodoViewModel.delete(todo);
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

        weeklyAdapter.setOnStatusCheckBoxChanged(new DateCategoryTodoRecyclerAdapter.OnStatusCheckBoxChangeListener() { //checkbox 클릭할 때마다 db 업데이트
            @Override
            public void onStatusCheckBoxChanged(Todo todo, boolean isChecked) {
                todo.setIsFinished(isChecked);
                weeklyTodoViewModel.update(todo);
            }
        });

        monthlyAdapter = new DateCategoryTodoRecyclerAdapter("M", currentDay, currentWeek, currentMonth, currentYear);
        monthlyRecyclerView.setAdapter(monthlyAdapter);
        monthlyTodoViewModel = ViewModelProviders.of(this).get(TodoViewModel.class); //parameter 원래 this 였는데 오류 안나서 안 바꿈
        monthlyTodoViewModel.getAllTodos().observe(getViewLifecycleOwner(), new Observer<List<Todo>>() { //parameter 원래 this 였는데 오류나서 바꿈
            @Override
            public void onChanged(@Nullable List<Todo> todos) {
                //update Recyclerview
                monthlyAdapter.setTodos(todos);
            }
        });

        monthlyAdapter.setOnItemClickListener(new DateCategoryTodoRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Todo todo) {
                //할 일 클릭 시
            }
        });

        monthlyAdapter.setOnItemLongClickListener(new DateCategoryTodoRecyclerAdapter.OnItemLongClickListener() { //할 일 꾹 누르면 delete to do 다이얼로그 창 뜸
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
                                intent.putExtra("todoData_category", todo.getTodoCategory());
                                intent.putExtra("todoData_checkState", todo.getIsFinished());

                                startActivityForResult(intent, EDIT_TODO_REQUEST);
                                break;
                            case R.id.item_delete:
                                DeleteTodoDialog deleteTodoDialog = new DeleteTodoDialog();
                                deleteTodoDialog.setDialogListener(new DeleteTodoDialog.DeleteTodoDialogListener() {
                                    @Override
                                    public void onPositiveClicked() {
                                        monthlyTodoViewModel.delete(todo);
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

        monthlyAdapter.setOnStatusCheckBoxChanged(new DateCategoryTodoRecyclerAdapter.OnStatusCheckBoxChangeListener() { //checkbox 클릭할 때마다 db 업데이트
            @Override
            public void onStatusCheckBoxChanged(Todo todo, boolean isChecked) {
                todo.setIsFinished(isChecked);
                monthlyTodoViewModel.update(todo);
            }
        });

        yearlyAdapter = new DateCategoryTodoRecyclerAdapter("Y", currentDay, currentWeek, currentMonth, currentYear);
        yearlyRecyclerView.setAdapter(yearlyAdapter);
        yearlyTodoViewModel = ViewModelProviders.of(this).get(TodoViewModel.class); //parameter 원래 this 였는데 오류 안나서 안 바꿈
        yearlyTodoViewModel.getAllTodos().observe(getViewLifecycleOwner(), new Observer<List<Todo>>() { //parameter 원래 this 였는데 오류나서 바꿈
            @Override
            public void onChanged(@Nullable List<Todo> todos) {
                //update Recyclerview
                yearlyAdapter.setTodos(todos);
            }
        });

        yearlyAdapter.setOnItemClickListener(new DateCategoryTodoRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Todo todo) {
                //할 일 클릭 시
            }
        });

        yearlyAdapter.setOnItemLongClickListener(new DateCategoryTodoRecyclerAdapter.OnItemLongClickListener() { //할 일 꾹 누르면 delete to do 다이얼로그 창 뜸
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
                                intent.putExtra("todoData_category", todo.getTodoCategory());
                                intent.putExtra("todoData_checkState", todo.getIsFinished());

                                startActivityForResult(intent, EDIT_TODO_REQUEST);
                                break;
                            case R.id.item_delete:
                                DeleteTodoDialog deleteTodoDialog = new DeleteTodoDialog();
                                deleteTodoDialog.setDialogListener(new DeleteTodoDialog.DeleteTodoDialogListener() {
                                    @Override
                                    public void onPositiveClicked() {
                                        yearlyTodoViewModel.delete(todo);
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

        yearlyAdapter.setOnStatusCheckBoxChanged(new DateCategoryTodoRecyclerAdapter.OnStatusCheckBoxChangeListener() { //checkbox 클릭할 때마다 db 업데이트
            @Override
            public void onStatusCheckBoxChanged(Todo todo, boolean isChecked) {
                todo.setIsFinished(isChecked);
                yearlyTodoViewModel.update(todo);
            }
        });


        final Button btn = (Button) rootView.findViewById(R.id.btn);
        btn.setText(currentYear + "년 " + currentMonth + "월 " + currentDay +"일");
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dialog = new DatePickerDialog(getContext(), listener, currentYear, currentMonth-1, currentDay);
                dialog.show();

                listener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        currentYear = year;
                        currentMonth = monthOfYear + 1;
                        currentDay = dayOfMonth;

                        Calendar cal_week = Calendar.getInstance();
                        cal_week.set(currentYear, currentMonth - 1, currentDay);
                        currentWeek = cal_week.get(Calendar.WEEK_OF_MONTH);

                        btn.setText(currentYear + "년 " +currentMonth+ "월 " +currentDay +"일");


                        dailyAdapter.setDate(currentDay, currentWeek, currentMonth, currentYear);
                        dailyRecyclerView.setAdapter(dailyAdapter);
                        dailyTodoViewModel = ViewModelProviders.of(HomeFragment.this).get(TodoViewModel.class); //parameter 원래 this 였는데 오류 안나서 안 바꿈
                        dailyTodoViewModel.getAllTodos().observe(getViewLifecycleOwner(), new Observer<List<Todo>>() { //parameter 원래 this 였는데 오류나서 바꿈
                            @Override
                            public void onChanged(@Nullable List<Todo> todos) {
                                //update Recyclerview
                                dailyAdapter.setTodos(todos);
                            }
                        });

                        weeklyAdapter.setDate(currentDay, currentWeek, currentMonth, currentYear);
                        weeklyRecyclerView.setAdapter(weeklyAdapter);
                        weeklyTodoViewModel = ViewModelProviders.of(HomeFragment.this).get(TodoViewModel.class); //parameter 원래 this 였는데 오류 안나서 안 바꿈
                        weeklyTodoViewModel.getAllTodos().observe(getViewLifecycleOwner(), new Observer<List<Todo>>() { //parameter 원래 this 였는데 오류나서 바꿈
                            @Override
                            public void onChanged(@Nullable List<Todo> todos) {
                                //update Recyclerview
                                weeklyAdapter.setTodos(todos);
                            }
                        });

                        monthlyAdapter.setDate(currentDay, currentWeek, currentMonth, currentYear);
                        monthlyRecyclerView.setAdapter(monthlyAdapter);
                        monthlyTodoViewModel = ViewModelProviders.of(HomeFragment.this).get(TodoViewModel.class); //parameter 원래 this 였는데 오류 안나서 안 바꿈
                        monthlyTodoViewModel.getAllTodos().observe(getViewLifecycleOwner(), new Observer<List<Todo>>() { //parameter 원래 this 였는데 오류나서 바꿈
                            @Override
                            public void onChanged(@Nullable List<Todo> todos) {
                                //update Recyclerview
                                monthlyAdapter.setTodos(todos);
                            }
                        });

                        yearlyAdapter.setDate(currentDay, currentWeek, currentMonth, currentYear);
                        yearlyRecyclerView.setAdapter(yearlyAdapter);
                        yearlyTodoViewModel = ViewModelProviders.of(HomeFragment.this).get(TodoViewModel.class); //parameter 원래 this 였는데 오류 안나서 안 바꿈
                        yearlyTodoViewModel.getAllTodos().observe(getViewLifecycleOwner(), new Observer<List<Todo>>() { //parameter 원래 this 였는데 오류나서 바꿈
                            @Override
                            public void onChanged(@Nullable List<Todo> todos) {
                                //update Recyclerview
                                yearlyAdapter.setTodos(todos);
                            }
                        });
                    }
                };
            }
        });

        final ImageView btn_goToday = rootView.findViewById(R.id.btn_goToday);
        btn_goToday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentDay = cal.get(Calendar.DATE);
                currentMonth = cal.get(Calendar.MONTH) + 1;
                currentYear = cal.get(Calendar.YEAR);

                Calendar cal_week = Calendar.getInstance();
                cal_week.set(currentYear, currentMonth - 1, currentDay);
                currentWeek = cal_week.get(Calendar.WEEK_OF_MONTH);

                btn.setText(currentYear + "년 " +currentMonth+ "월 " +currentDay +"일");
                //Todo 오늘 날짜의 리사이클러뷰 떠야 함
                dailyAdapter.setDate(currentDay, currentWeek, currentMonth, currentYear);
                dailyRecyclerView.setAdapter(dailyAdapter);
                dailyTodoViewModel = ViewModelProviders.of(HomeFragment.this).get(TodoViewModel.class); //parameter 원래 this 였는데 오류 안나서 안 바꿈
                dailyTodoViewModel.getAllTodos().observe(getViewLifecycleOwner(), new Observer<List<Todo>>() { //parameter 원래 this 였는데 오류나서 바꿈
                    @Override
                    public void onChanged(@Nullable List<Todo> todos) {
                        //update Recyclerview
                        dailyAdapter.setTodos(todos);
                    }
                });

                weeklyAdapter.setDate(currentDay, currentWeek, currentMonth, currentYear);
                weeklyRecyclerView.setAdapter(weeklyAdapter);
                weeklyTodoViewModel = ViewModelProviders.of(HomeFragment.this).get(TodoViewModel.class); //parameter 원래 this 였는데 오류 안나서 안 바꿈
                weeklyTodoViewModel.getAllTodos().observe(getViewLifecycleOwner(), new Observer<List<Todo>>() { //parameter 원래 this 였는데 오류나서 바꿈
                    @Override
                    public void onChanged(@Nullable List<Todo> todos) {
                        //update Recyclerview
                        weeklyAdapter.setTodos(todos);
                    }
                });

                monthlyAdapter.setDate(currentDay, currentWeek, currentMonth, currentYear);
                monthlyRecyclerView.setAdapter(monthlyAdapter);
                monthlyTodoViewModel = ViewModelProviders.of(HomeFragment.this).get(TodoViewModel.class); //parameter 원래 this 였는데 오류 안나서 안 바꿈
                monthlyTodoViewModel.getAllTodos().observe(getViewLifecycleOwner(), new Observer<List<Todo>>() { //parameter 원래 this 였는데 오류나서 바꿈
                    @Override
                    public void onChanged(@Nullable List<Todo> todos) {
                        //update Recyclerview
                        monthlyAdapter.setTodos(todos);
                    }
                });

                yearlyAdapter.setDate(currentDay, currentWeek, currentMonth, currentYear);
                yearlyRecyclerView.setAdapter(yearlyAdapter);
                yearlyTodoViewModel = ViewModelProviders.of(HomeFragment.this).get(TodoViewModel.class); //parameter 원래 this 였는데 오류 안나서 안 바꿈
                yearlyTodoViewModel.getAllTodos().observe(getViewLifecycleOwner(), new Observer<List<Todo>>() { //parameter 원래 this 였는데 오류나서 바꿈
                    @Override
                    public void onChanged(@Nullable List<Todo> todos) {
                        //update Recyclerview
                        yearlyAdapter.setTodos(todos);
                    }
                });
            }
        });




        return rootView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == EDIT_TODO_REQUEST && resultCode == RESULT_OK) {
            Bundle bundle = intent.getBundleExtra("todoData");
            Todo todo = (Todo) bundle.get("todoData");
            String  category = Character.toString(intent.getCharExtra("todoData_category", 'N'));

            switch (category){
                case "D":
                    dailyTodoViewModel.update(todo);
                    break;
                case "W":
                    weeklyTodoViewModel.update(todo);
                    break;
                case "M":
                    monthlyTodoViewModel.update(todo);
                    break;
                case "Y":
                    yearlyTodoViewModel.update(todo);
                    break;
                default:
                    break;
            }
            switch (todo.getTodoCategory()){
                case "D":
                    dailyTodoViewModel.update(todo);
                    break;
                case "W":
                    weeklyTodoViewModel.update(todo);
                    break;
                case "M":
                    monthlyTodoViewModel.update(todo);
                    break;
                case "Y":
                    yearlyTodoViewModel.update(todo);
                    break;
                default:
                    break;
            }





        }
    }
}
