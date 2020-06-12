package com.example.mp_plancat;

import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.Date;
import java.text.SimpleDateFormat;

public class MessageFragment extends DialogFragment implements View.OnClickListener {

    public static final String TAG_EVENT_DIALOG = "dialog_event";

    private SimpleDateFormat mformat = new SimpleDateFormat("yyyy년 M월 d일 "); // 날짜 포맷
    TextView txt_date;
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

        txt_date = (TextView)v.findViewById(R.id.txt_date);
        Date date = new Date();
        String str = txt_date.getText().toString();
        String time = mformat.format(date);
        time = time + str;
        txt_date.setText(time); // 현재 날짜로 설정

        // confirm button
        btn_confirm = (ImageButton)v.findViewById(R.id.btn_confirm);
        btn_confirm.setOnClickListener(this);
        setCancelable(false);   // 검은 영역 터치시에도 꺼지는 거 방지
        return v;
    }

    @Override
    public void onClick(View v){
        dismiss();
    }
}
