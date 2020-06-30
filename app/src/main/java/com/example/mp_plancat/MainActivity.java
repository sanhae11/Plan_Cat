package com.example.mp_plancat;
//메인액티비티

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.mp_plancat.database.AppDatabase;
import com.example.mp_plancat.database.entity.GameInfo;
import com.example.mp_plancat.todo.home.HomeFragment;
import com.example.mp_plancat.todo.TodoFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    public SharedPreferences prefs;
    public static AppDatabase db;

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


        Intent intent = getIntent();
        if(intent.getExtras() != null){
            getSupportFragmentManager().beginTransaction().replace(R.id.container, fragmentCat).commit(); //fragment 전환
        }
        else{
            getSupportFragmentManager().beginTransaction().replace(R.id.container, fragmentHome).commit();
        }


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

    public void checkFirstRun(){
        boolean isFirstRun = prefs.getBoolean("isFirstRun",true);
        if(isFirstRun)
        {
            Toast.makeText(this, "어플 설치 후 첫 실행입니다", Toast.LENGTH_SHORT).show();

            AsyncTask.execute(new Runnable() {
                @Override
                public void run() {
                    db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "database-name").build();

                    GameInfo gameInfo = new GameInfo();
                    Calendar cal = Calendar.getInstance();
                    int day = cal.get(Calendar.DATE);
                    int month = cal.get(Calendar.MONTH) + 1;
                    int year = cal.get(Calendar.YEAR);
                    gameInfo.setLastMessageUpdatedDay(day);
                    gameInfo.setLastMessageUpdatedMonth(month);
                    gameInfo.setLastMessageUpdatedYear(year);

                    db.gameInfoDao().insert(gameInfo);

                    Log.e("gameinfo", db.gameInfoDao().getAll().get(0).lastMessageUpdatedDay + " " + db.gameInfoDao().getAll().get(0).lastMessageUpdatedMonth + " " + db.gameInfoDao().getAll().get(0).lastMessageUpdatedYear);
                }
            });



            prefs.edit().putBoolean("isFirstRun",false).apply();
        }
    }

}
