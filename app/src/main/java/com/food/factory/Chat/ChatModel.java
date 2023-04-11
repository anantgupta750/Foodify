package com.food.factory.Chat;

public class ChatModel {
    String user,email,time;
    public static final int
            SEND_MESSAGE = 0,

    RECEIVE_MSG = 1;
//            SEND_IMAGE = 2,
//            SEND_PDF = 4,
//            RECIEVE_PDF = 5,
//            RECEIVE_IMAGE = 3;
    public int type;
    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ChatModel(String msg, String user, String time, int type) {
        this.email = msg;
        this.user = user;
        this.time =time;
        this.type = type;

    }

}
