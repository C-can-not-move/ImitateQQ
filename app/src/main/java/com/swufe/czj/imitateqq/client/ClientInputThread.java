package com.swufe.czj.imitateqq.client;

import android.util.Log;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.concurrent.Callable;

import com.swufe.czj.imitateqq.common.transportBean.TranObject;

/**
 * 客户端读消息线程
 */
public class ClientInputThread implements Callable<TranObject> {
    private Socket socket;//socket对象
    private TranObject msg;
    private boolean isStart = true;
    private ObjectInputStream ois;


    public ClientInputThread(Socket socket) {
        this.socket = socket;
        try {
            ois = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setStart(boolean isStart) {
        this.isStart = isStart;
    }

    //由于页面跳转不能在这个Java中实现，所以考虑用其他方法
    @Override
    public TranObject call() throws Exception {
        Object readObject = ois.readObject();//从流中读取对象。
        if (readObject != null && readObject instanceof TranObject) {
            try {
                while (isStart) {
                    Log.i("ClientInputThread", "开始接收消息！！！");
                    this.msg = (TranObject) readObject;
                    return this.msg;
                }
                ois.close();
                if (socket != null)
                    socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return this.msg;
    }
}
