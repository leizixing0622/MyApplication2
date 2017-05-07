package com.example.john.myapplication.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.john.myapplication.activity.R;
import com.example.john.myapplication.interfaces.MyItemClickListener;
import com.example.john.myapplication.model.CardItem;
import com.example.john.myapplication.model.Fruit;

import java.util.List;

/**
 * Created by John on 2017/5/2.
 */

public class CarFruitAdapter extends RecyclerView.Adapter<CarFruitAdapter.ViewHolder> {

    private List<CardItem> fruitList;
    private MyItemClickListener myItemClickListener;

    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public ImageView imageView;
        public TextView textView;
        public ImageView reduce_button;
        public ImageView add_button;
        public TextView amount;
        private MyItemClickListener myItemClickListener;

        public ViewHolder(View itemView, MyItemClickListener listener) {
            super(itemView);
            imageView = (ImageView)itemView.findViewById(R.id.fruit_image);
            textView = (TextView)itemView.findViewById(R.id.fruit_name);
            reduce_button = (ImageView) itemView.findViewById(R.id.reduce_amount);
            add_button = (ImageView) itemView.findViewById(R.id.add_amount);
            amount = (TextView) itemView.findViewById(R.id.sugar_amount);
            this.myItemClickListener = listener;
            reduce_button.setOnClickListener(this);
            add_button.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(myItemClickListener != null){
                switch (v.getId()){
                    case R.id.reduce_amount:
                        Log.d("reduce", "1");
                        break;
                    case R.id.add_amount:
                        Log.d("add", "1");
                        break;
                    default:
                        Log.d("default", "1");
                        break;
                }
                myItemClickListener.myOnItemClick(getPosition());
            }
        }
    }

    public CarFruitAdapter(List<CardItem> mFruitList) {
        this.fruitList = mFruitList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.car_fruit_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view, myItemClickListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        CardItem cardItem = fruitList.get(position);
        holder.imageView.setImageResource(cardItem.getFruit().getImageId());
        holder.textView.setText(cardItem.getFruit().getName());
        holder.amount.setText(String.valueOf(cardItem.getAmount()));
    }

    @Override
    public int getItemCount() {
        return fruitList.size();
    }

    public void setOnItemClickListener(MyItemClickListener onItemClickListener){
        this.myItemClickListener = onItemClickListener;
    }
}
