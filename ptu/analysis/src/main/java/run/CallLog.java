package run;

import org.apache.hadoop.io.Writable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class CallLog implements Writable {
    //主叫电话
    private String calling;
    //被叫电话
    private String called;
    //通话时间
    private String calltime;
    //通话时长
    private int calllen;
    //标记（1-主叫；2-被叫）
    private int flag;
    public String getCalling() {
        return calling;
    }
    public void setCalling(String calling) {
        this.calling = calling;
    }
    public String getCalled() {
        return called;
    }
    public void setCalled(String called) {
        this.called = called;
    }
    public String getCalltime() {
        return calltime;
    }
    public void setCalltime(String calltime) {
        this.calltime = calltime;
    }
    public int getCalllen() {
        return calllen;
    }
    public void setCalllen(int calllen) {
        this.calllen = calllen;
    }
    public int getFlag() {
        return flag;
    }
    public void setFlag(int flag) {
        this.flag = flag;
    }
    public CallLog(){
    }
    public void set(String calling, String called, String calltime, int
            calllen, int flag){
        this.calling = calling;
        this.called = called;
        this.calltime = calltime;
        this.calllen = calllen;
        this.flag = flag;
    }
    @Override
    public void write(DataOutput out) throws IOException {
        out.writeUTF(this.calling);
        out.writeUTF(this.called);
        out.writeUTF(this.calltime);
        out.writeInt(this.calllen);
        out.writeInt(this.flag);
    }
    @Override
    public void readFields(DataInput in) throws IOException {
        this.calling = in.readUTF();
        this.called = in.readUTF();
        this.calltime = in.readUTF();
        this.calllen = in.readInt();
        this.flag = in.readInt();
    }
}
