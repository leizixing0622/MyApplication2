package com.example.john.myapplication.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.john.myapplication.activity.R;
import com.example.john.myapplication.interfaces.MyItemClickListener;
import com.example.john.myapplication.model.Fruit;

import java.util.List;

/**
 * Created by John on 2017/5/2.
 */

public class FruitAdapter extends RecyclerView.Adapter<FruitAdapter.ViewHolder> {

    private List<Fruit> fruitList;
    private MyItemClickListener myItemClickListener;

    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public ImageView imageView;
        public TextView textView;
        private MyItemClickListener myItemClickListener;

        public ViewHolder(View itemView, MyItemClickListener listener) {
            super(itemView);
            imageView = (ImageView)itemView.findViewById(R.id.fruit_image);
            textView = (TextView)itemView.findViewById(R.id.fruit_name);
            this.myItemClickListener = listener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(myItemClickListener != null){
                myItemClickListener.myOnItemClick(getPosition());
            }
        }
    }

    public FruitAdapter(List<Fruit> fruits) {
        fruitList = fruits;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fruit_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view, myItemClickListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Fruit fruit = fruitList.get(position);
        holder.imageView.setImageResource(fruit.getImageId());
        holder.textView.setText(fruit.getName());
    }

    @Override
    public int getItemCount() {
        return fruitList.size();
    }

    public void setOnItemClickListener(MyItemClickListener onItemClickListener){
        this.myItemClickListener = onItemClickListener;
    }
}
