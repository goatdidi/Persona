package io;

import bean.DataOutput;

import java.io.*;

public class LocalFileDataOutput implements DataOutput {

    private PrintWriter writer = null;

    public LocalFileDataOutput(String path){
        setPath(path);
    }

    @Override
    public void setPath(String path) {
        try {
            writer = new PrintWriter(new OutputStreamWriter(new FileOutputStream(path),"UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void write(String data) throws Exception {
        writer.println(data);
        writer.flush();
    }

    @Override
    public void write(Object data) throws Exception {
        write(data.toString());
    }

    @Override
    public void close() throws IOException {
        if (writer != null){
            writer.close();
        }
    }
}
