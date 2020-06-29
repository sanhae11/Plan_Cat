package com.example.mp_plancat;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mp_plancat.database.entity.Goods;

import java.util.ArrayList;
import java.util.List;

public class MyThingsAdapter extends RecyclerView.Adapter<MyThingsAdapter.ThingHolder>{
    private List<Goods> goods = new ArrayList<>();
    private MyThingsAdapter.OnItemClickListener listener;
    private MyThingsAdapter.OnButtonClickListener buttonclickListener;

    @NonNull
    @Override
    public MyThingsAdapter.ThingHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_cat_mythings_item, parent, false);
        return new MyThingsAdapter.ThingHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyThingsAdapter.ThingHolder holder, final int position) { //recyclerview 바인딩
        final Goods currentGoods = goods.get(position);

        holder.thing_name.setText(currentGoods.getGoodsName());
        holder.thing_description.setText(currentGoods.getGoodsDescription());
        holder.btn_assign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(buttonclickListener != null) {
                    buttonclickListener.onButtonClick(currentGoods);
                }
            }
        });
        //holder.cat_img.setImageBitmap(currentCat.getCatImgBitmap());
    }

    @Override
    public int getItemCount() {
        return goods.size();
    }

    public void setGoods(List<Goods> goods){
        this.goods = goods;
        notifyDataSetChanged();
    }

    class ThingHolder extends RecyclerView.ViewHolder {
        private TextView thing_name;
        private TextView thing_description;
        private Button btn_assign;

        public ThingHolder(View itemView){
            super(itemView);
            thing_name = itemView.findViewById(R.id.shop_name);
            thing_description = itemView.findViewById(R.id.text1);
            btn_assign = itemView.findViewById(R.id.btn_assign);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if(listener != null && position != RecyclerView.NO_POSITION){
                        listener.onItemClick(goods.get(position));
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Goods goods);
    }
    public void setOnItemClickListener(MyThingsAdapter.OnItemClickListener listener){
        this.listener = listener;
    }

    public interface OnButtonClickListener {
        void onButtonClick(Goods goods);
    }
    public void setOnButtonClickListener(MyThingsAdapter.OnButtonClickListener listener){
        this.buttonclickListener = listener;
    }
}
