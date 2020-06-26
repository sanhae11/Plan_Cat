package com.example.mp_plancat;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mp_plancat.database.entity.GameInfo;
import com.example.mp_plancat.database.entity.Message;
import com.example.mp_plancat.database.entity.Todo;
import com.example.mp_plancat.todo.TodoViewModel;
import com.example.mp_plancat.todo.home.DateCategoryTodoRecyclerAdapter;

import java.util.Calendar;
import java.util.List;

public class NoRewardDialog extends DialogFragment {
    private ImageButton btn_confirm;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        final View dialog = inflater.inflate(R.layout.popup_message_list, null);

        btn_confirm = dialog.findViewById(R.id.btn_confirm);

        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        builder.setView(dialog);

        return builder.create();
    }


}
