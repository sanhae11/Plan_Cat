package com.example.mp_plancat;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;

import com.example.mp_plancat.database.entity.Cat;

public class CatInfoDialog extends DialogFragment {
    private ImageButton btn_check_white;
    private TextView cat_name, cat_description;
    private ImageView cat_img;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        final View dialog = inflater.inflate(R.layout.popup_cat_info, null);

        Bundle mArgs = getArguments();
        Cat cat = (Cat) mArgs.get("catData");

        cat_name = (TextView) dialog.findViewById(R.id.txt_cat_name);
        cat_description = (TextView) dialog.findViewById(R.id.txt_cat_description);
        cat_img = (ImageView) dialog.findViewById(R.id.cat_img) ;

        cat_name.setText(cat.getCatName());
        cat_description.setText(cat.getCatDescription());
        switch (cat.getCatID()){
            case 1:
                cat_img.setImageResource(R.drawable.cat_1);
                break;
            case 2:
                cat_img.setImageResource(R.drawable.cat_2);
                break;
            case 3:
                cat_img.setImageResource(R.drawable.cat_3);
                break;
            case 4:
                cat_img.setImageResource(R.drawable.cat_4);
                break;
            case 5:
                cat_img.setImageResource(R.drawable.cat_5);
                break;
            case 6:
                cat_img.setImageResource(R.drawable.cat_6);
                break;
            case 7:
                cat_img.setImageResource(R.drawable.cat_7);
                break;
            default:
                cat_img.setImageResource(R.drawable.cat_8);
                break;
        }

        btn_check_white = dialog.findViewById(R.id.btn_check_white);

        btn_check_white.setOnClickListener(new View.OnClickListener() {
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

