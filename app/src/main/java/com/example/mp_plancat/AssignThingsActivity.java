package com.example.mp_plancat;
//고양이 - 보유 물품 배치 화면

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

public class AssignThingsActivity extends AppCompatActivity {
    private ImageView location1, location2, location3, location4, location5;
    private ImageView x_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assign_things);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        location1 = (ImageView) findViewById(R.id.assign_location_1);
        location2 = (ImageView) findViewById(R.id.assign_location_2);
        location3 = (ImageView) findViewById(R.id.assign_location_3);
        location4 = (ImageView) findViewById(R.id.assign_location_4);
        location5 = (ImageView) findViewById(R.id.assign_location_5);

        location1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AssignThingsDialog assignThingsDialog = new AssignThingsDialog();
                assignThingsDialog.show(getSupportFragmentManager(), "Assign Things Dialog");
            }
        });
        location2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AssignThingsDialog assignThingsDialog = new AssignThingsDialog();
                assignThingsDialog.show(getSupportFragmentManager(), "Assign Things Dialog");
            }
        });
        location3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AssignThingsDialog assignThingsDialog = new AssignThingsDialog();
                assignThingsDialog.show(getSupportFragmentManager(), "Assign Things Dialog");
            }
        });
        location4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AssignThingsDialog assignThingsDialog = new AssignThingsDialog();
                assignThingsDialog.show(getSupportFragmentManager(), "Assign Things Dialog");
            }
        });
        location5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AssignThingsDialog assignThingsDialog = new AssignThingsDialog();
                assignThingsDialog.show(getSupportFragmentManager(), "Assign Things Dialog");
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
}
