package com.example.mp_plancat;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class TodoFragment extends Fragment {
    Button btn_create_todo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_todo, container, false);

        ActionBar actionBar = ((MainActivity)getActivity()).getSupportActionBar();
        actionBar.show();
        actionBar.setTitle("Todo");


        btn_create_todo = (Button) rootView.findViewById(R.id.btn_create_todo);
        btn_create_todo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().startActivity(new Intent(getActivity(), CreateTodoActivity.class));
            }
        });
        return rootView;
    }
}
