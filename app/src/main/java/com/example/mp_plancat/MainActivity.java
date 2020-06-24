package com.example.mp_plancat;
//메인액티비티

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.mp_plancat.todo.home.HomeFragment;
import com.example.mp_plancat.todo.TodoFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    public SharedPreferences prefs;

    HomeFragment fragmentHome;
    TodoFragment fragmentTodo;
    CatFragment fragmentCat;
    int category = 2; //현재 하단탭 중 어떤 화면인지 분류; to do=1, home=2, cat=3

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        prefs = getSharedPreferences("Pref", MODE_PRIVATE);

        checkFirstRun();

        fragmentHome = new HomeFragment();
        fragmentTodo = new TodoFragment();
        fragmentCat = new CatFragment();

        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragmentHome).commit();

        BottomNavigationView bottomNavigation = findViewById(R.id.bottom_navigation);
        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.tab_todo : //하단탭 중 to do 클릭 시
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragmentTodo).commit(); //fragment 전환
                        category = 1;
                        return true;
                    case R.id.tab_home : //하단탭 중 home 클릭 시
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragmentHome).commit(); //fragment 전환
                        category = 2;
                        return true;
                    case R.id.tab_cat : //하단탭 중 cat 클릭 시
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragmentCat).commit(); //fragment 전환
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
    public boolean onPrepareOptionsMenu(Menu menu) { //액션바의 점 세개 메뉴 누를 때, 특정 화면에 따라 메뉴 구성 달라짐

        if(category == 1){
            menu.findItem(R.id.menu_hide_past_list).setVisible(true); //'과거 목록 숨기기' 메뉴 보여줌
        }
        else if(category == 2){
            menu.findItem(R.id.menu_hide_past_list).setVisible(false); //'과거 목록 숨기기' 메뉴 없앰
        }
        else{
            menu.findItem(R.id.menu_hide_past_list).setVisible(true); //'과거 목록 숨기기' 메뉴 보여줌
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){ //각 메뉴 클릭 시 알맞게 동작
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

    public void checkFirstRun(){
        boolean isFirstRun = prefs.getBoolean("isFirstRun",true);
        if(isFirstRun)
        {
            Toast.makeText(this, "어플 설치 후 첫 실행입니다", Toast.LENGTH_SHORT).show();

            prefs.edit().putBoolean("isFirstRun",false).apply();
        }
    }

}
