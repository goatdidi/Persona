package run;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class PtuDriver {
    //hdfs 文件服务器地址
    private static String hdfsaddress="";
    static{
        Properties p = new Properties();
        try {
            p.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("hdfs.properties"));
            hdfsaddress = p.getProperty("hdfs.address");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) throws IllegalArgumentException,
            IOException, ClassNotFoundException, InterruptedException {
        System.setProperty("HADOOP_USER_NAME","root");
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf);
        job.setJarByClass(PtuDriver.class);
        job.setMapperClass(PtuMapper.class);
        job.setReducerClass(PtuReducer.class);
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(CallLog.class);
        job.setOutputKeyClass(ResultPojo.class);
        job.setOutputValueClass(NullWritable.class);

        FileInputFormat.setInputPaths(job, new Path(getpath("calllog")));
        FileOutputFormat.setOutputPath(job, new Path(getpath("calllogoutput")));
//设置输出格式的组件
        job.setOutputFormatClass(PtuOutputFormat.class);
        job.waitForCompletion(true);
    }
    /**
     * 获取输入文件路径
     * @return
     */
    private static String getpath(String param){
        String path = "";
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, -1);
        date = cal.getTime();
        path = hdfsaddress+param+"/"+dateToStr(date,"yyyyMMdd");
        return path;
    }
    /**
     * 格式化日期
     * @param date
     * @param format
     * @return
     */
    public static String dateToStr(Date date,String format){
        DateFormat df = new SimpleDateFormat(format);
        return df.format(date);
    }
}
