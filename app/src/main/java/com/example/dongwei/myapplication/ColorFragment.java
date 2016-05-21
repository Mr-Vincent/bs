package com.example.dongwei.myapplication;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

/**
 * Created by Administrator on 2016/4/11.
 *
 * @author:Administrator
 * @date:2016/4/11
 */
public class ColorFragment extends Fragment {
    public static final  String TAG = "ColorFragment";
    private ColorPicker picker;
    SocketUtils utils;

    public static Fragment getInstance(SocketUtils obj){
        ColorFragment cf = new ColorFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable( "arg", obj);
        cf.setArguments(bundle);
        return cf;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rv = inflater.inflate(
                R.layout.layout_switch2, container, false);
        setupView(rv);
        return rv;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(TAG, "destroy view");
        utils.closeConnection(new SocketUtils.OnCloseListener() {
            @Override
            public void onClose(boolean state) {
                if (state) {
                    Log.d(TAG, "close ok");
                } else {
                    Log.d(TAG, "close fail");
                }
            }
        });
    }
    public void setupView(View upView) {

        picker = (ColorPicker) upView.findViewById(R.id.color_picker_view);
        utils = (SocketUtils) getArguments().getSerializable("arg");
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

        Integer rr = Integer.parseInt(r, 16);
        Integer gg = Integer.parseInt(g, 16);
        Integer bb = Integer.parseInt(b, 16);

        String rgb = " r"+rr+",g"+gg+",b"+bb;//发送格式
        sendMsg(rgb);
        Log.d(TAG, rgb);

    }

    private void sendMsg(String msg) {
        utils.sendMsg(msg, new SocketUtils.OnSentListener() {
            @Override
            public void onSent(final boolean state) {
                if (state) {
                    Log.d(TAG, "sent ok");
                } else {
                    Log.d(TAG, "sent fail");
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getContext(), "服务器已断开", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });
    }

}
