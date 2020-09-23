package com.kaito.System;

import com.kaito.Block.BufferPool;
import com.kaito.Block.MsgBlock;
import com.kaito.Character.MyThread;
import com.kaito.Character.Receiver;
import com.kaito.Character.Sender;
import com.kaito.semaphore.MySemaphore;

import java.util.*;

public class MySystem {
    private static final int MAX_SIZE=512;
    private static MySemaphore CPU = new MySemaphore(0);
    //根据PID来选择对应的线程的表
    private static Map<Integer,MyThread> threadsMap = new HashMap<>();
    // 自定义线程池大小
    private static BufferPool bufferPool = new BufferPool(50);

    static private List<MyThread> readyList = new ArrayList<>();
    static private List<MyThread> urgentList = new ArrayList<>();
    public synchronized static void putWait(MyThread myThread){
        readyList.add(myThread);
    }
    public synchronized static void putUrgent(MyThread myThread){urgentList.add(myThread);}
    public static void send(int s_id, byte[] msg, int r_id) {
        send_block(s_id,msg,r_id);
        CPU.V();
    }
    private static void send_block(int s_id, byte[] msg, int r_id){
        int size_total = msg.length;
        int msg_total = size_total/MAX_SIZE;
        //判断是正好分配还是有剩余
        boolean left = (size_total%MAX_SIZE)!=0;
        msg_total = left?msg_total+1:msg_total;

        for (int i=0;i<msg_total;i++){
            MsgBlock msgBlock = bufferPool.get();
            int st = i*MAX_SIZE;
            int size = (i+1)*MAX_SIZE<size_total?MAX_SIZE:size_total-i*MAX_SIZE;
            // 设置好消息块
            msgBlock.set(s_id,msg_total,i,size);
            msgBlock.copyMsg(msg,st,size);

            // 发送消息
            MyThread receiver = threadsMap.get(r_id);
            receiver.getMsgList().add(msgBlock);

            //测试一下
            System.out.println(s_id+"发送消息"+new String(msgBlock.getMsg()));
        }

    }

    public static void dispatch(){
        System.out.println("CPU开始调度");
        while (true){
            //选出一个线程使之继续执行
            MyThread myThread = pickThread();
            if (myThread != null){
                synchronized (myThread){
                    System.out.println("CPU调度选取"+myThread.getPid()+"并且唤醒");
                    myThread.notify();
                }

                CPU.P();
            }
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

    public static void receive(int r_id) {
        MyThread receiver = threadsMap.get(r_id);
        int s_id = 0;
        // -1表示都没有满足的，就放入紧急等待队列
        while ((s_id = getEnoughMsg(receiver)) == -1){
            //没有完整信息的情况  放入紧急等待队列 让CPU执行
            putUrgent(receiver);
            try {
                CPU.V();
                synchronized (receiver) {
                    receiver.wait();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        //如果凑齐了的话
        Map<Integer, List<MsgBlock>> receiver_map = receiver.getMaps();
        List<MsgBlock> msgList = receiver_map.get(s_id);
        receiver_map.put(s_id,new LinkedList<MsgBlock>());

        byte[] bytes = getMsg(msgList);
        System.out.println(r_id+"接收到消息"+new String(bytes));
        CPU.V();

    }

    private static byte[] getMsg(List<MsgBlock> msgBlockList){
        int size = 0;
        for(MsgBlock msgBlock:msgBlockList){
            size += msgBlock.getSize();
        }
        byte[] bytes = new byte[size];
        for (MsgBlock msgBlock:msgBlockList){
            System.arraycopy(msgBlock.getMsg(),0,bytes,
                    msgBlock.getMsg_number()*MAX_SIZE,msgBlock.getSize());
        }
        return bytes;
    }

    private static int getEnoughMsg(MyThread receiver){
        // 获取接收者的消息队列
        List<MsgBlock> msgBlockList = receiver.getMsgList();
        // 获取接收者已经接收到的但是没有接收完全的map
        Map<Integer, List<MsgBlock>> receive_map = receiver.getMaps();
        for (MsgBlock msgBlock:msgBlockList){
            // 将所有消息队列接收，放到map中
            if(! receive_map.containsKey(msgBlock.getPid())){
                receive_map.put(msgBlock.getPid(),new LinkedList<MsgBlock>() );
            }
            receive_map.get(msgBlock.getPid()).add(msgBlock);
        }

        Set<Integer> s = receive_map.keySet();
        for (Integer i :s) {
            List s_msgList = receive_map.get(i);
            boolean flag = judge(s_msgList);
            if(flag){
                return i;
            }
        }
        return -1;
    }

    private static boolean judge(List<MsgBlock> msgList){
        // 判断有没有满的
        if(msgList.isEmpty()){
            return false;
        }else{
            int total = msgList.size();
            System.out.println("the msgList size is"+msgList.size());
            if(total == msgList.get(0).getMsg_total()){
                return true;
            }else{
                return false;
            }
        }
    }

    //提供一个可以添加线程表的方法
    public static void addMap(int pid,MyThread myThread){
        threadsMap.put(pid,myThread);
    }
}
