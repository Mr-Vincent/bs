package com.example.dongwei.myapplication;

import android.content.Context;
import android.content.SharedPreferences;
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
    SocketUtils utils;
    private static final String TAG = "ControllerFragment";
    Switch sw;

    public ControllerFragment() {
    }
    public static Fragment getInstance(SocketUtils obj){
        ControllerFragment cf = new ControllerFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable( "arg", obj);
        cf.setArguments(bundle);
        return cf;
    }


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
                if (state) {
                    Log.d(TAG, "close ok");
                } else {
                    Log.d(TAG, "close fail");
                }
            }
        });
    }

    private void setupView(View v) {
        sw = (Switch) v.findViewById(R.id.switch1);
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
        SharedPreferences sp = getActivity().getSharedPreferences("ip_address", Context.MODE_PRIVATE);
        String ip = sp.getString("ip_address", Constants.IP_ADDRESS);
        int port = Constants.PORT;
        utils = (SocketUtils) getArguments().getSerializable("arg");
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
                    sendMsg("ledon");
                } else {
                    Log.d("switch", "off");
                    sendMsg("ledoff");
                }
            }
        });
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
                            sw.setEnabled(state);
                            Toast.makeText(getContext(), "服务器已断开", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });
    }


}
