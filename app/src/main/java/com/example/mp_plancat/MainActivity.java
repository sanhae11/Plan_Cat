package com.example.mp_plancat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    HomeFragment fragmentHome;
    TodoFragment fragmentTodo;
    CatFragment fragmentCat;
    int category = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentHome = new HomeFragment();
        fragmentTodo = new TodoFragment();
        fragmentCat = new CatFragment();

        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragmentHome).commit();

        BottomNavigationView bottomNavigation = findViewById(R.id.bottom_navigation);
        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.tab_todo :
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragmentTodo).commit();
                        category = 1;
                        return true;
                    case R.id.tab_home :
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragmentHome).commit();
                        category = 2;
                        return true;
                    case R.id.tab_cat :
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragmentCat).commit();
                        category = 3;
                        return true;
                }
                return false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_actionbar, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        if(category == 1){
            menu.findItem(R.id.menu_hide_past_list).setVisible(true);
        }
        else if(category == 2){
            menu.findItem(R.id.menu_hide_past_list).setVisible(false);
        }
        else{
            menu.findItem(R.id.menu_hide_past_list).setVisible(true);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.menu_hide_done_list :
                //TODO:완료 목록 숨기기
                return true;
            case R.id.menu_hide_past_list :
                //TODO:과거 목록 숨기기
                return true;
            case R.id.menu_hide_coin :
                //TODO:코인 표시 숨기기
                return true;
            default :
                return super.onOptionsItemSelected(item);
        }
    }
}
