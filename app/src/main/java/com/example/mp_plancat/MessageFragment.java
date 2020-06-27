package com.example.mp_plancat;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.room.Room;

import com.example.mp_plancat.database.AppDatabase;
import com.example.mp_plancat.database.TodoDatabase;
import com.example.mp_plancat.database.entity.GameInfo;

import java.util.Calendar;
import java.util.List;

public class MessageFragment extends DialogFragment implements View.OnClickListener {
    public static AppDatabase db;
    public static TodoDatabase todoDb;
    Calendar cal = Calendar.getInstance();
    int getPoint, day, month, year, result_point;
    List<String> titles;

    public static final String TAG_EVENT_DIALOG = "dialog_event";
    TextView txt_date, txt_content, txt_coin, txt_goldcoin;
    ImageButton btn_confirm;

    private MessageFragmentListener messageFragmentListener;


    public MessageFragment(){}

    public static MessageFragment getInstance(){
        MessageFragment e = new MessageFragment();
        return e;
    }

    //인터페이스 설정
    interface MessageFragmentListener {
        void onPositiveClicked(int coin);
        void onNegativeClicked();
    }

    //호출할 리스너 초기화
    public void setDialogListener(MessageFragmentListener messageFragmentListener){
        this.messageFragmentListener = messageFragmentListener;
    }




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.popup_goldcoin, container);
        View cat_main = inflater.inflate(R.layout.fragment_cat, container);


        db = Room.databaseBuilder(getActivity().getApplicationContext(), AppDatabase.class, "database-name").build();
        todoDb = TodoDatabase.getInstance(getActivity().getApplication());

        txt_date = (TextView)v.findViewById(R.id.txt_date);
        txt_content = (TextView)v.findViewById(R.id.txt_completedplan);
        txt_coin = (TextView)v.findViewById(R.id.txt_coin);
        txt_goldcoin = (TextView) cat_main.findViewById(R.id.txt_goldcoin);

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


        // confirm button
        btn_confirm = (ImageButton)v.findViewById(R.id.btn_confirm);

        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {
                        GameInfo gameInfo = db.gameInfoDao().getAll().get(0);
                        int original_Npoint = gameInfo.normalPoint;
                        result_point = original_Npoint + getPoint;

                        //인터페이스의 함수를 호출하여 result_point에 저장된 값을 Cat Fragment로 전달


                        Log.e("test", original_Npoint + " " + getPoint);
                        Log.e("test", result_point + " ");

                        gameInfo.setNormalPoint(original_Npoint + getPoint);
                        gameInfo.setLastMessageUpdatedDay(cal.get(Calendar.DATE));
                        gameInfo.setLastMessageUpdatedMonth(cal.get(Calendar.MONTH) + 1);
                        gameInfo.setLastMessageUpdatedYear(cal.get(Calendar.YEAR));
                        db.gameInfoDao().update(gameInfo);
                    }
                });*/
                new getResultPointTask().execute();
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

    private class getResultPointTask extends AsyncTask<Void, Void, Integer>{

        @Override
        protected Integer doInBackground(Void... voids) {
            GameInfo gameInfo = db.gameInfoDao().getAll().get(0);
            int original_Npoint = gameInfo.normalPoint;
            result_point = original_Npoint + getPoint;

            Log.e("test", original_Npoint + " " + getPoint);
            Log.e("test", result_point + " ");

            //메시지 마지막 확인날짜 업데이트
            gameInfo.setNormalPoint(original_Npoint + getPoint);
            gameInfo.setLastMessageUpdatedDay(cal.get(Calendar.DATE));
            gameInfo.setLastMessageUpdatedMonth(cal.get(Calendar.MONTH) + 1);
            gameInfo.setLastMessageUpdatedYear(cal.get(Calendar.YEAR));
            db.gameInfoDao().update(gameInfo);
            return result_point;
        }
        @Override
        protected void onPostExecute(Integer result_point){
            //인터페이스의 함수를 호출하여 result_point에 저장된 값을 Cat Fragment로 전달
            messageFragmentListener.onPositiveClicked(result_point);
        }
    }
}
