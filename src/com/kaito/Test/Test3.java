package com.kaito.Test;

public class Test3 {
    public static void main(String[] args) {
        A a = new A();
        a.start();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        B b = new B(a);
        b.start();
    }
}

class B extends Thread{
    Thread a;
    public B(Thread a){
        this.a  = a;
    }

    @Override
    public void run() {
        synchronized (a){
            a.interrupt();
        }
    }
}
class A extends Thread{
    @Override
    public void run() {
        try {
            sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
