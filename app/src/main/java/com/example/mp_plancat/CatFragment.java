package com.example.mp_plancat;
//고양이 화면

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.room.Room;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mp_plancat.database.AppDatabase;
import com.example.mp_plancat.database.TodoDatabase;
import com.example.mp_plancat.database.entity.GameInfo;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Calendar;

public class CatFragment extends Fragment{
    public static AppDatabase db;
    public static TodoDatabase todoDb;
    FloatingActionButton fab_menu, fab_settings, fab_shop, fab_mythings, fab_catbook;
    Animation fabOpen, fabClose, fabRClockwise, fabRAntiClockwise;
    Calendar cal = Calendar.getInstance();
    TextView txt_goldcoin;
    int goldcoin;

    boolean isOpen = false;
    ImageView btn_msg;
    private static MediaPlayer mp;

    //TODO:이미지 움직이게 하기
    //TODO:추후 데이터베이스 작업한 후에, 포인트 보상 팝업창 구현

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_cat, container, false);

        mp = MediaPlayer.create(getActivity(),R.raw.main_bgm);
        mp.setLooping(true);
        mp.start();

        ActionBar actionBar = ((MainActivity)getActivity()).getSupportActionBar();
        actionBar.hide(); // 액션바 숨김

        db = Room.databaseBuilder(getActivity().getApplicationContext(), AppDatabase.class, "database-name").fallbackToDestructiveMigration().build();
        todoDb = TodoDatabase.getInstance(getActivity().getApplication());

        txt_goldcoin = (TextView) rootView.findViewById(R.id.txt_goldcoin);

        /*
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                goldcoin = db.gameInfoDao().getAll().get(0).normalPoint;
                txt_goldcoin.setText(goldcoin+"");
            }
        });*/
        new getGoldCoinTask().execute();

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
                        float points = todoDb.todoDao().getSumOfPointByDate(lastMessageUpdatedDay, lastMessageUpdatedMonth, lastMessageUpdatedYear);
                        if(!(lastMessageUpdatedDay == cal.get(Calendar.DATE) && lastMessageUpdatedMonth == cal.get(Calendar.MONTH) + 1 && lastMessageUpdatedYear == cal.get(Calendar.YEAR) )){
                            //느낌표 있는게 제대로 작동하는 것.!!!
                            Log.e("test", "1");
                            if(points != 0.0){
                                Log.e("test", "2");
                                MessageFragment e = MessageFragment.getInstance();

                                e.setDialogListener(new MessageFragment.MessageFragmentListener() {
                                    @Override
                                    public void onPositiveClicked(int coin) {
                                        txt_goldcoin.setText(Integer.toString(coin));
                                    }

                                    @Override
                                    public void onNegativeClicked() {

                                    }
                                });

                                e.show(getActivity().getSupportFragmentManager(), MessageFragment.TAG_EVENT_DIALOG);
                            }
                            else{
                                Log.e("test", "3");
                                NoRewardDialog noRewardDialog = new NoRewardDialog();

                                noRewardDialog.show(getActivity().getSupportFragmentManager(), "Message List Dialog");
                                Log.e("test", lastMessageUpdatedDay + " " +  lastMessageUpdatedMonth + " " + lastMessageUpdatedYear);
                                Log.e("test", cal.get(Calendar.DATE) + " " + (cal.get(Calendar.MONTH) + 1) + " " + cal.get(Calendar.YEAR));
                            }

                        }
                        else{ //보상 이미 확인한 경우
                            Log.e("test", "4");
                            NoRewardDialog noRewardDialog = new NoRewardDialog();

                            noRewardDialog.show(getActivity().getSupportFragmentManager(), "Message List Dialog");
                            Log.e("test", lastMessageUpdatedDay + " " +  lastMessageUpdatedMonth + " " + lastMessageUpdatedYear);
                            Log.e("test", cal.get(Calendar.DATE) + " " + (cal.get(Calendar.MONTH) + 1) + " " + cal.get(Calendar.YEAR));
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
    

    private class getGoldCoinTask extends AsyncTask<Void, Void, Integer>{

        @Override
        protected Integer doInBackground(Void... voids) {
            GameInfo gameInfo = db.gameInfoDao().getAll().get(0);
            Log.e("goldcoin" , gameInfo.normalPoint+"");
            return gameInfo.normalPoint;
        }
        @Override
        protected void onPostExecute(Integer gold_point){
            //인터페이스의 함수를 호출하여 result_point에 저장된 값을 Cat Fragment로 전달
            //messageFragmentListener.onPositiveClicked(gold_point);
            txt_goldcoin.setText(gold_point+"");
        }
    }
}

