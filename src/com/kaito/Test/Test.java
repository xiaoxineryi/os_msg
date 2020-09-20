package com.kaito.Test;

import java.io.PrintStream;

public class Test {
    public static void main(String[] args) {
        new ASystem().start();
        ASystem.p();
    }
}

class ASystem extends Thread{
    @Override
    public void run() {
        m();
    }

    public static void m(){
        int i = 0;
        while (true){
            i++;
            System.out.println("1");
            if (i > 5000){
                break;
            }
        }
    }

    public synchronized static  void p(){
        try {
            sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(2);
    }
}
