package com.example.mp_plancat.todo;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mp_plancat.MainActivity;
import com.example.mp_plancat.R;
import com.example.mp_plancat.todo.category.DailyFragment;
import com.example.mp_plancat.todo.category.MonthlyFragment;
import com.example.mp_plancat.todo.category.WeeklyFragment;
import com.example.mp_plancat.todo.category.YearlyFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;

public class TodoFragment extends Fragment {
    public static final int ADD_TODO_REQUEST = 1;

    ViewPagerAdapter adapter;
    ViewPager vp;

    private ArrayList<Fragment> fragments = new ArrayList<Fragment>();
    public DailyFragment dailyFragment = new DailyFragment();
    public WeeklyFragment weeklyFragment = new WeeklyFragment();
    public MonthlyFragment monthlyFragment = new MonthlyFragment();
    public YearlyFragment yearlyFragment = new YearlyFragment();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        Log.e("test", "todofrag : oncreateview() 실행");


        View view = (View) inflater.inflate(R.layout.fragment_todo, container, false);

        ActionBar actionBar = ((MainActivity)getActivity()).getSupportActionBar();
        actionBar.show(); //액션바 보여줌
        actionBar.setTitle("Todo");

        //To do 의 화면 swipe하면 다른 화면으로 넘어감
        vp = (ViewPager) view.findViewById(R.id.viewPager);
        adapter = new ViewPagerAdapter(getChildFragmentManager());

        fragments.add(0, dailyFragment);
        fragments.add(1, weeklyFragment);
        fragments.add(2, monthlyFragment);
        fragments.add(3, yearlyFragment);

        adapter.setFrag(fragments);
        adapter.getItem(0);
        vp.setAdapter(adapter);
        vp.setCurrentItem(0);

        //To do의 상단 탭 네 개짜리
        TabLayout tab = view.findViewById(R.id.tab);
        tab.setupWithViewPager(vp);

        FloatingActionButton btn_create_todo = (FloatingActionButton) view.findViewById(R.id.btn_create_todo);
        btn_create_todo.setOnClickListener(new View.OnClickListener() { //할 일 생성 버튼 클릭 시
            @Override
            public void onClick(View v) {
                //getActivity().startActivityForResult(new Intent(getActivity(), CreateTodoActivity.class), 3); //create to do activity로 감
                startActivityForResult(new Intent(getActivity(), CreateEditTodoActivity.class), ADD_TODO_REQUEST); //create to do activity로 감

            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent){
        if(requestCode == ADD_TODO_REQUEST && resultCode == RESULT_OK){
            Bundle bundle = intent.getBundleExtra("todoData");

            dailyFragment.setArguments(bundle);
            weeklyFragment.setArguments(bundle);
            monthlyFragment.setArguments(bundle);
            yearlyFragment.setArguments(bundle);

            fragments.add(0, dailyFragment);
            fragments.add(1, weeklyFragment);
            fragments.add(2, monthlyFragment);
            fragments.add(3, yearlyFragment);

            adapter = new ViewPagerAdapter(getChildFragmentManager());
            adapter.setFrag(fragments);

            vp.setAdapter(adapter);
            vp.setCurrentItem(0);
        }
    }
}
