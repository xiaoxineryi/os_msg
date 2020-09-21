package com.kaito.Character;

import com.kaito.Block.MsgBlock;
import com.kaito.System.MySystem;
import com.kaito.semaphore.MySemaphore;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public  class MyThread implements Sender,Receiver{
    int pid;
    LinkedList<MsgBlock> msgList;
    Map<Integer, List<MsgBlock>> maps;
    public MyThread(int pid){
        this.pid = pid;
        msgList = new LinkedList<>();
        maps = new HashMap<>();
    }

    public LinkedList<MsgBlock> getMsgList() {
        return msgList;
    }

    public void setMsgList(LinkedList<MsgBlock> msgList) {
        this.msgList = msgList;
    }

    public Map<Integer, List<MsgBlock>> getMaps() {
        return maps;
    }

    public void setMaps(Map<Integer, List<MsgBlock>> maps) {
        this.maps = maps;
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
        MySystem.send(this.pid,msg,r_id);
        System.out.println(this.getPid()+"发送进程执行完成");
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
        System.out.println(this.getPid()+"接收进程继续执行");
        MySystem.receive(this.pid);
        System.out.println(this.getPid()+"接收进程执行完成");
    }

}
