package com.example.dongwei.myapplication;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Administrator on 2016/4/11.
 *
 * @author:Administrator
 * @date:2016/4/11
 */
public class ColorFragment extends Fragment {
    public static final  String TAG = "ColorFragment";
    private ColorPicker picker;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rv = inflater.inflate(
                R.layout.layout_switch2, container, false);
        setupView(rv);
        return rv;
    }

    public void setupView(View upView) {
        picker = (ColorPicker) upView.findViewById(R.id.color_picker_view);
        picker.setOnColorChangedListener(new ColorPicker.OnColorChangedListener() {
            @Override
            public void onColorChange(int color) {
                String strColor = Integer.toHexString(color);
                Log.d(TAG, "hex color:" + strColor);

                toRGB(strColor);
            }
        });
    }

    private void toRGB(String strColor){
        String r = strColor.substring(2,4);
        String g = strColor.substring(4,6);
        String b = strColor.substring(6,8);

        Integer rr = Integer.parseInt(r,16);
        Integer gg = Integer.parseInt(g, 16);
        Integer bb = Integer.parseInt(b, 16);

        Log.d(TAG,""+rr+"-"+gg+"-"+bb);

    }

}
