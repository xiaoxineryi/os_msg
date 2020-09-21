package com.kaito.System;

import com.kaito.Character.MyThread;
import com.kaito.Character.Receiver;
import com.kaito.Character.Sender;

public class SystemUtil {
    private static Integer temp_num = 0;
    public static synchronized MyThread createMyThread(){
        MyThread myThread= new MyThread(++temp_num);
        MySystem.addMap(temp_num,myThread);
        return myThread;
    }
}
