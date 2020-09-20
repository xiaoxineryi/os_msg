package com.kaito.System;

import com.kaito.Character.MyThread;
import com.kaito.Character.Receiver;
import com.kaito.Character.Sender;

import java.util.ArrayList;
import java.util.List;

public class MySystem {
    static private List<MyThread> readyList = new ArrayList<>();
    static private List<MyThread> urgentList = new ArrayList<>();
    public static void putWait(MyThread myThread){
        readyList.add(myThread);
    }

    public static void send(Sender sender, byte[] msg, int r_id) {

    }

    public static void dispatch(){
        while (true){

        }
    }

    public static void receive(Receiver receiver) {
    }
}
