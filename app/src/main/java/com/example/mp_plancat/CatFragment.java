package com.example.mp_plancat;
//고양이 화면

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.room.Room;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mp_plancat.database.AppDatabase;
import com.example.mp_plancat.database.entity.GameInfo;
import com.example.mp_plancat.todo.DeleteTodoDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.w3c.dom.Text;

import java.util.Calendar;

public class CatFragment extends Fragment{
    public static AppDatabase db;
    FloatingActionButton fab_menu, fab_settings, fab_shop, fab_mythings, fab_catbook;
    Animation fabOpen, fabClose, fabRClockwise, fabRAntiClockwise;
    Calendar cal = Calendar.getInstance();

    boolean isOpen = false;
    ImageView btn_msg;

    //TODO:이미지 움직이게 하기
    //TODO:추후 데이터베이스 작업한 후에, 포인트 보상 팝업창 구현

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_cat, container, false);

        ActionBar actionBar = ((MainActivity)getActivity()).getSupportActionBar();
        actionBar.hide(); // 액션바 숨김

        db = Room.databaseBuilder(getActivity().getApplicationContext(), AppDatabase.class, "database-name").build();

        // 보상 알림창에, 포인트 안받을시, 노란포인트받을때, 은색포인트받을 때 각각 나누기........
        // 포인트 보상 알림창 + 팝업창 구현
        btn_msg = (ImageView)rootView.findViewById(R.id.ic_msg);
        btn_msg.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {
                        int lastMessageUpdatedDay = db.gameInfoDao().getAll().get(0).lastMessageUpdatedDay;
                        int lastMessageUpdatedMonth = db.gameInfoDao().getAll().get(0).lastMessageUpdatedMonth;
                        int lastMessageUpdatedYear = db.gameInfoDao().getAll().get(0).lastMessageUpdatedYear;
                        if((lastMessageUpdatedDay == cal.get(Calendar.DATE) && lastMessageUpdatedMonth == cal.get(Calendar.MONTH) + 1 && lastMessageUpdatedYear == cal.get(Calendar.YEAR) )){

                            MessageListDialog messageListDialog = new MessageListDialog();

                            messageListDialog.show(getActivity().getSupportFragmentManager(), "Message List Dialog");
                            Log.e("test", lastMessageUpdatedDay + " " +  lastMessageUpdatedMonth + " " + lastMessageUpdatedYear);
                            Log.e("test", cal.get(Calendar.DATE) + " " + (cal.get(Calendar.MONTH) + 1) + " " + cal.get(Calendar.YEAR));
                        }
                        else{
                            MessageFragment e = MessageFragment.getInstance();
                            e.show(getActivity().getSupportFragmentManager(), MessageFragment.TAG_EVENT_DIALOG);

                            /*
                            //오늘 날짜로 업데이트 하는 코드
                            GameInfo gameInfo = db.gameInfoDao().getAll().get(0);
                            gameInfo.setLastMessageUpdatedDay(cal.get(Calendar.DATE));
                            gameInfo.setLastMessageUpdatedMonth(cal.get(Calendar.MONTH) + 1);
                            gameInfo.setLastMessageUpdatedYear(cal.get(Calendar.YEAR));
                            db.gameInfoDao().update(gameInfo);*/
                        }
                    }
                });





            }
        });

        // Floating Action Button
        fab_menu = (FloatingActionButton)rootView.findViewById(R.id.fab_menu);
        fab_settings = (FloatingActionButton)rootView.findViewById(R.id.fab_settings);
        fab_shop = (FloatingActionButton)rootView.findViewById(R.id.fab_shop);
        fab_mythings = (FloatingActionButton)rootView.findViewById(R.id.fab_mythings);
        fab_catbook = (FloatingActionButton)rootView.findViewById(R.id.fab_catbook);

        // Animation
        fabOpen = AnimationUtils.loadAnimation(getActivity().getApplicationContext(),R.anim.fab_open);
        fabClose = AnimationUtils.loadAnimation(getActivity().getApplicationContext(),R.anim.fab_close);
        fabRClockwise = AnimationUtils.loadAnimation(getActivity().getApplicationContext(),R.anim.rotate_clockwise);
        fabRAntiClockwise = AnimationUtils.loadAnimation(getActivity().getApplicationContext(),R.anim.rotate_anticlockwise);

        fab_menu.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(isOpen){
                    fab_settings.startAnimation(fabClose);
                    fab_shop.startAnimation(fabClose);
                    fab_mythings.startAnimation(fabClose);
                    fab_catbook.startAnimation(fabClose);
                    fab_menu.startAnimation(fabRClockwise);

                    fab_settings.setClickable(false);
                    fab_shop.setClickable(false);
                    fab_mythings.setClickable(false);
                    fab_catbook.setClickable(false);

                    isOpen = false;
                }
                else{
                    fab_settings.startAnimation(fabOpen);
                    fab_shop.startAnimation(fabOpen);
                    fab_mythings.startAnimation(fabOpen);
                    fab_catbook.startAnimation(fabOpen);
                    fab_menu.startAnimation(fabRAntiClockwise);

                    fab_settings.setClickable(true);
                    fab_shop.setClickable(true);
                    fab_mythings.setClickable(true);
                    fab_catbook.setClickable(true);

                    isOpen = true;
                }
            }
        });

        fab_settings.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                SettingsFragment e = SettingsFragment.getInstance();
                e.show(getActivity().getSupportFragmentManager(), SettingsFragment.TAG_EVENT_DIALOG); // 클릭 시 Setting fragment 화면 띄움
            }
        });

        fab_shop.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                getActivity().startActivity(new Intent(getActivity(), ShopActivity.class)); // 클릭 시 shop activity 화면으로 감
            }
        });

        fab_mythings.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                getActivity().startActivity(new Intent(getActivity(), MyThingsActivity.class)); // 클릭 시 my things activity 화면으로 감
            }
        });

        fab_catbook.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                getActivity().startActivity(new Intent(getActivity(), CatBookActivity.class)); // 클릭 시 cat book activity 화면으로 감
            }
        });
        return rootView;
    }
}