package com.example.mp_plancat;
//할 일 생성 화면

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class CreateTodoActivity extends AppCompatActivity {
    String[] items = {"Daily", "Weekly", "Monthly", "Yearly"};
    InputMethodManager imm;
    ArrayList<Button> priority_btn_list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_todo);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true); //뒤로 가기 버튼
        actionBar.show(); //액션바 보여줌
        actionBar.setTitle("Create Todo");

        //create_todo 액티비티 실행됐을 때 키보드 나타나게 함; 앱 종료되어도 키보드 안 사라지는 문제 때문에 주석 처리
        //showKeyBoard();

        final Button btn_choose_date = (Button) findViewById(R.id.btn_choose_date);

        Spinner spinner = (Spinner) findViewById(R.id.spinner_category);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView adapterView, View view, int position, long id) {
                long now = System.currentTimeMillis();
                Date date = new Date(now);
                SimpleDateFormat simpleDate;
                String getDate;
                switch(position){
                    case 0:
                        simpleDate = new SimpleDateFormat("yyyy년 MM월 dd일");
                        getDate = simpleDate.format(date);
                        btn_choose_date.setText(getDate);
                        break;
                    case 1:
                        simpleDate = new SimpleDateFormat("yyyy년 MM월");
                        getDate = simpleDate.format(date);

                        //현재 해당 월의 몇 주차인지 받아옴
                        Calendar c = Calendar.getInstance();
                        getDate += " "+c.get(Calendar.WEEK_OF_MONTH)+"주";
                        btn_choose_date.setText(getDate);
                        break;
                    case 2:
                        simpleDate = new SimpleDateFormat("yyyy년 MM월");
                        getDate = simpleDate.format(date);
                        btn_choose_date.setText(getDate);
                        break;
                    case 3:
                        simpleDate = new SimpleDateFormat("yyyy년");
                        getDate = simpleDate.format(date);
                        btn_choose_date.setText(getDate);
                        break;
                    default:
                        break;
                }
            }
            public void onNothingSelected(AdapterView adapterView){

            }
        });

        //editText 이외의 부분 터치하면 키보드 감추기
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linearLayout);
        linearLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                EditText editText = (EditText)findViewById(R.id.editText_todo_title);
                disAppearKeyBoard();
                return false;
            }
        });


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

        //중요도 상중하 버튼 중 클릭한 것만 색칠됨
        btn_priority_top.setOnClickListener(new View.OnClickListener() { //중요도 상 클릭 시
            @Override
            public void onClick(View v) {
                checkPriority(btn_priority_top);
                uncheckPriority(btn_priority_middle);
                uncheckPriority(btn_priority_bottom);
            }
        });
        btn_priority_middle.setOnClickListener(new View.OnClickListener() { //중요도 중 클릭 시
            @Override
            public void onClick(View v) {
                uncheckPriority(btn_priority_top);
                checkPriority(btn_priority_middle);
                uncheckPriority(btn_priority_bottom);
            }
        });
        btn_priority_bottom.setOnClickListener(new View.OnClickListener() { //중요도 하 클릭 시
            @Override
            public void onClick(View v) {
                uncheckPriority(btn_priority_top);
                uncheckPriority(btn_priority_middle);
                checkPriority(btn_priority_bottom);
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

    public void showKeyBoard(){ //키보드 나타남
        if(imm == null)
            imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
    }
    public void disAppearKeyBoard(){ //키보드 사라짐
        if(imm == null)
            imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        EditText editText = (EditText) findViewById(R.id.editText_todo_title);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }

    public void checkPriority(final Button btn){ //중요도 버튼 체크
        btn.setBackgroundDrawable(getResources().getDrawable(R.drawable.custom_button_priority_checked));
        btn.setTextColor(Color.WHITE);
    }
    public void uncheckPriority(final Button btn){ //중요도 버튼 체크 해제
        btn.setBackgroundDrawable(getResources().getDrawable(R.drawable.custom_button_priority));
        btn.setTextColor(Color.BLACK);
    }
}



