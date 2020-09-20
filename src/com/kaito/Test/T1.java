package com.kaito.Test;

import com.kaito.Character.MyThread;
import com.kaito.System.MySystem;
import com.kaito.System.SystemUtil;

public class T1 {
    public static void main(String[] args) {
        SenderThread[] senders = new SenderThread[5];
        for (int i =0;i<5;i++){
            senders[i] = new SenderThread();
            senders[i].start();
        }

        ReceiverThread[] receivers = new ReceiverThread[5];
        for (int i=0;i<5;i++){
            receivers[i] = new ReceiverThread();
            receivers[i].start();
        }

        MySystem.dispatch();
    }
}

class SenderThread extends Thread{
    @Override
    public void run() {
        MyThread myThread = SystemUtil.createMyThread();
        myThread.send(new byte[20],1);
        System.out.println(myThread.getPid()+"开始执行发送");
        try {
            sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class ReceiverThread extends Thread{
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
