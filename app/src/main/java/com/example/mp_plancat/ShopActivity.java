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
import androidx.room.Room;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mp_plancat.database.AppDatabase;
import com.example.mp_plancat.database.CatDatabase;
import com.example.mp_plancat.database.TodoDatabase;
import com.example.mp_plancat.database.entity.GameInfo;
import com.example.mp_plancat.database.entity.Goods;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class ShopActivity extends AppCompatActivity {
    public static AppDatabase db;
    GoodsViewModel goodsViewModel;
    TextView txt_goldcoin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "database-name").fallbackToDestructiveMigration().build();

        txt_goldcoin = (TextView) findViewById(R.id.txt_goldcoin);
        new ShopActivity.getGoldCoinTask().execute();

        RecyclerView recyclerView = findViewById(R.id.shop_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this)); //parameter 원래는 this였음
        recyclerView.setHasFixedSize(true);



        final ShopAdapter adapter = new ShopAdapter();
        recyclerView.setAdapter(adapter);

        goodsViewModel = ViewModelProviders.of(this).get(GoodsViewModel.class); //parameter 원래 this 였는데 오류 안나서 안 바꿈
        goodsViewModel.getAllGoods().observe(this, new Observer<List<Goods>>() { //parameter 원래 this 였는데 오류나서 바꿈
            @Override
            public void onChanged(@Nullable List<Goods> goods) {
                //update Recyclerview
                adapter.setGoods(goods);
            }
        });

        adapter.setOnItemClickListener(new ShopAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Goods goods) {
            }
        });

        adapter.setOnButtonClickListener(new ShopAdapter.OnButtonClickListener() {
            @Override
            public void onButtonClick(Goods goods) {


                AssignThingsDialog assignThingsDialog = new AssignThingsDialog();
                assignThingsDialog.show(getSupportFragmentManager(), "Buy Things Dialog");

                assignThingsDialog.setOnButtonClickListener(new AssignThingsDialog.OnButtonClickListener() {
                    @Override
                    public void onButtonClick() {
                        AsyncTask.execute(new Runnable() {
                            @Override
                            public void run() {
                                int original_point = db.gameInfoDao().getAll().get(0).getNormalPoint();
                                int goods_point = goods.getPurchasePoint();
                                int result = original_point - goods_point;
                                Log.e("굿즈 정보", "id" + goods.goodsID + "name : " + goods.goodsName + " 설명  : " + goods.goodsDescription + " " + goods.isPurchased);
                                Log.e("test", original_point + " " + goods_point + " " + result);
                                if(result < 0){
                                    //포인트 모자라다는 팝업창 띄우기 . 에러남
                                    //Toast.makeText(getApplicationContext(), "포인트가 모자랍니다!", Toast.LENGTH_SHORT).show();
                                    Log.e("돈모자람 ", "돈모자람");
                                }
                                else if(goods.getIsPurchased() == 1){
                                    //이미 구매하였습니다!
                                }
                                else{
                                    //포인트 업데이트
                                    new ShopActivity.getGoldCoinTask().execute();
                                    GameInfo gameInfo = db.gameInfoDao().getAll().get(0);
                                    gameInfo.setNormalPoint(result);
                                    db.gameInfoDao().update(gameInfo);
                                    Log.e("point", db.gameInfoDao().getAll().get(0).getNormalPoint() + "");

                                    //굿즈 업데이트
                                    goods.setIsPurchased(1);
                                    goodsViewModel.update(goods);

                                    //List<Goods> purchasedlist = new ArrayList<>();
                                    //purchasedlist = db.goodsDao().getAllPurchased();
                                    //Log.e("구매목록 개수", "몇개 "+purchasedlist.size());
                                    //Log.e("굿즈 정보", "name : " + db.goodsDao().getAllPurchased().get(0).goodsName + " 설명  : " + goods.goodsDescription + " " + goods.isPurchased);

                                    //구매에 성공하였습니다!
                                }
                            }
                        });
                        //Todo 구매

                    }
                });
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
            View view = getLayoutInflater().inflate(R.layout.fragment_cat, null, false);
            TextView frag_cat_goldcoin = (TextView)view.findViewById(R.id.txt_goldcoin);
            //인터페이스의 함수를 호출하여 result_point에 저장된 값을 Cat Fragment로 전달
            //messageFragmentListener.onPositiveClicked(gold_point);
            txt_goldcoin.setText(gold_point+"");
            frag_cat_goldcoin.setText(gold_point + "");
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
