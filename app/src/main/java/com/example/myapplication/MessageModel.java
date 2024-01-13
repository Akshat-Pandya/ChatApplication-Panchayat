package com.example.myapplication;

public class MessageModel {

   private String message;
   private String time_stamp;
   private String senderid;
   private String receiverid;
    public MessageModel(){
        //Empty Constructor
    }

    public MessageModel(String message, String time_stamp, String senderid, String receiverid) {
        this.message = message;
        this.time_stamp = time_stamp;
        this.senderid = senderid;
        this.receiverid = receiverid;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTime_stamp() {
        return time_stamp;
    }

    public void setTime_stamp(String time_stamp) {
        this.time_stamp = time_stamp;
    }

    public String getSenderid() {
        return senderid;
    }

    public void setSenderid(String senderid) {
        this.senderid = senderid;
    }

    public String getReceiverid() {
        return receiverid;
    }

    public void setReceiverid(String receiverid) {
        this.receiverid = receiverid;
    }
}
