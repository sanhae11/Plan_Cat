package com.example.mp_plancat;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class CatBookActivity extends AppCompatActivity {
    ImageButton exit_button;
    ImageButton cat1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cat_book);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        exit_button=(ImageButton) findViewById(R.id.exitbtn);

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
        });
    }
}
