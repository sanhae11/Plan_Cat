package com.example.mp_plancat.todo;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import androidx.fragment.app.DialogFragment;

import com.example.mp_plancat.R;

public class NoTitleAlertDialog extends DialogFragment {
    private Button btn_ok;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        View dialog = inflater.inflate(R.layout.no_title_alert, null);

        btn_ok = dialog.findViewById(R.id.btn_ok);
        btn_ok.setOnClickListener(new View.OnClickListener() { //확인 버튼 눌렀을 때
            @Override
            public void onClick(View v) {
                NoTitleAlertDialog.this.getDialog().cancel(); //dialog 창 닫기
            }
        });

        builder.setView(dialog);

        return builder.create();
    }
}
