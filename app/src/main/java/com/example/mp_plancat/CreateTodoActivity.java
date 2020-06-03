package com.example.mp_plancat;
//할 일 생성 화면

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;

public class CreateTodoActivity extends AppCompatActivity {
    String[] items = {"Daily", "Weekly", "Monthly", "Yearly"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_todo);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true); //뒤로 가기 버튼
        actionBar.show(); //액션바 보여줌
        actionBar.setTitle("Create Todo");

        Spinner spinner = (Spinner) findViewById(R.id.spinner_category);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        /*
        DatePicker datePicker = (DatePicker)findViewById(R.id.dataPicker);
        datePicker.init(year, month, day, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                String date = year + "/" + monthOfYear + "/" + dayOfMonth;
            }
        });
        */

        final Button btn_priority_top = (Button) findViewById(R.id.btn_priority_top);
        final Button btn_priority_middle = (Button) findViewById(R.id.btn_priority_middle);
        final Button btn_priority_bottom = (Button) findViewById(R.id.btn_priority_bottom);
        btn_priority_top.setOnClickListener(new View.OnClickListener() { //중요도 상 클릭 시
            @Override
            public void onClick(View v) {
                //중요도 상 만 검은 동그라미로 감싸짐; 중,하 는 검은 동그라미 사라짐
                btn_priority_top.setBackgroundDrawable(getResources().getDrawable(R.drawable.custom_button_priority_checked));
                btn_priority_top.setTextColor(Color.WHITE);

                btn_priority_middle.setBackgroundDrawable(getResources().getDrawable(R.drawable.custom_button_priority));
                btn_priority_middle.setTextColor(Color.BLACK);

                btn_priority_bottom.setBackgroundDrawable(getResources().getDrawable(R.drawable.custom_button_priority));
                btn_priority_bottom.setTextColor(Color.BLACK);
            }
        });
        btn_priority_middle.setOnClickListener(new View.OnClickListener() { //중요도 중 클릭 시
            @Override
            public void onClick(View v) {
                //중요도 중 만 검은 동그라미로 감싸짐; 상,하 는 검은 동그라미 사라짐
                btn_priority_top.setBackgroundDrawable(getResources().getDrawable(R.drawable.custom_button_priority));
                btn_priority_top.setTextColor(Color.BLACK);

                btn_priority_middle.setBackgroundDrawable(getResources().getDrawable(R.drawable.custom_button_priority_checked));
                btn_priority_middle.setTextColor(Color.WHITE);

                btn_priority_bottom.setBackgroundDrawable(getResources().getDrawable(R.drawable.custom_button_priority));
                btn_priority_bottom.setTextColor(Color.BLACK);
            }
        });
        btn_priority_bottom.setOnClickListener(new View.OnClickListener() { //중요도 하 클릭 시
            @Override
            public void onClick(View v) {
                //중요도 하 만 검은 동그라미로 감싸짐; 상,중 은 검은 동그라미 사라짐
                btn_priority_top.setBackgroundDrawable(getResources().getDrawable(R.drawable.custom_button_priority));
                btn_priority_top.setTextColor(Color.BLACK);

                btn_priority_middle.setBackgroundDrawable(getResources().getDrawable(R.drawable.custom_button_priority));
                btn_priority_middle.setTextColor(Color.BLACK);

                btn_priority_bottom.setBackgroundDrawable(getResources().getDrawable(R.drawable.custom_button_priority_checked));
                btn_priority_bottom.setTextColor(Color.WHITE);
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_check_actionbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{ //뒤로가기 버튼 눌렀을 때 동작
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
