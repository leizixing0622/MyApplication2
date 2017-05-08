package com.example.john.myapplication.interfaces;

import android.widget.CompoundButton;

/**
 * Created by John on 2017/5/8.
 */

public interface MyCheckBoxListener extends CompoundButton.OnCheckedChangeListener {
    public void myCheckBoxClick(int position);
    public void myCheckBoxClick2(int position);
}
