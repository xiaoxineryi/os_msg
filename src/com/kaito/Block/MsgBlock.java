package com.kaito.Block;

public class MsgBlock {
    int pid;
    int msg_total;
    int msg_number;
    int size;
    byte[] msg;

    public MsgBlock(){
        //新建的时候相当于是新建缓冲块，后面添加其他内容相当于是提取
        msg = new byte[512];
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public void setMsg_total(int msg_total) {
        this.msg_total = msg_total;
    }

    public void setMsg_number(int msg_number) {
        this.msg_number = msg_number;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setMsg(byte[] msg) {
        this.msg = msg;
    }

    public int getPid() {
        return pid;
    }

    public int getMsg_total() {
        return msg_total;
    }

    public int getMsg_number() {
        return msg_number;
    }

    public int getSize() {
        return size;
    }

    public byte[] getMsg() {
        return msg;
    }
}
