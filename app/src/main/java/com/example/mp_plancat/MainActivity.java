package com.example.mp_plancat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    HomeFragment fragmentHome;
    TodoFragment fragmentTodo;
    CatFragment fragmentCat;

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
                        return true;
                    case R.id.tab_home :
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragmentHome).commit();
                        return true;
                    case R.id.tab_cat :
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragmentCat).commit();
                        return true;
                }
                return false;
            }
        });
    }
}
