package com.example.mp_plancat;

import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.room.Room;

import com.example.mp_plancat.database.AppDatabase;
import com.example.mp_plancat.database.TodoDatabase;
import com.example.mp_plancat.database.entity.GameInfo;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.List;

public class MessageFragment extends DialogFragment implements View.OnClickListener {
    public static AppDatabase db;
    public static TodoDatabase todoDb;
    Calendar cal = Calendar.getInstance();
    int getPoint, day, month, year;
    List<String> titles;

    public static final String TAG_EVENT_DIALOG = "dialog_event";

    private SimpleDateFormat mformat = new SimpleDateFormat("yyyy년 M월 d일 "); // 날짜 포맷
    TextView txt_date, txt_content, txt_coin;
    ImageButton btn_confirm;

    public MessageFragment(){}

    public static MessageFragment getInstance(){
        MessageFragment e = new MessageFragment();
        return e;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.popup_silvercoin, container);

        db = Room.databaseBuilder(getActivity().getApplicationContext(), AppDatabase.class, "database-name").build();
        //todoDb = Room.databaseBuilder(getActivity().getApplicationContext(), TodoDatabase.class, "todo_database").build();
        todoDb = TodoDatabase.getInstance(getActivity().getApplication());

        txt_date = (TextView)v.findViewById(R.id.txt_date);
        txt_content = (TextView)v.findViewById(R.id.txt_completedplan);
        txt_coin = (TextView)v.findViewById(R.id.txt_coin);

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                GameInfo gameInfo = db.gameInfoDao().getAll().get(0);

                day = gameInfo.lastMessageUpdatedDay;
                month = gameInfo.lastMessageUpdatedMonth;
                year = gameInfo.lastMessageUpdatedYear;

                txt_date.setText( year + "년 " + month + "월 " + day + "일 할 일 완료");

                Log.e("test", day + " " + month + " " + year + " ");

                //titles = new ArrayList<>();
                titles = todoDb.todoDao().getAllTitlesOfFinishedByDate(day, month, year);
                //titles = db.todoDao().getAllTitlesOfFinishedByDate(19, 6, 2020);

                String title_list_string = "";
                float points = todoDb.todoDao().getSumOfPointByDate(day, month, year);
                getPoint = (int)points;
                Log.e("test", points + "");
                if(titles != null && titles.size() != 0){
                    for(int i = 0; i < titles.size(); i++) {
                        Log.e("test", titles.get(i) + "");
                        title_list_string = title_list_string + titles.get(i) + "\n";
                    }
                }
                else{
                    title_list_string = "완료한 할 일이 없습니다!";
                }
                txt_content.setText(title_list_string);
                txt_coin.setText(Float.toString(points));
            }
        });


        //Date date = new Date();

        /*
        String time = mformat.format(date);
        time = time + str;
        txt_date.setText(time); // 현재 날짜로 설정*/

        // confirm button
        btn_confirm = (ImageButton)v.findViewById(R.id.btn_confirm);
        //btn_confirm.setOnClickListener(this);
        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {
                        GameInfo gameInfo = db.gameInfoDao().getAll().get(0);
                        int original_Npoint = gameInfo.normalPoint;
                        int result = original_Npoint + getPoint;
                        Log.e("test", original_Npoint + " " + getPoint);
                        Log.e("test", result + " ");

                        gameInfo.setNormalPoint(original_Npoint + getPoint);
                        gameInfo.setLastMessageUpdatedDay(cal.get(Calendar.DATE));
                        gameInfo.setLastMessageUpdatedMonth(cal.get(Calendar.MONTH) + 1);
                        gameInfo.setLastMessageUpdatedYear(cal.get(Calendar.YEAR));
                        db.gameInfoDao().update(gameInfo);
                    }
                });
                dismiss();
            }
        });
        setCancelable(false);   // 검은 영역 터치시에도 꺼지는 거 방지
        return v;
    }

    @Override
    public void onClick(View v){
        dismiss();
    }
}
