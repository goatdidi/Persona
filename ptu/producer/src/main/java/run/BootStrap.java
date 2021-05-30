package run;

import bean.LocalFileProducer;
import bean.Producer;
import io.LocalFileDataInput;
import io.LocalFileDataOutput;

import java.io.IOException;

public class BootStrap {
    /**
     * @param args：
     * args[0]:输入数据路径。输入数据格式为[电话号码联系人姓名]
     * args[1]:输出数据路径。输出数据格式为[主叫电话被叫电话通话时间
    通话时长]
     * @throws IOException
     */

    public static void main(String[] args) throws IOException{

        Producer producer = new LocalFileProducer();
        producer.setInput(new LocalFileDataInput(args[0]));
        producer.setOutput(new LocalFileDataOutput(args[1]));
//生产
        producer.produce();
//关闭
        producer.close();
    }
}
