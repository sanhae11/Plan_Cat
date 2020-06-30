package com.example.mp_plancat;
//고양이 - 보유 물품 배치 화면

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.mp_plancat.database.AppDatabase;
import com.example.mp_plancat.database.entity.GameInfo;
import com.example.mp_plancat.database.entity.Goods;
import com.example.mp_plancat.database.entity.Location;

import java.util.List;

public class AssignThingsActivity extends AppCompatActivity {
    private ImageView location1, location2, location3, location4, location5;
    private ImageView x_btn;
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assign_things);

        db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "database-name").fallbackToDestructiveMigration().build();

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        Bundle bundle = getIntent().getBundleExtra("goodsData");
        Goods selected_goods = (Goods)bundle.get("goodsData");

        location1 = (ImageView) findViewById(R.id.assign_location_1);
        location2 = (ImageView) findViewById(R.id.assign_location_2);
        location3 = (ImageView) findViewById(R.id.assign_location_3);
        location4 = (ImageView) findViewById(R.id.assign_location_4);
        location5 = (ImageView) findViewById(R.id.assign_location_5);

        new AssignThingsActivity.setTransparentTask().execute();

        location1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AssignThingsDialog assignThingsDialog = new AssignThingsDialog();
                assignThingsDialog.show(getSupportFragmentManager(), "Assign Things Dialog");

                assignThingsDialog.setOnButtonClickListener(new AssignThingsDialog.OnButtonClickListener() {
                    @Override
                    public void onButtonClick() {
                        selected_goods.setIsAssigned(1);
                        AsyncTask.execute(new Runnable() {
                            @Override
                            public void run() {
                                Location newloce = new Location(1, selected_goods.getGoodsID());
                                db.locationDao().insert(newloce);
                            }
                        });
                        finish();
                    }
                });
            }
        });
        location2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AssignThingsDialog assignThingsDialog = new AssignThingsDialog();
                assignThingsDialog.show(getSupportFragmentManager(), "Assign Things Dialog");

                assignThingsDialog.setOnButtonClickListener(new AssignThingsDialog.OnButtonClickListener() {
                    @Override
                    public void onButtonClick() {
                        selected_goods.setIsAssigned(1);
                        AsyncTask.execute(new Runnable() {
                            @Override
                            public void run() {
                                Location newloce = new Location(2, selected_goods.getGoodsID());
                                db.locationDao().insert(newloce);
                            }
                        });
                        finish();
                    }
                });
            }
        });
        location3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AssignThingsDialog assignThingsDialog = new AssignThingsDialog();
                assignThingsDialog.show(getSupportFragmentManager(), "Assign Things Dialog");

                assignThingsDialog.setOnButtonClickListener(new AssignThingsDialog.OnButtonClickListener() {
                    @Override
                    public void onButtonClick() {
                        selected_goods.setIsAssigned(1);
                        AsyncTask.execute(new Runnable() {
                            @Override
                            public void run() {
                                Location newloce = new Location(3, selected_goods.getGoodsID());
                                db.locationDao().insert(newloce);
                            }
                        });
                        Intent intent = new Intent();
                        intent.putExtra("result", "some value");
                        setResult(RESULT_OK, intent);

                        finish();
                    }
                });
            }
        });
        location4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AssignThingsDialog assignThingsDialog = new AssignThingsDialog();
                assignThingsDialog.show(getSupportFragmentManager(), "Assign Things Dialog");

                assignThingsDialog.setOnButtonClickListener(new AssignThingsDialog.OnButtonClickListener() {
                    @Override
                    public void onButtonClick() {
                        selected_goods.setIsAssigned(1);
                        AsyncTask.execute(new Runnable() {
                            @Override
                            public void run() {
                                Location newloce = new Location(4, selected_goods.getGoodsID());
                                db.locationDao().insert(newloce);
                            }
                        });
                        finish();
                    }
                });
            }
        });
        location5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AssignThingsDialog assignThingsDialog = new AssignThingsDialog();
                assignThingsDialog.show(getSupportFragmentManager(), "Assign Things Dialog");

                assignThingsDialog.setOnButtonClickListener(new AssignThingsDialog.OnButtonClickListener() {
                    @Override
                    public void onButtonClick() {
                        selected_goods.setIsAssigned(1);
                        AsyncTask.execute(new Runnable() {
                            @Override
                            public void run() {
                                Location newloce = new Location(5, selected_goods.getGoodsID());
                                db.locationDao().insert(newloce);
                            }
                        });
                        finish();
                    }
                });
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


    }

    private class setTransparentTask extends AsyncTask<Void, Void, List<Location>>{

        @Override
        protected List<Location> doInBackground(Void... voids) {
            List<Location> locationList = db.locationDao().getAllLocations();

            return locationList;
        }
        @Override
        protected void onPostExecute(List<Location> locations){
            for(int i = 0; i < locations.size(); i++){
                switch(locations.get(i).locationID){
                    case 1:
                        location1.setBackgroundResource(R.drawable.button_assign_location_invisible);
                        location1.setClickable(false);
                        break;
                    case 2:
                        location2.setBackgroundResource(R.drawable.button_assign_location_invisible);
                        location2.setClickable(false);
                        break;
                    case 3:
                        location3.setBackgroundResource(R.drawable.button_assign_location_invisible);
                        location3.setClickable(false);
                        break;
                    case 4:
                        location4.setBackgroundResource(R.drawable.button_assign_location_invisible);
                        location4.setClickable(false);
                        break;
                    default:
                        location5.setBackgroundResource(R.drawable.button_assign_location_invisible);
                        location5.setClickable(false);
                        break;
                }
            }
        }
    }
}
