package com.example.mp_plancat;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.mp_plancat.R;
import com.example.mp_plancat.SettingsFragment;
import com.kyleduo.switchbutton.SwitchButton;

public class BuyFragment extends DialogFragment implements View.OnClickListener {

    public static final String TAG_EVENT_DIALOG = "dialog_event";

    Button btn_yes, btn_no;

    public BuyFragment() {
    }

    public static BuyFragment getInstance() {
        BuyFragment e = new BuyFragment();
        return e;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.popup_buy, container);
        btn_yes = (Button)v.findViewById(R.id.btn_yes);
        btn_no = (Button)v.findViewById(R.id.btn_no);

        btn_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        // TODO : 배경음, 효과음 database & 설정
        setCancelable(false);   // 검은 영역 터치시에도 꺼지는 거 방지
        return v;
    }

    @Override
    public void onClick(View v) {

    }
}