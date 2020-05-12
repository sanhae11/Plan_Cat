package com.example.mp_plancat;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class CatFragment extends Fragment {
    Button btn_cat_book;
    Button btn_my_things;
    Button btn_shop;
    Button btn_settings;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_cat, container, false);

        ActionBar actionBar = ((MainActivity)getActivity()).getSupportActionBar();
        actionBar.hide();

        btn_cat_book = (Button) rootView.findViewById(R.id.btn_cat_book);
        btn_cat_book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().startActivity(new Intent(getActivity(), CatBookActivity.class));
            }
        });

        btn_my_things = (Button) rootView.findViewById(R.id.btn_my_things);
        btn_my_things.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().startActivity(new Intent(getActivity(), MyThingsActivity.class));
            }
        });

        btn_shop = (Button) rootView.findViewById(R.id.btn_shop);
        btn_shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().startActivity(new Intent(getActivity(), ShopActivity.class));
            }
        });

        btn_shop = (Button) rootView.findViewById(R.id.btn_shop);
        btn_shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //설정 팝업창 띄우기
            }
        });

        return rootView;
    }
}
