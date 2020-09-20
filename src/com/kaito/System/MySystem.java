package com.kaito.System;

import com.kaito.Character.MyThread;
import com.kaito.Character.Receiver;
import com.kaito.Character.Sender;
import com.kaito.semaphore.MySemaphore;

import java.util.ArrayList;
import java.util.List;

public class MySystem {
    static MySemaphore CPU = new MySemaphore(0);

    static private List<MyThread> readyList = new ArrayList<>();
    static private List<MyThread> urgentList = new ArrayList<>();
    public synchronized static void putWait(MyThread myThread){
        readyList.add(myThread);
    }
    public synchronized static void putUrgent(MyThread myThread){urgentList.add(myThread);}
    public static void send(Sender sender, byte[] msg, int r_id) {
        send_block();
        CPU.V();
    }
    private static void send_block(){

    }

    public static void dispatch(){
        System.out.println("CPU开始调度");
        while (true){
            //选出一个线程使之继续执行
            MyThread myThread = pickThread();
            if (myThread != null){
                synchronized (myThread){
                    myThread.notify();
                }
                CPU.P();
            }
            //
        }
    }

    private synchronized static MyThread pickThread(){
        //在这里可以修改选取的策略
        if (urgentList.isEmpty()){
            if (readyList.isEmpty()){
                return null;
            }else{
                return readyList.remove(0);
            }
        }else{
            return urgentList.remove(0);
        }
    }

    public static void receive(MyThread receiver) {
        if (true){
            putUrgent(receiver);
            try {
                //没有完整信息的情况
                CPU.V();
                synchronized (receiver) {
                    receiver.wait();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            CPU.V();
        }else{
            CPU.V();
        }
    }
}
