package com.example.mp_plancat.todo;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;

import androidx.fragment.app.DialogFragment;

import com.example.mp_plancat.R;

import java.util.Calendar;

public class YearlyPickerDialog extends DialogFragment {
    private static final int MAX_YEAR = 2099;
    private static final int MIN_YEAR = 2000;

    private DatePickerDialog.OnDateSetListener listener;
    private Calendar cal = Calendar.getInstance();
    private int year;
    private Button btn_cancel;
    private Button btn_ok;
    private NumberPicker yearPicker;

    public YearlyPickerDialog(int year){
        this.year = year;
    }

    public void setListener(DatePickerDialog.OnDateSetListener listener){
        this.listener = listener;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setCancelable(false); //dialog 창 이외의 영역 터치해도 창 안 꺼지게 함

        LayoutInflater inflater = getActivity().getLayoutInflater();

        View dialog = inflater.inflate(R.layout.yearly_picker, null);

        btn_cancel = dialog.findViewById(R.id.btn_cancel);
        btn_ok = dialog.findViewById(R.id.btn_ok);

        yearPicker = (NumberPicker) dialog.findViewById(R.id.yearlyPicker_year);

        setPickers(); //pickers 값 세팅

        yearPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() { //값이 변경될 때마다 year 값 받아옴
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                yearPicker.setValue(newVal); //dialog 창 열렸을 때 yearPicker의 처음 보여지는 값 다시 설정
            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() { //취소 버튼 눌렀을 때
            @Override
            public void onClick(View v) {
                YearlyPickerDialog.this.getDialog().cancel(); //dialog 창 닫기
            }
        });
        btn_ok.setOnClickListener(new View.OnClickListener() { //확인 버튼 눌렀을 때
            @Override
            public void onClick(View v) {
                listener.onDateSet(null, yearPicker.getValue(), 1, 1); //확인 버튼 누를 당시의 year 값을 넘겨줌

                //창 닫히기 직전의 year 값 저장
                year = yearPicker.getValue();
                YearlyPickerDialog.this.getDialog().cancel(); //dialog 창 닫기
            }
        });

        builder.setView(dialog);

        Dialog dialog1 = builder.create();
        dialog1.setCanceledOnTouchOutside(false); //dialog 창 이외의 영역 터치해도 창 안 꺼지게 함
        return dialog1;
    }

    public void setPickers(){ //pickers 값 세팅

        yearPicker.setMinValue(MIN_YEAR);
        yearPicker.setMaxValue(MAX_YEAR);
        yearPicker.setValue(year);
    }

}
