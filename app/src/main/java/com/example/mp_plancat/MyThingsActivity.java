package com.example.mp_plancat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.util.ArrayList;

public class MyThingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_things);

        // 리사이클러뷰에 표시할 데이터 리스트 생성.
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            list.add(String.format("TEXT %d", i));
        }

        // 리사이클러뷰에 LinearLayoutManager 객체 지정.
        RecyclerView recyclerView = findViewById(R.id.mythings_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // 리사이클러뷰에 SimpleTextAdapter 객체 지정.
        MyThingsAdapter adapter = new MyThingsAdapter(list);
        recyclerView.setAdapter(adapter);

        // 왜 배치 버튼이 안먹히지
//        Button btn_assign = findViewById(R.id.btn_assign);
//        btn_assign.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                MyThingsFragment e = MyThingsFragment.getInstance();
//                e.show(getSupportFragmentManager(), MyThingsFragment.TAG_EVENT_DIALOG);
//            }
//        });
    }
}