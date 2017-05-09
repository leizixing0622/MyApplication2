package com.example.john.myapplication.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.john.myapplication.activity.R;
import com.example.john.myapplication.interfaces.MyCheckBoxListener;
import com.example.john.myapplication.interfaces.MyItemClickListener;
import com.example.john.myapplication.model.CardItem;

import java.util.List;

/**
 * Created by John on 2017/5/2.
 */

public class CarFruitAdapter extends RecyclerView.Adapter<CarFruitAdapter.ViewHolder> {

    private List<CardItem> fruitList;
    private MyItemClickListener myItemClickListener;
    private MyCheckBoxListener myCheckBoxListener;

    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, CompoundButton.OnCheckedChangeListener{
        public ImageView imageView;
        public TextView textView;
        public ImageView reduce_button;
        public ImageView add_button;
        public TextView amount;
        public CheckBox checkBox;
        private MyItemClickListener myItemClickListener;
        private MyCheckBoxListener myCheckBoxListener;

        public ViewHolder(View itemView, MyItemClickListener listener, MyCheckBoxListener listener2) {
            super(itemView);
            imageView = (ImageView)itemView.findViewById(R.id.fruit_image);
            textView = (TextView)itemView.findViewById(R.id.fruit_name);
            reduce_button = (ImageView) itemView.findViewById(R.id.reduce_amount);
            add_button = (ImageView) itemView.findViewById(R.id.add_amount);
            amount = (TextView) itemView.findViewById(R.id.sugar_amount);
            this.myItemClickListener = listener;
            this.myCheckBoxListener = listener2;
            reduce_button.setOnClickListener(this);
            add_button.setOnClickListener(this);
            checkBox = (CheckBox) itemView.findViewById(R.id.fruit_checkbox);
            checkBox.setOnCheckedChangeListener(this);
        }

        @Override
        public void onClick(View v) {
            if(myItemClickListener != null){
                switch (v.getId()){
                    case R.id.reduce_amount:
                        myItemClickListener.reduceAmount(getPosition());
                        break;
                    case R.id.add_amount:
                        myItemClickListener.addAmount(getPosition());
                        break;
                    default:
                        Log.d("default", "1");
                        break;
                }
            }
        }

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if(isChecked){
                myCheckBoxListener.myCheckBoxClick(getPosition());
            }else{
                myCheckBoxListener.myCheckBoxClick2(getPosition());
            }
        }
    }

    public CarFruitAdapter(List<CardItem> mFruitList) {
        this.fruitList = mFruitList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.car_fruit_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view, myItemClickListener, myCheckBoxListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        CardItem cardItem = fruitList.get(position);
        holder.imageView.setImageResource(cardItem.getFruit().getImageId());
        holder.textView.setText(cardItem.getFruit().getName());
        holder.amount.setText(String.valueOf(cardItem.getAmount()));
        if(cardItem.getFruit().getChecked() == 0){
            holder.checkBox.setChecked(false);
        }else{
            holder.checkBox.setChecked(true);
        }
    }

    @Override
    public int getItemCount() {
        return fruitList.size();
    }

    public void setOnItemClickListener(MyItemClickListener onItemClickListener) {
        this.myItemClickListener = onItemClickListener;
    }
    public void setMyCheckBoxListener(MyCheckBoxListener myCheckBoxListener){
        this.myCheckBoxListener = myCheckBoxListener;
    }

}
