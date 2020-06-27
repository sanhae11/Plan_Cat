package com.example.mp_plancat;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

public class MyThingsActivity extends AppCompatActivity {

    ImageButton furniture_1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_things);

        furniture_1 = (ImageButton) findViewById(R.id.furniture_1);
        furniture_1.setOnClickListener(new View.OnClickListener(){
        @Override
        public void onClick(View v){
            MyThingsFragment e = MyThingsFragment.getInstance();
            e.show(getSupportFragmentManager(), MyThingsFragment.TAG_EVENT_DIALOG);
        }
        });
    }
}
