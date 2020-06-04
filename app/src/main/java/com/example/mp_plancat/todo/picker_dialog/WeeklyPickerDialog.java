package com.example.mp_plancat.todo.picker_dialog;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.NumberPicker;

import androidx.fragment.app.DialogFragment;

import com.example.mp_plancat.R;

import java.util.Calendar;

public class WeeklyPickerDialog extends DialogFragment {
    private static final int MAX_YEAR = 2099;
    private static final int MIN_YEAR = 2000;

    private DatePickerDialog.OnDateSetListener listener;
    private Calendar cal = Calendar.getInstance();
    private int year, month, week;
    private Button btn_cancel;
    private Button btn_ok;
    private NumberPicker yearPicker;
    private NumberPicker monthPicker;
    private NumberPicker weekPicker;

    public WeeklyPickerDialog(int year, int month, int week){
        this.year = year;
        this.month = month;
        this.week = week;
    }

    public void setListener(DatePickerDialog.OnDateSetListener listener){
        this.listener = listener;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setCancelable(false); //dialog 창 이외의 영역 터치해도 창 안 꺼지게 함

        LayoutInflater inflater = getActivity().getLayoutInflater();

        View dialog = inflater.inflate(R.layout.weekly_picker, null);

        btn_cancel = dialog.findViewById(R.id.btn_cancel);
        btn_ok = dialog.findViewById(R.id.btn_ok);

        yearPicker = (NumberPicker) dialog.findViewById(R.id.weeklyPicker_year);
        monthPicker = (NumberPicker) dialog.findViewById(R.id.weeklyPicker_month);
        weekPicker = (NumberPicker) dialog.findViewById(R.id.weeklyPicker_week);

        setPickers(); //pickers 값 세팅

        yearPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() { //값이 변경될 때마다 year 값 받아옴
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                yearPicker.setValue(newVal); //dialog 창 열렸을 때 yearPicker의 처음 보여지는 값 다시 설정
            }
        });
        monthPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {//값이 변경될 때마다 month 값 받아옴
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                monthPicker.setValue(newVal); //dialog 창 열렸을 때 monthPicker의 처음 보여지는 값 다시 설정
            }
        });
        weekPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() { //값이 변경될 때마다 week 값 받아옴
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                weekPicker.setValue(newVal); //dialog 창 열렸을 때 weekPicker의 처음 보여지는 값 다시 설정
            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() { //취소 버튼 눌렀을 때
            @Override
            public void onClick(View v) {
                WeeklyPickerDialog.this.getDialog().cancel(); //dialog 창 닫기
            }
        });
        btn_ok.setOnClickListener(new View.OnClickListener() { //확인 버튼 눌렀을 때
            @Override
            public void onClick(View v) {
                listener.onDateSet(null, yearPicker.getValue(), monthPicker.getValue(), weekPicker.getValue()); //확인 버튼 누를 당시의 year, month, week 값을 넘겨줌

                //창 닫히기 직전의 year, month, week 값 저장
                year = yearPicker.getValue();
                month = monthPicker.getValue();
                week = weekPicker.getValue();
                WeeklyPickerDialog.this.getDialog().cancel(); //dialog 창 닫기
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
        c.set(year, month, c.getActualMaximum(Calendar.DATE));

        weekPicker.setMinValue(1);
        weekPicker.setMaxValue(c.get(Calendar.WEEK_OF_MONTH));
        weekPicker.setValue(week);

        monthPicker.setMinValue(1);
        monthPicker.setMaxValue(12);
        monthPicker.setValue(month);

        yearPicker.setMinValue(MIN_YEAR);
        yearPicker.setMaxValue(MAX_YEAR);
        yearPicker.setValue(year);
    }

}
