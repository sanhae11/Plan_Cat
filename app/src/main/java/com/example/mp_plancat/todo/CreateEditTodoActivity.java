package com.example.mp_plancat.todo;
//할 일 생성 화면

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
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
import android.widget.Toast;

import com.example.mp_plancat.R;
import com.example.mp_plancat.database.entity.Todo;
import com.example.mp_plancat.todo.picker_dialog.DailyPickerDialog;
import com.example.mp_plancat.todo.picker_dialog.MonthlyPickerDialog;
import com.example.mp_plancat.todo.picker_dialog.WeeklyPickerDialog;
import com.example.mp_plancat.todo.picker_dialog.YearlyPickerDialog;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CreateEditTodoActivity extends AppCompatActivity {
    int check = 0; //activity 생성 이후 spinner 한 번도 클릭 안 했다면 0; 클릭하면 1씩 증가

    String[] items = {"Daily", "Weekly", "Monthly", "Yearly"};
    InputMethodManager imm;

    private int category = 0; //카테고리; 0=daily, 1=weekly, 2=monthly, 3=yearly
    private int priority = 1; //중요도; 1=상, 2=중, 3=하

    private Calendar cal = Calendar.getInstance();

    private int day = cal.get(Calendar.DATE);
    private int month = cal.get(Calendar.MONTH)+1;
    private int year = cal.get(Calendar.YEAR);
    private int week = cal.get(Calendar.WEEK_OF_MONTH);

    String dateFormatStr;

    private EditText editTextTitle;
    private Spinner spinner;
    private Button btn_choose_date;

    private DailyPickerDialog dailyPD;
    private WeeklyPickerDialog weeklyPD;
    private MonthlyPickerDialog monthlyPD;
    private YearlyPickerDialog yearlyPD;

    private Button btn_priority_top, btn_priority_middle, btn_priority_bottom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_edit_todo);

        editTextTitle = findViewById(R.id.editText_todo_title);
        spinner = (Spinner) findViewById(R.id.spinner_category);
        btn_choose_date = (Button) findViewById(R.id.btn_choose_date);

        btn_priority_top = (Button) findViewById(R.id.btn_priority_top);
        btn_priority_middle = (Button) findViewById(R.id.btn_priority_middle);
        btn_priority_bottom = (Button) findViewById(R.id.btn_priority_bottom);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true); //뒤로 가기 버튼
        actionBar.show(); //액션바 보여줌

        Intent intent = getIntent();

        if(intent.hasExtra("todoData_ID")){ //edit to do 일 때
            Bundle bundle = intent.getBundleExtra("todoData");
            Todo todo = (Todo) bundle.get("todoData");

            editSetting(todo);

            actionBar.setTitle("Edit Todo");
        }
        else{ //create to do 일 때
            actionBar.setTitle("Create Todo");

            setButtonText();
        }

        //create_todo 액티비티 실행됐을 때 키보드 나타나게 함; 앱 종료되어도 키보드 안 사라지는 문제 때문에 주석 처리 해둠
        //showKeyBoard();


        //spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setSelection(category);



        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView adapterView, View view, int position, long id) {
                if(++check > 1) { //spinner가 최소 한 번 눌렸을 때부터 if문 실행
                    long now = System.currentTimeMillis();
                    Date date = new Date(now);
                    SimpleDateFormat simpleDate;
                    String getDate;

                    initDate();
                    category = position;

                    //spinner에서 선택되는 카테고리에 맞는 포맷의 날짜가 버튼에 나타남
                    switch (position) {
                        case 0:
                            simpleDate = new SimpleDateFormat("yyyy년 MM월 dd일");
                            getDate = simpleDate.format(date);
                            btn_choose_date.setText(getDate);
                            Log.e("switchCheck", "스위치문 실행");
                            break;
                        case 1:
                            simpleDate = new SimpleDateFormat("yyyy년 MM월");
                            getDate = simpleDate.format(date);

                            //현재 해당 월의 몇 주차인지 받아옴
                            getDate += " " + cal.get(Calendar.WEEK_OF_MONTH) + "주";
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
        final DatePickerDialog.OnDateSetListener dailyListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year1, int month1, int dayOfMonth1) {
                //dialog 창의 확인 버튼 눌렀을 때의 값을 받아와서 저장
                year = year1;
                month = month1;
                cal.set(year, month-1, 1);
                if(dayOfMonth1 > cal.getActualMaximum(Calendar.DAY_OF_MONTH))
                    day = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
                else
                    day = dayOfMonth1;
                //Daily 카테고리일 때, DailyPickerDialog에서 선택한 날짜대로 버튼의 날짜도 바뀜
                dateFormatStr = year+"년 "+month+"월 "+day+"일";
                btn_choose_date.setText(dateFormatStr);

            }
        };

        //WeeklyPicker(연, 월, 주 선택하는 창)
        final DatePickerDialog.OnDateSetListener weeklyListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year1, int month1, int week1) {
                //dialog 창의 확인 버튼 눌렀을 때의 값을 받아와서 저장
                year = year1;
                month = month1;
                cal.set(year, month-1, 1);

                int max_day = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

                cal.set(year, month-1, max_day);

                int max_week = cal.getActualMaximum(Calendar.WEEK_OF_MONTH);

                if(week1 > max_week)
                    week = max_week;
                else
                    week = week1;
                //Weekly 카테고리일 때, WeeklyPickerDialog에서 선택한 날짜대로 버튼의 날짜도 바뀜
                dateFormatStr = year+"년 "+month+"월 "+week+"주";
                btn_choose_date.setText(dateFormatStr);

            }
        };

        //MonthlyPicker(연, 월 선택하는 창)
        final DatePickerDialog.OnDateSetListener monthlyListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year1, int month1, int day1) {
                //dialog 창의 확인 버튼 눌렀을 때의 값을 받아와서 저장
                year = year1;
                month = month1;
                //Monthly 카테고리일 때, MonthlyPickerDialog에서 선택한 날짜대로 버튼의 날짜도 바뀜
                dateFormatStr = year+"년 "+month+"월";
                btn_choose_date.setText(dateFormatStr);

            }
        };

        //YearlyPicker(연 선택하는 창)
        final DatePickerDialog.OnDateSetListener yearlyListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year1, int month1, int day1) {
                //dialog 창의 확인 버튼 눌렀을 때의 값을 받아와서 저장
                year = year1;
                //Yearly 카테고리일 때, YearlyPickerDialog에서 선택한 날짜대로 버튼의 날짜도 바뀜
                dateFormatStr = year+"년";
                btn_choose_date.setText(dateFormatStr);
            }
        };

        btn_choose_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //카테고리에 해당하는 dialog 창이 뜸
                cal.set(year, month-1, 1);

                int max_day = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

                if(day > max_day)
                    day = max_day;

                cal.set(year, month-1, max_day);

                int max_week = cal.getActualMaximum(Calendar.WEEK_OF_MONTH);

                if(week > max_week)
                    week = max_week;

                switch (category){
                    case 0:
                        dailyPD = new DailyPickerDialog(year, month, day);
                        dailyPD.setListener(dailyListener);
                        dailyPD.show(getSupportFragmentManager(), "DailyPickerTest");
                        break;
                    case 1:
                        weeklyPD = new WeeklyPickerDialog(year, month, week);
                        weeklyPD.setListener(weeklyListener);
                        weeklyPD.show(getSupportFragmentManager(), "WeeklyPickerTest");
                        break;
                    case 2:
                        monthlyPD = new MonthlyPickerDialog(year, month);
                        monthlyPD.setListener(monthlyListener);
                        monthlyPD.show(getSupportFragmentManager(), "MonthlyPickerTest");
                        break;
                    case 3:
                        yearlyPD = new YearlyPickerDialog(year);
                        yearlyPD.setListener(yearlyListener);
                        yearlyPD.show(getSupportFragmentManager(), "YearlyPickerTest");
                        break;
                    default:
                        break;
                }

            }
        });

        //중요도 상중하 버튼 중 클릭한 것만 색칠됨
        btn_priority_top.setOnClickListener(new View.OnClickListener() { //중요도 상 클릭 시
            @Override
            public void onClick(View v) { //중요도 상 클릭 시
                checkPriority(btn_priority_top);
                uncheckPriority(btn_priority_middle);
                uncheckPriority(btn_priority_bottom);
                priority = 1;
            }
        });
        btn_priority_middle.setOnClickListener(new View.OnClickListener() { //중요도 중 클릭 시
            @Override
            public void onClick(View v) { //중요도 중 클릭 시
                uncheckPriority(btn_priority_top);
                checkPriority(btn_priority_middle);
                uncheckPriority(btn_priority_bottom);
                priority = 2;
            }
        });
        btn_priority_bottom.setOnClickListener(new View.OnClickListener() { //중요도 하 클릭 시
            @Override
            public void onClick(View v) { //중요도 하 클릭 시
                uncheckPriority(btn_priority_top);
                uncheckPriority(btn_priority_middle);
                checkPriority(btn_priority_bottom);
                priority = 3;
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
            case android.R.id.home: //액션바의 뒤로가기 버튼 눌렀을 때 동작
                finish();
                return true;
            case R.id.check: //액션바의 체크 버튼 눌렀을 때의 동작
                if(getEditText().length() == 0 || spaceCheck(getEditText())){ //할 일 타이틀 입력하지 않았을 때
                    //할 일 입력하라는 toast message 띄움
                    Toast.makeText(this, "할 일을 입력하세요!", Toast.LENGTH_SHORT).show();
                }
                else{
                    String title = getEditText();
                    String category_str = InputConverter.getCategory(category);

                    Calendar startDate = InputConverter.getStartDate(day, week, month, year, category);
                    Calendar endDate = InputConverter.getEndDate(day, week, month, year, category);

                    double point = InputConverter.getCalculatedPoint(endDate, priority);

                    Todo todoData = new Todo(title, category_str, startDate, endDate, point, priority);

                    Intent intent = new Intent();
                    Bundle bundle = new Bundle();

                    int id = getIntent().getIntExtra("todoData_ID", -1);
                    if(id != -1){ //edit to do 일 시, id 값 세팅
                        todoData.setId(id);
                    }

                    int checkState = getIntent().getIntExtra("todoData_checkState", -1);
                    if(checkState != -1){ //edit to do 일 시, check state 세팅
                        if(checkState == 1)
                            todoData.setIsFinished(true);
                        else
                            todoData.setIsFinished(false);
                    }

                    Character character = getIntent().getCharExtra("todoData_category", 'N');
                    if(character != 'N'){
                        intent.putExtra("todoData_category", character);
                    }


                    bundle.putSerializable("todoData", todoData);
                    intent.putExtra("todoData", bundle);

                    setResult(RESULT_OK, intent);
                    finish();
                }
                return true;
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

    public void initDate(){ //date를 현재 날짜로 초기화
        cal = Calendar.getInstance();
        day = cal.get(Calendar.DATE);
        month = cal.get(Calendar.MONTH)+1;
        year = cal.get(Calendar.YEAR);
        week = cal.get(Calendar.WEEK_OF_MONTH);
    }

    public String getEditText(){
        EditText editText = findViewById(R.id.editText_todo_title);
        return editText.getText().toString();
    }

    public boolean spaceCheck(String str){ //문자열이 공백으로만 이루어져 있을 때 true return
        for (int i = 0; i < str.length(); i++){
            if(str.charAt(i) != ' ')
                return false;
        }
        return true;
    }

    public void editSetting(Todo todo){ //edit to do 일 때, to do의 정보 세팅해줌
        this.day = todo.getStartDate().get(Calendar.DAY_OF_MONTH);
        this.week = todo.getStartDate().get(Calendar.WEEK_OF_MONTH);
        this.month = todo.getStartDate().get(Calendar.MONTH) + 1;
        this.year = todo.getStartDate().get(Calendar.YEAR);

        switch (todo.getTodoCategory()){
            case "D":
                this.category = 0;
                this.dateFormatStr = year + "년 " + month + "월 " + day + "일";
                break;
            case "W":
                this.category = 1;
                this.dateFormatStr = year + "년 " + month + "월 " + week + "주";
                break;
            case "M":
                this.category = 2;
                this.dateFormatStr = year + "년 " + month + "월";
                break;
            default:
                this.category = 3;
                this.dateFormatStr = year + "년";
                break;
        }
        this.priority = todo.getPriority();

        editTextTitle.setText(todo.getTodoTitle());
        spinner.setSelection(category);
        btn_choose_date.setText(dateFormatStr);

        switch (priority){
            case 1:
                checkPriority(btn_priority_top);
                uncheckPriority(btn_priority_middle);
                uncheckPriority(btn_priority_bottom);
                break;
            case 2:
                uncheckPriority(btn_priority_top);
                checkPriority(btn_priority_middle);
                uncheckPriority(btn_priority_bottom);
                break;
            default:
                uncheckPriority(btn_priority_top);
                uncheckPriority(btn_priority_middle);
                checkPriority(btn_priority_bottom);
                break;
        }
    }

    public void setButtonText(){ //create to do 일 때, 날짜 선택 버튼의 텍스트 세팅해줌
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat simpleDate;
        String getDate;

        simpleDate = new SimpleDateFormat("yyyy년 MM월 dd일");
        getDate = simpleDate.format(date);
        btn_choose_date.setText(getDate);
    }
}



