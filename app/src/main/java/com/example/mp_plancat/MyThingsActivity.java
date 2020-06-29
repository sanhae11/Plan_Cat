package com.example.mp_plancat;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import com.example.mp_plancat.database.entity.Goods;

import java.util.List;

public class MyThingsActivity extends AppCompatActivity {
    GoodsViewModel goodsViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_things);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

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
}
