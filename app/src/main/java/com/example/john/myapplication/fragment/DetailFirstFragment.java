package com.example.john.myapplication.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.john.myapplication.activity.R;

/**
 * Created by John on 2017/5/3.
 */

public class DetailFirstFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.detail_first_fragment, container, false);
        ImageView imageView = (ImageView) view.findViewById(R.id.detail_introduce);
        imageView.setImageResource(R.mipmap.candy_detail);
        return view;
    }

}
