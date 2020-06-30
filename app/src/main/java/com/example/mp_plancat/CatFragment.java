package com.example.mp_plancat;
//고양이 화면

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
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
import android.widget.Toast;

import com.example.mp_plancat.database.AppDatabase;
import com.example.mp_plancat.database.TodoDatabase;
import com.example.mp_plancat.database.entity.GameInfo;
import com.example.mp_plancat.database.entity.Goods;
import com.example.mp_plancat.database.entity.Location;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class CatFragment extends Fragment{
    private int REQUEST_TEST = 3;

    public static AppDatabase db;
    public static TodoDatabase todoDb;
    FloatingActionButton fab_menu, fab_settings, fab_shop, fab_mythings, fab_catbook;
    Animation fabOpen, fabClose, fabRClockwise, fabRAntiClockwise;
    Calendar cal = Calendar.getInstance();
    TextView txt_goldcoin;
    int goldcoin;

    private static ImageView location1;
    private static ImageView location2;
    private static ImageView location3;
    private static ImageView location4;
    private static ImageView location5;

    boolean isOpen = false;
    ImageView btn_msg;

    //TODO:이미지 움직이게 하기
    //TODO:추후 데이터베이스 작업한 후에, 포인트 보상 팝업창 구현

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_cat, container, false);

        ActionBar actionBar = ((MainActivity)getActivity()).getSupportActionBar();
        actionBar.hide(); // 액션바 숨김

        db = Room.databaseBuilder(getActivity().getApplicationContext(), AppDatabase.class, "database-name").fallbackToDestructiveMigration().build();
        todoDb = TodoDatabase.getInstance(getActivity().getApplication());

        location1 = (ImageView) rootView.findViewById(R.id.assign_location_1);
        location2 = (ImageView) rootView.findViewById(R.id.assign_location_2);
        location3 = (ImageView) rootView.findViewById(R.id.assign_location_3);
        location4 = (ImageView) rootView.findViewById(R.id.assign_location_4);
        location5 = (ImageView) rootView.findViewById(R.id.assign_location_5);

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
        new getAssignedGoodsListTask().execute();

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
                        if((lastMessageUpdatedDay == cal.get(Calendar.DATE) && lastMessageUpdatedMonth == cal.get(Calendar.MONTH) + 1 && lastMessageUpdatedYear == cal.get(Calendar.YEAR) )){
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
                Intent intent = new Intent(getActivity(), MyThingsActivity.class);
                getActivity().startActivityForResult(intent, REQUEST_TEST); // 클릭 시 my things activity 화면으로 감
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_TEST || requestCode == 2) {
            if (resultCode == RESULT_OK) {
                new getAssignedGoodsListTask().execute();
            } else {   // RESULT_CANCEL

            }
        }
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

    public static class getAssignedGoodsListTask extends AsyncTask<Void, Void, List<Location>>{

        @Override
        protected List<Location> doInBackground(Void... voids) {
            List<Location> locations = new ArrayList<>();
            locations = db.locationDao().getAllLocations();
            Log.e("testcatfrag", locations.size()+"");
            //Log.e("test", locations.get(0).goodsID + " " + locations.get(0).locationID);
            //Log.e("test", locations.get(1).goodsID + " " + locations.get(1).locationID);





            return locations;
        }
        @Override
        protected void onPostExecute(List<Location> locations){
            ImageView target_imageview;
            for(int i = 0; i < locations.size(); i++){
                target_imageview = searchImageView(locations.get(i));

                setImage(target_imageview, locations.get(i));
                Log.e("testtest", locations.get(i).goodsID + " " + locations.get(i).locationID+"");
            }


        }

        private ImageView searchImageView(Location location){
            switch (location.locationID){
                case 1:
                    return location1;
                case 2:
                    return location2;
                case 3:
                    return location3;
                case 4:
                    return location4;
                default:
                    return location5;
            }
        }

        private void setImage(ImageView imageView, Location location){
            switch (location.goodsID){
                case 1:
                    imageView.setImageResource(R.drawable.goods_cat_1);
                    break;
                case 2:
                    imageView.setImageResource(R.drawable.goods_cat_2);
                    break;
                case 3:
                    imageView.setImageResource(R.drawable.goods_cat_3);
                    break;
                case 4:
                    imageView.setImageResource(R.drawable.goods_cat_4);
                    break;
                case 5:
                    imageView.setImageResource(R.drawable.goods_cat_5);
                    break;
                case 6:
                    imageView.setImageResource(R.drawable.goods_cat_6);
                    break;
                case 7:
                    imageView.setImageResource(R.drawable.goods_cat_7);
                    break;
                default:
                    imageView.setImageResource(R.drawable.goods_cat_8);
                    break;
            }
        }
    }

}