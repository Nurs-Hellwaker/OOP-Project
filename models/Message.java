package models;

import java.io.Serializable;
import java.util.Date;
//import models.Employee

public class Message implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private Employee sender;
    private Employee receiver;
    private String content;
    private Date sentDate;

    public Message(Employee sender, Employee receiver, String content) {
        this.sender = sender;
        this.receiver = receiver;
        this.content = content;
        this.sentDate = new Date();
    }

    public Employee getSender(){
        return sender;
    }

    public Employee getReceiver(){
        return receiver;
    }

    public String getContent(){
        return content;
    }

    public Date getDate(){
        return sentDate;
    }
    @Override
    public String toString() {
        return "[" + sentDate + "] From: " + sender.getName() + " - " + content;
    }
}
