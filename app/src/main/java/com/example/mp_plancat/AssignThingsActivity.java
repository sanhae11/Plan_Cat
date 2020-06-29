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
import android.widget.Toast;

public class AssignThingsActivity extends AppCompatActivity {
    private ImageButton location1, location2, location3, location4, location5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assign_things);

        location1 = (ImageButton) findViewById(R.id.assign_location_1);
        location2 = (ImageButton) findViewById(R.id.assign_location_2);
        location3 = (ImageButton) findViewById(R.id.assign_location_3);
        location4 = (ImageButton) findViewById(R.id.assign_location_4);
        location5 = (ImageButton) findViewById(R.id.assign_location_5);

        location1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AssignThingsActivity.this, "첫번째버튼클릭", Toast.LENGTH_SHORT).show();
            }
        });

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
    }
}
