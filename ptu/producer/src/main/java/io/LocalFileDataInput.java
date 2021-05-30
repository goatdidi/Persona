package io;

import bean.Data;
import bean.DataInput;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class LocalFileDataInput implements DataInput {

    //读取文件字符流
    private BufferedReader reader = null;

    public LocalFileDataInput(String path){
        setPath(path);
    }

    @Override
    public void setPath(String path) {
        try{
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(path),"UTF-8"));
        }catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }
    }

    @Override
    public Object read() throws IOException {
        return null;
    }

    @Override
    public <T extends Data> List<T> read(Class<T> clazz) throws IOException {
        List<T> list = new ArrayList<T>();
        try{
            String line = null;
            while ((line = reader.readLine()) != null){
                T t = clazz.newInstance();
                t.setValue(line);
                list.add(t);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public void close() throws IOException {
        if (reader != null){
            reader.close();
        }
    }
}
