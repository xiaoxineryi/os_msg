package com.kaito.semaphore;

public class MySemaphore {
    Integer mutex ;
    public MySemaphore(Integer mutex){
        this.mutex = mutex;
    }

    public synchronized void P(){
        mutex --;
        if (mutex <0){
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized void V(){
        mutex ++;
        if (mutex >=0){
            notify();
        }
    }


}
