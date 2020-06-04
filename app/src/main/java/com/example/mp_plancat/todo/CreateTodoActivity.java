package com.example.mp_plancat.todo;
//할 일 생성 화면

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
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

import com.example.mp_plancat.R;
import com.example.mp_plancat.todo.picker_dialog.DailyPickerDialog;
import com.example.mp_plancat.todo.picker_dialog.MonthlyPickerDialog;
import com.example.mp_plancat.todo.picker_dialog.WeeklyPickerDialog;
import com.example.mp_plancat.todo.picker_dialog.YearlyPickerDialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class CreateTodoActivity extends AppCompatActivity {
    String[] items = {"Daily", "Weekly", "Monthly", "Yearly"};
    InputMethodManager imm;
    ArrayList<Button> priority_btn_list;
    Button btn_choose_date;
    int category=0; //카테고리; 0=daily, 1=weekly, 2=monthly, 3=yearly

    private Calendar cal = Calendar.getInstance();

    private int day = cal.get(Calendar.DATE);
    private int month = cal.get(Calendar.MONTH)+1;
    private int year = cal.get(Calendar.YEAR);
    private int week = cal.get(Calendar.WEEK_OF_MONTH);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_todo);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true); //뒤로 가기 버튼
        actionBar.show(); //액션바 보여줌
        actionBar.setTitle("Create Todo");

        //create_todo 액티비티 실행됐을 때 키보드 나타나게 함; 앱 종료되어도 키보드 안 사라지는 문제 때문에 주석 처리 해둠
        //showKeyBoard();

        final Button btn_choose_date = (Button) findViewById(R.id.btn_choose_date);

        //spinner
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

                initDate();
                category = position;

                //spinner에서 선택되는 카테고리에 맞는 포맷의 날짜가 버튼에 나타남
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
                        getDate += " "+cal.get(Calendar.WEEK_OF_MONTH)+"주";
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


        //DailyPicker(연, 월, 일 선택하는 창)
        final DatePickerDialog.OnDateSetListener dailyListner = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year1, int month1, int dayOfMonth1) {
                //dialog 창의 확인 버튼 눌렀을 때의 값을 받아와서 저장
                year = year1;
                month = month1;
                day = dayOfMonth1;
                //Daily 카테고리일 때, DailyPickerDialog에서 선택한 날짜대로 버튼의 날짜도 바뀜
                btn_choose_date.setText(year+"년 "+month+"월 "+day+"일");

            }
        };

        //WeeklyPicker(연, 월, 주 선택하는 창)
        final DatePickerDialog.OnDateSetListener weeklyListner = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year1, int month1, int week1) {
                //dialog 창의 확인 버튼 눌렀을 때의 값을 받아와서 저장
                year = year1;
                month = month1;
                week = week1;
                //Weekly 카테고리일 때, WeeklyPickerDialog에서 선택한 날짜대로 버튼의 날짜도 바뀜
                btn_choose_date.setText(year+"년 "+month+"월 "+week+"주");

            }
        };

        //MonthlyPicker(연, 월 선택하는 창)
        final DatePickerDialog.OnDateSetListener monthlyListner = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year1, int month1, int day1) {
                //dialog 창의 확인 버튼 눌렀을 때의 값을 받아와서 저장
                year = year1;
                month = month1;
                //Monthly 카테고리일 때, MonthlyPickerDialog에서 선택한 날짜대로 버튼의 날짜도 바뀜
                btn_choose_date.setText(year+"년 "+month+"월");

            }
        };

        //YearlyPicker(연 선택하는 창)
        final DatePickerDialog.OnDateSetListener yearlyListner = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year1, int month1, int day1) {
                //dialog 창의 확인 버튼 눌렀을 때의 값을 받아와서 저장
                year = year1;
                //Yearly 카테고리일 때, YearlyPickerDialog에서 선택한 날짜대로 버튼의 날짜도 바뀜
                btn_choose_date.setText(year+"년");
            }
        };

        btn_choose_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //카테고리에 해당하는 dialog 창이 뜸
                switch (category){
                    case 0:
                        DailyPickerDialog dailyPD = new DailyPickerDialog(year, month, day);
                        dailyPD.setListener(dailyListner);
                        dailyPD.show(getSupportFragmentManager(), "DailyPickerTest");
                        break;
                    case 1:
                        WeeklyPickerDialog weeklyPD = new WeeklyPickerDialog(year, month, week);
                        weeklyPD.setListener(weeklyListner);
                        weeklyPD.show(getSupportFragmentManager(), "WeeklyPickerTest");
                        break;
                    case 2:
                        MonthlyPickerDialog monthlyPD = new MonthlyPickerDialog(year, month);
                        monthlyPD.setListener(monthlyListner);
                        monthlyPD.show(getSupportFragmentManager(), "MonthlyPickerTest");
                        break;
                    case 3:
                        YearlyPickerDialog yearlyPD = new YearlyPickerDialog(year);
                        yearlyPD.setListener(yearlyListner);
                        yearlyPD.show(getSupportFragmentManager(), "YearlyPickerTest");
                        break;
                    default:
                        break;
                }

            }
        });



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
            case android.R.id.home:{ //액션바의 뒤로가기 버튼 눌렀을 때 동작
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

    public void initDate(){
        cal = Calendar.getInstance();
        day = cal.get(Calendar.DATE);
        month = cal.get(Calendar.MONTH)+1;
        year = cal.get(Calendar.YEAR);
        week = cal.get(Calendar.WEEK_OF_MONTH);
    }
}



