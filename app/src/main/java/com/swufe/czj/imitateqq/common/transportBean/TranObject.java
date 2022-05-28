package com.swufe.czj.imitateqq.common.transportBean;

import java.io.Serializable;
import java.util.List;

/**
 * 传输的对象,直接通过Socket传输的最大对象
 */
public class TranObject<T> implements Serializable {


    private static final long serialVersionUID = 1L;

    private TranObjectType type;// 发送的消息类型 理解为协议

    private int fromUser;// 来自哪个用户 QQ号是用int定义的。
    private int toUser;// 发往哪个用户

    private T object;// 传输的对象，这个对象为模板类
    private List<Integer> group;// 群发给哪些用户

    public TranObject(TranObjectType type) {
        this.type = type;
    }

    public int getFromUser() {
        return fromUser;
    }

    public void setFromUser(int fromUser) {
        this.fromUser = fromUser;
    }

    public int getToUser() {
        return toUser;
    }

    public void setToUser(int toUser) {
        this.toUser = toUser;
    }

    public T getObject() {
        return object;
    }

    public void setObject(T object) {
        this.object = object;
    }

    public TranObjectType getType() {
        return type;
    }

    public List<Integer> getGroup() {
        return group;
    }

    public void setGroup(List<Integer> group) {
        this.group = group;
    }

    @Override
    public String toString() {
        return "TranObject [type=" + type + ", fromUser=" + fromUser
                + ", toUser=" + toUser + ", object=" + object + "]";
    }
}

