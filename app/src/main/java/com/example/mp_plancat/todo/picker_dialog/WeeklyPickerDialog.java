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

    //////////////////////////////////////////////////////////////

    private String[] four_weeks = new String[]{"1주", "2주", "3주", "4주"};
    private String[] five_weeks = new String[]{"1주", "2주", "3주", "4주", "5주"};
    private String[] six_weeks = new String[]{"1주", "2주", "3주", "4주", "5주", "6주"};

    private int chosenIndex = 0;

    //////////////////////////////////////////////////////////////

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

                cal.set(year, month-1, 1);

                int max_day = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

                cal.set(year, month-1, max_day);

                int max_week = cal.getActualMaximum(Calendar.WEEK_OF_MONTH);

                if(weekPicker.getValue() >= max_week)
                    week = max_week;
                else
                    week = weekPicker.getValue()+1;

                setPickers(); //pickers 값 세팅
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
        c.set(year, month-1,1);

        int max_day = c.getActualMaximum(Calendar.DAY_OF_MONTH);

        c.set(year, month-1, max_day);

        int max_week = c.getActualMaximum(Calendar.WEEK_OF_MONTH);

        if(week > max_week)
            week = max_week;

        weekPicker.setMinValue(1);
        weekPicker.setMaxValue(c.get(Calendar.WEEK_OF_MONTH));

        ////////////////////////////////////////////////////////////

        chosenIndex = week - 1;

        weekPicker.setValue(chosenIndex);
        if(max_week == 4)
            weekPicker.setDisplayedValues(four_weeks);
        else if(max_week == 5)
            weekPicker.setDisplayedValues(five_weeks);
        if(max_week == 6)
            weekPicker.setDisplayedValues(six_weeks);

        ///////////////////////////////////////////////////////////

        monthPicker.setMinValue(1);
        monthPicker.setMaxValue(12);
        monthPicker.setValue(month);

        yearPicker.setMinValue(MIN_YEAR);
        yearPicker.setMaxValue(MAX_YEAR);
        yearPicker.setValue(year);
    }

}
