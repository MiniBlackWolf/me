package com.example.home.common;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.example.home.data.UserInfoData;

public class Msg implements MultiItemEntity {
    public static final int TYPE_RECEIVED = 0;// 接收消息
    public static final int TYPE_SENT = 1;// 发送消息
    private Object content;
    private int type;
    private int datatype;
    private UserInfoData UserInfoData;

    //datatype 1:文本 2:图片 3：语音 4：文件
    public Msg(Object content, int type, int datatype, UserInfoData UserInfoData) {
        this.content = content;
        this.type = type;
        this.datatype = datatype;
        this.UserInfoData = UserInfoData;
    }

    public com.example.home.data.UserInfoData getUserInfoData() {
        return UserInfoData;
    }

    public void setUserInfoData(com.example.home.data.UserInfoData userInfoData) {
        UserInfoData = userInfoData;
    }

    public static int getTypeReceived() {
        return TYPE_RECEIVED;
    }

    public static int getTypeSent() {
        return TYPE_SENT;
    }

    public int getDatatype() {
        return datatype;
    }

    public void setDatatype(int datatype) {
        this.datatype = datatype;
    }

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public int getItemType() {
        return type;
    }
}