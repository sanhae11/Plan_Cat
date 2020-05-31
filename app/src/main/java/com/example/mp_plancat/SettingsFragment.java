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

    SwitchButton sb_bgsound, sb_soundeff;

    public SettingsFragment(){}

    public static SettingsFragment getInstance() {
        SettingsFragment e = new SettingsFragment();
        return e;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.popup_settings, container);

        // background sound switch
        sb_bgsound = (SwitchButton)v.findViewById(R.id.sb_bgsound);
        final TextView sb1 = (TextView)v.findViewById(R.id.sb1);
        sb_bgsound.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    sb1.setText("Option ON"); // example
                }
                else{
                    sb1.setText("Option OFF");
                }
            }
        });

        // sound effect switch
        sb_soundeff = (SwitchButton)v.findViewById(R.id.sb_soundeff);
        final TextView sb2 = (TextView)v.findViewById(R.id.sb2);
        sb_soundeff.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    sb2.setText("Option ON"); // example
                }
                else{
                    sb2.setText("Option OFF");
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