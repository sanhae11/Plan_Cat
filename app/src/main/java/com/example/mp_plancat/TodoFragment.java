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
        actionBar.show(); //액션바 보여줌
        actionBar.setTitle("Todo");


        btn_create_todo = (Button) rootView.findViewById(R.id.btn_create_todo);
        btn_create_todo.setOnClickListener(new View.OnClickListener() { //할 일 생성 버튼 클릭 시
            @Override
            public void onClick(View v) {
                getActivity().startActivity(new Intent(getActivity(), CreateTodoActivity.class)); //create to do activity로 감
            }
        });
        return rootView;
    }
}
