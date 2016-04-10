package com.example.dongwei.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.IOException;

/**
 * Created by Administrator on 2016/4/10.
 *
 * @author:Administrator
 * @date:2016/4/10
 */
public class TestActivity extends AppCompatActivity implements View.OnClickListener{
    Button sendBtn,closeBtn,openBtn;
    TCPClient client;
    private static final String TAG = "TestActivity";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_layout);
        openBtn = (Button) findViewById(R.id.openConn);
        sendBtn = (Button) findViewById(R.id.sendBtn);
        closeBtn = (Button) findViewById(R.id.closeBtn);
        openBtn.setOnClickListener(this);
        sendBtn.setOnClickListener(this);
        closeBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.openConn:
                //
                Log.d(TAG, "open");
                new Thread(){
                    @Override
                    public void run() {
                        boolean isConnect = openConnection();
                        Log.d(TAG,"open:"+isConnect);
                    }
                }.start();
                break;
            case R.id.sendBtn:
                //
                Log.d(TAG,"send");
                new Thread(){
                    @Override
                    public void run() {
                        sendMsg("helloworld");
                    }
                }.start();
                break;
            case R.id.closeBtn:
                //
                Log.d(TAG, "close");
                new Thread(){
                    @Override
                    public void run() {
                        closeConnection();
                    }
                }.start();
                break;
            default:
                break;
        }

    }

    private boolean openConnection(){
        client = new TCPClient();
        String ip = "192.168.1.101";
        int port  = 9000;
        try {
            boolean connectServer = client.connectServer(ip, port);
            return connectServer;
        } catch (IOException e) {
            e.printStackTrace();
            Log.d(TAG, "connect error");
            return false;
        }
    }

    private void sendMsg(String msg){
        try {
            client.sendMsg(msg);
        } catch (IOException e) {
            e.printStackTrace();
            Log.d(TAG,"send error");
        }
    }

    private void closeConnection(){
        try {
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
            Log.d(TAG, "close error");
        }
    }
}
