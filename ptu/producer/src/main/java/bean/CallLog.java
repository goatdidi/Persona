package bean;

public class CallLog {
    private String tel1;
    private String tel2;
    private String calltime;
    private String callcount;

    public void setTel1(String tel1) {
        this.tel1 = tel1;
    }

    public void setTel2(String tel2) {
        this.tel2 = tel2;
    }

    public void setCalltime(String calltime) {
        this.calltime = calltime;
    }

    public void setCallcount(String callcount) {
        this.callcount = callcount;
    }

    public String getTel1() {
        return tel1;
    }

    public String getTel2() {
        return tel2;
    }

    public String getCalltime() {
        return calltime;
    }

    public String getCallcount() {
        return callcount;
    }

    public CallLog(String tel1, String tel2, String calltime, String callcount) {
        this.tel1 = tel1;
        this.tel2 = tel2;
        this.calltime = calltime;
        this.callcount = callcount;
    }
    @Override
    public String toString(){
        return tel1 + "\t" + tel2 + "\t" + calltime + "\t" + callcount;
    }
}
