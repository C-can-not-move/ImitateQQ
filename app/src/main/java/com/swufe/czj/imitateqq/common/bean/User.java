package com.swufe.czj.imitateqq.common.bean;

import java.io.Serializable;

/**
 * 用户对象
 */
public class User implements Serializable {
    /**
     * 一个对象序列化的接口
     */
    private static final long serialVersionUID = 1L;

    private int id;// QQ号码
    private String password;// 密码
    private String name;// 昵称
    private String email;// 邮箱

    private int isOnline;// 是否在线
    private int img;// 头像图标
    private int group;// 哪一个分组
    private String ip;
    private int port;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getGroup() {
        return group;
    }

    public void setGroup(int group) {
        this.group = group;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getIsOnline() {
        return isOnline;
    }

    public void setIsOnline(int isOnline) {
        this.isOnline = isOnline;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof User) {//如果是User对象
            User user = (User) o;
            if (user.getId() == this.id && user.getIp().equals(this.ip)
                    && user.getPort() == this.port) {
                return true;
            }
        }

        return false;
    }

    @Override
    public String toString() {//重写object类的toString方法
        return "User [id=" + id + ", name=" + name + ", email=" + email
                + ", password=" + password + ", isOnline=" + isOnline
                + ", img=" + img + ", group=" + group + ", ip=" + ip
                + ", port=" + port + "]";
    }

}
