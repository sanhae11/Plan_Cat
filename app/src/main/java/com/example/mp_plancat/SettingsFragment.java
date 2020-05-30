package com.example.mp_plancat;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.kyleduo.switchbutton.SwitchButton;

public class SettingsFragment extends DialogFragment implements View.OnClickListener {

    public static final String TAG_EVENT_DIALOG = "dialog_event";

    public SettingsFragment(){}

    public static SettingsFragment getInstance() {
        SettingsFragment e = new SettingsFragment();
        return e;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.popup_settings, container);

        // 스위치 버튼 상태에 따라 문자열을 출력할 텍스트뷰
        final TextView optionState = (TextView)v.findViewById(R.id.textView);

        // 스위치 버튼
        SwitchButton switchButton = (SwitchButton)v.findViewById(R.id.sb_bgsound);
        switchButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // 스위치 버튼이 체크되었는지 검사하여 텍스트뷰에 각 경우에 맞게 출력
                if(isChecked){
                    optionState.setText("Option ON");
                }
                else{
                    optionState.setText("Option OFF");
                }
            }
        });
        return v;
    }

    @Override
    public void onClick(View v){
        dismiss();
    }
}