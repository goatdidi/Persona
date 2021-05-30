package bean;


import io.HDFSOutput;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import java.io.IOException;
import java.util.Arrays;
import java.util.Properties;

public class CallLogConsumer implements Consumer {

    KafkaConsumer<String,String> consumer;

    @Override
    public void consumer(){
        try {
            Properties prop = new Properties();
            prop.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("consumer.properties"));

            consumer = new KafkaConsumer<>(prop);

            consumer.subscribe(Arrays.asList("calllog"));

            //创建HDFSOutput对象
            Properties prop1 = new Properties();
            prop1.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("hdfsserver.properties"));
            HDFSOutput out = new HDFSOutput(prop1.getProperty("hdfsserver"));

            while (true){
                ConsumerRecords<String,String> records = consumer.poll(100);
                for (ConsumerRecord<String,String> record :records){
                    System.out.println(record.value());
                    //保存到HDFS
                    if (record.value()!=null && !record.value().equals("")){
                        out.write(record.value());
                    }
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void close() throws IOException{
        if (consumer != null){
            consumer.close();
        }
    }

}
