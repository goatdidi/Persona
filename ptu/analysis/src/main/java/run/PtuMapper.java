package run;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * mapper 对象
 * LongWritable :一行数据的偏移量
 Text :读取的一行数据13511111111 13522222222 20200117104440
 300 1
 Text :输出key：主叫电话
 run.CallLog :输出value：通话记录
 * @author 张旺
 */

public class PtuMapper extends Mapper<LongWritable, Text,Text ,CallLog> {
    Text k = new Text();
    CallLog v = new CallLog();
    @Override
    protected void map(LongWritable key, Text value, Mapper<LongWritable,
            Text, Text, CallLog>.Context context)
            throws IOException, InterruptedException {
            String line = value.toString();
            String[] strs = line.split("\t");
            if(strs.length == 5){
                k.set(strs[0]);
                v.set(strs[0], strs[1], strs[2], Integer.parseInt(strs[3]), Integer.parseInt(strs[4]));
                context.write(k, v);
        }
    }
}
