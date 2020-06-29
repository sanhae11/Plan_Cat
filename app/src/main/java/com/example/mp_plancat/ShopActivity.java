package com.example.mp_plancat;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.mp_plancat.database.AppDatabase;
import com.example.mp_plancat.database.entity.GameInfo;

import java.util.ArrayList;

public class ShopActivity extends AppCompatActivity {

    public static AppDatabase db;

    // 리사이클러뷰뷰
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
   // private ShopAdapter adapter;

    TextView txt_goldcoin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        // 액션바 숨기기
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "database-name").fallbackToDestructiveMigration().build();
        txt_goldcoin = (TextView) findViewById(R.id.txt_goldcoin);
        new getGoldCoinTask().execute();

        // 리사이클러뷰에 표시할 데이터 리스트 생성.
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            list.add(String.format("TEXT %d", i));
        }

        // 리사이클러뷰에 LinearLayoutManager 객체 지정.
        RecyclerView recyclerView = findViewById(R.id.shop_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // 리사이클러뷰에 SimpleTextAdapter 객체 지정.
        SimpleTextAdapter adapter = new SimpleTextAdapter(list);
        recyclerView.setAdapter(adapter);
    }
//        adapter.setOnItemClickListener(new CatRecyclerAdapter.OnItemClickListener() { //할 일 클릭 시 edit to do 화면으로 이동
//            @Override
//            public void onItemClick(Cat cat) {
//                //Todo 여기가 고양이 클릭했을 때 이벤트 발생하는 곳!!!
//            }
//        });

        private class getGoldCoinTask extends AsyncTask<Void, Void, Integer>{

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















//package com.example.mp_plancat;
//
//import androidx.appcompat.app.ActionBar;
//import androidx.appcompat.app.AppCompatActivity;
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.ImageButton;
//
//public class ShopActivity extends AppCompatActivity {
//    ImageButton exit_button;
//    ImageButton product1;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_shop);
//
//        ActionBar actionBar = getSupportActionBar();
//        actionBar.hide();
//
//        exit_button=(ImageButton) findViewById(R.id.exitbtn);
//
//        exit_button.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v){
//                Intent intent=new Intent(ShopActivity.this, MainActivity.class);
//                startActivity(intent);
//            }
//        });
//
//        product1=(ImageButton) findViewById(R.id.product1);
//
//        product1.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v){
//                Intent intent=new Intent(ShopActivity.this, ShowPopActivity.class);
//                startActivity(intent);
//            }
//        });
//    }
//}
