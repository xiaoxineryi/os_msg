package com.kaito.Block;

import com.kaito.semaphore.MySemaphore;

import java.util.LinkedList;
import java.util.List;


public class BufferPool {
    MySemaphore free_total;
    LinkedList<MsgBlock> msgBlockList;

    public BufferPool(int size){
        free_total = new MySemaphore(size);
        msgBlockList = new LinkedList<>();

        for(int i =0;i<size;i++){
            msgBlockList.add(new MsgBlock());
        }
    }

    public synchronized MsgBlock get(){
        free_total.P();
        return msgBlockList.removeFirst();
    }

    public synchronized void release(MsgBlock msgBlock){
        msgBlock.release();
        msgBlockList.add(msgBlock);
        free_total.V();
    }

}
