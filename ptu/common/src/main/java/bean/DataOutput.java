package bean;

import java.io.Closeable;

public interface DataOutput extends Closeable {

    void setPath(String path);

    void write(String data) throws Exception;

    void write(Object data) throws Exception;

}
