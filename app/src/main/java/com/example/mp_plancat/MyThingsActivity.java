package com.example.mp_plancat;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.mp_plancat.database.AppDatabase;
import com.example.mp_plancat.database.entity.GameInfo;
import com.example.mp_plancat.database.entity.Goods;

import java.util.List;

public class MyThingsActivity extends AppCompatActivity {
    public static AppDatabase db;
    GoodsViewModel goodsViewModel;
    TextView txt_goldcoin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_things);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "database-name").fallbackToDestructiveMigration().build();

        txt_goldcoin = (TextView) findViewById(R.id.txt_goldcoin);
        new getGoldCoinTask().execute();

        RecyclerView recyclerView = findViewById(R.id.my_things_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this)); //parameter 원래는 this였음
        recyclerView.setHasFixedSize(true);



        final MyThingsAdapter adapter = new MyThingsAdapter();
        recyclerView.setAdapter(adapter);

        goodsViewModel = ViewModelProviders.of(this).get(GoodsViewModel.class); //parameter 원래 this 였는데 오류 안나서 안 바꿈
        goodsViewModel.getAllGoods().observe(this, new Observer<List<Goods>>() { //parameter 원래 this 였는데 오류나서 바꿈
            @Override
            public void onChanged(@Nullable List<Goods> goods) {
                //update Recyclerview
                adapter.setGoods(goods);
            }
        });

        adapter.setOnItemClickListener(new MyThingsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Goods goods) {
            }
        });

        adapter.setOnButtonClickListener(new MyThingsAdapter.OnButtonClickListener() {
            @Override
            public void onButtonClick(Goods goods) {
                //AssignThingsDialog assignThingsDialog = new AssignThingsDialog();

                //assignThingsDialog.show(getSupportFragmentManager(), "Assign Things Dialog");
                startActivity(new Intent(MyThingsActivity.this, AssignThingsActivity.class));
            }
        });
        /*super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_things);

        furniture_1 = (ImageButton) findViewById(R.id.furniture_1);
        furniture_1.setOnClickListener(new View.OnClickListener(){
        @Override
        public void onClick(View v){
            MyThingsFragment e = MyThingsFragment.getInstance();
            e.show(getSupportFragmentManager(), MyThingsFragment.TAG_EVENT_DIALOG);
        }
        });*/
    }

    private class getGoldCoinTask extends AsyncTask<Void, Void, Integer> {

        @Override
        protected Integer doInBackground(Void... voids) {
            GameInfo gameInfo = db.gameInfoDao().getAll().get(0);
            Log.e("goldcoin" , gameInfo.normalPoint+"");
            return gameInfo.normalPoint;
        }
        @Override
        protected void onPostExecute(Integer gold_point){
            //인터페이스의 함수를 호출하여 result_point에 저장된 값을 Cat Fragment로 전달
            //messageFragmentListener.onPositiveClicked(gold_point);
            txt_goldcoin.setText(gold_point+"");
        }
    }
}
