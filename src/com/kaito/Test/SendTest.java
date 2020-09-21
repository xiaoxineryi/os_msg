package com.kaito.Test;

import java.io.*;

public class SendTest {
    public static void main(String[] args) throws IOException {
        byte[] s = "123".getBytes();
        System.out.println(s.length);
        System.out.println(s);
        String sp = new String(s);
        System.out.println(sp);

        Reader reader = new FileReader("src/A.txt");
        BufferedReader bufferedReader = new BufferedReader(reader);
        String ss = bufferedReader.readLine();
        byte[]  m =ss.getBytes();
        byte[]  c = new byte[512];
        System.arraycopy(m,0,c,0,512);
        System.out.println(c.length);
        System.out.println(new String(c));
        System.out.println(m.length);
        System.out.println(ss);
    }
}
