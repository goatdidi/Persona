package io;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;
import bean.DataOutput;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import util.DateUtil;


public class HDFSOutput implements DataOutput {
    private FileSystem fs = null;

    public HDFSOutput(String path){
        setPath(path);
    }
    @Override
    public void close() throws IOException{
        if (fs != null){
            fs.close();
        }
    }

    public void setPath(String path){
        Configuration conf = new Configuration();
        //设置可追加内容123
        conf.setBoolean("dfs.support.append", true);
        conf.set("fs.defaultFS", "hdfs://hadoop1:9000");
        try {
            fs = FileSystem.get(new URI(path), conf, "root");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    private void mkdir(String dirname) {
        try {
            //目录
            Path path = new Path(dirname);
            //文件路径
            Path filepath = new Path(dirname+"/call.log");
            if (!fs.exists(path)) {
                fs.mkdirs(path);// 创建目录
            }
            if(!fs.exists(filepath)){
                fs.create(filepath);//创建文件
                fs.close();
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void write(Object data) throws Exception {
    }
    @Override
    public void write(String data) throws Exception {
    // 初始化目录路径(保存到hdfs 中的文件目录:/calllog/20200519)
        String dirname = "/calllog/" + DateUtil.dateToStr(new Date(),"yyyyMMdd");
    //创建目录(目录和文件都有了)
        mkdir(dirname);
    //创建输入流
        ByteArrayInputStream bin = new
                ByteArrayInputStream(createdata(data).getBytes());
    //创建输出流
        OutputStream os = fs.append(new Path(dirname+"/call.log"));
    //流对拷
        IOUtils.copyBytes(bin,os,data.getBytes().length,true);
        IOUtils.closeStream(bin);
        IOUtils.closeStream(os);
    }
    /**
     * 根据通信日志，转换成两条通信记录
     * 原始数据：13511111111 13522222222 20200117104440 300
     * 输出数据：
     * 13511111111 13522222222 20200117104440 300 1
     * 13522222222 13511111111 20200117104440 300 2
     * @param data
     * @return
     */
    private String createdata(String data){
        String rtn = "";
        String[] strs = data.split("\t");
        if(strs.length == 4){
            rtn = data + "\t1\n";
    //交换两个两个电话号码的位置
            String tmp = strs[0];
            strs[0] = strs[1];
            strs[1] = tmp;
    //组合第二条记录
            rtn += strs[0] + "\t" +strs[1] + "\t" + strs[2] + "\t" +
                    strs[3]+"\t2\n";
        }
        return rtn;
    }
}
