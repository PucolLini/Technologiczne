package org.example;

import java.io.Serializable;

public class Message implements Serializable {
    //protokol komunikacji (reguly i format wiadomosci)
    private int number;
    private String content;

    Message(int number, String content){
        this.number = number;
        this.content = content;
    }

    //set and get
    public void setNumber(int number){
        this.number=number;
    }
    public void setContent(String content){
        this.content=content;
    }

    public int getNumber(){
        return number;
    }
    public String getContent(){
        return content;
    }
}
