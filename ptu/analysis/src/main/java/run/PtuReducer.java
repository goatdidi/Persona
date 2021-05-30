package run;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;


/**
 * reduce 对象
 * Text:输入的电话号码
 * run.CallLog：输入的通话记录
 * run.ResultPojo：输出的key，为分析后的结果
 * NullWritable：输出的value，为null
 * @author 张旺
 *
 */

public class PtuReducer extends Reducer<Text,CallLog,ResultPojo, NullWritable> {
    private ResultPojo k = new ResultPojo();
    private int year;
    private int month;
    private int day;
    /**
     *在setup 中初始化年月日数据。这个数据一次执行是一致的，所以在这里统一初始化
     */
    @Override
    protected void setup(Reducer<Text, CallLog, ResultPojo, NullWritable>.Context context)
            throws IOException, InterruptedException {
// //初始化日期数据
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, -1);
        year = cal.get(Calendar.YEAR);
//1 月从0 开始，不符合我们的使用习惯，所以+1
        month = cal.get(Calendar.MONTH)+1;
        day = cal.get(Calendar.DAY_OF_MONTH);
        super.setup(context);
    }
    protected void reduce(Text key, Iterable<CallLog> values,
                          Reducer<Text, CallLog, ResultPojo, NullWritable>.Context
                                  context) throws IOException, InterruptedException {
        int telcount = 0;
        int telcallingcount = 0;
        int telcalledcount = 0;
        int telsum = 0;
        int telcallingsum = 0;
        int telcalledsum = 0;
        for (CallLog log : values) {
//总通话时间
            telcount += 1; //总通话次数
            telsum += log.getCalllen();//通话时长
            if (log.getFlag() == 1) {
//主叫
                telcallingcount += 1;
                telcallingsum += log.getCalllen();
            } else {
//被叫
                telcalledcount += 1;
                telcalledsum += log.getCalllen();
            }
        }
        k.set(key.toString(), year, month, day, telcount, telcallingcount, telcalledcount, telsum, telcallingsum, telcalledsum);
        context.write(k, NullWritable.get());
    }
}
