package com.example.mp_plancat;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class AssignThingsDialog extends DialogFragment {
    private static final String TAG1 = "Cat Fragment";
    private ImageButton btn_check_white, btn_x_white;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        final View dialog = inflater.inflate(R.layout.popup_assign_things, null);


        btn_check_white = dialog.findViewById(R.id.btn_check_white);
        btn_x_white = dialog.findViewById(R.id.btn_x_white);

        btn_check_white.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Todo 장난감+고양이 이미지 메인화면에 뿌리기
                //Todo 프래그먼트 시작할 때 어떻게 해야 함,,?
                Intent intent = new Intent(getActivity(), MainActivity.class);

                Bundle bundle = new Bundle();
                bundle.putInt("AssignActivity", 1);
                intent.putExtra("AssignActivity", bundle);
                startActivity(intent);

                dismiss();
            }
        });

        btn_x_white.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //X 버튼 클릭 시 액티비티 종료
                dismiss();
            }
        });
        builder.setView(dialog);

        return builder.create();
    }
}