package com.example.dongwei.myapplication;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import java.io.IOException;

/**
 * Created by dongwei on 16/4/9.
 */
public class ControllerFragment extends Fragment {
    final SocketUtils utils = new SocketUtils();
    private static final String TAG = "ControllerFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rv = inflater.inflate(
                R.layout.layout_switch1, container, false);
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
                if(state){
                    Log.d(TAG,"close ok");
                }else {
                    Log.d(TAG,"close fail");
                }
            }
        });
    }

    private void setupView(View v) {
        final Switch sw = (Switch) v.findViewById(R.id.switch1);
        /*传统方式*/
        /*new Thread() {
            @Override
            public void run() {
                isEnable = openConnection();
                Log.d(TAG, "isEnable:" + isEnable);
                if (!isEnable) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getContext(), "服务器未响应^_^", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        }.start();
        Log.d(TAG,"是否可用："+isEnable);
        sw.setEnabled(isEnable);*/

        /*回调方式*/
        String ip = "192.168.1.101";
        int port = 9000;

        utils.connect2Server(ip, port, new SocketUtils.OnConnectedListener() {
            @Override
            public void onConnected(final boolean state) {
                Log.d(TAG, "socket是否可用：" + state);
                if (!state) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            sw.setEnabled(state);
                            Toast.makeText(getContext(), "服务器未响应", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });
        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, final boolean isChecked) {
                if (isChecked) {
                    Log.d("switch", "on");
                    sendMsg("on");
                } else {
                    Log.d("switch", "off");
                    sendMsg("off");
                }
            }
        });
    }

    private void sendMsg(String msg){
        utils.sendMsg(msg, new SocketUtils.OnSentListener() {
            @Override
            public void onSent(boolean state) {
                if(state){
                    Log.d(TAG,"sent ok");
                }else {
                    Log.d(TAG,"sent fail");
                }
            }
        });
    }


}
