package com.example.mp_plancat;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.material.tabs.TabLayout;

public class TodoFragment extends Fragment {
    int i = 0;

    //Button btn_create_todo;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = (View) inflater.inflate(R.layout.fragment_todo, container, false);

        ActionBar actionBar = ((MainActivity)getActivity()).getSupportActionBar();
        actionBar.show(); //액션바 보여줌
        actionBar.setTitle("Todo");

        //To do 의 화면 swipe하면 다른 화면으로 넘어감
        ViewPager vp = (ViewPager) view.findViewById(R.id.viewPager);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        vp.setAdapter(adapter);
        vp.setCurrentItem(0);

        //To do의 상단 탭 네 개짜리
        TabLayout tab = view.findViewById(R.id.tab);
        tab.setupWithViewPager(vp);

        /*
        btn_create_todo = (Button) rootView.findViewById(R.id.btn_create_todo);
        btn_create_todo.setOnClickListener(new View.OnClickListener() { //할 일 생성 버튼 클릭 시
            @Override
            public void onClick(View v) {
                getActivity().startActivity(new Intent(getActivity(), CreateTodoActivity.class)); //create to do activity로 감
            }
        });
        */
        return view;
    }
}
