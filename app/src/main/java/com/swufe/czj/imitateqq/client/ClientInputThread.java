package com.swufe.czj.imitateqq.client;

import android.util.Log;

import com.swufe.czj.imitateqq.common.transportBean.TranObject;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

/**
 * 客户端读消息线程
 */
public class ClientInputThread extends Thread {
    private Socket socket;
    private TranObject msg;
    private boolean isStart = true;
    private ObjectInputStream ois;
    private MessageListener messageListener;// 消息监听接口对象

    public ClientInputThread(Socket socket) {
        this.socket = socket;
        try {
            ois = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 提供给外部的消息监听方法
     *
     * @param messageListener
     *            消息监听接口对象
     */
    public void setMessageListener(MessageListener messageListener) {
        this.messageListener = messageListener;
    }

    public void setStart(boolean isStart) {
        this.isStart = isStart;
    }

    @Override
    public void run() {
        try {
            while (isStart) {
                Log.i("ClientInputThread","读线程接收消息");
                msg = (TranObject) ois.readObject();
                Log.i("ClientInputThread",msg.toString());
                // 每收到一条消息，就调用接口的方法，并传入该消息对象，外部在实现接口的方法时，就可以及时处理传入的消息对象了
                messageListener.Message(msg);
            }
            ois.close();
            if (socket != null)
                socket.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
