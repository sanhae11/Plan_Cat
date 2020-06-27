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

        holder.cat_name.setText(String.valueOf(currentCat.getCatName()));
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
