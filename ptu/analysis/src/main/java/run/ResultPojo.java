package run;

import org.apache.hadoop.io.Writable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class ResultPojo implements Writable {
    //电话号码
    private String telnum;
    //年份
    private int year;
    //月份
    private int month;
    //日期
    private int day;
    //通话总次数
    private int telcount;
    //主叫次数
    private int telcallingcount;
    //被叫次数
    private int telcalledcount;
    //通话总时长
    private int telsum;
    //主叫时长
    private int telcallingsum;
    //被叫时长
    private int telcalledsum;
    public String getTelnum() {
        return telnum;
    }
    public void setTelnum(String telnum) {
        this.telnum = telnum;
    }
    public int getYear() {
        return year;
    }
    public void setYear(int year) {
        this.year = year;
    }
    public int getMonth() {
        return month;
    }
    public void setMonth(int month) {
        this.month = month;
    }
    public int getDay() {
        return day;
    }
    public void setDay(int day) {
        this.day = day;
    }
    public int getTelcount() {
        return telcount;
    }
    public void setTelcount(int telcount) {
        this.telcount = telcount;
    }
    public int getTelcallingcount() {
        return telcallingcount;
    }
    public void setTelcallingcount(int telcallingcount) {
        this.telcallingcount = telcallingcount;
    }
    public int getTelcalledcount() {
        return telcalledcount;
    }
    public void setTelcalledcount(int telcalledcount) {
        this.telcalledcount = telcalledcount;
    }
    public int getTelsum() {
        return telsum;
    }
    public void setTelsum(int telsum) {
        this.telsum = telsum;
    }
    public int getTelcallingsum() {
        return telcallingsum;
    }
    public void setTelcallingsum(int telcallingsum) {
        this.telcallingsum = telcallingsum;
    }
    public int getTelcalledsum() {
        return telcalledsum;
    }
    public void setTelcalledsum(int telcalledsum) {
        this.telcalledsum = telcalledsum;
    }
    public void set(String telnum, int year, int month, int day, int telcount,
                    int telcallingcount,
                    int telcalledcount, int telsum, int telcallingsum, int
                            telcalledsum) {
        this.telnum = telnum;
        this.year = year;
        this.month = month;
        this.day = day;
        this.telcount = telcount;
        this.telcallingcount = telcallingcount;
        this.telcalledcount = telcalledcount;
        this.telsum = telsum;
        this.telcallingsum = telcallingsum;
        this.telcalledsum = telcalledsum;
    }
    public ResultPojo(){
    }
    @Override
    public void write(DataOutput out) throws IOException {
        out.writeChars(this.telnum);
        out.writeInt(this.year);
        out.writeInt(this.month);
        out.writeInt(this.day);
        out.writeInt(this.telcount);
        out.writeInt(this.telcallingcount);
        out.writeInt(this.telcalledcount);
        out.writeInt(this.telsum);
        out.writeInt(this.telcallingsum);
        out.writeInt(this.telcalledsum);
    }
    @Override
    public void readFields(DataInput in) throws IOException {
        this.telnum = in.readUTF();
        this.year = in.readInt();
        this.month = in.readInt();
        this.day = in.readInt();
        this.telcount = in.readInt();
        this.telcallingcount = in.readInt();
        this.telcalledcount = in.readInt();
        this.telsum = in.readInt();
        this.telcallingsum = in.readInt();
        this.telcalledsum = in.readInt();
    }
}
