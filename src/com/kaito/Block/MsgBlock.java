package com.kaito.Block;

public class MsgBlock {
    Integer pid;
    Integer msg_total;
    Integer msg_number;
    Integer size;
    byte[] msg;

    public MsgBlock(){
        //新建的时候相当于是新建缓冲块，后面添加其他内容相当于是提取
        msg = new byte[512];
    }
    public void release() {
        pid = null;
        msg_total = null;
        msg_number = null;
        size = null;
        msg = new byte[512];
    }

    public void set(int pid,int msg_total,int msg_number,int size){
        this.pid = pid;
        this.msg_total = msg_total;
        this.msg_number = msg_number;
        this.size = size;
    }

    public void copyMsg(byte[] msg,int st,int size){
        System.arraycopy(msg,st,this.msg,0,size);
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
