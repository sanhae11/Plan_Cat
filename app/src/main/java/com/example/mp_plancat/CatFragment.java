package com.example.mp_plancat;
//고양이 화면

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class CatFragment extends Fragment{
    Button btn_cat_book;
    Button btn_my_things;
    Button btn_shop;
    Button btn_settings;

    ImageView btn_msg;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_cat, container, false);

        ActionBar actionBar = ((MainActivity)getActivity()).getSupportActionBar();
        actionBar.hide(); //액션바 숨김

        //TODO:이미지 움직이게 하기
        //TODO:추후 데이터베이스 작업한 후에, 포인트 보상 팝업창 구현

        // 포인트 보상 알림창 + 팝업창 구현
        btn_msg = (ImageView)rootView.findViewById(R.id.ic_msg);
        btn_msg.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                MessageFragment e = MessageFragment.getInstance();
                e.show(getActivity().getSupportFragmentManager(), MessageFragment.TAG_EVENT_DIALOG);
            }
        });



        btn_cat_book = (Button) rootView.findViewById(R.id.btn_cat_book);
        btn_cat_book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().startActivity(new Intent(getActivity(), CatBookActivity.class)); // 클릭 시 cat book activity 화면으로 감
            }
        });

        btn_my_things = (Button) rootView.findViewById(R.id.btn_my_things);
        btn_my_things.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().startActivity(new Intent(getActivity(), MyThingsActivity.class)); // 클릭 시 my things activity 화면으로 감
            }
        });

        btn_shop = (Button) rootView.findViewById(R.id.btn_shop);
        btn_shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().startActivity(new Intent(getActivity(), ShopActivity.class)); // 클릭 시 shop activity 화면으로 감
            }
        });

        btn_shop = (Button) rootView.findViewById(R.id.btn_settings);
        btn_shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO:설정 팝업창 띄우기
            }
        });

        return rootView;
    }
}