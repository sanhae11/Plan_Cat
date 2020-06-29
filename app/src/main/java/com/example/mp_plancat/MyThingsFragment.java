package com.example.mp_plancat;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MyThingsFragment extends DialogFragment implements View.OnClickListener{

    public static final String TAG_EVENT_DIALOG = "dialog_event";

    private SimpleDateFormat mformat = new SimpleDateFormat("yyyy년 M월 d일 "); // 날짜 포맷
    TextView txt_date;
    ImageButton btn_confirm;

    public MyThingsFragment(){}

    public static MyThingsFragment getInstance(){
        MyThingsFragment e = new MyThingsFragment();
        return e;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.popup_mythings, container);

        // confirm button ; go assgin_things
        btn_confirm = (ImageButton)v.findViewById(R.id.btn_confirm);
        btn_confirm.setOnClickListener(this);
        btn_confirm.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                getActivity().startActivity(new Intent(getActivity(), AssignThingsActivity.class)); // 클릭 시 assignThings activity 화면으로 감
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
