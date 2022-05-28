package com.swufe.czj.imitateqq.client;

import com.swufe.czj.imitateqq.common.transportBean.TranObject;

/**
 * 消息监听接口
 */
public interface MessageListener {
    public void Message(TranObject msg);
}
