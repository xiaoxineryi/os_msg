package com.kaito.Character;

import com.kaito.Block.MsgBlock;
import com.kaito.System.MySystem;
import com.kaito.semaphore.MySemaphore;

import java.util.LinkedList;

public  class MyThread implements Sender,Receiver{
    private final int MAX_SIZE=512;
    int pid;
    LinkedList<MsgBlock> msgList;
    public MyThread(int pid){
        this.pid = pid;
        msgList = new LinkedList<>();
    }


    public void send(byte[] msg, int r_id){
        try {
            MySystem.putWait(this);
            this.wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        MySystem.send(this,msg,r_id);
    }

    public void receive(){
        MySystem.putWait(this);
        try {
            this.wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        MySystem.receive(this);
    }

}
