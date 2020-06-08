package com.example.mp_plancat.todo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.mp_plancat.todo.category.DailyFragment;
import com.example.mp_plancat.todo.category.MonthlyFragment;
import com.example.mp_plancat.todo.category.WeeklyFragment;
import com.example.mp_plancat.todo.category.YearlyFragment;

import java.util.ArrayList;

public class ViewPagerAdapter extends FragmentPagerAdapter { //ViewPager에 content 표시하기 위한 Adapter
    private ArrayList<String> itext = new ArrayList<String>(); //상단 탭의 각 title에 해당하는 string list
    private DailyFragment dailyFragment = new DailyFragment();
    private WeeklyFragment weeklyFragment = new WeeklyFragment();
    private MonthlyFragment monthlyFragment = new MonthlyFragment();
    private YearlyFragment yearlyFragment = new YearlyFragment();

    public ViewPagerAdapter(FragmentManager fm){
        super(fm);

        getItem(0);

        itext.add("DAILY");
        itext.add("WEEKLY");
        itext.add("MONTHLY");
        itext.add("YEARLY");
    }

    @NonNull
    @Override
    public  Fragment getItem(int position){ //현재 탭에 해당하는 fragment 호출
        if(position == 0)
            return dailyFragment;
        else if(position == 1)
            return weeklyFragment;
        else if(position == 2)
            return monthlyFragment;
        else
            return yearlyFragment;
    }

    @Override
    public int getCount(){
        return 4; //To do 탭의 fragment 개수(daily, weekly, monthly, yearly)
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position){
        return itext.get(position); //position에 따라 상단 탭의 타이틀 반환
    }
}
