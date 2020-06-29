package com.example.mp_plancat;

import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;

import androidx.fragment.app.DialogFragment;

public class NotCollectedCatDialog extends DialogFragment {
    private ImageButton btn_check_white;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        final View dialog = inflater.inflate(R.layout.popup_not_collected_cat, null);


        btn_check_white = dialog.findViewById(R.id.btn_check_white);

        btn_check_white.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //X 버튼 클릭 시 액티비티 종료
                dismiss();
            }
        });
        builder.setView(dialog);

        Dialog dialog1 = builder.create();

        return builder.create();
    }
}
