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

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public void send(byte[] msg, int r_id){
        try {
            MySystem.putWait(this);
            synchronized (this){
                System.out.println(this.pid+"发送进程进入等待队列");
                this.wait();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(this.pid+"发送进程继续执行");
        MySystem.send(this,msg,r_id);
    }

    public void receive(){
        MySystem.putWait(this);
        try {
            synchronized (this){
                System.out.println(this.pid+"接收进程进入等待队列");
                this.wait();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(this.pid+"接收进程继续执行");
        MySystem.receive(this);
    }

}
