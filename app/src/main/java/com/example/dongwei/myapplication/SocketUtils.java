package com.example.dongwei.myapplication;

import java.io.IOException;
import java.io.Serializable;

/**
 * Created by Administrator on 2016/4/10.
 *
 * @author:Administrator
 * @date:2016/4/10
 */
public class SocketUtils implements Serializable{
    private final TCPClient client = new TCPClient();
    public  void connect2Server(final String ip, final int port,final OnConnectedListener listener){
        new Thread(){
            @Override
            public void run() {
                boolean b = false;
                try {
                    b = client.connectServer(ip, port);
                    listener.onConnected(b);
                } catch (IOException e) {
                    listener.onConnected(b);
                    e.printStackTrace();
                }
            }
        }.start();
    }
    public void sendMsg(final String msg, final OnSentListener listener) {
        new Thread(){
            @Override
            public void run() {
                try {
                    if(client.getSocketState()){
                        client.sendMsg(msg);
                        listener.onSent(true);
                    }else{
                        listener.onSent(false);
                    }
                } catch (IOException e) {
                    listener.onSent(false);
                    e.printStackTrace();
                }
            }
        }.start();
    }

    public void closeConnection(OnCloseListener listener){
        try {
            if(client != null&&client.getSocketState()){
                client.close();
                listener.onClose(true);
            }else {
                listener.onClose(false);
            }
        } catch (IOException e) {
            listener.onClose(false);
            e.printStackTrace();
        }
    }
    interface OnConnectedListener{
         void onConnected(boolean state);
    }

    interface OnSentListener{
        void onSent(boolean state);
    }

    interface OnCloseListener{
        void onClose(boolean state);
    }
}
