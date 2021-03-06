package com.example.mp_plancat.todo;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.example.mp_plancat.R;

public class DeleteTodoDialog extends DialogFragment {
    private Button btn_cancel;
    private Button btn_ok;
    private DeleteTodoDialogListener deleteTodoDialogListener;

    public interface DeleteTodoDialogListener{
        void onPositiveClicked();
        void onNegativeClicked();
    }

    //호출할 리스너 초기화
    public void setDialogListener(DeleteTodoDialogListener deleteTodoDialogListener){
        this.deleteTodoDialogListener = deleteTodoDialogListener;
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        final View dialog = inflater.inflate(R.layout.delete_todo_alert, null);

        btn_ok = dialog.findViewById(R.id.btn_ok);
        btn_ok.setOnClickListener(new View.OnClickListener() { //확인 버튼 눌렀을 때
            @Override
            public void onClick(View v) {
                deleteTodoDialogListener.onPositiveClicked();
                DeleteTodoDialog.this.getDialog().cancel(); //dialog 창 닫기
            }
        });
        btn_cancel = dialog.findViewById(R.id.btn_cancel);
        btn_cancel.setOnClickListener(new View.OnClickListener() { //취소 버튼 눌렀을 때
            @Override
            public void onClick(View v) {
                deleteTodoDialogListener.onNegativeClicked();
                DeleteTodoDialog.this.getDialog().cancel(); //dialog 창 닫기
            }
        });

        builder.setView(dialog);

        return builder.create();
    }
}
