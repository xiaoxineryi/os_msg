package com.kaito.Test;

import java.io.PrintStream;

public class Test {
    public static void main(String[] args) {
        new ASystem().start();
        new BSystem().start();
    }
}
class BSystem extends Thread{
    @Override
    public void run() {
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

    public synchronized static void p(){
        System.out.println(2);
    }
}
