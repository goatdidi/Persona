package run;

import bean.CallLogConsumer;
import bean.Consumer;

import java.io.IOException;


public class Bootstrap {
    public static void main(String[] args) throws IOException{
        Consumer consumer = new CallLogConsumer();

        consumer.consumer();
        consumer.close();
    }
}
