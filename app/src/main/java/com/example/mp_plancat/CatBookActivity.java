package com.example.mp_plancat;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.example.mp_plancat.database.CatDatabase;
import com.example.mp_plancat.database.TodoDatabase;
import com.example.mp_plancat.database.entity.Cat;
import com.example.mp_plancat.database.entity.Todo;
import com.example.mp_plancat.todo.TodoViewModel;
import com.example.mp_plancat.todo.category.CategoryTodoRecyclerAdapter;

import java.util.List;

public class CatBookActivity extends AppCompatActivity {
    ImageButton exit_button;
    ImageButton cat1;

    private CatViewModel catViewModel;

    CatDatabase catDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cat_book);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        catDatabase = CatDatabase.getInstance(getApplicationContext());

        RecyclerView recyclerView = findViewById(R.id.cat_recyclerview);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2)); //parameter 원래는 this였음
        recyclerView.setHasFixedSize(true);

        final CatRecyclerAdapter adapter = new CatRecyclerAdapter();
        recyclerView.setAdapter(adapter);

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                Log.e("test", catDatabase.catDao().getNumOfCats() + "");
            }
        });
        catViewModel = ViewModelProviders.of(this).get(CatViewModel.class); //parameter 원래 this 였는데 오류 안나서 안 바꿈
        catViewModel.getAllCats().observe(this, new Observer<List<Cat>>() { //parameter 원래 this 였는데 오류나서 바꿈
            @Override
            public void onChanged(@Nullable List<Cat> cats) {
                //update Recyclerview
                adapter.setCats(cats);
            }
        });

        adapter.setOnItemClickListener(new CatRecyclerAdapter.OnItemClickListener() { //할 일 클릭 시 edit to do 화면으로 이동
            @Override
            public void onItemClick(Cat cat) {
                //Todo 여기가 고양이 클릭했을 때 이벤트 발생하는 곳!!!
            }
        });

        /*exit_button=(ImageButton) findViewById(R.id.exitbtn);

        exit_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent=new Intent(CatBookActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        cat1=(ImageButton) findViewById(R.id.cat1);

        cat1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent=new Intent(CatBookActivity.this, CatPopActivity.class);
                startActivity(intent);
            }
        });*/
    }
}
