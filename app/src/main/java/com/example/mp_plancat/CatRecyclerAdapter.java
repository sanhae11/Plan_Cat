package com.example.mp_plancat;

import android.graphics.Color;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mp_plancat.database.entity.Cat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

public class CatRecyclerAdapter extends RecyclerView.Adapter<CatRecyclerAdapter.CatHolder> {
    private List<Cat> cats = new ArrayList<>();
    private CatRecyclerAdapter.OnItemClickListener listener;

    @NonNull
    @Override
    public CatRecyclerAdapter.CatHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_cat_item, parent, false);
        return new CatRecyclerAdapter.CatHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CatRecyclerAdapter.CatHolder holder, final int position) { //recyclerview 바인딩
        final Cat currentCat = cats.get(position);
        String name, description;

        holder.cat_name.setText(String.valueOf(currentCat.getCatName()));

        if(currentCat.isCollected == 1){
            switch(currentCat.getCatID()) {
                case 1:
                    holder.cat_img.setImageResource(R.drawable.cat_1);
                    name = "멈이";
                    description = "멈멈이? 개냥이인가?";
                    break;
                case 2:
                    holder.cat_img.setImageResource(R.drawable.cat_2);
                    name = "짜장";
                    description = "점심으로 짜장을 먹고 온 듯 하다";
                    break;
                case 3:
                    holder.cat_img.setImageResource(R.drawable.cat_3);
                    name = "토미";
                    description = "귀여운 회색 고양이이다";
                    break;
                case 4:
                    holder.cat_img.setImageResource(R.drawable.cat_4);
                    name = "치즈";
                    description = "치즈를 좋아한다";
                    break;
                case 5:
                    holder.cat_img.setImageResource(R.drawable.cat_5);
                    name = "탄이";
                    description = "탄이 역시 짜장이와 점심을 먹은 듯 하다";
                    break;
                case 6:
                    holder.cat_img.setImageResource(R.drawable.cat_6);
                    name = "귤이";
                    description = "귤을 좋아하는 특이한 고양이이";
                    break;
                case 7:
                    holder.cat_img.setImageResource(R.drawable.cat_7);
                    name = "삼색";
                    description = "삼색고양이이다";
                    break;
                default:
                    holder.cat_img.setImageResource(R.drawable.cat_8);
                    name = "껌냥";
                    description = "껌껌하다";
                    break;
            }
            holder.cat_name.setText(name);
        }


        //holder.cat_img.setImageBitmap(currentCat.getCatImgBitmap());
    }

    @Override
    public int getItemCount() {
        return cats.size();
    }

    public void setCats(List<Cat> cats){
        this.cats = cats;
        notifyDataSetChanged();
    }

    class CatHolder extends RecyclerView.ViewHolder {
        private TextView cat_name;
        private ImageView cat_img;

        public CatHolder(View itemView){
            super(itemView);
            cat_name = itemView.findViewById(R.id.cat_name);
            cat_img = itemView.findViewById(R.id.cat_img);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if(listener != null && position != RecyclerView.NO_POSITION){
                        listener.onItemClick(cats.get(position));
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Cat cat);
    }
    public void setOnItemClickListener(CatRecyclerAdapter.OnItemClickListener listener){
        this.listener = listener;
    }
}
