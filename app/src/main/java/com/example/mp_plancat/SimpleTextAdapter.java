package com.example.mp_plancat;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mp_plancat.database.entity.Cat;
import com.example.mp_plancat.database.entity.Goods;

import java.util.ArrayList;
// shop adapter 임 이름 수정해야함
public class SimpleTextAdapter extends RecyclerView.Adapter<SimpleTextAdapter.ViewHolder> {
    private SimpleTextAdapter.OnButtonClickListener buttonclickListener;
    private SimpleTextAdapter.OnItemClickListener listener;

    private ArrayList<String> mData = null ;

    // 아이템 뷰를 저장하는 뷰홀더 클래스.
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView1 ;
        //Todo 배치버튼 선언
        Button btn_buy;

        ViewHolder(View itemView) {
            super(itemView) ;

            // 뷰 객체에 대한 참조. (hold strong reference)
            textView1 = itemView.findViewById(R.id.text1) ;
            //Todo 배치 버튼 R.id.이름 으로 할당
            btn_buy = (Button)itemView.findViewById(R.id.btn_buy);
        }
    }

    // 생성자에서 데이터 리스트 객체를 전달받음.
    SimpleTextAdapter(ArrayList<String> list) {
        mData = list ;
    }

    // onCreateViewHolder() - 아이템 뷰를 위한 뷰홀더 객체 생성하여 리턴.
    @Override
    public SimpleTextAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext() ;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;
        LayoutInflater inflater2 = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;

        View view = inflater.inflate(R.layout.recyclerview_cat_shop_item, parent, false) ;

        SimpleTextAdapter.ViewHolder vh = new SimpleTextAdapter.ViewHolder(view) ;

        return vh;
    }

    // onBindViewHolder() - position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시.
    @Override
    public void onBindViewHolder(SimpleTextAdapter.ViewHolder holder, int position) {
        String text = mData.get(position) ;
        holder.textView1.setText(text) ;

        //Todo button.setOnClickListener 코드 생성. 이 코드 안에 온버튼클릭리스너가 들어가면 될듯
    }
        public void setOnItemClickListener(SimpleTextAdapter.OnItemClickListener listener){
            this.listener = listener;
        }
        public interface OnButtonClickListener {
        void onButtonClick(Goods goods);
    }
    public void setOnButtonClickListener(SimpleTextAdapter.OnButtonClickListener listener){
        this.buttonclickListener = buttonclickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(Cat cat);
    }



    // getItemCount() - 전체 데이터 갯수 리턴.
    @Override
    public int getItemCount() {
        return mData.size() ;
    }
}
