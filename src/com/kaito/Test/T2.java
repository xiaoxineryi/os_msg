package com.kaito.Test;

import com.kaito.Character.MyThread;
import com.kaito.System.MySystem;
import com.kaito.System.SystemUtil;

public class T2 {
    public static void main(String[] args) throws InterruptedException {
        SenderThread2 sender = new SenderThread2();
        sender.start();
        Thread.sleep(500);
        ReceiverThread2 receivers = new ReceiverThread2();
        receivers.start();

        MySystem.dispatch();
    }
}
class SenderThread2 extends Thread{
    @Override
    public void run() {
        MyThread myThread = SystemUtil.createMyThread();
        myThread.send("12345".getBytes(),2);
        System.out.println(myThread.getPid()+"开始执行发送");
        try {
            sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class ReceiverThread2 extends Thread{
    @Override
    public void run() {
        MyThread myThread = SystemUtil.createMyThread();
        myThread.receive();
        System.out.println(myThread.getPid()+"开始执行接收");
        try {
            sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
