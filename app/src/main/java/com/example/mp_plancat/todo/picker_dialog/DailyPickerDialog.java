package com.example.mp_plancat.todo.picker_dialog;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.NumberPicker;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.example.mp_plancat.R;

import java.util.Calendar;

public class DailyPickerDialog extends DialogFragment {
    private static final int MAX_YEAR = 2099;
    private static final int MIN_YEAR = 2000;

    private DatePickerDialog.OnDateSetListener listener;
    private Calendar cal = Calendar.getInstance();
    private int year, month, day;
    private Button btn_cancel;
    private Button btn_ok;
    private NumberPicker yearPicker;
    private NumberPicker monthPicker;
    private NumberPicker dayPicker;

    public DailyPickerDialog(int year, int month, int day){
        this.year = year;
        this.month = month;
        this.day = day;
    }

    public void setListener(DatePickerDialog.OnDateSetListener listener){
        this.listener = listener;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setCancelable(false); //dialog 창 이외의 영역 터치해도 창 안 꺼지게 함

        LayoutInflater inflater = getActivity().getLayoutInflater();

        View dialog = inflater.inflate(R.layout.daily_picker, null);

        btn_cancel = dialog.findViewById(R.id.btn_cancel);
        btn_ok = dialog.findViewById(R.id.btn_ok);

        yearPicker = (NumberPicker) dialog.findViewById(R.id.dailyPicker_year);
        monthPicker = (NumberPicker) dialog.findViewById(R.id.dailyPicker_month);
        dayPicker = (NumberPicker) dialog.findViewById(R.id.dailyPicker_day);

        setPickers(); //pickers 값 세팅

        btn_cancel.setOnClickListener(new View.OnClickListener() { //취소 버튼 눌렀을 때
            @Override
            public void onClick(View v) {
                DailyPickerDialog.this.getDialog().cancel(); //dialog 창 닫기
            }
        });
        btn_ok.setOnClickListener(new View.OnClickListener() { //확인 버튼 눌렀을 때
            @Override
            public void onClick(View v) {
                listener.onDateSet(null, yearPicker.getValue(), monthPicker.getValue(), dayPicker.getValue()); //확인 버튼 누를 당시의 year, month, day 값을 넘겨줌

                //창 닫히기 직전의 year, month, day 값 저장
                year = yearPicker.getValue();
                month = monthPicker.getValue();
                cal.set(year, month-1, 1);
                if(dayPicker.getValue() > cal.getActualMaximum(Calendar.DAY_OF_MONTH))
                    day = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
                else
                    day = dayPicker.getValue();

                setPickers();//pickers 값 세팅
                DailyPickerDialog.this.getDialog().cancel(); //dialog 창 닫기
            }
        });

        builder.setView(dialog);

        Dialog dialog1 = builder.create();
        dialog1.setCanceledOnTouchOutside(false); //dialog 창 이외의 영역 터치해도 창 안 꺼지게 함
        dialog1.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND); //dialog 창 이외 부분 투명하게 보이게 함
        return dialog1;
    }

    public void setPickers(){ //pickers 값 세팅
        Calendar c = Calendar.getInstance();
        c.set(year, month-1, 1);
        if(day > c.getActualMaximum(Calendar.DAY_OF_MONTH))
            day = c.getActualMaximum(Calendar.DAY_OF_MONTH);
        dayPicker.setMinValue(1);
        dayPicker.setMaxValue(c.getActualMaximum(Calendar.DAY_OF_MONTH));
        dayPicker.setValue(day);

        monthPicker.setMinValue(1);
        monthPicker.setMaxValue(12);
        monthPicker.setValue(month);

        yearPicker.setMinValue(MIN_YEAR);
        yearPicker.setMaxValue(MAX_YEAR);
        yearPicker.setValue(year);
    }

}
