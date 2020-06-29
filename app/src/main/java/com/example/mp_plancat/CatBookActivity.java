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

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mp_plancat.database.CatDatabase;
import com.example.mp_plancat.database.entity.Cat;
import com.example.mp_plancat.database.entity.GameInfo;


import java.util.Calendar;
import java.util.List;

public class CatBookActivity extends AppCompatActivity {
    ImageButton exit_button;
    ImageButton cat1;
    ImageView x_btn;

    TextView txt_numOfCats;

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

        txt_numOfCats = (TextView) findViewById(R.id.txt_num_of_cats);
        new getNumOfCatsTask().execute();

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

        adapter.setOnItemClickListener(new CatRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Cat cat) {
                //Todo 여기가 고양이 클릭했을 때 이벤트 발생하는 곳!!!
                Log.e("iscollected", cat.getIsCollected()+"");
                Log.e("name", cat.getCatName()+"");
                Log.e("desc", cat.getCatDescription()+"");
                if(cat.getIsCollected() == 1){
                    //수집된 고양이를 클릭했을 경우, 고양이 정보 뜸

                    Bundle args = new Bundle();
                    args.putSerializable("catData", cat);

                    CatInfoDialog catInfoDialog = new CatInfoDialog();
                    catInfoDialog.setArguments(args);
                    catInfoDialog.show(getSupportFragmentManager(), "Cat Info Dialog");
                }
                else {
                    //물음표 고양이를 클릭했을 경우, 다이얼로그 창
                    NotCollectedCatDialog notCollectedCatDialog = new NotCollectedCatDialog();

                    notCollectedCatDialog.show(getSupportFragmentManager(), "Not Collected Cat Dialog");
                }
            }
        });


        x_btn = (ImageView) findViewById(R.id.x_btn);
        x_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //엑스 버튼 누를 시 액티비티 종료
                finish();
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

    private class getNumOfCatsTask extends AsyncTask<Void, Void, String>{

        @Override
        protected String doInBackground(Void... voids) {
            int NumOfCats = catDatabase.catDao().getNumOfCats();
            int NumOfCollected = catDatabase.catDao().getNumOfCollectedCats();
            String str = Integer.toString(NumOfCollected) + "/" + Integer.toString(NumOfCats);
            return str;
        }
        @Override
        protected void onPostExecute(String string){
            txt_numOfCats.setText(string);

        }
    }
}
