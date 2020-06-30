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

import java.util.ArrayList;
import java.util.List;

public class ShopAdapter extends RecyclerView.Adapter<ShopAdapter.ThingHolder>{
    private List<Goods> goods = new ArrayList<>();
    private ShopAdapter.OnItemClickListener listener;
    private ShopAdapter.OnButtonClickListener buttonclickListener;

    @NonNull
    @Override
    public ShopAdapter.ThingHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_cat_shop_item, parent, false);
        return new ShopAdapter.ThingHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ShopAdapter.ThingHolder holder, final int position) { //recyclerview 바인딩
        final Goods currentGoods = goods.get(position);

        holder.thing_name.setText(currentGoods.getGoodsName());
        holder.thing_description.setText(currentGoods.getGoodsDescription());
        holder.point.setText(Integer.toString(currentGoods.getPurchasePoint()));
        holder.btn_buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(buttonclickListener != null) {
                    buttonclickListener.onButtonClick(currentGoods);
                }
            }
        });

        switch(currentGoods.getGoodsID()) {
            case 1:
                holder.shop_img.setImageResource(R.drawable.goods_1);
                break;
            case 2:
                holder.shop_img.setImageResource(R.drawable.goods_2);
                break;
            case 3:
                holder.shop_img.setImageResource(R.drawable.goods_3);
                break;
            case 4:
                holder.shop_img.setImageResource(R.drawable.goods_4);
                break;
            case 5:
                holder.shop_img.setImageResource(R.drawable.goods_5);
                break;
            case 6:
                holder.shop_img.setImageResource(R.drawable.goods_6);
                break;
            case 7:
                holder.shop_img.setImageResource(R.drawable.goods_7);
                break;
            default:
                holder.shop_img.setImageResource(R.drawable.goods_8);
                break;
        }

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
        private TextView point;
        private Button btn_buy;
        private ImageView shop_img;

        public ThingHolder(View itemView){
            super(itemView);
            thing_name = itemView.findViewById(R.id.shop_name);
            thing_description = itemView.findViewById(R.id.text1);
            point = itemView.findViewById(R.id.text_gold_coin);
            btn_buy = itemView.findViewById(R.id.btn_buy);
            shop_img = itemView.findViewById(R.id.shop_img);

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
    public void setOnItemClickListener(ShopAdapter.OnItemClickListener listener){
        this.listener = listener;
    }

    public interface OnButtonClickListener {
        void onButtonClick(Goods goods);
    }
    public void setOnButtonClickListener(ShopAdapter.OnButtonClickListener listener){
        this.buttonclickListener = listener;
    }
}
