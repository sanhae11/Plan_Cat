package com.example.mp_plancat;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;

public class AssignThingsDialog extends DialogFragment {
    private static final String TAG1 = "Cat Fragment";
    private ImageButton btn_check_white, btn_x_white;
    private TextView dialog_content;

    private AssignThingsDialog.OnButtonClickListener buttonclickListener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        final View dialog = inflater.inflate(R.layout.popup_two_button, null);

        dialog_content = dialog.findViewById(R.id.dialog_content);
        if(getTag().equals("Assign Things Dialog")){
            Log.e("test dialog", "assign things dialog 실행");
            dialog_content.setText("장난감을 배치하시겠습니까?");
        }
        else if(getTag().equals("Buy Things Dialog")){
            dialog_content.setText("장난감을 구매하시겠습니까?");
        }
        else if(getTag().equals("Cancel Assignment Dialog")){
            dialog_content.setText("배치를 취소하시겠습니까?");
        }

        btn_check_white = dialog.findViewById(R.id.btn_check_white);
        btn_x_white = dialog.findViewById(R.id.btn_x_white);

        btn_check_white.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Todo 장난감+고양이 이미지 메인화면에 뿌리기
                //Todo 프래그먼트 시작할 때 어떻게 해야 함,,?

                if(buttonclickListener != null) {
                    buttonclickListener.onButtonClick();
                }

/*
                if(getTag().equals("Assign Things Dialog")){
                    Log.e("test dialog", "assign things dialog 실행");
                    dialog_content.setText("장난감을 배치하시겠습니까?");
                    Intent intent = new Intent(getActivity(), MainActivity.class);

                    Bundle bundle = new Bundle();
                    bundle.putInt("AssignActivity", 1);
                    intent.putExtra("AssignActivity", bundle);
                    startActivity(intent);
                    //Todo 배치
                }
                else if(getTag().equals("Buy Things Dialog")){
                    dialog_content.setText("장난감을 구매하시겠습니까?");
                    //Todo 구매
                }
                else if
*/
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

    public interface OnButtonClickListener {
        void onButtonClick();
    }
    public void setOnButtonClickListener(AssignThingsDialog.OnButtonClickListener listener){
        this.buttonclickListener = listener;
    }
}