package bean;

import java.io.Closeable;

public interface Consumer extends Closeable {

    void consumer();
}
