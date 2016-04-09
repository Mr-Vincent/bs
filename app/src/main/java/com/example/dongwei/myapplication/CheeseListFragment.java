package com.example.dongwei.myapplication;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;

/**
 * Created by dongwei on 16/4/9.
 */
public class CheeseListFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rv = inflater.inflate(
                R.layout.layout_switch1, container, false);
        setupView(rv);
        return rv;
    }

    private void setupView(View v) {
        Switch sw = (Switch) v.findViewById(R.id.switch1);
        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    Log.d("switch","on");
                }else{
                    Log.d("switch","off");
                }
            }
        });
    }


}
