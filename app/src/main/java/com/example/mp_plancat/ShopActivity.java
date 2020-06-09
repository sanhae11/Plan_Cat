package com.example.mp_plancat;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class ShopActivity extends AppCompatActivity {
    ImageButton exit_button;
    ImageButton product1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        exit_button=(ImageButton) findViewById(R.id.exitbtn);

        exit_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent=new Intent(ShopActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        product1=(ImageButton) findViewById(R.id.product1);

        product1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent=new Intent(ShopActivity.this, ShowPopActivity.class);
                startActivity(intent);
            }
        });
    }
}
