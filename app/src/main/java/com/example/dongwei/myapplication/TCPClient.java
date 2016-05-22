package com.example.dongwei.myapplication;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.Socket;

/**
 * Created by Administrator on 2016/4/10.
 *
 * @author:Administrator
 * @date:2016/4/10
 */
public class TCPClient implements Serializable{
    Socket socket;
    OutputStream outputStream;
    /**
     * 连接服务器
     * @param ip
     * @param port
     * @return 是否连接成功
     * @throws IOException
     */
    public boolean connectServer(String ip,int port) throws IOException {
        socket = new Socket(ip,port);
        socket.setSoTimeout(2000);
        boolean connected = socket.isConnected();
        if(connected){
            outputStream = socket.getOutputStream();
        }
        return connected;
    }

    public boolean getSocketState(){
        if(socket!=null){
            return socket.isConnected();
        }else{
            return false;
        }
    }

    /**
     * 向服务器发送消息
     * @param msg 发送的内容
     * @throws IOException
     */
    public void sendMsg(String msg) throws IOException {
        outputStream.write(str2bytes(msg));
        outputStream.flush();
    }

    public void close() throws IOException {
            socket.close();
            outputStream.close();
    }

    /**
     * 将字符转化为字节数组
     * @param str
     * @return
     */
    private byte[] str2bytes(String str){
//        str = str + "\r\n";
        return str.getBytes();
    }

    public static void main(String[] args) {
        TCPClient client = new TCPClient();
        try {
            boolean connectServer = client.connectServer("192.168.0.103", 9090);
            if(connectServer){
                client.sendMsg("ledon");
                while(true){

                }
            }else{
                System.out.println("connection cannot be established");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
