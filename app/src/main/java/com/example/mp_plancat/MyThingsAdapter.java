package com.example.mp_plancat;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mp_plancat.database.entity.Goods;
import com.example.mp_plancat.database.entity.Todo;

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

        if(currentGoods.getIsAssigned() == 1){
            holder.btn_assign.setText("배치 취소");
        }
        else{
            holder.btn_assign.setText("배치하기");
        }


        holder.btn_assign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(buttonclickListener != null) {
                    buttonclickListener.onButtonClick(currentGoods);
                }
            }
        });

        switch(currentGoods.getGoodsID()) {
            case 1:
                holder.thing_img.setImageResource(R.drawable.goods_1);
                break;
            case 2:
                holder.thing_img.setImageResource(R.drawable.goods_2);
                break;
            case 3:
                holder.thing_img.setImageResource(R.drawable.goods_3);
                break;
            case 4:
                holder.thing_img.setImageResource(R.drawable.goods_4);
                break;
            case 5:
                holder.thing_img.setImageResource(R.drawable.goods_5);
                break;
            case 6:
                holder.thing_img.setImageResource(R.drawable.goods_6);
                break;
            case 7:
                holder.thing_img.setImageResource(R.drawable.goods_7);
                break;
            default:
                holder.thing_img.setImageResource(R.drawable.goods_8);
                break;
        }
        //holder.cat_img.setImageBitmap(currentCat.getCatImgBitmap());
    }

    @Override
    public int getItemCount() {
        return goods.size();
    }

    public void setGoods(List<Goods> goods){
        List<Goods> newGoods = new ArrayList<>();

        for(int i = 0; i < goods.size(); i++){
            if(goods.get(i).isPurchased == 1){
                newGoods.add(goods.get(i));
            }
        }

        this.goods = newGoods;
        notifyDataSetChanged();
    }

    class ThingHolder extends RecyclerView.ViewHolder {
        private TextView thing_name;
        private TextView thing_description;
        private Button btn_assign;
        private ImageView thing_img;

        public ThingHolder(View itemView){
            super(itemView);
            thing_name = itemView.findViewById(R.id.shop_name);
            thing_description = itemView.findViewById(R.id.text1);
            btn_assign = itemView.findViewById(R.id.btn_assign);
            thing_img = itemView.findViewById(R.id.shop_img);

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
