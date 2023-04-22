package com.sprta.hanghae992.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class Message {


    String msg;
    int statusCode;
    public Message(String b, int n) {
        this.msg = b;
        this.statusCode = n;
    }


}
