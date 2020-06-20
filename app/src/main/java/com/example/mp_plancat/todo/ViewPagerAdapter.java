package com.example.mp_plancat.todo;

import android.os.Bundle;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class ViewPagerAdapter extends FragmentPagerAdapter { //ViewPager에 content 표시하기 위한 Adapter
    private ArrayList<String> itext = new ArrayList<String>(); //상단 탭의 각 title에 해당하는 string list
    private ArrayList<Fragment> fragments = new ArrayList<Fragment>();


    private Bundle bundle;


    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);

        itext.add("DAILY");
        itext.add("WEEKLY");
        itext.add("MONTHLY");
        itext.add("YEARLY");
    }

    @NonNull
    @Override
    public Fragment getItem(int position) { //현재 탭에 해당하는 fragment 호출
        return fragments.get(position);
    }

    public void setFrag(ArrayList<Fragment> fragments){
        this.fragments = fragments;
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

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        super.destroyItem(container, position, object);
    }
}
