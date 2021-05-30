package bean;

import util.DateUtil;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class LocalFileProducer implements Producer{

    private DataInput input;
    private  DataOutput output;

    public volatile boolean flag = true;

    @Override
    public void setInput(DataInput input) {
        this.input = input;
    }

    @Override
    public void setOutput(DataOutput output) {
        this.output = output;
    }

    @Override
    public void produce() {
        try{
            //读取通讯录数据
            List<Contact> contacts = input.read(Contact.class);
            //测试
            System.out.println(contacts);
            while (flag){
                //从通讯录中随机查找2个电话号码
                int index1 = new Random().nextInt(contacts.size());
                int index2;
                while (true){
                    index2 = new Random().nextInt(contacts.size());
                    if (index1 != index2){
                        break;
                    }
                }
                Contact call1 = contacts.get(index1);
                Contact call2 = contacts.get(index2);
                //生成随机的通话时间
                String strstarttime = "20200101000000";
                String strendtime = "20210101000000";
                long starttime = DateUtil.strToDate(strstarttime, "yyyyMMddHHmmss").getTime();
                long endtime = DateUtil.strToDate(strendtime, "yyyyMMddHHmmss").getTime();
                long calltime = starttime +(long)((endtime-starttime)*Math.random());
                //通话时间
                String strcalltime = DateUtil.dateToStr(new Date(calltime),"yyyyMMddHHmmss");
                //生成随机的通话时常
                Integer callcount = new Random().nextInt(3000);
                //生成通话记录
                CallLog calllog = new CallLog(call1.getTel(), call2.getTel(), strcalltime, callcount.toString());
                //System.out.println(calllog);
                //输出通话记录
                output.write(calllog.toString());
                Thread.sleep(500);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void close() throws IOException {
        if (input != null){
            input.close();
        }
        if (output != null){
            output.close();
        }
    }
}
